package ltd.aliothstar.blackshoresbox.usecase.impl

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ltd.aliothstar.blackshoresbox.network.Game
import ltd.aliothstar.blackshoresbox.network.Role
import ltd.aliothstar.blackshoresbox.repository.KuroApiRepository
import ltd.aliothstar.blackshoresbox.repository.UserAuthStateRepository
import ltd.aliothstar.blackshoresbox.usecase.KuroApiUseCase

class KuroAPiUseCaseImpl @Inject constructor(
    private val kuroApiRepository: KuroApiRepository,
    private val userAuthStateRepository: UserAuthStateRepository
) : KuroApiUseCase {
    override suspend fun findRoleByGame(
        token: String,
        game: Game
    ): Flow<Role> = flow {
        kuroApiRepository.findRoleList(
            token = token,
            game = game
        ).firstOrNull()?.first {
            it.game == game
        }?.let {
            emit(it)
        }
    }

    override suspend fun updateUserAuthState(
        token: String
    ): Flow<Boolean> = flow {
        kuroApiRepository.mineV2(
            token = token
        ).firstOrNull()?.let { mine ->
            userAuthStateRepository.setAuthState {
                this.token = token
                userId = mine.userId.toInt()
                isLoggedIn = true
            }
        }
    }
}