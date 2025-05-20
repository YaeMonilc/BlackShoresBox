package ltd.aliothstar.blackshoresbox.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ltd.aliothstar.blackshoresbox.datastore.UserAuthState
import ltd.aliothstar.blackshoresbox.datastore.UserAuthStateDatastore
import ltd.aliothstar.blackshoresbox.datastore.UserAuthStateSerializer
import java.io.File

@InstallIn(SingletonComponent::class)
@Module
class DatastoreDI {
    private val userAuthStateDatastore: DataStore<UserAuthState>
        get() = DataStoreFactory.create(
            serializer = UserAuthStateSerializer(),
            produceFile = { File("user_auth_state.pb") }
        )

    @Provides
    @UserAuthStateDatastore
    fun provideUserAuthStateDatastore() = userAuthStateDatastore

}