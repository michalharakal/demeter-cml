package com.fiwio.iot.demeter.domain.gateway

import com.fiwio.iot.demeter.domain.repository.SchedulesRepository
import org.junit.Assert.*
import org.junit.Test

class DailyReoccuringEventsTest: UnitTest() {

    private val authenticator = DailyReoccuringEvents(MockedSchedulesRepositor())

    @Test
    fun `returns default value`() {
        authenticator.userLoggedIn() shouldBe true
    }

    class MockedSchedulesRepositor : SchedulesRepository {

    }
}