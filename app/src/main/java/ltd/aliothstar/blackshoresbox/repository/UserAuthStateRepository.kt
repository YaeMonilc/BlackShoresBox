package ltd.aliothstar.blackshoresbox.repository

import kotlinx.coroutines.flow.Flow
import ltd.aliothstar.blackshoresbox.datastore.UserAuthState

interface UserAuthStateRepository {
    fun getAuthState(): Flow<UserAuthState>
    suspend fun setAuthState(action: UserAuthState.Builder.() -> Unit)

    suspend fun setToken(token: String)
    suspend fun setUserId(userId: Int)
    suspend fun setLoggedIn(isLoggedIn: Boolean)
}