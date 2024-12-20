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
import com.andihasan7.lib.vsop87d.elpmpp02.enum.MoonAltType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PositionType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.SunAltType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.earthposition.EarthPosition
import com.andihasan7.lib.vsop87d.elpmpp02.sunposition.SunPosition
import com.andihasan7.lib.vsop87d.elpmpp02.correction.Correction
import com.andihasan7.lib.vsop87d.elpmpp02.moonposition.MoonPosition
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.DeltaT

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

    // Moon Data
    /**
    * Moon True Geocentric Longitude in degree, lambda apostrophe
    */
    val moonTrueGeocentricLongitude = MoonPosition.moonGeocentricLongitude(jd, deltaT, PositionType.TRUE, UnitType.DEGREES)

    /**
     * Moon True Geocentric Longitude DMS in degree, lambda apostrophe
     */
    val moonTrueGeocentricLongitudeDMS = ConvertUtil.toDegreeFullRound2(moonTrueGeocentricLongitude)

    /**
    * Moon Apparent Geocentric Longitude in degree, lambda
    */
    val moonApparentGeocentricLongitude = MoonPosition.moonGeocentricLongitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Longitude DMS in degree, lambda
     */
    val moonApparentGeocentricLongitudeDMS = ConvertUtil.toDegreeFullRound2(moonApparentGeocentricLongitude)

    /**
    * Moon True Geocentric Latitude in degree, beta apostrophe
    */
    val moonTrueGeocentricLatitude = MoonPosition.moonGeocentricLatitude(jd, deltaT, PositionType.TRUE, UnitType.DEGREES)

    /**
     * Moon True Geocentric Latitude DMS in degree, beta apostrophe
     */
    val moonTrueGeocentricLatitudeDMS = ConvertUtil.toDegreeFullRound2(moonTrueGeocentricLatitude)

    /**
    * Moon Apparent Geocentric Latitude in degree, beta
    */
    val moonApparentGeocentricLatitude = MoonPosition.moonGeocentricLatitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Latitude DMS in degree, beta
     */
    val moonApparentGeocentricLatitudeDMS = ConvertUtil.toDegreeFullRound2(moonApparentGeocentricLatitude)

    /**
    * Moon True Geocentric Distance KM
    */
    val moonTrueGeocentricDistanceKM = MoonPosition.moonGeocentricDistance(jd, deltaT, PositionType.TRUE, DistanceType.KM)
    
    /**
    * Moon True Geocentric Distance AU
    */
    val moonTrueGeocentricDistanceAU = MoonPosition.moonGeocentricDistance(jd, deltaT, PositionType.TRUE, DistanceType.AU)
    
    /**
    * Moon True Geocentric Distance ER
    */
    val moonTrueGeocentricDistanceER = MoonPosition.moonGeocentricDistance(jd, deltaT, PositionType.TRUE, DistanceType.ER)
    
    /**
    * Moon Apparent Geocentric Distance KM
    */
    val moonAppaGeocentricDistanceKM = MoonPosition.moonGeocentricDistance(jd, deltaT, PositionType.APPARENT, DistanceType.KM)
    
    /**
    * Moon Apparent Geocentric Distance AU
    */
    val moonAppaGeocentricDistanceAU = MoonPosition.moonGeocentricDistance(jd, deltaT, PositionType.APPARENT, DistanceType.AU)
    
    /**
    * Moon Apparent Geocentric Distance ER
    */
    val moonAppaGeocentricDistanceER = MoonPosition.moonGeocentricDistance(jd, deltaT, PositionType.APPARENT, DistanceType.ER)
    
    /**
    * Moon Apparent Geocentric Right Ascension, alpha
    */
    val moonAppaGeocentricRightAscension = MoonPosition.moonAppaGeocentricRightAscension(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Right Ascension DMS, alpha
     */
    val moonAppaGeocentricRightAscensionDMS = ConvertUtil.toDegreeFullRound2(moonAppaGeocentricRightAscension)

    /**
    * Moon Apparent Geocentric Declination, delta
    */
    val moonAppaGeocentricDeclination = MoonPosition.moonAppaGeocentricDeclination(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Declination DMS, delta
     */
    val moonAppaGeocentricDeclinationDMS = ConvertUtil.toDegreeFullRound2(moonAppaGeocentricDeclination)

    /**
    * Moon Geocentric Greenwich Hour Angle default in degree
    */
    val moonGeoGreenwichHourAngle = MoonPosition.moonGeoGreenwichHourAngle(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Greenwich Hour Angle DMS default in degree
     */
    val moonGeoGreenwichHourAngleDMS = ConvertUtil.toDegreeFullRound2(moonGeoGreenwichHourAngle)

    /**
    * Moon Geocentric Local Hour Angle default in degree
    */
    val moonGeoLocalHourAngle = MoonPosition.moonGeoLocalHourAngle(jd, longitude, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Local Hour Angle DMS default in degree
     */
    val moonGeoLocalHourAngleDMS = ConvertUtil.toDegreeFullRound2(moonGeoLocalHourAngle)

    /**
    * Moon Geocentric Azimuth default in degree, A
    */
    val moonGeoAzimuth = MoonPosition.moonGeoAzimuth(jd, longitude, latitude, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Azimuth DMS default in degree, A
     */
    val moonGeoAzimuthDMS = ConvertUtil.toDegreeFullRound2(moonGeoAzimuth)

    /**
    * Moon Geocentric Altitude default in degree, h
    */
    val moonGeoAltitude = MoonPosition.moonGeoAltitude(jd, longitude, latitude, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Altitude DMS default in degree, h
     */
    val moonGeoAltitudeDMS = ConvertUtil.toDegreeFullRound2(moonGeoAltitude)

    /**
    * Moon Equatorial Horizontal Parallax default in degree, phi
    */
    val moonEquatorialHorizontalParallax = MoonPosition.moonEquatorialHorizontalParallax(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Equatorial Horizontal Parallax DMS default in degree, phi
     */
    val moonEquatorialHorizontalParallaxDMS = ConvertUtil.toDegreeFullRound2(moonEquatorialHorizontalParallax)

    /**
    * Moon Apparent Geocentric Semidiameter default in degree, s
    */
    val moonGeoSemidiameter = MoonPosition.moonGeoSemidiameter(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Semidiameter DMS default in degree, s
     */
    val moonGeoSemidiameterDMS = ConvertUtil.toDegreeFullRound2(moonGeoSemidiameter)

    /**
    * Moon-Sun Apparent Geocentric Elongation default in degree, d
    */
    val moonSunGeoElongation = MoonPosition.moonSunGeoElongation(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon-Sun Apparent Geocentric Elongation DMS default in degree, d
     */
    val moonSunGeoElongationDMS = ConvertUtil.toDegreeFullRound2(moonSunGeoElongation)

    /**
    * Moon Apparent Geocentric Phase Angle default in degree, i
    */
    val moonGeoPhaseAngle = MoonPosition.moonGeoPhaseAngle(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Phase Angle default in degree, i
     */
    val moonGeoPhaseAngleDMS = ConvertUtil.toDegreeFullRound2(moonGeoPhaseAngle)

    /**
    * Moon Geocentric Disk Illuminated Fraction default in degree, k
    */
    val moonGeoDiskIlluminatedFraction = MoonPosition.moonGeoDiskIlluminatedFraction(jd, deltaT, UnitType.DEGREES)
    
    /**
    * Moon Geocentric Disk Illuminated Fraction Percent, k
    */
    val moonGeoDiskIlluminatedFractionPercent = moonGeoDiskIlluminatedFraction * 100
    
    /**
    * Moon Geocentric Bright Limb Angle default in degree, x
    */
    val moonGeoBrightLimbAngle = MoonPosition.moonGeoBrightLimbAngle(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Bright Limb Angle DMS default in degree, x
     */
    val moonGeoBrightLimbAngleDMS = ConvertUtil.toDegreeFullRound2(moonGeoBrightLimbAngle)

    /**
    * Moon term n in radian, n
    */
    val moonTermN = MoonPosition.moonTermN(jd, longitude, latitude, elevation, deltaT)
    
    /**
    * Parallax in the Moon Right Ascension default in degree, deltaAlpha
    */
    val parallaxInTheMoonRightAscension = MoonPosition.parallaxInTheMoonRightAscension(jd, longitude, latitude, elevation, deltaT, UnitType.DEGREES)

    /**
     * Parallax in the Moon Right Ascension DMS default in degree, deltaAlpha
     */
    val parallaxInTheMoonRightAscensionDMS = ConvertUtil.toDegreeFullRound2(parallaxInTheMoonRightAscension)

    /**
    * Parallax in the Moon Altitude default in degree, P
    */
    val parallaxInTheMoonAltitude = MoonPosition.parallaxInTheMoonAltitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Parallax in the Moon Altitude DMS default in degree, P
     */
    val parallaxInTheMoonAltitudeDMS = ConvertUtil.toDegreeFullRound2(parallaxInTheMoonAltitude)

    /**
    * Moon Topocentric Longitude default in degree
    */
    val moonTopoLongitude = MoonPosition.moonTopoLongitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Longitude DMS default in degree
     */
    val moonTopoLongitudeDMS = ConvertUtil.toDegreeFullRound2(moonTopoLongitude)

    /**
    * Moon Topocentric Latitude default in degree
    */
    val moonTopoLatitude = MoonPosition.moonTopoLatitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Latitude DMS default in degree
     */
    val moonTopoLatitudeDMS = ConvertUtil.toDegreeFullRound2(moonTopoLatitude)

    /**
    * Moon Topocentric Right Ascension default in degree, alpha apostrophe
    */
    val moonTopoRightAscension = MoonPosition.moonTopoRightAscension(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Right Ascension DMS default in degree, alpha apostrophe
     */
    val moonTopoRightAscensionDMS = ConvertUtil.toDegreeFullRound2(moonTopoRightAscension)

    /**
    * Moon Topocentric Declination default in degree, delta apostrophe
    */
    val moonTopoDeclination = MoonPosition.moonTopoDeclination(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Declination DMS default in degree, delta apostrophe
     */
    val moonTopoDeclinationDMS = ConvertUtil.toDegreeFullRound2(moonTopoDeclination)

    /**
    * Moon Topocentric Semidiameter, s apostrophe
    */
    val moonTopoSemidiameter = MoonPosition.moonTopoSemidiameter(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Semidiameter DMS, s apostrophe
     */
    val moonTopoSemidiameterDMS = ConvertUtil.toDegreeFullRound2(moonTopoSemidiameter)

    /**
    * Moon Topocentric Greenwich Hour Angle default in degree, GHA
    */
    val moonTopoGreenwichHourAngle = MoonPosition.moonTopoGreenwichHourAngle(jd, longitude, latitude, deltaT)

    /**
     * Moon Topocentric Greenwich Hour Angle DMS default in degree, GHA
     */
    val moonTopoGreenwichHourAngleDMS = ConvertUtil.toDegreeFullRound2(moonTopoGreenwichHourAngle)

    /**
    * Moon Topocentric Local Hour Angle default in degree, LHA
    */
    val moonTopoLocalHourAngle = MoonPosition.moonTopoLocalHourAngle(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Local Hour Angle DMS default in degree, LHA
     */
    val moonTopoLocalHourAngleDMS = ConvertUtil.toDegreeFullRound2(moonTopoLocalHourAngle)

    /**
    * Moon Topocentric Azimuth default in degree, A apostrophe
    */
    val moonTopoAzimuth = MoonPosition.moonTopoAzimuth(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Azimuth DMS default in degree, A apostrophe
     */
    val moonTopoAzimuthDMS = ConvertUtil.toDegreeFullRound2(moonTopoAzimuth)

    /**
    * Moon Airless Topocentric Altitude Center in degree
    */
    val moonAirlessTopoAltitudeCenterLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.AIRLESS_CENTER, temperature, pressure)

    /**
     * Moon Airless Topocentric Altitude Center DMS in degree
     */
    val moonAirlessTopoAltitudeCenterLimbDMS = ConvertUtil.toDegreeFullRound2(moonAirlessTopoAltitudeCenterLimb)

    /**
    * Moon Apparent Topocentric Altitude Center in degree
    */
    val moonApparentTopoAltitudeCenterLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.APPARENT_CENTER, temperature, pressure)

    /**
     * Moon Apparent Topocentric Altitude Center DMS in degree
     */
    val moonApparentTopoAltitudeCenterLimbDMS = ConvertUtil.toDegreeFullRound2(moonApparentTopoAltitudeCenterLimb)

    /**
    * Moon Observed Topocentric Altitude Center in degree
    */
    val moonObservedTopoAltitudeCenterLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.OBSERVED_CENTER, temperature, pressure)

    /**
     * Moon Observed Topocentric Altitude Center DMS in degree
     */
    val moonObservedTopoAltitudeCenterLimbDMS = ConvertUtil.toDegreeFullRound2(moonObservedTopoAltitudeCenterLimb)

    // Upper
    /**
    * Moon Airless Topocentric Altitude Upper in degree
    */
    val moonAirlessTopoAltitudeUpperLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.AIRLESS_UPPER, temperature, pressure)

    /**
     * Moon Airless Topocentric Altitude Upper DMS in degree
     */
    val moonAirlessTopoAltitudeUpperLimbDMS = ConvertUtil.toDegreeFullRound2(moonAirlessTopoAltitudeUpperLimb)

    /**
    * Moon Apparent Topocentric Altitude Upper in degree
    */
    val moonApparentTopoAltitudeUpperLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.APPARENT_UPPER, temperature, pressure)

    /**
     * Moon Apparent Topocentric Altitude Upper DMS in degree
     */
    val moonApparentTopoAltitudeUpperLimbDMS = ConvertUtil.toDegreeFullRound2(moonApparentTopoAltitudeUpperLimb)

    /**
    * Moon Observed Topocentric Altitude Upper in degree
    */
    val moonObservedTopoAltitudeUpperLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.OBSERVED_UPPER, temperature, pressure)

    /**
     * Moon Observed Topocentric Altitude Upper DMS in degree
     */
    val moonObservedTopoAltitudeUpperLimbDMS = ConvertUtil.toDegreeFullRound2(moonObservedTopoAltitudeUpperLimb)

    // lower
    /**
    * Moon Airless Topocentric Altitude Lower in degree
    */
    val moonAirlessTopoAltitudeLowerLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.AIRLESS_LOWER, temperature, pressure)

    /**
     * Moon Airless Topocentric Altitude Lower DMS in degree
     */
    val moonAirlessTopoAltitudeLowerLimbDMS = ConvertUtil.toDegreeFullRound2(moonAirlessTopoAltitudeLowerLimb)

    /**
    * Moon Apparent Topocentric Altitude Lower in degree
    */
    val moonApparentTopoAltitudeLowerLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.APPARENT_LOWER, temperature, pressure)

    /**
     * Moon Apparent Topocentric Altitude Lower DMS in degree
     */
    val moonApparentTopoAltitudeLowerLimbDMS = ConvertUtil.toDegreeFullRound2(moonApparentTopoAltitudeLowerLimb)

    /**
    * Moon Observed Topocentric Altitude Lower in degree
    */
    val moonObservedTopoAltitudeLowerLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.OBSERVED_LOWER, temperature, pressure)

    /**
     * Moon Observed Topocentric Altitude Lower DMS in degree
     */
    val moonObservedTopoAltitudeLowerLimbDMS = ConvertUtil.toDegreeFullRound2(moonObservedTopoAltitudeLowerLimb)

    /**
    * Moon Atmospheric Refraction Topocentric Altitude Center in degree
    */
    val moonAtmosphericRefTopoAltitudeCenter = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.REFRACTION_CENTER, temperature, pressure)

    /**
     * Moon Atmospheric Refraction Topocentric Altitude Center DMS in degree
     */
    val moonAtmosphericRefTopoAltitudeCenterDMS = ConvertUtil.toDegreeFullRound2(moonAtmosphericRefTopoAltitudeCenter)

    /**
    * Moon Atmospheric Refraction Topocentric Altitude Upper in degree
    */
    val moonAtmosphericRefTopoAltitudeUpper = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.REFRACTION_UPPER, temperature, pressure)

    /**
     * Moon Atmospheric Refraction Topocentric Altitude Upper DMS in degree
     */
    val moonAtmosphericRefTopoAltitudeUpperDMS = ConvertUtil.toDegreeFullRound2(moonAtmosphericRefTopoAltitudeUpper)

    /**
    * Moon Atmospheric Refraction Topocentric Altitude Lower in degree
    */
    val moonAtmosphericRefTopoAltitudeLower = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.REFRACTION_LOWER, temperature, pressure)

    /**
     * Moon Atmospheric Refraction Topocentric Altitude Lower DMS in degree
     */
    val moonAtmosphericRefTopoAltitudeLowerDMS = ConvertUtil.toDegreeFullRound2(moonAtmosphericRefTopoAltitudeLower)

    /**
    * Moon Sun Topocentric Elongation default in degree, d apostrophe 
    */
    val moonSunTopoElongation = MoonPosition.moonSunTopoElongation(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Sun Topocentric Elongation DMS default in degree, d apostrophe
     */
    val moonSunTopoElongationDMS = ConvertUtil.toDegreeFullRound2(moonSunTopoElongation)

    /**
    * Moon Topocentric Phase Angle in degree, i apostrophe
    */
    val moonTopoPhaseAngle = MoonPosition.moonTopoPhaseAngle(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Phase Angle DMS in degree, i apostrophe
     */
    val moonTopoPhaseAngleDMS = ConvertUtil.toDegreeFullRound2(moonTopoPhaseAngle)

    /**
    * Moon Topocentric Disk Illuminated Fraction
    */
    val moonTopoDiskIlluminatedFraction = MoonPosition.moonTopoDiskIlluminatedFraction(jd, longitude, latitude, elevation, deltaT)

    /**
    * Moon Topocentric Disk Illuminated Fraction Percent 
    */
    val moonTopoDiskIlluminatedFractionPercent = moonTopoDiskIlluminatedFraction * 100
    
    /**
    * Moon Topocentric Bright Limb Angle
    */
    val moonTopoBrightLimbAngle = MoonPosition.moonTopoBrightLimbAngle(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Bright Limb Angle DMS
     */
    val moonTopoBrightLimbAngleDMS = ConvertUtil.toDegreeFullRound2(moonTopoBrightLimbAngle)
    
}
