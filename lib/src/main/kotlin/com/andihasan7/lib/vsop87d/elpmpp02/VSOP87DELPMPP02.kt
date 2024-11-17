/**
 * This file is part of lib-vsop87d-elpmpp02.
 *
 * lib-vsop87d-elpmpp02 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * lib-vsop87d-elpmpp02 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with lib-vsop87d-elpmpp02.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 * @programmed by: Andi Hasan A
 * @github: https://github.com/hasanelfalakiy
 * 
 *
 */
 
package com.andihasan7.lib.vsop87d.elpmpp02

import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.deltaT
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DateFormat
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.earthposition.EarthPosition
import com.andihasan7.lib.vsop87d.elpmpp02.sunposition.SunPosition
import com.andihasan7.lib.vsop87d.elpmpp02.correction.Correction
import kotlin.mod

/**
* VSOP87DELPMPP02
* Complete Sun and Moon Ephemeris Data
*
* ```
*    date: Int = 1, // Gregorian date
*    month: Int = 1, // Gregorian month
*    year: Int = 2000, // Gregorian year
*    latitude: Double = 0.0, // observer latitude
*    longitude: Double = 0.0, // observer longitude
*    elevation: Double = 0.0, // observer elevation in meters
*    timeZone: Double = 0.0, // time zone
*    hourDouble: Double = 0.0, // decimal hour
*    temperature: Double = 10, // average annual local temperature (in °C)
*    pressure: Double = 1010, // annual average local air pressure (in millibars)
*    checkDeltaT: Boolean = true // choice to use deltaT or not, true = deltaT, false = ignore deltaT
* ```
*/
class VSOP87DELPMPP02(
    date: Int = 1, // Gregorian date
    month: Int = 1, // Gregorian month
    year: Int = 2000, // Gregorian year
    latitude: Double = 0.0, // observer latitude
    longitude: Double = 0.0, // observer longitude
    elevation: Double = 0.0, // observer elevation in meters
    timeZone: Double = 0.0, // time zone
    hourDouble: Double = 0.0, // decimal hour
    temperature: Double = 10.0, // average annual local temperature (in °C)
    pressure: Double = 1010.0, // annual average local air pressure (in millibars)
    checkDeltaT: Boolean = true // choice to use deltaT or not
) {
    
    
    /**
    * Julian Day
    */
    val jd = TimeUtil.gregorianToJD(date, month, year, hourDouble, timeZone)
    
    /**
    * Delta T
    */
    val deltaT: Double = if (checkDeltaT == true) {
        deltaT(jd)
    } else {
        0.0
    }
    
    /**
    * Number of Date
    */
    val numbDate: Int? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.DATE)
    
    /**
    * Number of Month
    */
    val numbMonth: Int? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.MONTH_INT)
    
    /**
    * Name of Month
    */
    val nameMonth: String? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.MONTH_NAME)
    
    /**
    * Number of Year
    */
    val numbYear: Int? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.YEAR)
    
    /**
    * Number of Day
    */
    val numbDay: Int? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.DAY_INT)
    
    /**
    * Name of Day
    */
    val nameDay: String? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.DAY_NAME)
    
    /**
    * Number of Pasaran
    */
    val numbPasaran: Int? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.PASARAN_INT)
    
    /**
    * Name of pasaran
    */
    val namePasaran: String? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.PASARAN_NAME)
    
    /**
    * Hour Double or Decimal
    */
    val hourDouble: Double? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.HOUR_DOUBLE)
    
    /**
    * Frac of Day
    */
    val fracDay: Double? = TimeUtil.jdToGregorian(jd, timeZone, DateFormat.FRAC_DAY)
    
    
    
    /**
    * Julian Day Ephemeris, JDE
    */
    val jde = TimeUtil.julianType(jd, deltaT, JulianType.JDE)
    
    /**
    * Julian Century (JC) for standart epoch 2000
    */
    val jc = TimeUtil.julianType(jd, deltaT, JulianType.JC)
    
    /**
    * Julian Century Ephemeris (JCE) for standart epoch 2000
    */
    val jce = TimeUtil.julianType(jd, deltaT, JulianType.JCE)
    
    /**
    * Julian Millennium (JM) for standart epoch 2000
    */
    val jm = TimeUtil.julianType(jd, deltaT, JulianType.JM)
    
    /**
    * Julian Millennium Ephemeris (JME) for standart epoch 2000
    */
    val jme = TimeUtil.julianType(jd, deltaT, JulianType.JME)
    
    /**
    * Nutation in Longitude, deltaPsi
    * model of IAU 2000B
    */
    val nutationInLongitude = Nutation.nutationInLonAndObliquity(jd, deltaT)[0]
    
    /**
    * Nutation of Obliquity, deltaEpsilon
    * model of IAU 2000B
    */
    val nutationInObliquity = Nutation.nutationInLonAndObliquity(jd, deltaT)[1]
    
    /**
    * Mean Obliquity of Ecliptic, epsilon zero
    */
    val meanObliquityOfEcliptic = Nutation.meanObliquityOfEcliptic(jd, deltaT)
    
    /**
    * True Obliquity of Ecliptic, epsilon
    */
    val trueObliquityOfEcliptic = Nutation.trueObliquityOfEcliptic(jd, deltaT)
    
    /**
    * Earth Heliocentric Longitude in radian
    */
    val earthHeliocentricLongitudeRadian = EarthPosition.earthHeliocentricLongitude(jd, deltaT, UnitType.RADIANS)
    
    /**
    * Earth Heliocentric Longitude in degree
    */
    val earthHeliocentricLongitudeDegree = EarthPosition.earthHeliocentricLongitude(jd, deltaT, UnitType.DEGREES)
    
    /**
    * Earth Heliocentric Latitude in radian
    */
    val earthHeliocentricLatitudeRadian = EarthPosition.earthHeliocentricLatitude(jd, deltaT, UnitType.RADIANS)
    
    /**
    * Earth Heliocentric Latitude in degree
    */
    val earthHeliocentricLatitudeDegree = EarthPosition.earthHeliocentricLatitude(jd, deltaT, UnitType.DEGREES)
    
    /**
    * Sun True Geocentric Longitude in degree
    */
    val sunTrueGeocentricLongitude = SunPosition.sunTrueGeocentricLongitude(jd, deltaT, UnitType.DEGREES)
    
    /**
    * Sun True Geocentric Latitude in degree
    */
    val sunTrueGeocentricLatitude = SunPosition.sunTrueGeocentricLatitude(jd, deltaT, UnitType.DEGREES)
    
    /**
    * Abration
    */
    val abr = Correction.abration(jd, deltaT) / 3600.0
    
    /**
    * Sun Apparent Geocentric Longitude in degree
    */
    val sunApparentGeocentricLongitude = SunPosition.sunApparentGeocentricLongitude(jd, deltaT, UnitType.DEGREES)
    
    /**
    * Sun True Geocentric Distance AU
    */
    val sunGeocentricDistanceAU = EarthPosition.earthRadiusVector(jd, deltaT, DistanceType.AU)
    
    /**
    * Sun True Geocentric Distance KM
    */
    val sunGeocentricDistanceKM = EarthPosition.earthRadiusVector(jd, deltaT, DistanceType.KM)
    
    /**
    * Sun True Geocentric Distance ER
    */
    val sunGeocentricDistanceER = EarthPosition.earthRadiusVector(jd, deltaT, DistanceType.ER)
    
    /**
    * Sun Apparent Geocentric Semidiameter in degree, s
    */
    val sunApparentGeoSemidiameter = SunPosition.sunApparentGeoSemidiameter(jd, deltaT)
    
    /**
    * Sun Apparent Geocentric Right Ascension in degree, a
    */
    val sunApparentGeoRightAscension = SunPosition.sunApparentGeoRightAscension(jd, deltaT)
    
    /**
    * Sun Apparent Geocentric Declination in degree, d
    */
    val sunApparentGeoDeclination = SunPosition.sunApparentGeoDeclination(jd, deltaT)
    
    /**
    * Greenwich Mean Sidereal Time default in degree, GMST, v0
    */
    val greenwichMeanSiderealTime = SunPosition.greenwichMeanSiderealTime(jd, UnitType.DEGREES)
    
    /**
    * Greenwich Apparent Sidereal Time, GAST, v
    */
    val greenwichApparentSiderealTime = SunPosition.greenwichApparentSiderealTime(jd, deltaT)
    
    /**
    * Local Apparent Sidereal Time, LAST, theta
    */
    val localApparentSiderealTime = SunPosition.localApparentSiderealTime(jd, longitude, deltaT, UnitType.DEGREES)
    
    /**
    * Sun Geocentric Greenwich Hour Angle, GHA, Ho
    */
    val sunGeoGreenwichHourAngle = SunPosition.sunGeoGreenwichHourAngle(jd, deltaT)
    
    /**
    * Sun Geocentric Local Hour Angle, LHA, H
    */
    val sunGeoLocalHourAngle = SunPosition.sunGeoLocalHourAngle(jd, longitude, deltaT, UnitType.DEGREES)
    
    /**
    * Sun Geocentric Azimuth, A
    */
    val sunGeoAzimuth = SunPosition.sunGeoAzimuth(jd, longitude, latitude, deltaT)
    
    /**
    * Sun Geocentric Altitude, h
    */
    val sunGeoAltitude = SunPosition.sunGeoAltitude(jd, longitude, latitude, deltaT)
    
    // Sun Topocentric Coordinate 
    
    /**
    * Sun Equatorial Horizontal Parallax, phi
    */
    val sunEquatorialHorizontalParallax = SunPosition.sunEquatorialHorizontalParallax(jd, deltaT)
    
    /**
    * term u in radian
    */
    val termU = Correction.termU(latitude)
    
    /**
    * term x in radian
    */
    val termX = Correction.termX(latitude, elevation)
    
    /**
    * term y in radian
    */
    val termY = Correction.termY(latitude, elevation)
    
    /**
    * Sun term n in radian
    */
    val sunTermN = SunPosition.sunTermN(jd, longitude, latitude, elevation, deltaT)
    
    /**
    * Parallax in the Sun Right Ascension, deltaAlpha
    */
    val parallaxInTheSunRightAscension = SunPosition.parallaxInTheSunRightAscension(jd, longitude, latitude, elevation, deltaT, UnitType.DEGREES)
    
    /**
    * Parallax in the Sun Altitude
    */
    val parallaxInTheSunAltitude = SunPosition.parallaxInTheSunAltitude(jd, longitude, latitude, elevation, deltaT, UnitType.DEGREES)
    
    /**
    * Atmospheric Refraction from Airless Altitude
    */
    val atmosphericRefractionFromAirlessAltitude = SunPosition.atmosphericRefractionFromAirlessAltitude(sunGeoAltitude, pressure, temperature)
    
    /**
    * Sun Topocentric Longitude, lambda apostrophe
    */
    val sunTopoLongitude = SunPosition.sunTopoLongitude(jd, longitude, latitude, elevation, deltaT)
    
    
    
}
