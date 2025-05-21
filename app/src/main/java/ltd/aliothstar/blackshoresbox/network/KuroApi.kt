package ltd.aliothstar.blackshoresbox.network

import jakarta.inject.Qualifier
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import retrofit2.Call
import retrofit2.awaitResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

const val KURO_API_BASE_URL = "https://api.kurobbs.com/"

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class KuroApi

interface KuroApiInterface {
    @FormUrlEncoded
    @POST("/user/getSmsCode")
    fun getSmsCode(
        @Field("mobile")
        mobile: Long,
        @Field("geeTestData")
        geeTestData: String? = null
    ): Call<KuroBaseResponse<GetSmsResult>>

    @FormUrlEncoded
    @POST("/user/sdkLogin")
    fun sdkLogin(
        @Field("mobile")
        mobile: Long,
        @Field("code")
        code: Int
    ): Call<KuroBaseResponse<SdkLoginResult>>

    @FormUrlEncoded
    @POST("/user/mineV2")
    fun mineV2(
        @Header("token")
        token: String,
        @Field("otherUserId")
        userId: Int?
    ): Call<KuroBaseResponse<MineV2Result>>

    @FormUrlEncoded
    @POST("/user/role/findRoleList")
    fun findRoleList(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game
    ): Call<KuroBaseResponse<List<Role>>>

    @FormUrlEncoded
    @POST("/user/signIn/info")
    fun signInInfo(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES
    ): Call<KuroBaseResponse<SignInInfoResult>>

    @FormUrlEncoded
    @POST("/user/signIn")
    fun signIn(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES
    ): Call<KuroBaseResponse<SignInResult>>

    @FormUrlEncoded
    @POST("/gamer/widget/game3/refresh")
    fun widgetGame3Refresh(
        @Header("token")
        token: String
    ): Call<KuroBaseResponse<WidgetGame3RefreshResult>>

    @FormUrlEncoded
    @POST("/aki/roleBox/akiBox/refreshData")
    fun akiRefreshData(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES,
        @Field("roleId")
        roleId: Long,
        @Field("serverId")
        serverId: String
    ): Call<KuroBaseResponse<Boolean>>

    @FormUrlEncoded
    @POST("/aki/roleBox/akiBox/baseData")
    fun akiBaseData(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES,
        @Field("roleId")
        roleId: Long,
        @Field("serverId")
        serverId: String
    ): Call<KuroBaseResponse<AkiBaseDataResult>>

    @FormUrlEncoded
    @POST("/aki/roleBox/akiBox/roleData")
    fun akiRoleData(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES,
        @Field("roleId")
        roleId: Long,
        @Field("serverId")
        serverId: String,
        @Field("channelId")
        channelId: Int = 19,
        @Field("countryCode")
        country: Country,
    ): Call<KuroBaseResponse<AkiRoleDataResult>>

    @FormUrlEncoded
    @POST("/aki/roleBox/akiBox/getRoleDetail")
    fun akiGetRoleDetail(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES,
        @Field("roleId")
        roleId: Long,
        @Field("serverId")
        serverId: String,
        @Field("channelId")
        channelId: Int = 19,
        @Field("countryCode")
        country: Country,
        @Field("id")
        akiRoleId: Int
    ): Call<KuroBaseResponse<AkiGetRoleDetailResult>>

    @FormUrlEncoded
    @POST("/aki/roleBox/akiBox/towerDataDetail")
    fun akiTowerDataDetail(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES,
        @Field("roleId")
        roleId: Long,
        @Field("serverId")
        serverId: String
    ): Call<KuroBaseResponse<AkiTowerDataDetailResult>>

    @FormUrlEncoded
    @POST("/aki/roleBox/akiBox/slashDetail")
    fun akiSlashDetail(
        @Header("token")
        token: String,
        @Field("roleId")
        roleId: Long,
        @Field("serverId")
        serverId: String,
        @Field("userId")
        userId: Long
    ): Call<KuroBaseResponse<AkiSlashDetailResult>>

