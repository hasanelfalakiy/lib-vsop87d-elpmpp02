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
import java.io.BufferedReader
import java.io.InputStreamReader

object EarthLBRReader {
    
    /**
    * LBR periodic table reader for earth
    *
    * @param t is the same as jme Julian Millenium Ephemeris
    * @param path periodic table .csv file path
    *
    * @return totalCoefficient: Double
    *
    */
    fun earthLBRTermsReader(t: Double, path: String): Double {
        
        val inputStream = this::class.java.classLoader.getResourceAsStream(path)
        val reader = BufferedReader(InputStreamReader(inputStream))

        var totalCoefficient = 0.0 // Variable to store total coefficients

        // Skip the first line (header)
        reader.readLine() // Skip header

        // Loop to read CSV rows and calculate coefficients
        reader.forEachLine { line ->
            val columns = line.split(",")
            val vA = columns[1].toDouble()
            val vB = columns[2].toDouble()
            val vC = columns[3].toDouble()

            // Calculate the coefficient and add it to the total.
            val coefficient = vA * cos(vB + vC * t)
        
            totalCoefficient += coefficient
        }

        reader.close() // Close the reader when finished

        return totalCoefficient // Return total coefficients
        
    }
    
}