package com.andihasan7.lib.vsop87d.elpmpp02

import com.andihasan7.lib.vsop87d.elpmpp02.convertutil.ConvertUtil
import com.andihasan7.lib.vsop87d.elpmpp02.moonsighting.MoonSighting
import kotlin.test.Test

class MoonSightingTest {

    @Test
    fun moonSightingTest() {

        val ms = MoonSighting(
            monthOfHijri = 9,
            yearOfHijri = 1446,
            longitude = 111.4333333334,
            latitude = -7.43333333334,
            elevation = 150.0,
            timeZone = 7.0,
            addDate = 0,
            checkDeltaT = true
        )

        val jdNewMoon = ms.jdGeoNewMoonCor
        val dateNewMoon = ms.dateNewMoon
        val dateNewMoonAA = ms.dateNewMoonAA
        val hourNewMoon = ms.hourGeoNewMoon
        val hourNewMoonAA = ms.hourGeoNewMoonAA
        val utcHourNewMoon = ms.utcGeoHourNewMoon
        val dateIntNM = ms.dateNMInt
        val monthIntNM = ms.monthNMInt
        val yearIntNM = ms.yearNMInt
        val maghribNM = ms.maghribLocalDateNewMoon ?: 0.0
        val moonGeoAltitudeDMS = ms.moonGeoAltitudeDMS
        val moonAirlessTopoAltUpperDMS = ms.moonAirlessTopoAltitudeUpperLimbDMS
        val moonAirlessTopoAltCenterDMS = ms.moonAirlessTopoAltitudeCenterLimbDMS
        val moonAirlessTopoAltLowerDMS = ms.moonAirlessTopoAltitudeLowerLimbDMS
        val moonAppaTopoAltUpperDMS = ms.moonAppaTopoAltUpperLimbDMS
        val moonAppaTopoAltCenterDMS = ms.moonAppaTopoAltCenterLimbDMS
        val moonAppaTopoAltLowerDMS = ms.moonAppaTopoAltLowerLimbDMS

        println("jd astronomical algorithm: ${ms.jdNewMoonAstronomicalAlgorithm}")
        println("")
        println("JD New Moon/Ijtima: $jdNewMoon")
        println("New Moon/Ijtima': $dateNewMoon, ${ConvertUtil.toTimeFullRound2(hourNewMoon ?: 0.0)} LT, ${ConvertUtil.toTimeFullRound2(utcHourNewMoon ?: 0.0)} UTC")
        println("New Moon/Ijtima' AA: $dateNewMoonAA, ${ConvertUtil.toTimeFullRound2(hourNewMoonAA ?: 0.0)} LT, ${ConvertUtil.toTimeFullRound2(utcHourNewMoon ?: 0.0)} UTC")
        println("date month year NM: $dateIntNM $monthIntNM $yearIntNM")
        println("maghrib NM: $maghribNM, ${ConvertUtil.toTimeFullRound2(maghribNM)}")
        println("")
        println("moon geo alt: $moonGeoAltitudeDMS")
        println("moon airless topo alt upper: $moonAirlessTopoAltUpperDMS")
        println("moon airless topo alt center: $moonAirlessTopoAltCenterDMS")
        println("moon airless topo alt lower: $moonAirlessTopoAltLowerDMS")
        println("")
        println("moon appa topo alt upper: $moonAppaTopoAltUpperDMS")
        println("moon appa topo alt center: $moonAppaTopoAltCenterDMS")
        println("moon appa topo alt lower: $moonAppaTopoAltLowerDMS")

    }
}