    @FormUrlEncoded
    @POST("/aki/roleBox/akiBox/challengeDetails")
    fun akiChallengeDetails(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES,
        @Field("roleId")
        roleId: Long,
        @Field("serverId")
        serverId: String,
        @Field("channelId")
        channelId: Int = 19,
        @Field("countryCode")
        country: Country
    ): Call<KuroBaseResponse<AkiChallengeDetailResult>>

    @FormUrlEncoded
    @POST("/aki/roleBox/akiBox/exploreIndex")
    fun akiExploreIndex(
        @Header("token")
        token: String,
        @Field("gameId")
        game: Game = Game.WUTHERING_WAVES,
        @Field("roleId")
        roleId: Long,
        @Field("serverId")
        serverId: String,
        @Field("channelId")
        channelId: Int = 19,
        @Field("countryCode")
        country: Country
    ): Call<KuroBaseResponse<AkiExploreIndexResult>>
}

@Serializable(Game.Companion.GameSerializer::class)
enum class Game(
    val id: Int
) {
    WUTHERING_WAVES(3);

    companion object {
        object GameSerializer : KSerializer<Game> {
            class UnknownGameIdException : RuntimeException()

            override val descriptor: SerialDescriptor
                get() = PrimitiveSerialDescriptor("Game", PrimitiveKind.INT)

            override fun serialize(
                encoder: Encoder,
                value: Game
            ) = encoder.encodeInt(value.id)

            override fun deserialize(decoder: Decoder): Game =
                decoder.decodeInt().let {
                    fromValue(it) ?: throw UnknownGameIdException()
                }
        }

        fun fromValue(value: Int) = entries.firstOrNull { it.id == value }
    }

    override fun toString(): String {
        return id.toString()
    }
}

@Serializable(with = Country.Companion.CountrySerializer::class)
enum class Country(
    val id: Int
) {
    HUANG_LONG(1),
    BLACK_SHORES(2),
    RINASCITA(3);

    companion object {
        object CountrySerializer : KSerializer<Country> {
            class UnknownCountryIdException : RuntimeException()

            override val descriptor: SerialDescriptor
                get() = PrimitiveSerialDescriptor("Country", PrimitiveKind.INT)

            override fun serialize(
                encoder: Encoder,
                value: Country
            ) = encoder.encodeInt(value.id)

            override fun deserialize(decoder: Decoder): Country =
                decoder.decodeInt().let {
                    Country.Companion.fromValue(it) ?: throw UnknownCountryIdException()
                }
        }

        fun fromValue(value: Int) = entries.firstOrNull { it.id == value }
    }

    override fun toString(): String {
        return id.toString()
    }
}

@Serializable
class KuroBaseResponse<T>(
    val code: Code,
    val data: T? = null,
    val success: Boolean? = null,
    val msg: String? = null
) {
    @Serializable(Code.Companion.CodeSerializer::class)
    enum class Code(
        val value: Int
    ) {
        SUCCESS(200),
        SEND_SMS_CODE_FREQUENTLY(242),
        SMS_CODE_INVALID(-130),
        TOKEN_EXPIRED(220)
        ;

        companion object {
            fun fromValue(value: Int) = entries.firstOrNull { it.value == value }

            object CodeSerializer : KSerializer<Code> {
                override val descriptor: SerialDescriptor
                    get() = PrimitiveSerialDescriptor("Code", PrimitiveKind.INT)

                override fun serialize(
                    encoder: Encoder,
                    value: Code
                ) = encoder.encodeInt(value.value)

                override fun deserialize(decoder: Decoder): Code =
                    decoder.decodeInt().let {
                        fromValue(it) ?: throw ErrorResponse.UnknownResponseCodeException(it)
                    }
            }
        }
    }

    sealed class ErrorResponse(
        override val message: String? = null,
        var body: String? = null
    ) : RuntimeException(message) {
        companion object {
            fun fromCode(code: Code): ErrorResponse? =
                when (code) {
                    Code.SUCCESS -> null
                    Code.SEND_SMS_CODE_FREQUENTLY -> SendSmsCodeFrequentlyException()
                    Code.SMS_CODE_INVALID -> SmsCodeInvalidException()
                    Code.TOKEN_EXPIRED -> TokenExpiredException()
                }
        }

        class UnknownResponseCodeException(
            code: Int
        ) : ErrorResponse("Unknown response code: $code")
        class SendSmsCodeFrequentlyException : ErrorResponse("Send sms code frequently")
        class SmsCodeInvalidException : ErrorResponse("Sms code invalid")
        class TokenExpiredException : ErrorResponse("Token expired")
    }
}

