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

import com.andihasan7.lib.vsop87d.elpmpp02.dataclass.Vsop87dCsvRow
import com.esotericsoftware.kryo.kryo5.Kryo
import com.esotericsoftware.kryo.kryo5.io.Input
import kotlin.math.cos


object EarthLBRReader {

    /**
     * function to read earth lbr binary file and calculate the coefficients
     *
     * @param t is the same as jme Julian Millenium Ephemeris
     * @param resourcePath path of binary periodic term file
     *
     * @return totalCoefficients
     */
    fun earthLBRBinaryReader(t: Double, resourcePath: String): Double {

        val inputStream = this::class.java.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException("Resource $resourcePath not found")

        val kryo = Kryo()
        kryo.register(Vsop87dCsvRow::class.java)
        var totalCoefficients = 0.0

        Input(inputStream).use { input ->
            while (input.available() > 0) {
                val row = kryo.readObject(input, Vsop87dCsvRow::class.java)
                val coefficient = row.vA * cos(row.vB + row.vC * t)
                totalCoefficients += coefficient
            }
        }
        return totalCoefficients
    }
    
}