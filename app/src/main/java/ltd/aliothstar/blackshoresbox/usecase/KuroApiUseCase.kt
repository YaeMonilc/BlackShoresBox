package ltd.aliothstar.blackshoresbox.usecase

import kotlinx.coroutines.flow.Flow
import ltd.aliothstar.blackshoresbox.network.Game
import ltd.aliothstar.blackshoresbox.network.Role
import ltd.aliothstar.blackshoresbox.network.WikiGetPageResult

interface KuroApiUseCase {
    suspend fun getCurrentSideModule(): Flow<List<WikiGetPageResult.ContentJson.SideModule>>

    suspend fun findRoleByGame(
        token: String,
        game: Game
    ): Flow<Role>

    suspend fun updateUserAuthState(
        token: String
    ): Flow<Boolean>
}