@Serializable
data class GetSmsResult(
    val geeTest: Boolean
)

@Serializable
data class SdkLoginResult(
    val enableChildMode: Boolean,
    val gender: Int,
    val headUrl: String,
    val isAdmin: Boolean,
    val isRegister: Int,
    val signature: String,
    val token: String,
    val userId: Int,
    val userName: String
)

@Serializable
data class MineV2Result(
    val mine: Mine
) {
    @Serializable
    data class Mine(
        val collectCount: Int,
        val commentCount: Int,
        val fansCount: Int,
        val fansNewCount: Int,
        val followCount: Int,
        val gender: Int,
        val goldNum: Int,
        val headUrl: String,
        val ifCompleteQuiz: Int,
        val ipRegion: String,
        val isFollow: Int,
        val isLoginUser: Int,
        val isMute: Int,
        val lastLoginModelType: String,
        val lastLoginTime: String,
        val levelTotal: Int,
        val likeCount: Int,
        val medalList: List<JsonElement>,
        val mobile: String,
        val postCount: Int,
        val registerTime: String,
        val signature: String,
        val signatureReviewStatus: Int,
        val status: Int,
        val userId: String,
        val userName: String
    )
}

@Serializable
data class Role(
    val bindStatus: Int,
    val createTime: String,
    @SerialName("gameId")
    val game: Game,
    val id: String,
    val isDefault: Boolean,
    val isHidden: Boolean,
    val roleId: String,
    val roleLevel: Int,
    val roleName: String,
    val roleNum: Int,
    val serverId: String,
    val serverName: String,
    val userId: String
)

@Serializable
data class SignInInfoResult(
    val continueDays: Int? = null,
    val hasSignIn: Boolean
)

@Serializable
data class SignInResult(
    val hasSignIn: Boolean,
    val gainVoList: List<Item>
) {
    @Serializable
    data class Item(
        val gainValue: Int
    )
}

@Serializable
data class AkiBaseDataResult(
    val achievementCount: Int,
    val achievementStar: Int,
    val activeDays: Int,
    val bigCount: Int,
    val boxList: List<BoxItem>,
    val chapterId: Int,
    @SerialName("creatTime")
    val createTime: Long,
    val energy: Int,
    val id: Int,
    val level: Int,
    val liveness: Int,
    val livenessMaxCount: Int,
    val livenessUnlock: Boolean,
    val maxEnergy: Int,
    val name: String,
    val phantomBoxList: List<PhantomBoxItem>,
    val roleNum: Int,
    val rougeIconUrl: String,
    val rougeScore: Int,
    val rougeScoreLimit: Int,
    val rougeTitle: String,
    val showToGuest: Boolean,
    val smallCount: Int,
    val storeEnergy: Int,
    val storeEnergyIconUrl: String,
    val storeEnergyLimit: Int,
    val storeEnergyTitle: String,
    val treasureBoxList: List<TreasureBoxItem>,
    val weeklyInstCount: Int,
    val weeklyInstCountLimit: Int,
    val weeklyInstIconUrl: String,
    val weeklyInstTitle: String,
    val worldLevel: Int
) {
    @Serializable
    data class BoxItem(
        val boxName: String,
        val num: Int
    )

    @Serializable
    data class PhantomBoxItem(
        val name: String,
        val num: Int
    )

    @Serializable
    data class TreasureBoxItem(
        val name: String,
        val num: Int
    )
}

@Serializable
data class AkiRoleDataResult(
    val roleList: List<AkiRole>
) {
    @Serializable
    data class AkiRole(
        val acronym: String,
        val attributeId: Int,
        val attributeName: String,
        val breach: Int,
        val chainUnlockNum: Int,
        val isMainRole: Boolean,
        val level: Int,
        val roleIconUrl: String,
        val roleId: Int,
        val roleName: String,
        val rolePicUrl: String,
        val roleSkin: RoleSkin,
        val starLevel: Int,
        val weaponTypeId: Int,
        val weaponTypeName: String
    ) {
        @Serializable
        data class RoleSkin(
            val isAddition: Boolean,
            val picUrl: String,
            val priority: Int,
            val quality: Int,
            val qualityName: String,
            val skinIcon: String,
            val skinId: Int,
            val skinName: String
        )
    }
}

