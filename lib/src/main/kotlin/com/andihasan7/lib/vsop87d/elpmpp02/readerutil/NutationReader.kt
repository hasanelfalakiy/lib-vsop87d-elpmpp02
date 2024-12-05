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

import kotlin.math.cos
import kotlin.math.sin

object NutationReader {
    
    /**
    * function to read nutation in longitude terms for model IAU 2000B
    *
    * @param t is the same as jce
    * @param l Mean Anomaly of the Moon, L in radian
    * @param l1 Anomaly of the Sun, L' in radian
    * @param f Mean argument of the latitude of the Moon, F in radian
    * @param d Mean Elongation of the Moon from the Sun, D in radian
    * @param omega Mean Longitude of the Ascending Node of the Moon, omega in radian
    * @param arrayDoubleArray object of array
    *
    * @return nutationInLongitude the unit is 0.0000001s
    */
    fun nutationInLongitudeReader(t: Double, l: Double, l1: Double, f: Double, d: Double, omega: Double, arrayDoubleArray: Array<DoubleArray>): Double {

        var totalCoefficients = 0.0

        for (row in arrayDoubleArray) {
            totalCoefficients += (row[5] + row[6] * t) * sin(row[0] * l + row[1] * l1 + row[2] * f + row[3] * d + row[4] * omega) + row[7] * cos(row[0] * l + row[1] * l1 + row[2] * f + row[3] * d + row[4] * omega)
        }

        return totalCoefficients
    }

    /**
    * function to read nutation in obliquity terms for model IAU 2000B
    *
    * @param t is the same as jce
    * @param l Mean Anomaly of the Moon, L in radian
    * @param l1 Anomaly of the Sun, L' in radian
    * @param f Mean argument of the latitude of the Moon, F in radian
    * @param d Mean Elongation of the Moon from the Sun, D in radian
    * @param omega Mean Longitude of the Ascending Node of the Moon, omega in radian
    * @param arrayDoubleArray object of array
    *
    * @return nutationInObliquity the unit is 0.0000001s
    */
    fun nutationInObliquityReader(t: Double, l: Double, l1: Double, f: Double, d: Double, omega: Double, arrayDoubleArray: Array<DoubleArray>): Double {

        var totalCoefficients = 0.0

        for (row in arrayDoubleArray) {
            totalCoefficients += (row[8] + row[9] * t) * cos(row[0] * l + row[1] * l1 + row[2] * f + row[3] * d + row[4] * omega) + row[10] * sin(row[0] * l + row[1] * l1 + row[2] * f + row[3] * d + row[4] * omega)
        }

        return totalCoefficients
    }
}
