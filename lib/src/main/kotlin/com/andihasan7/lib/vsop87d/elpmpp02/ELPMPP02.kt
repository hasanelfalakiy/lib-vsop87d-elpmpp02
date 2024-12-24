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
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.DeltaT
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PositionType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.MoonAltType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.moonposition.MoonPosition

/**
* ELPMPP02
* Complete Moon Ephemeris Data
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
class ELPMPP02(
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