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

package com.andihasan7.lib.vsop87d.elpmpp02.moonsighting

import com.andihasan7.lib.vsop87d.elpmpp02.ELPMPP02
import com.andihasan7.lib.vsop87d.elpmpp02.convertutil.ConvertUtil
import com.andihasan7.lib.vsop87d.elpmpp02.correction.Correction
import com.andihasan7.lib.vsop87d.elpmpp02.enum.ConjunctionReturn
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DateFormat
import com.andihasan7.lib.vsop87d.elpmpp02.enum.MoonAltType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PhaseType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.SunAltType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.moonphase.MoonPhase
import com.andihasan7.lib.vsop87d.elpmpp02.prayertimes.PrayerTimes
import com.andihasan7.lib.vsop87d.elpmpp02.moonposition.MoonPosition
import com.andihasan7.lib.vsop87d.elpmpp02.sunposition.SunPosition
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.DeltaT
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import kotlin.math.floor
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.abs


/**
 * , 
 */
 
/**
* MoonSighting
* Data on the position of the sun and moon during ijtima and support for adding days
*
* ```
*    var monthOfHijri: Int = 1, // month of hijri
*    var yearOfHijri: Int = 1446, // year of hijri
*    var longitude: Double = 0.0, // longitude of observer
*    var latitude: Double = 0.0, // latitude of observer
*    var elevation: Double = 0.0, // elevation of observer
*    var timeZone: Double = 0.0, // time zone of observer
*    var addDate: Int = 0, // additional day/date
*    var checkDeltaT: Boolean = true, // choice to use deltaT or not
*    var temperature: Double = 10.0, // average annual local temperature (in °C)
*    var pressure: Double = 1010.0, // annual average local air pressure (in millibars)
* ```
*/ 
class MoonSighting(
    var monthOfHijri: Int = 1, // month of hijri
    var yearOfHijri: Int = 1446, // year of hijri
    var longitude: Double = 0.0, // longitude of observer
    var latitude: Double = 0.0, // latitude of observer
    var elevation: Double = 0.0, // elevation of observer
    var timeZone: Double = 0.0, // time zone of observer
    var addDate: Int = 0, // additional day/date
    var checkDeltaT: Boolean = true, // choice to use deltaT or not
    var temperature: Double = 10.0, // average annual local temperature (in °C)
    var pressure: Double = 1010.0, // annual average local air pressure (in millibars)
) {

    /**
     * jd New Moon from Astronomical Algorithm book without tabular interpolation
     */
    val jdNewMoonAstronomicalAlgorithm get() = MoonPhase.moonPhase(monthOfHijri, yearOfHijri, PhaseType.NEWMOON)

    /**
     * JD julian day when new moon/JD ijtima with tabular interpolation without deltaT
     */
    val jdGeoNewMoon get() = MoonPhase.moonGeoConjunction(monthOfHijri, yearOfHijri, 0.0, ConjunctionReturn.JDCONJUNCTION)

    /**
     * jd when maghrib with tabular interpolation without deltaT
     */
    val jdGhurubSyams get() = SunPosition.jdMaghrib(jdGeoNewMoon, longitude, latitude, elevation, timeZone)

    /**
     * jd when maghrib + addDate
     */
    val jdGhurubSyamsPlus get() = SunPosition.jdMaghrib(jdGeoNewMoon + addDate, longitude, latitude, elevation, timeZone)
    
    /**
     * deltaT
     */
    val deltaT get() = DeltaT.deltaT(floor(jdGeoNewMoon) + 0.5)
    
    /**
     * deltaT round 2
     */
    val deltaT2 get() = ConvertUtil.run { (deltaT).round(2) }

    /**
     * jd julian day geo when new moon/JD ijtima with deltaT
     */
    val jdGeoNewMoonCor get() = MoonPhase.moonGeoConjunction(monthOfHijri, yearOfHijri, deltaT, ConjunctionReturn.JDCONJUNCTION)

    /**
     * longitude new moon geocentric
     */
    val lonGeoNewMoon get() = MoonPhase.moonGeoConjunction(monthOfHijri, yearOfHijri, deltaT, ConjunctionReturn.LONGITUDE)
    
    /**
     * longitude new moon geocentric DMS
     */
    val lonGeoNewMoonDMS get() = ConvertUtil.toDegreeFullRound2(lonGeoNewMoon)

    /**
     * jd julian day topo when new moon/jd ijtima with deltaT
     */
    val jdTopoNewMoon get() = MoonPhase.moonTopoConjunction(monthOfHijri, yearOfHijri, deltaT, longitude, latitude, elevation, ConjunctionReturn.JDCONJUNCTION)

    /**
     * longitude new moon topocentric
     */
    val lonTopoNewMoon get() = MoonPhase.moonTopoConjunction(monthOfHijri, yearOfHijri, deltaT, longitude, latitude, elevation, ConjunctionReturn.LONGITUDE)
    
    /**
     * longitude new moon topocentric DMS
     */
    val lonTopoNewMoonDMS get() = ConvertUtil.toDegreeFullRound2(lonTopoNewMoon)

    /**
     * date when new moon DPDDMMYYM format from astronomical algorithm
     */
    val dateNewMoonAA get() = TimeUtil.jdToGregorian<String>(jdNewMoonAstronomicalAlgorithm, timeZone, DateFormat.DPDDMMYYM)

    /**
     * date when new moon geocentric DPDDMMYYM format
     */
    val dateNewMoonGeo get() = TimeUtil.jdToGregorian<String>(jdGeoNewMoonCor, timeZone, DateFormat.DPDDMMYYM)
    
    /**
     * date when new moon topocentric DPDDMMYYM format
     */
    val dateNewMoonTopo get() = TimeUtil.jdToGregorian<String>(jdTopoNewMoon, timeZone, DateFormat.DPDDMMYYM)

    /**
     * local hour when new moon geocentric from astronomical algorithm
     */
    val hourGeoNewMoonAA get() = TimeUtil.jdToGregorian<Double>(jdNewMoonAstronomicalAlgorithm, timeZone, DateFormat.HOUR_DOUBLE)
    
    /**
     * local hour when new moon geocentric from astronomical algorithm HMS
     */
    val hourGeoNewMoonAAHMS get() = ConvertUtil.toTimeFullRound2(hourGeoNewMoonAA ?: 0.0)

    /**
     * local hour when new moon geocentric
     */
    val hourGeoNewMoon get() = TimeUtil.jdToGregorian<Double>(jdGeoNewMoonCor, timeZone, DateFormat.HOUR_DOUBLE)
    
    /**
     * local hour when new moon geocentric HMS
     */
    val hourGeoNewMoonHMS get() = ConvertUtil.toTimeFullRound2(hourGeoNewMoon ?: 0.0)
    
    /**
     * UTC hour when new moon geocentric
     */
    val utcGeoHourNewMoon get() = TimeUtil.jdToGregorian<Double>(jdGeoNewMoonCor, 0.0, DateFormat.HOUR_DOUBLE)
    
    /**
     * UTC hour when new moon geocentric HMS
     */
    val utcGeoHourNewMoonHMS get() = ConvertUtil.toTimeFullRound2(utcGeoHourNewMoon ?: 0.0)
    
    /**
     * local hour when new moon topocentric 
     */
    val hourTopoNewMoon get() = TimeUtil.jdToGregorian<Double>(jdTopoNewMoon, timeZone, DateFormat.HOUR_DOUBLE)
    
    /**
     * local hour when new moon topocentric HMS
     */
    val hourTopoNewMoonHMS get() = ConvertUtil.toTimeFullRound2(hourTopoNewMoon ?: 0.0)
    
    /**
     * UTC hour when new moon topocentric 
     */
    val utcTopoHourNewMoon get() = TimeUtil.jdToGregorian<Double>(jdTopoNewMoon, 0.0, DateFormat.HOUR_DOUBLE)
    
    /**
     * UTC hour when new moon topocentric HMS
     */
    val utcTopoHourNewMoonHMS get() = ConvertUtil.toTimeFullRound2(utcTopoHourNewMoon ?: 0.0)

    /**
     * date new moon int
     */
    val dateNMInt get() = TimeUtil.jdToGregorian<Int>(jdGeoNewMoonCor, timeZone, DateFormat.DATE)
    /**
     * month new moon int
     */
    val monthNMInt get() = TimeUtil.jdToGregorian<Int>(jdGeoNewMoonCor, timeZone, DateFormat.MONTH_INT)
    /**
     * year new moon int
     */
    val yearNMInt get() = TimeUtil.jdToGregorian<Int>(jdGeoNewMoonCor, timeZone, DateFormat.YEAR)

    /**
     * maghrib local
     */
    val maghribLocalDateNewMoon get() = TimeUtil.jdToGregorian<Double>(jdGhurubSyamsPlus, timeZone, DateFormat.HOUR_DOUBLE)
    
    /**
     * maghrib local HMS
     */
    val maghribLocalDateNewMoonHMS get() = ConvertUtil.toTimeFullRound2(maghribLocalDateNewMoon ?: 0.0)

    /**
     * object of ephemeris data of moonsighting
     */
    val elp = ELPMPP02(
        date = dateNMInt ?: 0,
        month = monthNMInt ?: 0,
        year = yearNMInt ?: 0,
        longitude = longitude,
        latitude = latitude,
        elevation = elevation,
        timeZone = timeZone,
        hourDouble = maghribLocalDateNewMoon ?: 0.0,
        checkDeltaT = checkDeltaT,
        temperature = temperature,
        pressure = pressure
    )
    
    /**
    * dip/ku
    */
    val dip get() = Correction.dip(elevation)

    /**
     * moon airless geocentric altitude
     */
    val moonGeoAltitude get() = elp.moonGeoAltitude

    /**
     * moon airless geocentric altitude DMS, t hakiki
     */
    val moonGeoAltitudeDMS get() = elp.moonGeoAltitudeDMS

    // airless topocentric
    /**
     * moon airless topo altitude upper limb
     */
    val moonAirlessTopoAltitudeUpperLimb get() = elp.moonAirlessTopoAltitudeUpperLimb
    /**
     * moon airless topo altitude upper limb DMS
     */
    val moonAirlessTopoAltitudeUpperLimbDMS get() = elp.moonAirlessTopoAltitudeUpperLimbDMS
    /**
     * moon airless topo altitude center limb
     */
    val moonAirlessTopoAltitudeCenterLimb get() = elp.moonAirlessTopoAltitudeCenterLimb
    /**
     * moon airless topo altitude center limb DMS
     */
    val moonAirlessTopoAltitudeCenterLimbDMS get() = elp.moonAirlessTopoAltitudeCenterLimbDMS
    /**
     * moon airless topo altitude lower limb
     */
    val moonAirlessTopoAltitudeLowerLimb get() = elp.moonAirlessTopoAltitudeLowerLimb
    /**
     * moon airless topo altitude lower limb DMS
     */
    val moonAirlessTopoAltitudeLowerLimbDMS get() = elp.moonAirlessTopoAltitudeLowerLimbDMS

    // moon apparent topo altitude
    /**
     * moon apparent topo alt upper limb
     */
    val moonAppaTopoAltUpperLimb get() = elp.moonApparentTopoAltitudeUpperLimb

    /**
     * moon apparent topo alt upper limb DMS
     */
    val moonAppaTopoAltUpperLimbDMS get() = elp.moonApparentTopoAltitudeUpperLimbDMS

    /**
     * moon apparent topo alt center limb
     */
    val moonAppaTopoAltCenterLimb get() = elp.moonApparentTopoAltitudeCenterLimb

    /**
     * moon apparent topo alt center limb DMS
     */
    val moonAppaTopoAltCenterLimbDMS get() = elp.moonApparentTopoAltitudeCenterLimbDMS

    /**
     * moon apparent topo alt lower limb
     */
    val moonAppaTopoAltLowerLimb get() = elp.moonApparentTopoAltitudeLowerLimb

    /**
     * moon apparent topo alt lower limb DMS
     */
    val moonAppaTopoAltLowerLimbDMS get() = elp.moonApparentTopoAltitudeLowerLimbDMS

    // observed topo alt, mar'i
    /**
     * moon observed topo alt upper limb
     */
    val moonObservedTopoAltUpperLimb get() = elp.moonObservedTopoAltitudeUpperLimb

    /**
     * moon observed topo alt upper limb DMS
     */
    val moonObservedTopoAltUpperLimbDMS get() = elp.moonObservedTopoAltitudeUpperLimbDMS

    /**
     * moon observed topo alt center limb
     */
    val moonObservedTopoAltCenterLimb get() = elp.moonObservedTopoAltitudeCenterLimb

    /**
     * moon observed topo alt center limb DMS
     */
    val moonObserrvedTopoAltCenterLimbDMS get() = elp.moonObservedTopoAltitudeCenterLimbDMS

    /**
     * moon observed topo alt lower limb
     */
    val moonObservedTopoAltLowerLimb get() = elp.moonObservedTopoAltitudeLowerLimb

    /**
     * moon observed topo alt lower limb DMS
     */
    val moonObservedTopoAltLowerLimbDMS get() = elp.moonObservedTopoAltitudeLowerLimbDMS
    
    /**
    * sun topocentric longitude 
    */
    val sunTopoLongitude get() = SunPosition.sunTopoLongitude(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT)
    
    /**
    * sun topocentric longitude DMS
    */
    val sunTopoLongitudeDMS get() = ConvertUtil.toDegreeFullRound2(sunTopoLongitude)
    
    /**
    * sun topocentric latitude 
    */
    val sunTopoLatitude get() = SunPosition.sunTopoLatitude(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT)
    
    /**
    * sun topocentric latitude DMS
    */
    val sunTopoLatitudeDMS get() = ConvertUtil.toDegreeFullRound2(sunTopoLatitude)
    
    /**
    * moon topocentric longitude
    */
    val moonTopoLongitude get() = elp.moonTopoLongitude
    
    /**
    * moon topocentric longitude DMS
    */
    val moonTopoLongitudeDMS get() = elp.moonTopoLongitudeDMS
    
    /**
    * moon topocentric latitude
    */
    val moonTopoLatitude get() = elp.moonTopoLatitude
    
    /**
    * moon topocentric latitude DMS
    */
    val moonTopoLatitudeDMS get() = elp.moonTopoLatitudeDMS
    
    /**
    * sun apparent topo right ascension
    */
    val sunTopoRightAscension get() = SunPosition.sunTopoRightAscension(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT)
    
    /**
    * sun apparent topo right ascension DMS
    */
    val sunTopoRightAscensionDMS get() = ConvertUtil.toDegreeFullRound2(sunTopoRightAscension)
    
    /**
    * sun apparent topo right ascension hour
    */
    val sunTopoRightAscensionHour get() = sunTopoRightAscension / 15
    
    /**
    * sun apparent topo right ascension HMS
    */
    val sunTopoRightAscensionHMS get() = ConvertUtil.toTimeFullRound2(sunTopoRightAscensionHour)
    
    /**
    * moon apparent topo right ascension
    */
    val moonTopoRightAscension get() = MoonPosition.moonTopoRightAscension(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT)
    
    /**
    * moon apparent topo right ascension DMS
    */
    val moonTopoRightAscensionDMS get() = ConvertUtil.toDegreeFullRound2(moonTopoRightAscension)
    
    /**
    * moon apparent topo right ascension hour
    */
    val moonTopoRightAscensionHour get() = moonTopoRightAscension / 15
    
    /**
    * moon apparent topo right ascension HMS
    */
    val moonTopoRightAscensionHMS get() = ConvertUtil.toTimeFullRound2(moonTopoRightAscensionHour)
    
    /**
    * sun topo declination
    */
    val sunTopoDeclination get() = SunPosition.sunTopoDeclination(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT)
    
    /**
    * sun topo declination DMS
    */
    val sunTopoDeclinationDMS get() = ConvertUtil.toDegreeFullRound2(sunTopoDeclination)
    
    /**
    * moon geo declination
    */
    val moonGeoDeclination get() = elp.moonAppaGeocentricDeclination
    
    /**
    * moon geo declination DMS
    */
    val moonGeoDeclinationDMS get() = elp.moonAppaGeocentricDeclinationDMS
    
    /**
    * moon topo declination
    */
    val moonTopoDeclination get() = elp.moonTopoDeclination
    
    /**
    * moon topo declination DMS
    */
    val moonTopoDeclinationDMS get() = elp.moonTopoDeclinationDMS
    
    /**
    * sun topo azimuth
    */
    val sunTopoAzimuth get() = SunPosition.sunTopoAzimuth(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT)
    
    /**
    * sun topo azimuth DMS
    */
    val sunTopoAzimuthDMS get() = ConvertUtil.toDegreeFullRound2(sunTopoAzimuth)
    
    /**
    * sun airless topo altitude
    */
    val sunTopoAltitude get() = SunPosition.sunTopoAltitude(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT)
    
    /**
    * sun airless topo altitude DMS
    */
    val sunTopoAltitudeDMS get() = ConvertUtil.toDegreeFullRound2(sunTopoAltitude)
    
    /**
    * moon topo azimuth
    */
    val moonTopoAzimuth get() = elp.moonTopoAzimuth
    
    /**
    * moon topo azimuth DMS
    */
    val moonTopoAzimuthDMS get() = elp.moonTopoAzimuthDMS
    
    /**
    * moon illuminated
    */
    val moonTopoIlluminated get() = elp.moonTopoDiskIlluminatedFraction
    
    /**
    * moon illuminated percent round 2
    */
    val moonTopoIlluminatedPercent2 get() = ConvertUtil.run { (elp.moonTopoDiskIlluminatedFractionPercent).round(2) }
    
    /**
    * moon geo semidiameter
    */
    val moonGeoSemidiameter get() = elp.moonGeoSemidiameter
    
    /**
    * moon geo semidiameter DMS
    */
    val moonGeoSemidiameterDMS get() = elp.moonGeoSemidiameterDMS
    
    /**
    * moon topo semidiameter
    */
    val moonTopoSemidiameter get() = elp.moonTopoSemidiameter
    
    /**
    * moon topo semidiameter
    */
    val moonTopoSemidiameterDMS get() = elp.moonTopoSemidiameterDMS
    
    /**
    * moon-sun geo elongation
    */
    val moonSunGeoElongation get() = elp.moonSunGeoElongation
    
    /**
    * moon-sun geo elongation DMS
    */
    val moonSunGeoElongationDMS get() = elp.moonSunGeoElongationDMS
    
    /**
    * moon-sun topo elongation
    */
    val moonSunTopoElongation get() = elp.moonSunTopoElongation
    
    /**
    * moon-sun topo elongation DMS
    */
    val moonSunTopoElongationDMS get() = elp.moonSunTopoElongationDMS
    
    /**
    * moon horizontal parallax
    */
    val moonHorizontalParallax get() = elp.moonEquatorialHorizontalParallax
    
    /**
    * moon horizontal parallax DMS
    */
    val moonHorizontalParallaxDMS get() = ConvertUtil.toDegreeFullRound2(moonHorizontalParallax)
    
    /**
    * moon geo distance km
    */
    val moonGeoDistanceKM get() = ConvertUtil.run { (elp.moonAppaGeocentricDistanceKM).round(2) }
    
    
    
    /**
    * diff RA sun - RA moon
    */
    val diffRASunMoon get() = moonTopoRightAscension - sunTopoRightAscension
    
    /**
    * hilal duration/muktsu from diff RA sun - RA moon
    */
    val hilalDurationOld get() = diffRASunMoon / 15
    
    /**
    * hilal duration/muktsu Counter HMS from diff RA sun - RA moon
    */
    val hilalDurationOldHMS get() = ConvertUtil.toCounterHHMMSS2(hilalDurationOld)
    
    /**
    * moon set/hilal terbenam from diff RA sun - RA moon
    */
    val moonSetOld get() = (maghribLocalDateNewMoon ?: 0.0) + hilalDurationOld // from tsimarul murid
    
    /**
    * moon set/hilal terbenam HMS from diff RA sun - RA moon
    */
    val moonSetOldHMS get() = ConvertUtil.toTimeFullRound2(moonSetOld)
    
    /**
    * moon age/umur hilal from diff RA sun - RA moon
    */
    val moonAgeOld get() = (maghribLocalDateNewMoon ?: 0.0) - (hourGeoNewMoon ?: 0.0)
    
    /**
    * moon age/umur hilal Counter HMS from diff RA sun - RA moon
    */
    val moonAgeOldHMS get() = ConvertUtil.toCounterHHMMSS2(moonAgeOld)
    
    
    /**
    * date of moon set with add date
    */
    val dateMoonSetPlus: Int? get() = TimeUtil.jdToGregorian(jdGhurubSyamsPlus, timeZone, DateFormat.DATE)
    
    /**
    * month of moon set with add date
    */
    val monthMoonSetPlus: Int? get() = TimeUtil.jdToGregorian(jdGhurubSyamsPlus, timeZone, DateFormat.MONTH_INT)
    
    /**
    * year of moon set with add date
    */
    val yearMoonSetPlus: Int? get() = TimeUtil.jdToGregorian(jdGhurubSyamsPlus, timeZone, DateFormat.YEAR)
    
    
    
    /**
    * jd moon set
    */
    val jdMoonSet get() = TimeUtil.gregorianToJD(dateMoonSetPlus ?: 0, monthMoonSetPlus ?: 0, yearMoonSetPlus ?: 0) + (moonSetOld - timeZone) / 24.0
    
    /**
    * moon muktsul hilal
    */
    // val moonDuration get() = ((jdMoonSet - jdGhurubSyamsPlus) * 24.0)
    
    /**
    * moon best time
    */
    val moonBestTime get() = (maghribLocalDateNewMoon ?: 0.0) + 4.0 / 9.0 * hilalDurationOld
    
    /**
    * moon best time HMS
    */
    val moonBestTimeHMS get() = ConvertUtil.toTimeFullRound2(moonBestTime)
    
    /**
    * crecent width topo/lebar hilal
    */
    val crecentWidthTopo get() = (moonTopoSemidiameter) * (1 - cos(Math.toRadians(moonSunTopoElongation)))
    
    /**
    * crecent width/lebar hilal DMS
    */
    val crecentWidthTopoDMS get() = ConvertUtil.toDegreeFullRound2(crecentWidthTopo)
    
    /**
    * moon topo azimuth set
    */
    val moonTopoAzimuthSet get() = MoonPosition.moonTopoAzimuth(jdMoonSet, longitude, latitude, elevation, deltaT)
    
    /**
    * moon topo azimuth set DMS
    */
    val moonTopoAzimuthSetDMS get() = ConvertUtil.toDegreeFullRound2(moonTopoAzimuthSet)
    
    /**
    * related topo alt
    */
    val relatedTAlt get() = MoonPosition.moonTopoAltitude(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT) + abs(SunPosition.sunTopoAltitude(jdGhurubSyamsPlus, longitude, latitude, elevation, deltaT, SunAltType.AIRLESS, UnitType.DEGREES, temperature, pressure))
    
    /**
    * range q Odeh
    */
    val qOdeh get() = relatedTAlt - (-0.1018 * (crecentWidthTopo * 60).pow(3) + 0.7319 * (crecentWidthTopo * 60).pow(2) - 6.3226 * (crecentWidthTopo * 60) + 7.1651)
    
    /**
    * range q Odeh round 3
    */
    val qOdeh3 get() = ConvertUtil.run { (qOdeh).round(3) }
    
    /**
    * altitude hilal without add date
    */
    val tHilal get() = MoonPosition.moonTopoAltitude(jdGhurubSyams, longitude, latitude, elevation, deltaT, MoonAltType.OBSERVED_CENTER, temperature, pressure)
    
    /**
    * moon-sun topocentric elongation without add date
    */
    val moonSunElo get() = MoonPosition.moonSunTopoElongation(jdGhurubSyams, longitude, latitude, elevation, deltaT)
    
    val _irNeoM get() = if (tHilal >= 3.0 && moonSunElo >= 6.4) {
        1
    } else {
        2
    }
    
    /**
    * raw prediction based of neo mabims 3°, 6.4°
    */
    val _predictionNeoMabims get() = ((floor(jdGeoNewMoonCor + 0.5 + timeZone / 24.0)) - timeZone / 24.0) + _irNeoM
    
    /**
    * prediction based of neo mabims 3°, 6.4°
    */
    val predictionNeoMabims get() = TimeUtil.jdToGregorian<String>(_predictionNeoMabims, timeZone, DateFormat.DPDDMMYYM)
    
    

}
