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

import io.deephaven.csv.CsvSpecs
import io.deephaven.csv.reading.CsvReader
import io.deephaven.csv.sinks.SinkFactory
import kotlin.math.cos


object EarthLBRReader {

    /**
     * function to read earth lbr binary file and calculate the coefficients
     *
     * @param t is the same as jme Julian Millenium Ephemeris
     * @param arrayDoubleArray object of array
     *
     * @return totalCoefficients
     */
    fun earthLBRReader(t: Double, arrayDoubleArray: Array<DoubleArray>): Double {

        var totalCoefficients = 0.0

        for (row in arrayDoubleArray) {
            totalCoefficients += row[0] * cos(row[1] + row[2] * t)
        }

        return totalCoefficients
    }

    /**
     * function to read earth lbr csv file and calculate the coefficients with deephaven
     *
     * @param t is the same as jme Julian Millenium Ephemeris
     * @param resourcePath path of csv file
     *
     * @return totalCoefficients
     */
    fun earthLBRDeephavenReader(t: Double, resourcePath: String): Double {

        val inputStream = this::class.java.classLoader.getResourceAsStream(resourcePath) ?: throw IllegalArgumentException("File not found: $resourcePath")

        // configure csv specs
        val specs = CsvSpecs.csv()
        // read csv using Deephaven
        val result = CsvReader.read(specs, inputStream, SinkFactory.arrays())
        val numRows = result.numRows()

        var totalCoefficients = 0.0

        // find the columns by name
        val aColumn = result.columns()[1].data() as? DoubleArray ?: throw IllegalStateException("Column 1 (A) not found or invalid type")
        val bColumn = result.columns()[2].data() as? DoubleArray ?: throw IllegalStateException("Column 2 (B) not found or invalid type")
        val cColumn = result.columns()[3].data() as? DoubleArray ?: throw IllegalStateException("Column 3 (C) not found or invalid type")

        // proses each row
        for (rowIndex in 0 until numRows.toInt()) {
            val vA = aColumn[rowIndex]
            val vB = bColumn[rowIndex]
            val vC = cColumn[rowIndex]

            totalCoefficients += vA * cos(vB + vC * t)
        }

        return totalCoefficients

    }

}

