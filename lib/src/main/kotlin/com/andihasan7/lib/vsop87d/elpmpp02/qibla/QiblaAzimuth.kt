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

package com.andihasan7.lib.vsop87d.elpmpp02.qibla

import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

/**
 * QiblaAzimuth
 */
class QiblaAzimuth {

    val LATITUDEKABAH = 21.4225
    val LONGITUDEKABAH = 39.826111111111

    /**
     * arahQiblat
     * @param latitude of observer
     * @param longitude of observer
     * @return [0 = azimuthUB, 1 = azimuthBU, 2 = azimuthUTSB]
     */
    fun qiblaAzimuth(latitude: Double, longitude: Double): DoubleArray {

        // longitude azimuth different

        val lonAzimuthDifferent = 360 - LONGITUDEKABAH + longitude - 360

        val h = Math.toDegrees(asin(sin(Math.toRadians(latitude)) * sin(Math.toRadians(LATITUDEKABAH)) + cos(Math.toRadians(latitude))  * cos(Math.toRadians(LATITUDEKABAH)) * cos(Math.toRadians(lonAzimuthDifferent))))

        // Azimuth U-B
        val azimuthUB = Math.toDegrees(acos((sin(Math.toRadians(LATITUDEKABAH)) - sin(Math.toRadians(latitude)) * sin(Math.toRadians(h))) / cos(Math.toRadians(latitude)) / cos(Math.toRadians(h))))

        // Azimuth B-U
        val azimuthBU = 90 - azimuthUB

        // Azimuth UTSB
        val azimuthUTSB = 360 - azimuthUB

        return doubleArrayOf(azimuthUB, azimuthBU, azimuthUTSB)
    }
}