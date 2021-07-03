package com.vad.budgets.util

import com.vad.budgets.util.Utility.format
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilityTest{
    @Test
    fun firstDateOfTheMonthInFormat() {
        val date = Utility.getFirstDateTimeOfMonth(5, 2021)
        assertEquals("Jun 01, 2021", date.format())
    }

    @Test
    fun lastDateOfTheMonthInFormat() {
        val date = Utility.getLastDateTimeOfMonth(5, 2021)
        assertEquals("Jun 30, 2021", date.format())
    }
}
