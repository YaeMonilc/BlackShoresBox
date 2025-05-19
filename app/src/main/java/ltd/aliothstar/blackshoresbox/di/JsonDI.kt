package ltd.aliothstar.blackshoresbox.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@InstallIn(SingletonComponent::class)
@Module
class JsonDI {
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}