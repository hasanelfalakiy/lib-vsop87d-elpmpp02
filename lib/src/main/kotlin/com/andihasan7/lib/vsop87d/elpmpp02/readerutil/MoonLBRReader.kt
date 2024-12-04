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

import com.andihasan7.lib.vsop87d.elpmpp02.dataclass.Elpmpp02CsvRow
import com.esotericsoftware.kryo.kryo5.Kryo
import com.esotericsoftware.kryo.kryo5.io.Input
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.pow

object MoonLBRReader {

    /**
     * function to read moon lbr binary file and calculate the coefficients
     *
     * @param t is the same as jce Julian Century Ephemeris
     * @param resourcePath path of binary file
     *
     * @return totalCoefficients
     */
    fun moonLBRBinaryReader(t: Double, resourcePath: String): Double {

        val inputStream = this::class.java.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException("Resource $resourcePath not found")

        val kryo = Kryo()
        kryo.register(Elpmpp02CsvRow::class.java)
        var totalCoefficients = 0.0

        Input(inputStream).use { input ->
            while (input.available() > 0) {
                val row = kryo.readObject(input, Elpmpp02CsvRow::class.java)
                val coefficient = row.vVN * sin(row.vA0 + row.vA1 * t + row.vA2 * t.pow(2) + row.vA3 * t.pow(3) + row.vA4 * t.pow(4))
                totalCoefficients += coefficient
            }
        }
        return totalCoefficients
    }

    /**
     * function to read moon lbr and calculate the coefficients
     *
     * @param t is the same as jme Julian Millenium Ephemeris
     * @param resourcePath path of binary periodic term file
     *
     * @return totalCoefficients
     */
    fun moonLBRReader(t: Double, resourcePath: String): Double {

        val dataArray = ReadBinaryAsArray.readBinaryAsArray(resourcePath)

        var totalCoefficients = 0.0
        for (row in dataArray) {
            val vVN = row[0]
            val vA0 = row[1]
            val vA1 = row[2]
            val vA2 = row[3]
            val vA3 = row[4]
            val vA4 = row[5]
            totalCoefficients += vVN * sin(vA0 + vA1 * t + vA2 * t.pow(2) + vA3 * t.pow(3) + vA4 * t.pow(4))
        }
        return totalCoefficients
    }
}