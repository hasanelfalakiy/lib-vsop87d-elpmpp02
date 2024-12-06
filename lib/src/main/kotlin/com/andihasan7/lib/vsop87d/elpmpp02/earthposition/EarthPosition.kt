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
 
package com.andihasan7.lib.vsop87d.elpmpp02.earthposition

import com.andihasan7.lib.vsop87d.elpmpp02.earthterms.*
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import com.andihasan7.lib.vsop87d.elpmpp02.readerutil.EarthLBRReader
import kotlin.math.cos
import kotlin.math.pow
import kotlin.mod

object EarthPosition {
    
    
    /**
    * Earth Heliocentric Longitude L default in degrees
    * 
    * @param jd Julian Day
    * @param deltaT in arcsecond
    *
    * @return eartHeliocentricLongitude
    */
    fun earthHeliocentricLongitude(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
    
        val earthL0 = EARTH_L0.earth_L0
        val earthL1 = EARTH_L1.earth_L1
        val earthL2 = EARTH_L2.earth_L2
        val earthL3 = EARTH_L3.earth_L3
        val earthL4 = EARTH_L4.earth_L4
        val earthL5 = EARTH_L5.earth_L5
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        /*val l0 = EarthLBRReader.earthLBRReader(t, earthL0)
        val l1 = EarthLBRReader.earthLBRReader(t, earthL1)
        val l2 = EarthLBRReader.earthLBRReader(t, earthL2)
        val l3 = EarthLBRReader.earthLBRReader(t, earthL3)
        val l4 = EarthLBRReader.earthLBRReader(t, earthL4)
        val l5 = EarthLBRReader.earthLBRReader(t, earthL5)*/
        var l0 = 0.0
        var l1 = 0.0
        var l2 = 0.0
        var l3 = 0.0
        var l4 = 0.0
        var l5 = 0.0

        for (row in earthL0) {
            l0 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthL1) {
            l1 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthL2) {
            l2 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthL3) {
            l3 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthL4) {
            l4 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthL5) {
            l5 += row[0] * cos(row[1] + row[2] * t)
        }

        val eartHeliocentricLongitude = (l0 + l1 * t + l2 * t.pow(2) + l3 * t.pow(3) + l4 * t.pow(4) + l5 * t.pow(5)).mod(360.0)

        return when (unitType) {

            UnitType.DEGREES -> (Math.toDegrees(eartHeliocentricLongitude)).mod(360.0)
            UnitType.RADIANS -> eartHeliocentricLongitude
        }
        
    }
    
    /**
    * Earth Heliocentric Latitude B default in degrees
    * 
    * @param jd Julian Day
    * @param deltaT in arcsecond
    *
    * @return eartHeliocentricLatitude
    */
    fun earthHeliocentricLatitude(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
    
        val earthB0 = EARTH_B0.earth_B0
        val earthB1 = EARTH_B1.earth_B1
        val earthB2 = EARTH_B2.earth_B2
        val earthB3 = EARTH_B3.earth_B3
        val earthB4 = EARTH_B4.earth_B4
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        /*val b0 = EarthLBRReader.earthLBRReader(t, earthB0)
        val b1 = EarthLBRReader.earthLBRReader(t, earthB1)
        val b2 = EarthLBRReader.earthLBRReader(t, earthB2)
        val b3 = EarthLBRReader.earthLBRReader(t, earthB3)
        val b4 = EarthLBRReader.earthLBRReader(t, earthB4)*/

        var b0 = 0.0
        var b1 = 0.0
        var b2 = 0.0
        var b3 = 0.0
        var b4 = 0.0

        for (row in earthB0) {
            b0 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthB1) {
            b1 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthB2) {
            b2 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthB3) {
            b3 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthB4) {
            b4 += row[0] * cos(row[1] + row[2] * t)
        }
        
        val eartHeliocentricLatitude = b0 + b1 * t + b2 * t.pow(2) + b3 * t.pow(3) + b4 * t.pow(4)
        
        return when (unitType) {
            UnitType.DEGREES -> (Math.toDegrees(eartHeliocentricLatitude)).mod(360.0)
            UnitType.RADIANS -> eartHeliocentricLatitude
        }
    }
    
    /**
    * Earth Radius Vector R is the same as Sun Geocentric Distance in AU unit
    * 
    * @param jd, Julian Day
    * @param deltaT, in arcsecond
    *
    * @return eartRadiusVector
    */
    fun earthRadiusVector(jd: Double, deltaT: Double = 0.0, distanceType: DistanceType): Double {
    
        val earthR0 = EARTH_R0.earth_R0
        val earthR1 = EARTH_R1.earth_R1
        val earthR2 = EARTH_R2.earth_R2
        val earthR3 = EARTH_R3.earth_R3
        val earthR4 = EARTH_R4.earth_R4
        val earthR5 = EARTH_R5.earth_R5
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        /*val r0 = EarthLBRReader.earthLBRReader(t, earthR0)
        val r1 = EarthLBRReader.earthLBRReader(t, earthR1)
        val r2 = EarthLBRReader.earthLBRReader(t, earthR2)
        val r3 = EarthLBRReader.earthLBRReader(t, earthR3)
        val r4 = EarthLBRReader.earthLBRReader(t, earthR4)
        val r5 = EarthLBRReader.earthLBRReader(t, earthR5)*/
        var r0 = 0.0
        var r1 = 0.0
        var r2 = 0.0
        var r3 = 0.0
        var r4 = 0.0
        var r5 = 0.0

        for (row in earthR0) {
            r0 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthR1) {
            r1 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthR2) {
            r2 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthR3) {
            r3 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthR4) {
            r4 += row[0] * cos(row[1] + row[2] * t)
        }

        for (row in earthR5) {
            r5 += row[0] * cos(row[1] + row[2] * t)
        }

        
        val _earthRadiusVector = r0 + r1 * t + r2 * t.pow(2) + r3 * t.pow(3) + r4 * t.pow(4) + r5 * t.pow(5)
        
        val earthRadiusVector = when (distanceType) {
            DistanceType.AU -> _earthRadiusVector
            DistanceType.KM -> {
                _earthRadiusVector * 149597870.7
            }
            DistanceType.ER -> {
                _earthRadiusVector * 149597870.7 / 6371.0
            }
        }
        
        return earthRadiusVector
    }
}
