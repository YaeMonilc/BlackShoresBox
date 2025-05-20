package ltd.aliothstar.blackshoresbox.repository.impl

import androidx.datastore.core.DataStore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import ltd.aliothstar.blackshoresbox.datastore.UserAuthState
import ltd.aliothstar.blackshoresbox.datastore.UserAuthStateDatastore
import ltd.aliothstar.blackshoresbox.repository.UserAuthStateRepository

class UserAuthStateRepositoryImpl @Inject constructor(
    @UserAuthStateDatastore
    private val userAuthStateDatastore: DataStore<UserAuthState>
) : UserAuthStateRepository {

    override fun getAuthState(): Flow<UserAuthState> = userAuthStateDatastore.data

    override suspend fun setAuthState(action: UserAuthState.Builder.() -> Unit) {
        userAuthStateDatastore.updateData {
            it.toBuilder()
                .apply {
                    action()
                }.build()
        }
    }

    override suspend fun setToken(token: String) {
        setAuthState {
            this.token = token
        }
    }

    override suspend fun setUserId(userId: Int) {
        setAuthState {
            this.userId = userId
        }
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        setAuthState {
            this.isLoggedIn = isLoggedIn
        }
    }
}