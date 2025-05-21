package ltd.aliothstar.blackshoresbox.di

import android.content.Context
import android.os.Build
import androidx.room.util.copy
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import ltd.aliothstar.blackshoresbox.network.KURO_API_BASE_URL
import ltd.aliothstar.blackshoresbox.network.KuroApi
import ltd.aliothstar.blackshoresbox.network.KuroApiInterface
import ltd.aliothstar.blackshoresbox.network.KuroBaseResponse
import ltd.aliothstar.blackshoresbox.repository.UserAuthStateRepository
import ltd.aliothstar.blackshoresbox.util.nonStandardKuroResponseProcess
import ltd.aliothstar.blackshoresbox.util.randomText
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

@InstallIn(SingletonComponent::class)
@Module
class NetworkDI {

    @KuroApi
    @Provides
    fun provideKuroApi(
        @ApplicationContext
        context: Context,
        json: Json,
        userAuthStateRepository: UserAuthStateRepository
    ): KuroApiInterface =
        Retrofit.Builder().apply {
            baseUrl(KURO_API_BASE_URL)
            client(OkHttpClient.Builder().apply {
                addInterceptor { chain ->
                    chain.proceed(
                        chain
                            .request()
                            .newBuilder().apply {
                                header("devCode", randomText(32, true))
                                header("source", "Android")
                                header("model", Build.MODEL)
                                header(
                                    name = "lang",
                                    value = context.resources.configuration.locales[0].toLanguageTag()
                                )
                            }.build()
                    ).let { originResponse ->
                        originResponse.body?.let { body ->
                            body.string().nonStandardKuroResponseProcess().let { string ->
                                json.decodeFromString<KuroBaseResponse<JsonElement>>(string).let { response ->
                                    if (response.code != KuroBaseResponse.Code.SUCCESS) {
                                        if (response.code == KuroBaseResponse.Code.TOKEN_EXPIRED) {
                                            runBlocking {
                                                userAuthStateRepository.setAuthState {
                                                    token = ""
                                                    userId = 0
                                                    isLoggedIn = false
                                                }
                                            }
                                        }

                                        KuroBaseResponse.ErrorResponse.fromCode(response.code)?.let { exception ->
                                            throw exception.apply {
                                                this.body = string
                                            }
                                        }
                                    }
                                }

                                originResponse.newBuilder().apply {
                                    body(string.toResponseBody(body.contentType()))
                                }.build()
                            }
                        } ?: originResponse
                    }
                }
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build())

            addConverterFactory(
                json.asConverterFactory(
                    contentType = "application/json; charset=UTF8".toMediaType()
                )
            )
        }.build().create<KuroApiInterface>()
}