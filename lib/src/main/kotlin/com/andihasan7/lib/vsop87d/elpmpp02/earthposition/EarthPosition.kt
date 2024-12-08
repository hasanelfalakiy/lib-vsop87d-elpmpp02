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
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.readerutil.EarthLBRReader
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import kotlin.math.pow

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
    
        /*val earthL0 = EARTH_L0.earth_L0
        val earthL1 = EARTH_L1.earth_L1
        val earthL2 = EARTH_L2.earth_L2
        val earthL3 = EARTH_L3.earth_L3
        val earthL4 = EARTH_L4.earth_L4
        val earthL5 = EARTH_L5.earth_L5*/
        val earthL0 = "EARTH-L0.csv"
        val earthL1 = "EARTH-L1.csv"
        val earthL2 = "EARTH-L2.csv"
        val earthL3 = "EARTH-L3.csv"
        val earthL4 = "EARTH-L4.csv"
        val earthL5 = "EARTH-L5.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val l0 = EarthLBRReader.earthLBRDeephavenReader(t, earthL0)
        val l1 = EarthLBRReader.earthLBRDeephavenReader(t, earthL1)
        val l2 = EarthLBRReader.earthLBRDeephavenReader(t, earthL2)
        val l3 = EarthLBRReader.earthLBRDeephavenReader(t, earthL3)
        val l4 = EarthLBRReader.earthLBRDeephavenReader(t, earthL4)
        val l5 = EarthLBRReader.earthLBRDeephavenReader(t, earthL5)

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
    
        /*val earthB0 = EARTH_B0.earth_B0
        val earthB1 = EARTH_B1.earth_B1
        val earthB2 = EARTH_B2.earth_B2
        val earthB3 = EARTH_B3.earth_B3
        val earthB4 = EARTH_B4.earth_B4*/
        val earthB0 = "EARTH-B0.csv"
        val earthB1 = "EARTH-B1.csv"
        val earthB2 = "EARTH-B2.csv"
        val earthB3 = "EARTH-B3.csv"
        val earthB4 = "EARTH-B4.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val b0 = EarthLBRReader.earthLBRDeephavenReader(t, earthB0)
        val b1 = EarthLBRReader.earthLBRDeephavenReader(t, earthB1)
        val b2 = EarthLBRReader.earthLBRDeephavenReader(t, earthB2)
        val b3 = EarthLBRReader.earthLBRDeephavenReader(t, earthB3)
        val b4 = EarthLBRReader.earthLBRDeephavenReader(t, earthB4)
        
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
    
        /*val earthR0 = EARTH_R0.earth_R0
        val earthR1 = EARTH_R1.earth_R1
        val earthR2 = EARTH_R2.earth_R2
        val earthR3 = EARTH_R3.earth_R3
        val earthR4 = EARTH_R4.earth_R4
        val earthR5 = EARTH_R5.earth_R5*/
        val earthR0 = "EARTH-R0.csv"
        val earthR1 = "EARTH-R1.csv"
        val earthR2 = "EARTH-R2.csv"
        val earthR3 = "EARTH-R3.csv"
        val earthR4 = "EARTH-R4.csv"
        val earthR5 = "EARTH-R5.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val r0 = EarthLBRReader.earthLBRDeephavenReader(t, earthR0)
        val r1 = EarthLBRReader.earthLBRDeephavenReader(t, earthR1)
        val r2 = EarthLBRReader.earthLBRDeephavenReader(t, earthR2)
        val r3 = EarthLBRReader.earthLBRDeephavenReader(t, earthR3)
        val r4 = EarthLBRReader.earthLBRDeephavenReader(t, earthR4)
        val r5 = EarthLBRReader.earthLBRDeephavenReader(t, earthR5)
        
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
