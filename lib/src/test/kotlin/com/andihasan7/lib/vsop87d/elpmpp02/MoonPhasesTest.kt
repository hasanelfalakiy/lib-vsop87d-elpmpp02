package com.andihasan7.lib.vsop87d.elpmpp02

import com.andihasan7.lib.vsop87d.elpmpp02.convertutil.ConvertUtil
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DateFormat
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PhaseType
import com.andihasan7.lib.vsop87d.elpmpp02.moonphase.MoonPhase
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import kotlin.test.Test

class MoonPhasesTest {

    @Test
    fun moonPhasesTest() {

        val mp = MoonPhase
        val newMoon = mp.moonPhase(1, 1446, PhaseType.FIRSTQUARTER)
        val day = TimeUtil.jdToGregorian<String>(newMoon, 7.0, DateFormat.DAY_NAME)
        val pasaran = TimeUtil.jdToGregorian<String>(newMoon, 7.0, DateFormat.PASARAN_NAME)
        val date = TimeUtil.jdToGregorian<Int>(newMoon, 7.0, DateFormat.DATE)
        val month = TimeUtil.jdToGregorian<String>(newMoon, 7.0, DateFormat.MONTH_NAME)
        val year = TimeUtil.jdToGregorian<Int>(newMoon, 7.0, DateFormat.YEAR)
        val hour = TimeUtil.jdToGregorian<Double>(newMoon, 7.0, DateFormat.HOUR_DOUBLE)

        println("New Moon: $day $pasaran, $date $month $year, ${ConvertUtil.toTimeFullRound2(hour ?: 0.0)}")
    }
}