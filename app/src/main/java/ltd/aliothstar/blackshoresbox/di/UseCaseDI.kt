package ltd.aliothstar.blackshoresbox.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ltd.aliothstar.blackshoresbox.usecase.KuroApiUseCase
import ltd.aliothstar.blackshoresbox.usecase.impl.KuroAPiUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseDI {
    @Binds
    abstract fun bindKuroApiUseCase(
        impl: KuroAPiUseCaseImpl
    ): KuroApiUseCase
}