@Serializable
data class AkiGetRoleDetailResult(
    val chainList: List<Chain>,
    val equipPhantomAttributeList: List<EquipPhantomAttribute>,
    val level: Int,
    val phantomData: PhantomData,
    val role: Role,
    val roleAttributeList: List<RoleAttribute>,
    val roleSkin: RoleSkin,
    val skillList: List<SkillData>,
    val weaponData: WeaponData
) {
    @Serializable
    data class Chain(
        val description: String,
        val iconUrl: String,
        val name: String,
        val order: Int,
        val unlocked: Boolean
    )

    @Serializable
    data class EquipPhantomAttribute(
        val attributeName: String,
        val attributeValue: String,
        val iconUrl: String
    )

    @Serializable
    data class PhantomData(
        val cost: Int,
        val equipPhantomList: List<EquipPhantom>
    ) {
        @Serializable
        data class EquipPhantom(
            val cost: Int,
            val fetterDetail: FetterDetail,
            val level: Int,
            val mainProps: List<PhantomPropDetail>,
            val phantomProp: PhantomProp,
            val quality: Int,
            val subProps: List<PhantomPropDetail>
        ) {
            @Serializable
            data class FetterDetail(
                val firstDescription: String,
                val groupId: Int,
                val iconUrl: String,
                val name: String,
                val num: Int,
                val secondDescription: String
            )

            @Serializable
            data class PhantomPropDetail(
                val attributeName: String,
                val attributeValue: String,
                val iconUrl: String
            )

            @Serializable
            data class PhantomProp(
                val cost: Int,
                val iconUrl: String,
                val name: String,
                val phantomId: Long,
                val phantomPropId: Long,
                val quality: Int,
                val skillDescription: String
            )
        }
    }

    @Serializable
    data class Role(
        val acronym: String,
        val attributeId: Int,
        val attributeName: String,
        val breach: Int,
        val chainUnlockNum: Int,
        val isMainRole: Boolean,
        val level: Int,
        val roleIconUrl: String,
        val roleId: Int,
        val roleName: String,
        val rolePicUrl: String,
        val starLevel: Int,
        val weaponTypeId: Int,
        val weaponTypeName: String
    )

    @Serializable
    data class RoleAttribute(
        val attributeId: Int,
        val attributeName: String,
        val attributeValue: String,
        val iconUrl: String,
        val sort: Int
    )

    @Serializable
    data class RoleSkin(
        val isAddition: Boolean,
        val picUrl: String,
        val priority: Int,
        val quality: Int,
        val qualityName: String,
        val skinIcon: String,
        val skinId: Int,
        val skinName: String
    )

    @Serializable
    data class SkillData(
        val level: Int,
        val skill: SkillDetail
    ) {
        @Serializable
        data class SkillDetail(
            val description: String,
            val iconUrl: String,
            val id: Int,
            val name: String,
            val type: String
        )
    }

    @Serializable
    data class WeaponData(
        val breach: Int,
        val level: Int,
        val resonLevel: Int,
        val weapon: WeaponDetail
    ) {
        @Serializable
        data class WeaponDetail(
            val effectDescription: String,
            val weaponEffectName: String,
            val weaponIcon: String,
            val weaponId: Long,
            val weaponName: String,
            val weaponStarLevel: Int,
            val weaponType: Int
        )
    }
}

@Serializable
data class AkiTowerDataDetailResult(
    val difficultyList: List<Difficulty>,
    val isUnlock: Boolean,
    val seasonEndTime: Long
) {
    @Serializable
    data class Difficulty(
        val difficulty: Int,
        val difficultyName: String,
        val towerAreaList: List<TowerArea>
    ) {
        @Serializable
        data class TowerArea(
            val areaId: Int,
            val areaName: String,
            val floorList: List<Floor>,
            val maxStar: Int,
            val star: Int
        ) {
            @Serializable
            data class Floor(
                val floor: Int,
                val picUrl: String,
                val roleList: List<Role>? = null,
                val star: Int
            ) {
                @Serializable
                data class Role(
                    val iconUrl: String,
                    val roleId: Int
                )
            }
        }
    }
}


