package com.setel.restaurant.data.di

import com.setel.restaurant.data.DefaultOperatingHoursRepository
import com.setel.restaurant.data.OperatingHoursRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsOperatingHoursRepository(
        operatingHoursRepository: DefaultOperatingHoursRepository
    ): OperatingHoursRepository
}

class FakeOperatingHoursRepository @Inject constructor() : OperatingHoursRepository {
    override val operatingHourss: Flow<List<String>> = flowOf(fakeOperatingHourss)

    override suspend fun add(name: String) {
        throw NotImplementedError()
    }
}

val fakeOperatingHourss = listOf("One", "Two", "Three")
