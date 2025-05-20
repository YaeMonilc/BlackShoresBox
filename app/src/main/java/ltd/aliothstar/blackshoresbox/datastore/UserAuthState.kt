package ltd.aliothstar.blackshoresbox.datastore

import androidx.datastore.core.Serializer
import jakarta.inject.Qualifier
import java.io.InputStream
import java.io.OutputStream

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserAuthStateDatastore

class UserAuthStateSerializer : Serializer<UserAuthState> {
    override val defaultValue: UserAuthState
        get() = UserAuthState
            .newBuilder()
            .apply {
                token = ""
                userId = -1
                isLoggedIn = false
            }.build()

    override suspend fun readFrom(input: InputStream): UserAuthState =
        UserAuthState.parseFrom(input)

    override suspend fun writeTo(
        t: UserAuthState,
        output: OutputStream
    ) = t.writeTo(output)
}