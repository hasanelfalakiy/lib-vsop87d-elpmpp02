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

import kotlin.math.sin
import kotlin.math.pow
import java.io.BufferedReader
import java.io.InputStreamReader

object MoonLBRReader {
    
    /**
    * LBR periodic table reader for moon
    *
    * @param t is the same as jce Julian Ephemeris  Century
    * @param path periodic table .csv file path
    *
    * @return totalCoefficient: Double
    *
    */
    fun moonLBRTermsReader(t: Double, path: String): Double {
        
        val inputStream = this::class.java.classLoader.getResourceAsStream(path)
        
        // Reading files with bufferedReader and UTF-8 encoding
        return inputStream.bufferedReader(Charsets.UTF_8).use { reader ->

            var totalCoefficient = 0.0 // Variable to store total coefficients

            // Skip the first line (header)
            reader.readLine() // Skip header

            // Loop to read CSV rows and calculate coefficients
            reader.lineSequence().forEach { line ->
                val columns = line.split(",")
                val vVN = columns[1].toDouble()
                val vA0 = columns[2].toDouble()
                val vA1 = columns[3].toDouble()
                val vA2 = columns[4].toDouble()
                val vA3 = columns[5].toDouble()
                val vA4 = columns[6].toDouble()

                // Calculate the coefficient and add it to the total.
                val coefficient = vVN * sin(vA0 + vA1 * t + vA2 * t.pow(2) + vA3 * t.pow(3) + vA4 * t.pow(4))
        
                totalCoefficient += coefficient
            }

            totalCoefficient // Return total coefficients
        }
    }
}