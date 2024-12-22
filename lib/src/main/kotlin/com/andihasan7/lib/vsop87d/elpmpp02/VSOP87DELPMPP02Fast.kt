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

import com.andihasan7.lib.vsop87d.elpmpp02.correction.Correction
import com.andihasan7.lib.vsop87d.elpmpp02.earthposition.EarthPosition
import com.andihasan7.lib.vsop87d.elpmpp02.enum.*
import com.andihasan7.lib.vsop87d.elpmpp02.moonposition.MoonPosition
import com.andihasan7.lib.vsop87d.elpmpp02.sunposition.SunPosition
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.DeltaT
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import kotlin.math.*

/**
 * VSOP87DELPMPP02 Fast Warning!!! still not completed
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
class VSOP87DELPMPP02Fast(
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
    val sunApparentGeocentricLongitude = (sunTrueGeocentricLongitude + nutationInLongitude + abr).mod(360.0)

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
    val sunApparentGeoSemidiameter = 0.266563888889 / sunGeocentricDistanceAU

    /**
     * Sun Apparent Geocentric Right Ascension in degree, a
     */
    val sunApparentGeoRightAscension = (Math.toDegrees(atan2(sin(Math.toRadians(sunApparentGeocentricLongitude)) * cos(Math.toRadians(trueObliquityOfEcliptic)) - tan(Math.toRadians(sunTrueGeocentricLatitude)) * sin(Math.toRadians(trueObliquityOfEcliptic)), cos(Math.toRadians(sunApparentGeocentricLongitude))))).mod(360.0)

    /**
     * Sun Apparent Geocentric Declination in degree, d
     */
    val sunApparentGeoDeclination = Math.toDegrees(asin(sin(Math.toRadians(sunTrueGeocentricLatitude)) * cos(Math.toRadians(trueObliquityOfEcliptic)) + cos(Math.toRadians(sunTrueGeocentricLatitude)) * sin(Math.toRadians(trueObliquityOfEcliptic)) * sin(Math.toRadians(sunApparentGeocentricLongitude))))

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
    val atmosphericRefractionFromAirlessAltitude = Correction.atmosphericRefractionFromAirlessAltitude(sunGeoAltitude, temperature, pressure)

    /**
     * Sun Topocentric Longitude, lambda apostrophe
     */
    val sunTopoLongitude = SunPosition.sunTopoLongitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Latitude, beta apostrophe
     */
    val sunTopoLatitude = SunPosition.sunTopoLatitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Right Ascension, alpha apostrophe
     */
    val sunTopoRightAscension = SunPosition.sunTopoRightAscension(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Declination, delta apostrophe
     */
    val sunTopoDeclination = SunPosition.sunTopoDeclination(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Local Hour Angle default in degree, H apostrophe
     */
    val sunTopoLocalHourAngle = SunPosition.sunTopoLocalHourAngle(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Topocentric Azimuth, A apostrophe
     */
    val sunTopoAzimuth = SunPosition.sunTopoAzimuth(jd, longitude, latitude, elevation, deltaT)

    /**
     * Sun Airless Topocentric Altitude, h'
     */
    val sunAirlessTopoAltitude = SunPosition.sunTopoAltitude(jd, longitude, latitude, elevation, deltaT, SunAltType.AIRLESS, UnitType.DEGREES, temperature, pressure)

    /**
     * Sun Apparent Topocentric Altitude, ha'
     */
    val sunApparentTopoAltitude = SunPosition.sunTopoAltitude(jd, longitude, latitude, elevation, deltaT, SunAltType.APPARENT, UnitType.DEGREES, temperature, pressure)

    /**
     * Sun Observer Topocentric Altitude, ho'
     */
    val sunObserverTopoAltitude = SunPosition.sunTopoAltitude(jd, longitude, latitude, elevation, deltaT, SunAltType.OBSERVER, UnitType.DEGREES, temperature, pressure)

    /**
     * Sun Topocentric Semidiameter, s apostrophe
     */
    val sunTopoSemidiameter = SunPosition.sunTopoSemidiameter(jd, longitude, latitude, elevation, deltaT)

    /**
     * Equation of Time, e
     */
    val equationOfTime = SunPosition.equationOfTime(jd, deltaT)

    // Moon Data
    /**
     * Moon True Geocentric Longitude in degree, lambda apostrophe
     */
    val moonTrueGeocentricLongitude = MoonPosition.moonGeocentricLongitude(jd, deltaT, PositionType.TRUE, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Longitude in degree, lambda
     */
    val moonApparentGeocentricLongitude = MoonPosition.moonGeocentricLongitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)

    /**
     * Moon True Geocentric Latitude in degree, beta apostrophe
     */
    val moonTrueGeocentricLatitude = MoonPosition.moonGeocentricLatitude(jd, deltaT, PositionType.TRUE, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Latitude in degree, beta
     */
    val moonApparentGeocentricLatitude = MoonPosition.moonGeocentricLatitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)

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
     * Moon Apparent Geocentric Declination, delta
     */
    val moonAppaGeocentricDeclination = MoonPosition.moonAppaGeocentricDeclination(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Greenwich Hour Angle default in degree
     */
    val moonGeoGreenwichHourAngle = MoonPosition.moonGeoGreenwichHourAngle(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Local Hour Angle default in degree
     */
    val moonGeoLocalHourAngle = MoonPosition.moonGeoLocalHourAngle(jd, longitude, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Azimuth default in degree, A
     */
    val moonGeoAzimuth = MoonPosition.moonGeoAzimuth(jd, longitude, latitude, deltaT, UnitType.DEGREES)

    /**
     * Moon Geocentric Altitude default in degree, h
     */
    val moonGeoAltitude = MoonPosition.moonGeoAltitude(jd, longitude, latitude, deltaT, UnitType.DEGREES)

    /**
     * Moon Equatorial Horizontal Parallax default in degree, phi
     */
    val moonEquatorialHorizontalParallax = MoonPosition.moonEquatorialHorizontalParallax(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Semidiameter default in degree, s
     */
    val moonGeoSemidiameter = MoonPosition.moonGeoSemidiameter(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon-Sun Apparent Geocentric Elongation default in degree, d
     */
    val moonSunGeoElongation = MoonPosition.moonSunGeoElongation(jd, deltaT, UnitType.DEGREES)

    /**
     * Moon Apparent Geocentric Phase Angle default in degree, i
     */
    val moonGeoPhaseAngle = MoonPosition.moonGeoPhaseAngle(jd, deltaT, UnitType.DEGREES)

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
     * Moon term n in radian, n
     */
    val moonTermN = MoonPosition.moonTermN(jd, longitude, latitude, elevation, deltaT)

    /**
     * Parallax in the Moon Right Ascension default in degree, deltaAlpha
     */
    val parallaxInTheMoonRightAscension = MoonPosition.parallaxInTheMoonRightAscension(jd, longitude, latitude, elevation, deltaT, UnitType.DEGREES)

    /**
     * Parallax in the Moon Altitude default in degree, P
     */
    val parallaxInTheMoonAltitude = MoonPosition.parallaxInTheMoonAltitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Longitude default in degree
     */
    val moonTopoLongitude = MoonPosition.moonTopoLongitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Latitude default in degree
     */
    val moonTopoLatitude = MoonPosition.moonTopoLatitude(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Right Ascension default in degree, alpha apostrophe
     */
    val moonTopoRightAscension = MoonPosition.moonTopoRightAscension(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Declination default in degree, delta apostrophe
     */
    val moonTopoDeclination = MoonPosition.moonTopoDeclination(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Semidiameter, s apostrophe
     */
    val moonTopoSemidiameter = MoonPosition.moonTopoSemidiameter(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Greenwich Hour Angle default in degree, GHA
     */
    val moonTopoGreenwichHourAngle = MoonPosition.moonTopoGreenwichHourAngle(jd, longitude, latitude, deltaT)

    /**
     * Moon Topocentric Local Hour Angle default in degree, LHA
     */
    val moonTopoLocalHourAngle = MoonPosition.moonTopoLocalHourAngle(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Azimuth default in degree, A apostrophe
     */
    val moonTopoAzimuth = MoonPosition.moonTopoAzimuth(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Airless Topocentric Altitude Center in degree
     */
    val moonAirlessTopoAltitudeCenterLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.AIRLESS_CENTER, temperature, pressure)

    /**
     * Moon Apparent Topocentric Altitude Center in degree
     */
    val moonApparentTopoAltitudeCenterLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.APPARENT_CENTER, temperature, pressure)

    /**
     * Moon Observed Topocentric Altitude Center in degree
     */
    val moonObservedTopoAltitudeCenterLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.OBSERVED_CENTER, temperature, pressure)

    // Upper
    /**
     * Moon Airless Topocentric Altitude Upper in degree
     */
    val moonAirlessTopoAltitudeUpperLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.AIRLESS_UPPER, temperature, pressure)

    /**
     * Moon Apparent Topocentric Altitude Upper in degree
     */
    val moonApparentTopoAltitudeUpperLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.APPARENT_UPPER, temperature, pressure)

    /**
     * Moon Observed Topocentric Altitude Upper in degree
     */
    val moonObservedTopoAltitudeUpperLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.OBSERVED_UPPER, temperature, pressure)

    // lower
    /**
     * Moon Airless Topocentric Altitude Lower in degree
     */
    val moonAirlessTopoAltitudeLowerLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.AIRLESS_LOWER, temperature, pressure)

    /**
     * Moon Apparent Topocentric Altitude Lower in degree
     */
    val moonApparentTopoAltitudeLowerLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.APPARENT_LOWER, temperature, pressure)

    /**
     * Moon Observed Topocentric Altitude Lower in degree
     */
    val moonObservedTopoAltitudeLowerLimb = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.OBSERVED_LOWER, temperature, pressure)

    /**
     * Moon Atmospheric Refraction Topocentric Altitude Center in degree
     */
    val moonAtmosphericRefTopoAltitudeCenter = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.REFRACTION_CENTER, temperature, pressure)

    /**
     * Moon Atmospheric Refraction Topocentric Altitude Upper in degree
     */
    val moonAtmosphericRefTopoAltitudeUpper = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.REFRACTION_UPPER, temperature, pressure)

    /**
     * Moon Atmospheric Refraction Topocentric Altitude Lower in degree
     */
    val moonAtmosphericRefTopoAltitudeLower = MoonPosition.moonTopoAltitude(jd, longitude, latitude, elevation, deltaT, MoonAltType.REFRACTION_LOWER, temperature, pressure)

    /**
     * Moon Sun Topocentric Elongation default in degree, d apostrophe
     */
    val moonSunTopoElongation = MoonPosition.moonSunTopoElongation(jd, longitude, latitude, elevation, deltaT)

    /**
     * Moon Topocentric Phase Angle in degree, i apostrophe
     */
    val moonTopoPhaseAngle = MoonPosition.moonTopoPhaseAngle(jd, longitude, latitude, elevation, deltaT)

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
}