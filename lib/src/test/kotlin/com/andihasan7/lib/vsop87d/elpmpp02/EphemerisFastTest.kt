package com.andihasan7.lib.vsop87d.elpmpp02

import com.andihasan7.lib.vsop87d.elpmpp02.convertutil.ConvertUtil
import kotlin.test.Test

class EphemerisFastTest {

    @Test
    fun fastEphemerisTest() {

        val lat = -(7 + 1 / 60 + 44.6 / 3600)
        val lon = (106.0 + 33.0 / 60 + 27.8 / 3600)
        val hourD = (17 + 51.0 / 60 + 27.0 / 3600)

        val ephe = VSOP87DELPMPP02Fast(
            date = 2,
            month = 4,
            year = 2023,
            latitude = -7.029055555556,
            longitude = 106.557722222222,
            elevation = 52.685,
            timeZone = 7.0,
            hourDouble = 17.8575,
            checkDeltaT = true
        )

        val cv = ConvertUtil

        val jd = ephe.jd
        val deltaT = ephe.deltaT
        val jde = ephe.jde
        val jc = ephe.jc
        val jce = ephe.jce
        val jm = ephe.jm
        val jme = ephe.jme
        val hourDouble = (ephe.hourDouble) // use ?. for casting to other data type
        val nutationInLongitude = ephe.nutationInLongitude
        val nutationInObliquity = ephe.nutationInObliquity
        val meanObliquityOfEcliptic = ephe.meanObliquityOfEcliptic
        val trueObliquityOfEcliptic = ephe.trueObliquityOfEcliptic
        val earthHelioLon = ephe.earthHeliocentricLongitudeDegree
        val earthHelioLat = ephe.earthHeliocentricLatitudeDegree
        val sunTrueGeoLon = ephe.sunTrueGeocentricLongitude
        val sunTrueGeoLat = ephe.sunTrueGeocentricLatitude
        val abr = ephe.abr
        val sunAppaGeoLon = ephe.sunApparentGeocentricLongitude
        val sunGeoDistanceAU = ephe.sunGeocentricDistanceAU
        val sunGeoDistanceKM = ephe.sunGeocentricDistanceKM
        val sunGeoDistanceER = ephe.sunGeocentricDistanceER
        val sunAppaGeoSemidiameter = ephe.sunApparentGeoSemidiameter
        val sunAppaGeoRA = ephe.sunApparentGeoRightAscension
        val sunAppaGeoDec = ephe.sunApparentGeoDeclination

        println("Ephemeris VSOP87D & ELPMPP02 Full Periodic Terms (38.326)")
        println("")
        println("this is unit test")
        println("")
        println("Number of Date: ${ephe.numbDate}")
        println("Number of Month: ${ephe.numbMonth}")
        println("Name of Month: ${ephe.nameMonth}")
        println("Number of Year: ${ephe.numbYear}")
        println("Number of Day: ${ephe.numbDay}")
        println("Name of Day: ${ephe.nameDay}")
        println("Number of Pasaran: ${ephe.numbPasaran}")
        println("Name of Pasaran: ${ephe.namePasaran}")
        println("Hour Decimal: $hourDouble")
        println("Frac of Day: ${ephe.fracDay}")
        println("")
        println("Julian Day: $jd")
        println("Delta T: $deltaT")
        println("JDE: $jde")
        println("JC: $jc")
        println("JCE: $jce")
        println("JM: $jm")
        println("JME: $jme")
        println("Nutation in Lon: $nutationInLongitude, ${cv.toDegreeFullRound2(nutationInLongitude)}")
        println("Nutation in Obli: $nutationInObliquity, ${cv.toDegreeFullRound2(nutationInObliquity)}")
        println("Mean Obliquity: $meanObliquityOfEcliptic, ${cv.toDegreeFullRound2(meanObliquityOfEcliptic)}")
        println("True Obliquity: $trueObliquityOfEcliptic, ${cv.toDegreeFullRound2(trueObliquityOfEcliptic)}")
        println("")
        println("Earth Helio Lon: $earthHelioLon, ${cv.toDegreeFullRound2(earthHelioLon)}")
        println("Earth Helio Lat: $earthHelioLat, ${cv.toDegreeFullRound2(earthHelioLat)}")
        println("")
        println("Sun Geocentric Coor:")
        println("")
        println("Sun True Geo Lon: $sunTrueGeoLon, ${cv.toDegreeFullRound2(sunTrueGeoLon)}")
        println("Sun True Geo Lat: $sunTrueGeoLat, ${cv.toDegreeFullRound2(sunTrueGeoLat)}")
        println("Abration: $abr, ${cv.toDegreeFullRound2(abr)}")
        println("Sun Appa Geo Lon: $sunAppaGeoLon, ${cv.toDegreeFullRound2(sunAppaGeoLon)}")
        println("Sun Geo Distance AU: $sunGeoDistanceAU")
        println("Sun Geo Distance KM: $sunGeoDistanceKM")
        println("Sun Geo Distance ER: $sunGeoDistanceER")
        println("Sun Appa Geo Semidiameter: $sunAppaGeoSemidiameter, ${cv.toDegreeFullRound2(sunAppaGeoSemidiameter)}")
        println("Sun Appa Geo Right Ascen: $sunAppaGeoRA, ${cv.toDegreeFullRound2(sunAppaGeoRA)}")
        println("Sun Appa Geo Declination: $sunAppaGeoDec, ${cv.toDegreeFullRound2(sunAppaGeoDec)}")
    }
}