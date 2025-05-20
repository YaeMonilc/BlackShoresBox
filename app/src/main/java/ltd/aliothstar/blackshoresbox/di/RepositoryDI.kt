package ltd.aliothstar.blackshoresbox.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ltd.aliothstar.blackshoresbox.repository.UserAuthStateRepository
import ltd.aliothstar.blackshoresbox.repository.impl.UserAuthStateRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryDI {
    @Binds
    abstract fun bindUserAuthStateRepository(
        impl: UserAuthStateRepositoryImpl
    ): UserAuthStateRepository
}