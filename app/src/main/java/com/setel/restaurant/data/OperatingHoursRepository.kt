package com.setel.restaurant.data

import com.setel.restaurant.data.local.database.OperatingHours
import com.setel.restaurant.data.local.database.OperatingHoursDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface OperatingHoursRepository {
    val operatingHourss: Flow<List<String>>

    suspend fun add(name: String)
}

class DefaultOperatingHoursRepository @Inject constructor(
    private val operatingHoursDao: OperatingHoursDao
) : OperatingHoursRepository {

    override val operatingHourss: Flow<List<String>> =
        operatingHoursDao.getOperatingHourss().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        operatingHoursDao.insertOperatingHours(OperatingHours(name = name))
    }
}
