/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.setel.restaurant.data

import com.setel.restaurant.data.local.database.OperatingHours
import com.setel.restaurant.data.local.database.OperatingHoursDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for [DefaultOperatingHoursRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultOperatingHoursRepositoryTest {

    @Test
    fun operatingHourss_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultOperatingHoursRepository(FakeOperatingHoursDao())

        repository.add("Repository")

        assertEquals(repository.operatingHourss.first().size, 1)
    }
}

private class FakeOperatingHoursDao : OperatingHoursDao {

    private val data = mutableListOf<OperatingHours>()

    override fun getOperatingHourss(): Flow<List<OperatingHours>> = flow {
        emit(data)
    }

    override suspend fun insertOperatingHours(item: OperatingHours) {
        data.add(0, item)
    }
}
