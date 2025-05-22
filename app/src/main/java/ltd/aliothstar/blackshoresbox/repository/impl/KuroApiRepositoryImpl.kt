package ltd.aliothstar.blackshoresbox.repository.impl

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
import ltd.aliothstar.blackshoresbox.network.KuroApi
import ltd.aliothstar.blackshoresbox.network.KuroApiInterface
import ltd.aliothstar.blackshoresbox.network.MineV2Result
import ltd.aliothstar.blackshoresbox.network.Role
import ltd.aliothstar.blackshoresbox.network.SdkLoginResult
import ltd.aliothstar.blackshoresbox.network.SignInInfoResult
import ltd.aliothstar.blackshoresbox.network.SignInResult
import ltd.aliothstar.blackshoresbox.network.WidgetGame3RefreshResult
import ltd.aliothstar.blackshoresbox.network.WikiGetPageResult
import ltd.aliothstar.blackshoresbox.network.asFlow
import ltd.aliothstar.blackshoresbox.repository.KuroApiRepository

class KuroApiRepositoryImpl @Inject constructor(
    @KuroApi
    private val api: KuroApiInterface
) : KuroApiRepository {
    override suspend fun wikiGetPage(): Flow<WikiGetPageResult> =
        api.wikiGetPage().asFlow()

    override suspend fun getSmsCode(
        mobile: Long,
        geeTestData: String?
    ): Flow<GetSmsResult> =
        api.getSmsCode(
            mobile = mobile,
            geeTestData = geeTestData
        ).asFlow()

    override suspend fun sdkLogin(
        mobile: Long,
        code: Int
    ): Flow<SdkLoginResult> =
        api.sdkLogin(
            mobile = mobile,
            code = code
        ).asFlow()

    override suspend fun mineV2(
        token: String,
        userId: Int?
    ): Flow<MineV2Result.Mine> =
        api.mineV2(
            token = token,
            userId = userId
        ).asFlow().map {
            it.mine
        }

    override suspend fun findRoleList(
        token: String,
        game: Game
    ): Flow<List<Role>> =
        api.findRoleList(
            token = token,
            game = game
        ).asFlow()

    override suspend fun signInInfo(
        token: String,
        game: Game
    ): Flow<SignInInfoResult> =
        api.signInInfo(
            token = token,
            game = game
        ).asFlow()

    override suspend fun signIn(
        token: String,
        game: Game
    ): Flow<SignInResult> =
        api.signIn(
            token = token,
            game = game
        ).asFlow()

    override suspend fun widgetGame3Refresh(
        token: String
    ): Flow<WidgetGame3RefreshResult> =
        api.widgetGame3Refresh(
            token = token
        ).asFlow()

    override suspend fun akiRefreshData(
        token: String,
        game: Game,
        roleId: Long,
        serverId: String
    ): Flow<Boolean> =
        api.akiRefreshData(
            token = token,
            game = game,
            roleId = roleId,
            serverId = serverId
        ).asFlow()

    override suspend fun akiBaseData(
        token: String,
        game: Game,
        roleId: Long,
        serverId: String
    ): Flow<AkiBaseDataResult> =
        api.akiBaseData(
            token = token,
            game = game,
            roleId = roleId,
            serverId = serverId
        ).asFlow()

    override suspend fun akiRoleData(
        token: String,
        game: Game,
        roleId: Long,
        serverId: String,
        country: Country
    ): Flow<AkiRoleDataResult> =
        api.akiRoleData(
            token = token,
            game = game,
            roleId = roleId,
            serverId = serverId,
            country = country
        ).asFlow()

    override suspend fun akiGetRoleDetail(
        token: String,
        game: Game,
        roleId: Long,
        serverId: String,
        country: Country,
        akiRoleId: Int
    ): Flow<AkiGetRoleDetailResult> =
        api.akiGetRoleDetail(
            token = token,
            game = game,
            roleId = roleId,
            serverId = serverId,
            country = country,
            akiRoleId = akiRoleId
        ).asFlow()

    override suspend fun akiTowerDataDetail(
        token: String,
        game: Game,
        roleId: Long,
        serverId: String,
        userId: Long
    ): Flow<AkiTowerDataDetailResult> =
        api.akiTowerDataDetail(
            token = token,
            game = game,
            roleId = roleId,
            serverId = serverId
        ).asFlow()

    override suspend fun akiSlashDetail(
        token: String,
        roleId: Long,
        serverId: String,
        userId: Long
    ): Flow<AkiSlashDetailResult> =
        api.akiSlashDetail(
            token = token,
            roleId = roleId,
            serverId = serverId,
            userId = userId
        ).asFlow()

    override suspend fun akiChallengeDetails(
        token: String,
        roleId: Long,
        serverId: String,
        country: Country
    ): Flow<AkiChallengeDetailResult> =
        api.akiChallengeDetails(
            token = token,
            roleId = roleId,
            serverId = serverId,
            country = country
        ).asFlow()

    override suspend fun akiExploreIndex(
        token: String,
        roleId: Long,
        serverId: String,
        country: Country
    ): Flow<AkiExploreIndexResult> =
        api.akiExploreIndex(
            token = token,
            roleId = roleId,
            serverId = serverId,
            country = country
        ).asFlow()
}