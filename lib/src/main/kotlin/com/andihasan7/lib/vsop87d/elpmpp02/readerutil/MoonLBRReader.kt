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
 
package com.andihasan7.lib.vsop87d.elpmpp02.readerutil

import kotlin.math.pow
import kotlin.math.sin

object MoonLBRReader {

    /**
     * function to read earth lbr binary file and calculate the coefficients
     *
     * @param t is the same as jme Julian Millenium Ephemeris
     * @param arrayDoubleArray object of array
     *
     * @return totalCoefficients
     */
    fun moonLBRReader(t: Double, arrayDoubleArray: Array<DoubleArray>): Double {

        var totalCoefficients = 0.0

        for (row in arrayDoubleArray) {
            totalCoefficients += row[0] * sin(row[1] + row[2] * t + row[3] * t.pow(2) + row[4] * t.pow(3) + row[5] * t.pow(4))
        }

        return totalCoefficients
    }
}