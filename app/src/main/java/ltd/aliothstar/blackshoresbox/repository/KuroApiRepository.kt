package ltd.aliothstar.blackshoresbox.repository

import kotlinx.coroutines.flow.Flow
import ltd.aliothstar.blackshoresbox.network.AkiBaseDataResult
import ltd.aliothstar.blackshoresbox.network.AkiChallengeDetailResult
import ltd.aliothstar.blackshoresbox.network.AkiExploreIndexResult
import ltd.aliothstar.blackshoresbox.network.AkiGetRoleDetailResult
import ltd.aliothstar.blackshoresbox.network.AkiRoleDataResult
import ltd.aliothstar.blackshoresbox.network.AkiSlashDetailResult
import ltd.aliothstar.blackshoresbox.network.AkiTowerDataDetailResult
import ltd.aliothstar.blackshoresbox.network.Country
import ltd.aliothstar.blackshoresbox.network.Game
import ltd.aliothstar.blackshoresbox.network.GetSmsResult
import ltd.aliothstar.blackshoresbox.network.MineV2Result
import ltd.aliothstar.blackshoresbox.network.Role
import ltd.aliothstar.blackshoresbox.network.SdkLoginResult
import ltd.aliothstar.blackshoresbox.network.SignInInfoResult
import ltd.aliothstar.blackshoresbox.network.SignInResult
import ltd.aliothstar.blackshoresbox.network.WidgetGame3RefreshResult

interface KuroApiRepository {
    suspend fun getSmsCode(
        mobile: Long,
        geeTestData: String? = null
    ): Flow<GetSmsResult>

    suspend fun sdkLogin(
        mobile: Long,
        code: Int
    ): Flow<SdkLoginResult>

    suspend fun mineV2(
        token: String,
        userId: Int? = null
    ): Flow<MineV2Result.Mine>

    suspend fun findRoleList(
        token: String,
        game: Game = Game.WUTHERING_WAVES
    ): Flow<List<Role>>

    suspend fun signInInfo(
        token: String,
        game: Game = Game.WUTHERING_WAVES
    ): Flow<SignInInfoResult>

    suspend fun signIn(
        token: String,
        game: Game = Game.WUTHERING_WAVES
    ): Flow<SignInResult>

    suspend fun widgetGame3Refresh(
        token: String
    ): Flow<WidgetGame3RefreshResult>

    suspend fun akiRefreshData(
        token: String,
        game: Game = Game.WUTHERING_WAVES,
        roleId: Long,
        serverId: String
    ): Flow<Boolean>

    suspend fun akiBaseData(
        token: String,
        game: Game = Game.WUTHERING_WAVES,
        roleId: Long,
        serverId: String
    ): Flow<AkiBaseDataResult>

    suspend fun akiRoleData(
        token: String,
        game: Game = Game.WUTHERING_WAVES,
        roleId: Long,
        serverId: String,
        country: Country
    ): Flow<AkiRoleDataResult>

    suspend fun akiGetRoleDetail(
        token: String,
        game: Game = Game.WUTHERING_WAVES,
        roleId: Long,
        serverId: String,
        country: Country,
        akiRoleId: Int
    ): Flow<AkiGetRoleDetailResult>

    suspend fun akiTowerDataDetail(
        token: String,
        game: Game = Game.WUTHERING_WAVES,
        roleId: Long,
        serverId: String,
        userId: Long
    ): Flow<AkiTowerDataDetailResult>

    suspend fun akiSlashDetail(
        token: String,
        roleId: Long,
        serverId: String,
        userId: Long
    ): Flow<AkiSlashDetailResult>

    suspend fun akiChallengeDetails(
        token: String,
        roleId: Long,
        serverId: String,
        country: Country
    ): Flow<AkiChallengeDetailResult>

    suspend fun akiExploreIndex(
        token: String,
        roleId: Long,
        serverId: String,
        country: Country
    ): Flow<AkiExploreIndexResult>
}