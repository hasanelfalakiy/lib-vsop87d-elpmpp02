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
 */
 
package com.andihasan7.lib.vsop87d.elpmpp02

import com.andihasan7.lib.vsop87d.elpmpp02.convertutil.ConvertUtil
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DateFormat
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PositionType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.SunAltType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.earthposition.EarthPosition
import com.andihasan7.lib.vsop87d.elpmpp02.sunposition.SunPosition
import com.andihasan7.lib.vsop87d.elpmpp02.correction.Correction
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.DeltaT

/**
* VSOP87D
* Complete Sun Ephemeris Data
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
class VSOP87D(
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
        DeltaT.deltaT(jd)
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
     * Nutation in Longitude DMS, deltaPsi
     * model of IAU 2000B
     */
    val nutationInLongitudeDMS = ConvertUtil.toDegreeFullRound2(nutationInLongitude)
    
    /**
    * Nutation of Obliquity, deltaEpsilon
    * model of IAU 2000B
    */
    val nutationInObliquity = Nutation.nutationInLonAndObliquity(jd, deltaT)[1]

    /**
     * Nutation of Obliquity DMS, deltaEpsilon
     * model of IAU 2000B
     */
    val nutationInObliquityDMS = ConvertUtil.toDegreeFullRound2(nutationInObliquity)
    
    /**
    * Mean Obliquity of Ecliptic, epsilon zero
    */
    val meanObliquityOfEcliptic = Nutation.meanObliquityOfEcliptic(jd, deltaT)

    /**
     * Mean Obliquity of Ecliptic DMS, epsilon zero
     */
    val meanObliquityOfEclipticDMS = ConvertUtil.toDegreeFullRound2(meanObliquityOfEcliptic)
    
    /**
    * True Obliquity of Ecliptic, epsilon
    */
    val trueObliquityOfEcliptic = Nutation.trueObliquityOfEcliptic(jd, deltaT)

    /**
     * True Obliquity of EclipticDMS, epsilon
     */
    val trueObliquityOfEclipticDMS = ConvertUtil.toDegreeFullRound2(trueObliquityOfEcliptic)
    
    /**
    * Earth Heliocentric Longitude in radian
    */
    val earthHeliocentricLongitudeRadian = EarthPosition.earthHeliocentricLongitude(jd, deltaT, UnitType.RADIANS)
    
    /**
    * Earth Heliocentric Longitude in degree
    */
    val earthHeliocentricLongitudeDegree = EarthPosition.earthHeliocentricLongitude(jd, deltaT, UnitType.DEGREES)

    /**
     * Earth Heliocentric Longitude DMS in degree
     */
    val earthHeliocentricLongitudeDegreeDMS = ConvertUtil.toDegreeFullRound2(earthHeliocentricLongitudeDegree)
    
    /**
    * Earth Heliocentric Latitude in radian
    */
    val earthHeliocentricLatitudeRadian = EarthPosition.earthHeliocentricLatitude(jd, deltaT, UnitType.RADIANS)
    
    /**
    * Earth Heliocentric Latitude in degree
    */
    val earthHeliocentricLatitudeDegree = EarthPosition.earthHeliocentricLatitude(jd, deltaT, UnitType.DEGREES)

    /**
     * Earth Heliocentric Latitude DMS in degree
     */
    val earthHeliocentricLatitudeDegreeDMS = ConvertUtil.toDegreeFullRound2(earthHeliocentricLatitudeDegree)
    
    /**
    * Sun True Geocentric Longitude in degree
    */
    val sunTrueGeocentricLongitude = SunPosition.sunTrueGeocentricLongitude(jd, deltaT, UnitType.DEGREES)

    /**
     * Sun True Geocentric Longitude DMS in degree
     */
    val sunTrueGeocentricLongitudeDMS = ConvertUtil.toDegreeFullRound2(sunTrueGeocentricLongitude)
    
    /**
    * Sun True Geocentric Latitude in degree
    */
    val sunTrueGeocentricLatitude = SunPosition.sunTrueGeocentricLatitude(jd, deltaT, UnitType.DEGREES)

    /**
     * Sun True Geocentric Latitude DMS in degree
     */
    val sunTrueGeocentricLatitudeDMS = ConvertUtil.toDegreeFullRound2(sunTrueGeocentricLatitude)
    
    /**
    * Abration
    */
    val abr = Correction.abration(jd, deltaT) / 3600.0

    /**
     * Abration DMS
     */
    val abrDMS = ConvertUtil.toDegreeFullRound2(abr)
    
    /**
    * Sun Apparent Geocentric Longitude in degree
    */
    val sunApparentGeocentricLongitude = SunPosition.sunApparentGeocentricLongitude(jd, deltaT, UnitType.DEGREES)

    /**
     * Sun Apparent Geocentric Longitude DMS in degree
     */
    val sunApparentGeocentricLongitudeDMS = ConvertUtil.toDegreeFullRound2(sunApparentGeocentricLongitude)
    
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
     * Sun Apparent Geocentric Semidiameter DMS in degree, s
     */
    val sunApparentGeoSemidiameterDMS = ConvertUtil.toDegreeFullRound2(sunApparentGeoSemidiameter)
    
    /**
    * Sun Apparent Geocentric Right Ascension in degree, a
    */
    val sunApparentGeoRightAscension = SunPosition.sunApparentGeoRightAscension(jd, deltaT)

    /**
     * Sun Apparent Geocentric Right Ascension DMS in degree, a
     */
    val sunApparentGeoRightAscensionDMS = ConvertUtil.toDegreeFullRound2(sunApparentGeoRightAscension)

    /**
     * Sun Apparent Geocentric Declination in degree, d
     */
    val sunApparentGeoDeclination = SunPosition.sunApparentGeoDeclination(jd, deltaT)

    /**
     * Sun Apparent Geocentric Declination DMS in degree, d
     */
    val sunApparentGeoDeclinationDMS = ConvertUtil.toDegreeFullRound2(sunApparentGeoDeclination)

    /**
    * Greenwich Mean Sidereal Time default in degree, GMST, v0
    */
    val greenwichMeanSiderealTime = SunPosition.greenwichMeanSiderealTime(jd, UnitType.DEGREES)

    /**
     * Greenwich Mean Sidereal Time DMS default in degree, GMST, v0
     */
    val greenwichMeanSiderealTimeDMS = ConvertUtil.toDegreeFullRound2(greenwichMeanSiderealTime)

    /**
    * Greenwich Apparent Sidereal Time, GAST, v
    */
    val greenwichApparentSiderealTime = SunPosition.greenwichApparentSiderealTime(jd, deltaT)

    /**
     * Greenwich Apparent Sidereal Time DMS, GAST, v
     */
    val greenwichApparentSiderealTimeDMS = ConvertUtil.toDegreeFullRound2(greenwichApparentSiderealTime)

    /**
    * Local Apparent Sidereal Time, LAST, theta
    */
    val localApparentSiderealTime = SunPosition.localApparentSiderealTime(jd, longitude, deltaT, UnitType.DEGREES)

    /**
     * Local Apparent Sidereal Time DMS, LAST, theta
     */
    val localApparentSiderealTimeDMS = ConvertUtil.toDegreeFullRound2(localApparentSiderealTime)
    
    /**
    * Sun Geocentric Greenwich Hour Angle, GHA, Ho
    */
    val sunGeoGreenwichHourAngle = SunPosition.sunGeoGreenwichHourAngle(jd, deltaT)

    /**
     * Sun Geocentric Greenwich Hour Angle DMS, GHA, Ho
     */
    val sunGeoGreenwichHourAngleDMS = ConvertUtil.toDegreeFullRound2(sunGeoGreenwichHourAngle)
    
    /**
    * Sun Geocentric Local Hour Angle, LHA, H
    */
    val sunGeoLocalHourAngle = SunPosition.sunGeoLocalHourAngle(jd, longitude, deltaT, UnitType.DEGREES)

    /**
     * Sun Geocentric Local Hour Angle DMS, LHA, H
     */
    val sunGeoLocalHourAngleDMS = ConvertUtil.toDegreeFullRound2(sunGeoLocalHourAngle)
    
    /**
    * Sun Geocentric Azimuth, A
    */
    val sunGeoAzimuth = SunPosition.sunGeoAzimuth(jd, longitude, latitude, deltaT)

    /**
     * Sun Geocentric Azimuth DMS, A
     */
    val sunGeoAzimuthDMS = ConvertUtil.toDegreeFullRound2(sunGeoAzimuth)
    
    /**
    * Sun Geocentric Altitude, h
    */
    val sunGeoAltitude = SunPosition.sunGeoAltitude(jd, longitude, latitude, deltaT)

    /**
     * Sun Geocentric Altitude DMS, h
     */
    val sunGeoAltitudeDMS = ConvertUtil.toDegreeFullRound2(sunGeoAltitude)
    
    // Sun Topocentric Coordinate 
    
    /**
    * Sun Equatorial Horizontal Parallax, phi
    */
    val sunEquatorialHorizontalParallax = SunPosition.sunEquatorialHorizontalParallax(jd, deltaT)

    /**
     * Sun Equatorial Horizontal Parallax DMS, phi
     */
    val sunEquatorialHorizontalParallaxDMS = ConvertUtil.toDegreeFullRound2(sunEquatorialHorizontalParallax)
    
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
     * Parallax in the Sun Right Ascension DMS, deltaAlpha
     */
    val parallaxInTheSunRightAscensionDMS = ConvertUtil.toDegreeFullRound2(parallaxInTheSunRightAscension)

    /**
    * Parallax in the Sun Altitude
    */
    val parallaxInTheSunAltitude = SunPosition.parallaxInTheSunAltitude(jd, longitude, latitude, elevation, deltaT, UnitType.DEGREES)

    /**
     * Parallax in the Sun Altitude DMS
     */
    val parallaxInTheSunAltitudeDMS = ConvertUtil.toDegreeFullRound2(parallaxInTheSunAltitude)

    /**
    * Atmospheric Refraction from Airless Altitude
    */
    val atmosphericRefractionFromAirlessAltitude = Correction.atmosphericRefractionFromAirlessAltitude(sunGeoAltitude, temperature, pressure)

    /**
     * Atmospheric Refraction from Airless Altitude DMS
     */
    val atmosphericRefractionFromAirlessAltitudeDMS = ConvertUtil.toDegreeFullRound2(atmosphericRefractionFromAirlessAltitude)

    /**
    * Sun Topocentric Longitude, lambda apostrophe
    */
    val sunTopoLongitude = SunPosition.sunTopoLongitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Longitude DMS, lambda apostrophe
     */
    val sunTopoLongitudeDMS = ConvertUtil.toDegreeFullRound2(sunTopoLongitude)

    /**
    * Sun Topocentric Latitude, beta apostrophe
    */
    val sunTopoLatitude = SunPosition.sunTopoLatitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Latitude DMS, beta apostrophe
     */
    val sunTopoLatitudeDMS = ConvertUtil.toDegreeFullRound2(sunTopoLatitude)

    /**
    * Sun Topocentric Right Ascension, alpha apostrophe
    */
    val sunTopoRightAscension = SunPosition.sunTopoRightAscension(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Right Ascension DMS, alpha apostrophe
     */
    val sunTopoRightAscensionDMS = ConvertUtil.toDegreeFullRound2(sunTopoRightAscension)

    /**
    * Sun Topocentric Declination, delta apostrophe
    */
    val sunTopoDeclination = SunPosition.sunTopoDeclination(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Declination DMS, delta apostrophe
     */
    val sunTopoDeclinationDMS = ConvertUtil.toDegreeFullRound2(sunTopoDeclination)

    /**
    * Sun Topocentric Local Hour Angle default in degree, H apostrophe
    */
    val sunTopoLocalHourAngle = SunPosition.sunTopoLocalHourAngle(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Local Hour Angle DMS default in degree, H apostrophe
     */
    val sunTopoLocalHourAngleDMS = ConvertUtil.toDegreeFullRound2(sunTopoLocalHourAngle)

    /**
    * Sun Topocentric Azimuth, A apostrophe 
    */
    val sunTopoAzimuth = SunPosition.sunTopoAzimuth(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Azimuth DMS, A apostrophe
     */
    val sunTopoAzimuthDMS = ConvertUtil.toDegreeFullRound2(sunTopoAzimuth)

    /**
    * Sun Airless Topocentric Altitude, h'
    */
    val sunAirlessTopoAltitude = SunPosition.sunTopoAltitude(jd, longitude, latitude, elevation, deltaT, SunAltType.AIRLESS, UnitType.DEGREES, temperature, pressure)

    /**
     * Sun Airless Topocentric Altitude DMS, h'
     */
    val sunAirlessTopoAltitudeDMS = ConvertUtil.toDegreeFullRound2(sunAirlessTopoAltitude)

    /**
    * Sun Apparent Topocentric Altitude, ha'
    */
    val sunApparentTopoAltitude = SunPosition.sunTopoAltitude(jd, longitude, latitude, elevation, deltaT, SunAltType.APPARENT, UnitType.DEGREES, temperature, pressure)

    /**
     * Sun Apparent Topocentric Altitude DMS, ha'
     */
    val sunApparentTopoAltitudeDMS = ConvertUtil.toDegreeFullRound2(sunApparentTopoAltitude)

    /**
    * Sun Observer Topocentric Altitude, ho'
    */
    val sunObserverTopoAltitude = SunPosition.sunTopoAltitude(jd, longitude, latitude, elevation, deltaT, SunAltType.OBSERVER, UnitType.DEGREES, temperature, pressure)

    /**
     * Sun Observer Topocentric Altitude DMS, ho'
     */
    val sunObserverTopoAltitudeDMS = ConvertUtil.toDegreeFullRound2(sunObserverTopoAltitude)

    /**
    * Sun Topocentric Semidiameter, s apostrophe
    */
    val sunTopoSemidiameter = SunPosition.sunTopoSemidiameter(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Semidiameter DMS, s apostrophe
     */
    val sunTopoSemidiameterDMS = ConvertUtil.toDegreeFullRound2(sunTopoSemidiameter)

    /**
    * Equation of Time, e
    */
    val equationOfTime = SunPosition.equationOfTime(jd, deltaT)

    /**
     * Equation of Time HMS, e
     */
    val equationOfTimeHMS = ConvertUtil.toCounterMMSS2(equationOfTime)

}
