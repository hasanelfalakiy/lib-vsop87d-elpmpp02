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
        val newMoon = mp.moonPhase(1, 1446, PhaseType.NEWMOON)
        val dateNewMoon = TimeUtil.jdToGregorian<String>(newMoon, 7.0, DateFormat.DPDDMMYYM)
        val hourNewMoon = TimeUtil.jdToGregorian<Double>(newMoon, 7.0, DateFormat.HOUR_DOUBLE)
        println("New Moon: $dateNewMoon, ${ConvertUtil.toTimeFullRound2(hourNewMoon ?: 0.0)}")

        val firstMoon = mp.moonPhase(1, 1446, PhaseType.FIRSTQUARTER)
        val dateFirstMoon = TimeUtil.jdToGregorian<String>(firstMoon, 7.0, DateFormat.DPDDMMYYM)
        val hourFirstMoon = TimeUtil.jdToGregorian<Double>(firstMoon, 7.0, DateFormat.HOUR_DOUBLE)
        println("First Moon: $dateFirstMoon, ${ConvertUtil.toTimeFullRound2(hourFirstMoon ?: 0.0)}")

        val fullMoon = mp.moonPhase(1, 1446, PhaseType.FULLMOON)
        val dateFullMoon = TimeUtil.jdToGregorian<String>(fullMoon, 7.0, DateFormat.DPDDMMYYM)
        val hourFullMoon = TimeUtil.jdToGregorian<Double>(fullMoon, 7.0, DateFormat.HOUR_DOUBLE)
        println("Full Moon: $dateFullMoon, ${ConvertUtil.toTimeFullRound2(hourFullMoon ?: 0.0)}")

        val lastMoon = mp.moonPhase(1, 1446, PhaseType.LASTQUARTER)
        val dateLastMoon = TimeUtil.jdToGregorian<String>(lastMoon, 7.0, DateFormat.DPDDMMYYM)
        val hourLastMoon = TimeUtil.jdToGregorian<Double>(lastMoon, 7.0, DateFormat.HOUR_DOUBLE)
        println("Last Moon: $dateLastMoon, ${ConvertUtil.toTimeFullRound2(hourLastMoon ?: 0.0)}")
    }
}