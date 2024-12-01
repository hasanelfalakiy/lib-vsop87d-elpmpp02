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
import java.io.BufferedReader
import java.io.InputStreamReader

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
    * @param path nutation .csv file path string
    *
    * @return nutationInLongitude the unit is 0.0000001s
    */
    fun readNutationInLongitude(t: Double, l: Double, l1: Double, f: Double, d: Double, omega: Double, path: String): Double {
    
        val inputStream = this::class.java.classLoader.getResourceAsStream(path)
        val reader = BufferedReader(InputStreamReader(inputStream))

        var totalCoefficient = 0.0 // Variable to store total coefficients

        // Skip the first line (header)
        reader.readLine() // Skip header

        // Loop to read CSV rows and calculate coefficients
        reader.forEachLine { line ->
            val columns = line.split(",")
            val vL = columns[1].toDouble()
            val vL1 = columns[2].toDouble()
            val vF = columns[3].toDouble()
            val vD = columns[4].toDouble()
            val vOmega = columns[5].toDouble()
        
            val vA = columns[6].toDouble()
            val vA1 = columns[7].toDouble()
            val vA2 = columns[8].toDouble()

            // Calculate the coefficient and add it to the total.
            val coefficient = (vA + vA1 * t) * sin(vL * l + vL1 * l1 + vF * f + vD * d + vOmega * omega) + vA2 * cos(vL * l + vL1 * l1 + vF * f + vD * d + vOmega * omega)
        
            totalCoefficient += coefficient
        }

        reader.close() // Close the reader when finished

        return totalCoefficient // Return total coefficients
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
    * @param path nutation .csv file path string
    *
    * @return nutationInObliquity the unit is 0.0000001s
    */
    fun readNutationInObliquity(t: Double, l: Double, l1: Double, f: Double, d: Double, omega: Double, path: String): Double {
    
        val inputStream = this::class.java.classLoader.getResourceAsStream(path)
        val reader = BufferedReader(InputStreamReader(inputStream))

        var totalCoefficient = 0.0 // Variable to store total coefficients

        // Skip the first line (header)
        reader.readLine() // Skip header

        // Loop to read CSV rows and calculate coefficients
        reader.forEachLine { line ->
            val columns = line.split(",")
            val vL = columns[1].toDouble()
            val vL1 = columns[2].toDouble()
            val vF = columns[3].toDouble()
            val vD = columns[4].toDouble()
            val vOmega = columns[5].toDouble()
        
            val vB = columns[9].toDouble()
            val vB1 = columns[10].toDouble()
            val vB2 = columns[11].toDouble()

            // Calculate the coefficient and add it to the total.
            val coefficient = (vB + vB1 * t) * cos(vL * l + vL1 * l1 + vF * f + vD * d + vOmega * omega) + vB2 * sin(vL * l + vL1 * l1 + vF * f + vD * d + vOmega * omega)
        
            totalCoefficient += coefficient
        }

        reader.close() // Close the reader when finished

        return totalCoefficient // Return total coefficients
    }
}
