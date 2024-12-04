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

import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import com.andihasan7.lib.vsop87d.elpmpp02.readerutil.EarthLBRReader
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
    
        val earthL0 = "EARTH-L0.bin"
        val earthL1 = "EARTH-L1.bin"
        val earthL2 = "EARTH-L2.bin"
        val earthL3 = "EARTH-L3.bin"
        val earthL4 = "EARTH-L4.bin"
        val earthL5 = "EARTH-L5.bin"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val l0 = EarthLBRReader.earthLBRReader(t, earthL0)
        val l1 = EarthLBRReader.earthLBRReader(t, earthL1)
        val l2 = EarthLBRReader.earthLBRReader(t, earthL2)
        val l3 = EarthLBRReader.earthLBRReader(t, earthL3)
        val l4 = EarthLBRReader.earthLBRReader(t, earthL4)
        val l5 = EarthLBRReader.earthLBRReader(t, earthL5)
        
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
    
        val earthB0 = "EARTH-B0.bin"
        val earthB1 = "EARTH-B1.bin"
        val earthB2 = "EARTH-B2.bin"
        val earthB3 = "EARTH-B3.bin"
        val earthB4 = "EARTH-B4.bin"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val b0 = EarthLBRReader.earthLBRReader(t, earthB0)
        val b1 = EarthLBRReader.earthLBRReader(t, earthB1)
        val b2 = EarthLBRReader.earthLBRReader(t, earthB2)
        val b3 = EarthLBRReader.earthLBRReader(t, earthB3)
        val b4 = EarthLBRReader.earthLBRReader(t, earthB4)
        
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
    
        val earthR0 = "EARTH-R0.bin"
        val earthR1 = "EARTH-R1.bin"
        val earthR2 = "EARTH-R2.bin"
        val earthR3 = "EARTH-R3.bin"
        val earthR4 = "EARTH-R4.bin"
        val earthR5 = "EARTH-R5.bin"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val r0 = EarthLBRReader.earthLBRReader(t, earthR0)
        val r1 = EarthLBRReader.earthLBRReader(t, earthR1)
        val r2 = EarthLBRReader.earthLBRReader(t, earthR2)
        val r3 = EarthLBRReader.earthLBRReader(t, earthR3)
        val r4 = EarthLBRReader.earthLBRReader(t, earthR4)
        val r5 = EarthLBRReader.earthLBRReader(t, earthR5)
        
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
