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

import com.andihasan7.lib.vsop87d.elpmpp02.VSOP87DELPMPP02
import com.andihasan7.lib.vsop87d.elpmpp02.enum.ConjunctionReturn
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DateFormat
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PhaseType
import com.andihasan7.lib.vsop87d.elpmpp02.moonphase.MoonPhase
import com.andihasan7.lib.vsop87d.elpmpp02.prayertimes.PrayerTimes
import com.andihasan7.lib.vsop87d.elpmpp02.sunposition.SunPosition
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.DeltaT
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import kotlin.math.floor


/**
 * MoonSighting
 */
class MoonSighting(
    monthOfHijri: Int = 1, // month of hijri
    yearOfHijri: Int = 1446, // year of hijri
    longitude: Double = 0.0, // longitude of observer
    latitude: Double = 0.0, // latitude of observer
    elevation: Double = 0.0, // elevation of observer
    timeZone: Double = 0.0, // time zone of observer
    addDate: Int = 0, // additional day/date
    checkDeltaT: Boolean = true, // choice to use deltaT or not
    temperature: Double = 10.0, // average annual local temperature (in °C)
    pressure: Double = 1010.0, // annual average local air pressure (in millibars)
) {

    /**
     * jd New Moon from Astronomical Algorithm book without tabular interpolation
     */
    val jdNewMoonAstronomicalAlgorithm = MoonPhase.moonPhase(monthOfHijri, yearOfHijri, PhaseType.NEWMOON)

    /**
     * JD julian day when new moon/JD ijtima with tabular interpolation without deltaT
     */
    val jdGeoNewMoon = MoonPhase.moonGeoConjunction(monthOfHijri, yearOfHijri, 0.0, ConjunctionReturn.JDCONJUNCTION)

    /**
     * jd when maghrib with tabular interpolation without deltaT
     */
    val jdGhurubSyams = SunPosition.jdMaghrib(jdGeoNewMoon, longitude, latitude, elevation, timeZone)

    /**
     * jd when maghrib + addDate
     */
    val jdGhurubSyamsPlus = SunPosition.jdMaghrib(jdGeoNewMoon + addDate, longitude, latitude, elevation, timeZone)
    /**
     * deltaT
     */
    val deltaT = DeltaT.deltaT(floor(jdGeoNewMoon) + 0.5)

    /**
     * jd julian day geo when new moon/JD ijtima with deltaT
     */
    val jdGeoNewMoonCor = MoonPhase.moonGeoConjunction(monthOfHijri, yearOfHijri, deltaT, ConjunctionReturn.JDCONJUNCTION)

    /**
     * longitude new moon geocentric
     */
    val lonGeoNewMoon = MoonPhase.moonGeoConjunction(monthOfHijri, yearOfHijri, deltaT, ConjunctionReturn.LONGITUDE)

    /**
     * jd julian day topo when new moon/jd ijtima with deltaT
     */
    val jdTopoNewMoon = MoonPhase.moonTopoConjunction(monthOfHijri, yearOfHijri, deltaT, longitude, latitude, elevation, ConjunctionReturn.JDCONJUNCTION)

    /**
     * longitude new moon topocentric
     */
    val lonTopoNewMoon = MoonPhase.moonTopoConjunction(monthOfHijri, yearOfHijri, deltaT, longitude, latitude, elevation, ConjunctionReturn.LONGITUDE)

    /**
     * date when new moon DPDDMMYYM format from astronomical algorithm
     */
    val dateNewMoonAA = TimeUtil.jdToGregorian<String>(jdNewMoonAstronomicalAlgorithm, timeZone, DateFormat.DPDDMMYYM)

    /**
     * date when new moon DPDDMMYYM format
     */
    val dateNewMoon = TimeUtil.jdToGregorian<String>(jdGeoNewMoonCor, timeZone, DateFormat.DPDDMMYYM)

    /**
     * local hour when new moon geocentric from astronomical algorithm
     */
    val hourGeoNewMoonAA = TimeUtil.jdToGregorian<Double>(jdNewMoonAstronomicalAlgorithm, timeZone, DateFormat.HOUR_DOUBLE)

    /**
     * local hour when new moon geocentric
     */
    val hourGeoNewMoon = TimeUtil.jdToGregorian<Double>(jdGeoNewMoonCor, timeZone, DateFormat.HOUR_DOUBLE)
    /**
     * UTC hour when new moon geocentric
     */
    val utcGeoHourNewMoon = TimeUtil.jdToGregorian<Double>(jdGeoNewMoonCor, 0.0, DateFormat.HOUR_DOUBLE)

    /**
     * date new moon int
     */
    val dateNMInt = TimeUtil.jdToGregorian<Int>(jdGeoNewMoonCor, timeZone, DateFormat.DATE)
    /**
     * month new moon int
     */
    val monthNMInt = TimeUtil.jdToGregorian<Int>(jdGeoNewMoonCor, timeZone, DateFormat.MONTH_INT)
    /**
     * year new moon int
     */
    val yearNMInt = TimeUtil.jdToGregorian<Int>(jdGeoNewMoonCor, timeZone, DateFormat.YEAR)

    /**
     * maghrib local
     */
    val maghribLocalDateNewMoon = TimeUtil.jdToGregorian<Double>(jdGhurubSyamsPlus, timeZone, DateFormat.HOUR_DOUBLE)

    /**
     * object of ephemeris data of moonsighting
     */
    val vsop = VSOP87DELPMPP02(
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
     * moon airless geocentric altitude
     */
    val moonGeoAltitude = vsop.moonGeoAltitude

    /**
     * moon airless geocentric altitude DMS
     */
    val moonGeoAltitudeDMS = vsop.moonGeoAltitudeDMS

    // airless topocentric
    /**
     * moon airless topo altitude upper limb
     */
    val moonAirlessTopoAltitudeUpperLimb = vsop.moonAirlessTopoAltitudeUpperLimb
    /**
     * moon airless topo altitude upper limb DMS
     */
    val moonAirlessTopoAltitudeUpperLimbDMS = vsop.moonAirlessTopoAltitudeUpperLimbDMS
    /**
     * moon airless topo altitude center limb
     */
    val moonAirlessTopoAltitudeCenterLimb = vsop.moonAirlessTopoAltitudeCenterLimb
    /**
     * moon airless topo altitude center limb DMS
     */
    val moonAirlessTopoAltitudeCenterLimbDMS = vsop.moonAirlessTopoAltitudeCenterLimbDMS
    /**
     * moon airless topo altitude lower limb
     */
    val moonAirlessTopoAltitudeLowerLimb = vsop.moonAirlessTopoAltitudeLowerLimb
    /**
     * moon airless topo altitude lower limb DMS
     */
    val moonAirlessTopoAltitudeLowerLimbDMS = vsop.moonAirlessTopoAltitudeLowerLimbDMS

    // moon apparent topo altitude
    /**
     * moon apparent topo alt upper limb
     */
    val moonAppaTopoAltUpperLimb = vsop.moonApparentTopoAltitudeUpperLimb

    /**
     * moon apparent topo alt upper limb DMS
     */
    val moonAppaTopoAltUpperLimbDMS = vsop.moonApparentTopoAltitudeUpperLimbDMS

    /**
     * moon apparent topo alt center limb
     */
    val moonAppaTopoAltCenterLimb = vsop.moonApparentTopoAltitudeCenterLimb

    /**
     * moon apparent topo alt center limb DMS
     */
    val moonAppaTopoAltCenterLimbDMS = vsop.moonApparentTopoAltitudeCenterLimbDMS

    /**
     * moon apparent topo alt lower limb
     */
    val moonAppaTopoAltLowerLimb = vsop.moonApparentTopoAltitudeLowerLimb

    /**
     * moon apparent topo alt lower limb DMS
     */
    val moonAppaTopoAltLowerLimbDMS = vsop.moonApparentTopoAltitudeLowerLimbDMS

    // observed topo alt
    /**
     * moon observed topo alt upper limb
     */
    val moonObservedTopoAltUpperLimb = vsop.moonObservedTopoAltitudeUpperLimb

    /**
     * moon observed topo alt upper limb DMS
     */
    val moonObservedTopoAltUpperLimbDMS = vsop.moonObservedTopoAltitudeUpperLimbDMS

    /**
     * moon observed topo alt center limb
     */
    val moonObservedTopoAltCenterLimb = vsop.moonObservedTopoAltitudeCenterLimb

    /**
     * moon observed topo alt center limb DMS
     */
    val moonObserrvedTopoAltCenterLimbDMS = vsop.moonObservedTopoAltitudeCenterLimbDMS

    /**
     * moon observed topo alt lower limb
     */
    val moonObservedTopoAltLowerLimb = vsop.moonObservedTopoAltitudeLowerLimb

    /**
     * moon observed topo alt lower limb DMS
     */
    val moonObservedTopoAltLowerLimbDMS = vsop.moonObservedTopoAltitudeLowerLimbDMS

}
