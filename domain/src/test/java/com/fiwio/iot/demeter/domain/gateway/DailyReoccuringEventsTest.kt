package com.fiwio.iot.demeter.domain.gateway

import com.fiwio.iot.demeter.domain.UnitTest
import com.fiwio.iot.demeter.domain.model.schedule.ActionEvent
import com.fiwio.iot.demeter.domain.model.schedule.ActionEvents
import com.fiwio.iot.demeter.domain.model.schedule.DayTime
import com.fiwio.iot.demeter.domain.repository.SchedulesRepository
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.mockito.Mock

class DailyReoccuringEventsTest : UnitTest() {

    @Mock
    private lateinit var repo: SchedulesRepository

    private lateinit var authenticator: DailyReoccuringEvents

    @Test
    fun `returns 1 found actions`() {

        authenticator = DailyReoccuringEvents(repo)

        given {
            repo.getDailyEvents()
        }.willReturn {
            generateSingleEventInDay()
        }

        val list = authenticator.getEvent(DayTime(8, 0, 0))
        list shouldEqual generateSingleEventInDay()
        list.actionEvents.size shouldEqual 1

    }

    @Test
    fun `returns 1 found actions from multiple in different time`() {

        authenticator = DailyReoccuringEvents(repo)

        given {
            repo.getDailyEvents()
        }.willReturn {
            generateMultipleEventsInDay()
        }

        val list = authenticator.getEvent(DayTime(8, 0, 0))
        list shouldEqual generateSingleEventInDay()
        list.actionEvents.size shouldEqual 1
    }

    @Test
    fun `returns multiple found actions from multiple in the same time`() {

        authenticator = DailyReoccuringEvents(repo)

        given {
            repo.getDailyEvents()
        }.willReturn {
            generateMultipleEventsTheSameTime()
        }

        val list = authenticator.getEvent(DayTime(8, 0, 0))
        list shouldEqual generateMultipleEventsTheSameTime()
        list.actionEvents.size shouldEqual 2
    }

    @Test
    fun `returns only scheduled for a given time`() {

        authenticator = DailyReoccuringEvents(repo)

        given {
            repo.getDailyEvents()
        }.willReturn {
            generateMultipleEvents()
        }

        val list = authenticator.getEvent(DayTime(8, 0, 0))
        list shouldEqual generateMultipleEventsTheSameTime()
        list.actionEvents.size shouldEqual 2
    }


    @Test
    fun `returns no actions`() {

        authenticator = DailyReoccuringEvents(repo)

        given {
            repo.getDailyEvents()
        }.willReturn {
            ActionEvents(emptyList())
        }

        val list = authenticator.getEvent(DayTime(8, 0, 0))
        list.actionEvents shouldEqual emptyList()

    }

    private fun generateSingleEventInDay(): ActionEvents {
        return ActionEvents(listOf(
                ActionEvent("", "", "", DayTime(8, 0, 0))))
    }

    private fun generateMultipleEventsInDay(): ActionEvents {
        return ActionEvents(listOf(
                ActionEvent("", "", "", DayTime(8, 0, 0)),
                ActionEvent("evening", "", "", DayTime(19, 0, 0))
                ))
    }

    private fun generateMultipleEventsTheSameTime(): ActionEvents {
        return ActionEvents(listOf(
                ActionEvent("", "", "", DayTime(8, 0, 0)),
                ActionEvent("evening", "garden", "open", DayTime(8, 0, 0))
        ))
    }

    private fun generateMultipleEvents(): ActionEvents {
        return ActionEvents(listOf(
                ActionEvent("", "", "", DayTime(8, 0, 0)),
                ActionEvent("", "", "", DayTime(19, 0, 0)),
                ActionEvent("evening", "garden", "open", DayTime(8, 0, 0))
        ))
    }



}