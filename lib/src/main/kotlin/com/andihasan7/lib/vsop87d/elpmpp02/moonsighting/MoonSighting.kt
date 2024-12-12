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
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DateFormat
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PhaseType
import com.andihasan7.lib.vsop87d.elpmpp02.moonphase.MoonPhase
import com.andihasan7.lib.vsop87d.elpmpp02.prayertimes.PrayerTimes
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil

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
     * JD julian day when new moon/JD ijtima
     */
    val jdNewMoon = MoonPhase.moonPhase(monthOfHijri, yearOfHijri, PhaseType.NEWMOON)
    /**
     * date when new moon DPDDMMYYM format
     */
    val dateNewMoon = TimeUtil.jdToGregorian<String>(jdNewMoon, timeZone, DateFormat.DPDDMMYYM)
    /**
     * local hour when new moon
     */
    val hourNewMoon = TimeUtil.jdToGregorian<Double>(jdNewMoon, timeZone, DateFormat.HOUR_DOUBLE)
    /**
     * UTC hour when new moon
     */
    val utcHourNewMoon = TimeUtil.jdToGregorian<Double>(jdNewMoon, 0.0, DateFormat.HOUR_DOUBLE)

    /**
     * date new moon int
     */
    val dateNMInt = TimeUtil.jdToGregorian<Int>(jdNewMoon + addDate, timeZone, DateFormat.DATE)
    /**
     * month new moon int
     */
    val monthNMInt = TimeUtil.jdToGregorian<Int>(jdNewMoon + addDate, timeZone, DateFormat.MONTH_INT)
    /**
     * year new moon int
     */
    val yearNMInt = TimeUtil.jdToGregorian<Int>(jdNewMoon + addDate, timeZone, DateFormat.YEAR)

    /**
     * object of prayer times of moonsighting
     */
    val pt = PrayerTimes(
        date = dateNMInt ?: 0,
        month = monthNMInt ?: 0,
        year = yearNMInt ?: 0,
        longitude = longitude,
        latitude = latitude,
        elevation = elevation,
        timeZone = timeZone,
        ihtiyatDzuhur = 0,
        otherIhtiyat = 0
    )

    /**
     * sunrise/maghrib of day of new moon
     */
    val maghribDateNewMoon = pt.maghribWD

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
        hourDouble = maghribDateNewMoon,
        checkDeltaT = checkDeltaT,
        temperature = temperature,
        pressure = pressure
    )

    /**
     * moon geo altitude
     */
    val moonGeoAltitude = vsop.moonGeoAltitude

    /**
     * moon geo altitude DMS
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


}