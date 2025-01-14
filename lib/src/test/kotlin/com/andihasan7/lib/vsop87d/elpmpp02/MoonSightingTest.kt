package com.andihasan7.lib.vsop87d.elpmpp02

import com.andihasan7.lib.vsop87d.elpmpp02.convertutil.ConvertUtil
import com.andihasan7.lib.vsop87d.elpmpp02.moonsighting.MoonSighting
import kotlin.test.Test

class MoonSightingTest {

    @Test
    fun moonSightingTest() {

        val ms = MoonSighting(
            monthOfHijri = 1,
            yearOfHijri = 1446,
            longitude = 111.4333333334,
            latitude = -7.43333333334,
            elevation = 150.0,
            timeZone = 7.0,
            addDate = 1,
            checkDeltaT = true
        )

        val jdNewMoon = ms.jdGeoNewMoonCor
        val deltaT = ms.deltaT
        val dateNewMoonGeo = ms.dateNewMoonGeo
        val dateNewMoonTopo = ms.dateNewMoonTopo
        val dateNewMoonAA = ms.dateNewMoonAA
        val hourNewMoonGeo = ms.hourGeoNewMoon
        val hourNewMoonGeoHMS = ms.hourGeoNewMoonHMS
        val hourNewMoonTopo = ms.hourTopoNewMoon
        val hourNewMoonTopoHMS = ms.hourTopoNewMoonHMS
        val hourNewMoonAA = ms.hourGeoNewMoonAA
        val hourNewMoonAAHMS = ms.hourGeoNewMoonAAHMS
        val utcHourNewMoonGeo = ms.utcGeoHourNewMoon
        val utcHourNewMoonGeoHMS = ms.utcGeoHourNewMoonHMS
        val utcHourNewMoonTopo = ms.utcTopoHourNewMoon
        val utcHourNewMoonTopoHMS = ms.utcTopoHourNewMoonHMS
        val dayNewMoonGeo = ms.dayNewMoonGeo
        val pasaranNewMoonGeo = ms.pasaranNewMoonGeo
        val dateIntNM = ms.dateNMInt
        val monthIntNM = ms.monthNMInt
        val monthNMName = ms.monthNMName
        val yearIntNM = ms.yearNMInt
        val lonGeoNewMoon = ms.lonGeoNewMoon
        val lonGeoNewMoonDMS = ms.lonGeoNewMoonDMS
        val lonTopoNewMoon = ms.lonTopoNewMoon
        val lonTopoNewMoonDMS = ms.lonTopoNewMoonDMS
        val maghribNM = ms.maghribLocalDateNewMoon ?: 0.0
        val maghribNMHMS = ms.maghribLocalDateNewMoonHMS
        val moonGeoAltitudeDMS = ms.moonGeoAltitudeDMS
        val moonAirlessTopoAltUpperDMS = ms.moonAirlessTopoAltitudeUpperLimbDMS
        val moonAirlessTopoAltCenterDMS = ms.moonAirlessTopoAltitudeCenterLimbDMS
        val moonAirlessTopoAltLowerDMS = ms.moonAirlessTopoAltitudeLowerLimbDMS
        val moonAppaTopoAltUpperDMS = ms.moonAppaTopoAltUpperLimbDMS
        val moonAppaTopoAltCenterDMS = ms.moonAppaTopoAltCenterLimbDMS
        val moonAppaTopoAltLowerDMS = ms.moonAppaTopoAltLowerLimbDMS
        val moonObservTopoAltUpperDMS = ms.moonObservedTopoAltUpperLimbDMS
        val moonObservTopoAltCenterDMS = ms.moonObserrvedTopoAltCenterLimbDMS
        val moonObservTopoAltLowerDMS = ms.moonObservedTopoAltLowerLimbDMS
        val sunTopoLon = ms.sunTopoLongitude
        val sunTopoLonDMS = ms.sunTopoLongitudeDMS
        val sunTopoLat = ms.sunTopoLatitude
        val sunTopoLatDMS = ms.sunTopoLatitudeDMS
        val moonTopoLonDMS = ms.moonTopoLongitudeDMS
        val moonTopoLatDMS = ms.moonTopoLatitudeDMS
        val sunTopoRA = ms.sunTopoRightAscension
        val moonTopoRA = ms.moonTopoRightAscension
        val sunTopoRAHour = ms.sunTopoRightAscensionHMS
        val moonTopoRAHour = ms.moonTopoRightAscensionHMS
        val sunTopoDeclination = ms.sunTopoDeclination
        val moonTopoDeclination = ms.moonTopoDeclinationDMS
        val sunTopoAz = ms.sunTopoAzimuth
        val sunTopoAlt = ms.sunTopoAltitude
        val moonTopoAzDMS = ms.moonTopoAzimuthDMS
        val moonTopoIlluminatedPercent = ms.moonTopoIlluminatedPercent2
        val moonTopoSemidiameterDMS = ms.moonTopoSemidiameterDMS
        val moonSunGeoElongationDMS = ms.moonSunGeoElongationDMS
        val moonSunTopoElongationDMS = ms.moonSunTopoElongationDMS
        val moonGeoDistanceKM = ms.moonGeoDistanceKM2
        
        val diffRASunMoon = ms.diffRASunMoon
        val hilalDuration = ms.hilalDurationOld
        val moonSet = ms.moonSetOld
        val moonAge = ms.moonAgeOld
        val nurulHilal = ms.nurulHilal
        val mrg = ms.mrg
        val mrgString = ms.mrgString
        val isVisibleNeoMabims = ms.isVisibleIRNU
        
        val crecentWidth = ms.crecentWidthTopo
        val moonTopoPosition = ms.moonTopoPosition
        val moonTopoPositionString = ms.moonTopoPositionString
        //val moonDuration = ms.moonDuration
        val moonBestTime = ms.moonBestTime
        val qOdeh = ms.qOdeh3
        val moonHorizontalParallaxDMS = ms.moonHorizontalParallaxDMS
        val moonTopoAzimuthSetDMS = ms.moonTopoAzimuthSetDMS
        val predictionNeoMabims = ms.predictionIRNU
        
        
        println("jd astronomical algorithm: ${ms.jdNewMoonAstronomicalAlgorithm}")
        println("New Moon/Ijtima' AA: $dateNewMoonAA, $hourNewMoonAAHMS LT")
        println("")
        println("JD New Moon/Ijtima: $jdNewMoon")
        println("JD New Moon with iteration: ${ms.jdGhurubSyamsPlus}")
        println("")
        println("day pasaran: $dayNewMoonGeo $pasaranNewMoonGeo")
        println("month name: $monthNMName")
        println("")
        println("New Moon/Ijtima' geo : $dateNewMoonGeo, ${ConvertUtil.toTimeFullRound2(hourNewMoonGeo ?: 0.0)} LT, ${ConvertUtil.toTimeFullRound2(utcHourNewMoonGeo ?: 0.0)} UTC")
        println("New Moon/Ijtima' topo: $dateNewMoonTopo, ${ConvertUtil.toTimeFullRound2(hourNewMoonTopo ?: 0.0)} LT, ${ConvertUtil.toTimeFullRound2(utcHourNewMoonTopo ?: 0.0)} UTC")
        println("deltaT: $deltaT, ${ms.deltaT2}")
        println("Lon Geo New Moon : $lonGeoNewMoon, $lonGeoNewMoonDMS")
        println("Lon Topo New Moon: $lonTopoNewMoon, $lonTopoNewMoonDMS")
        println("")
        
        println("date month year NM: $dateIntNM $monthIntNM $yearIntNM")
        println("maghrib NM: $maghribNM, $maghribNMHMS LT")
        println("")
        println("moon geo/hakiki alt: $moonGeoAltitudeDMS")
        println("sun topo alt: $sunTopoAlt, ${ms.sunTopoAltitudeDMS}")
        println("")
        println("T. moon airless topo alt upper: $moonAirlessTopoAltUpperDMS")
        println("moon airless topo alt center  : $moonAirlessTopoAltCenterDMS")
        println("moon airless topo alt lower   : $moonAirlessTopoAltLowerDMS")
        println("")
        println("moon appa topo alt upper : $moonAppaTopoAltUpperDMS")
        println("moon appa topo alt center: $moonAppaTopoAltCenterDMS")
        println("moon appa topo alt lower : $moonAppaTopoAltLowerDMS")
        println("")
        println("moon observed topo alt upper : $moonObservTopoAltUpperDMS")
        println("moon observed topo alt center: $moonObservTopoAltCenterDMS")
        println("moon observed topo alt lower : $moonObservTopoAltLowerDMS")
        println("")
        println("sun topo longitude: $sunTopoLon, $sunTopoLonDMS")
        println("sun topo latitude : $sunTopoLat, $sunTopoLatDMS")
        println("moon topo lon     : $moonTopoLonDMS")
        println("moon topo lat     : $moonTopoLatDMS")
        println("")
        println("Sun RA      : $sunTopoRA, ${ms.sunTopoRightAscensionDMS}")
        println("Moon RA     : $moonTopoRA, ${ms.moonTopoRightAscensionDMS}")
        println("Sun RA Hour : $sunTopoRAHour, ${ms.sunTopoRightAscensionHMS}")
        println("Moon RA Hour: $moonTopoRAHour, ${ms.moonTopoRightAscensionHMS}")
        println("")
        println("sun topo dec : $sunTopoDeclination, ${ms.sunTopoDeclinationDMS}")
        println("moon topo dec: $moonTopoDeclination")
        println("sun topo az  : $sunTopoAz, ${ms.sunTopoAzimuthDMS}")
        println("moon topo az : $moonTopoAzDMS")
        println("")
        println("moon topo illuminated : $moonTopoIlluminatedPercent %")
        println("moon topo semidiameter: $moonTopoSemidiameterDMS")
        println("moon geo elongation   : $moonSunGeoElongationDMS")
        println("moon topo elongation  : $moonSunTopoElongationDMS")
        println("")
        println("diff RA: $diffRASunMoon, ${ConvertUtil.toDegreeFullRound2(diffRASunMoon)}")
        println("hilal duration: $hilalDuration, ${ms.hilalDurationOldHMS}")
        println("moon set: $moonSet, ${ms.moonSetOldHMS}")
        println("moon age: $moonAge, ${ms.moonAgeOldHMS}")
        println("")
        println("crecent width : $crecentWidth, ${ms.crecentWidthTopoDMS}")
        println("moon topo azimuth set: $moonTopoAzimuthSetDMS")
        // println("moon duration muktsul: $moonDuration, ${ConvertUtil.toDegreeFullRound2(moonDuration)}")
        println("moon best time: $moonBestTime, ${ms.moonBestTimeHMS}")
        println("range q Odeh: $qOdeh")
        println("moon position : $moonTopoPosition, ${ms.moonTopoPositionDMS}")
        println("moon position string: $moonTopoPositionString")
        println("prediction IRNU/neo mabims: $predictionNeoMabims")
        println("is visible: $isVisibleNeoMabims")
        println("${ms.dayOfIRNUPrediction} ${ms.pasaranOfIRNUPrediction}, ${ms.dateOfIRNUPrediction} ${ms.monthOfIRNUPredictionString} ${ms.yearOfIRNUPrediction}")
        println("")
        println("moon eq horizontal parallax: $moonHorizontalParallaxDMS")
        println("moon geo distance km: $moonGeoDistanceKM")
        println("nurul hilal: $nurulHilal")
        println("mrg: $mrg, $mrgString")
        println("")

    }
}