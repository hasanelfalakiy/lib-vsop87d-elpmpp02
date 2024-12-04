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

import com.andihasan7.lib.vsop87d.elpmpp02.dataclass.Nutation2000bCsvRow
import com.esotericsoftware.kryo.kryo5.Kryo
import com.esotericsoftware.kryo.kryo5.io.Input
import kotlin.math.cos
import kotlin.math.sin
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.pow

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
    * @param path nutation .bin file path string
    *
    * @return nutationInLongitude the unit is 0.0000001s
    */
    fun nutationInLongitudeBinaryReader(t: Double, l: Double, l1: Double, f: Double, d: Double, omega: Double, resourcePath: String): Double {

        val inputStream = this::class.java.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException("Resource $resourcePath not found")

        val kryo = Kryo()
        kryo.register(Nutation2000bCsvRow::class.java)
        var totalCoefficients = 0.0

        Input(inputStream).use { input ->
            while (input.available() > 0) {
                val row = kryo.readObject(input, Nutation2000bCsvRow::class.java)
                val coefficient = (row.vA + row.vA1 * t) * sin(row.vL * l + row.vL1 * l1 + row.vF * f + row.vD * d + row.vOmega * omega) + row.vA2 * cos(row.vL * l + row.vL1 * l1 + row.vF * f + row.vD * d + row.vOmega * omega)
                totalCoefficients += coefficient
            }
        }
        return totalCoefficients
    }

    fun nutationInLongitudeReader(t: Double, l: Double, l1: Double, f: Double, d: Double, omega: Double, resourcePath: String): Double {

        val dataArray = ReadBinaryAsArray.readBinaryAsArray(resourcePath)

        var totalCoefficients = 0.0
        for (row in dataArray) {
            val vL = row[0]
            val vL1 = row[1]
            val vF = row[2]
            val vD = row[3]
            val vOmega = row[4]
            val vA = row[5]
            val vA1 = row[6]
            val vA2 = row[7]
            totalCoefficients += (vA + vA1 * t) * sin(vL * l + vL1 * l1 + vF * f + vD * d + vOmega * omega) + vA2 * cos(vL * l + vL1 * l1 + vF * f + vD * d + vOmega * omega)
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
    * @param path nutation .bin file path string
    *
    * @return nutationInObliquity the unit is 0.0000001s
    */
    fun nutationInObliquityBinaryReader(t: Double, l: Double, l1: Double, f: Double, d: Double, omega: Double, resourcePath: String): Double {

        val inputStream = this::class.java.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException("Resource $resourcePath not found")

        val kryo = Kryo()
        kryo.register(Nutation2000bCsvRow::class.java)
        var totalCoefficients = 0.0

        Input(inputStream).use { input ->
            while (input.available() > 0) {
                val row = kryo.readObject(input, Nutation2000bCsvRow::class.java)
                val coefficient = (row.vB + row.vB1 * t) * cos(row.vL * l + row.vL1 * l1 + row.vF * f + row.vD * d + row.vOmega * omega) + row.vB2 * sin(row.vL * l + row.vL1 * l1 + row.vF * f + row.vD * d + row.vOmega * omega)
                totalCoefficients += coefficient
            }
        }
        return totalCoefficients
    }

    fun nutationInObliquityReader(t: Double, l: Double, l1: Double, f: Double, d: Double, omega: Double, resourcePath: String): Double {

        val dataArray = ReadBinaryAsArray.readBinaryAsArray(resourcePath)

        var totalCoefficients = 0.0
        for (row in dataArray) {
            val vL = row[0]
            val vL1 = row[1]
            val vF = row[2]
            val vD = row[3]
            val vOmega = row[4]
            val vB = row[8]
            val vB1 = row[9]
            val vB2 = row[10]
            totalCoefficients += (vB + vB1 * t) * cos(vL * l + vL1 * l1 + vF * f + vD * d + vOmega * omega) + vB2 * sin(vL * l + vL1 * l1 + vF * f + vD * d + vOmega * omega)
        }
        return totalCoefficients
    }
}