@Serializable
data class AkiSlashDetailResult(
    val difficultyList: List<DifficultyDetail>,
    val isUnlock: Boolean,
    val seasonEndTime: Long
) {
    @Serializable
    data class DifficultyDetail(
        val allScore: Int,
        val challengeList: List<Challenge>,
        val detailPageBG: String,
        val difficulty: Int,
        val difficultyName: String,
        val homePageBG: String,
        val maxScore: Int,
        val teamIcon: String
    ) {
        @Serializable
        data class Challenge(
            val challengeId: Int,
            val challengeName: String,
            val halfList: List<Half>,
            val rank: String,
            val score: Int
        ) {
            @Serializable
            data class Half(
                val buffDescription: String,
                val buffIcon: String,
                val buffName: String,
                val buffQuality: Int,
                val roleList: List<Role>,
                val score: Int
            ) {
                @Serializable
                data class Role(
                    val iconUrl: String,
                    val roleId: Int
                )
            }
        }
    }
}

@Serializable
data class AkiChallengeDetailResult(
    val challengeInfo: Map<String, List<Challenge>>,
    val isUnlock: Boolean,
    val open: Boolean
) {
    @Serializable
    data class Challenge(
        val bossHeadIcon: String,
        val bossIconUrl: String,
        val bossLevel: Int,
        val bossName: String,
        val challengeId: Int,
        val difficulty: Int,
        val passTime: Int,
        val roles: List<Role>
    ) {
        @Serializable
        data class Role(
            val natureId: Int,
            val roleHeadIcon: String,
            val roleLevel: Int,
            val roleName: String
        )
    }
}

@Serializable
data class AkiExploreIndexResult(
    val detectionInfoList: List<DetectionInfo>,
    val exploreList: List<Explore>,
    val open: Boolean
) {
    @Serializable
    data class DetectionInfo(
        val acronym: String,
        val detectionIcon: String,
        val detectionId: Long,
        val detectionName: String,
        val level: Int,
        val levelName: String
    )

    @Serializable
    data class Explore(
        val areaInfoList: List<AreaInfo>,
        val country: Country
    ) {
        @Serializable
        data class AreaInfo(
            val areaId: Int,
            val areaName: String,
            val areaPic: String,
            val areaProgress: Int,
            val itemList: List<Item>
        ) {
            @Serializable
            data class Item(
                val icon: String,
                val name: String,
                val progress: Int,
                val type: Int
            )
        }

        @Serializable
        data class Country(
            val bgColor: String,
            val countryId: Int,
            val countryName: String,
            val detailPageAreaMaskColor: String,
            val detailPageAreaPic: String,
            val detailPageDarkColor: String,
            val detailPageFontColor: String,
            val detailPageImage: String,
            val detailPageLightColor: String,
            val detailPagePic: String,
            val detailPageProgressColor: String,
            val homePageIcon: String,
            val homePageImage: String
        )
    }
}

@Serializable
data class WidgetGame3RefreshResult(
    val gameId: Int,
    val userId: Long,
    val serverTime: Long,
    val serverId: String,
    val serverName: String,
    val signInUrl: String,
    val signInTxt: String,
    val hasSignIn: Boolean,
    val roleId: String,
    val roleName: String,
    val energyData: Data,
    val livenessData: Data,
    val battlePassData: List<Data>,
    val storeEnergyData: Data,
    val towerData: Data,
    val slashTowerData: Data,
    val weeklyData: Data
) {
    @Serializable
    data class Data(
        val name: String,
        val img: String,
        val key: JsonElement?,
        val refreshTimeStamp: Long,
        val timePreDesc: JsonElement?,
        val expireTimeStamp: Long,
        val value: JsonElement?,
        val status: Int,
        val cur: Int,
        val total: Int
    )
}

fun <T> Call<KuroBaseResponse<T>>.asFlow() = flow<T> {
    awaitResponse().body()?.data?.let { data ->
        emit(data)
    }
}