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
    
        val earthL0 = "src/main/assets/EARTH-L0.csv"
        val earthL1 = "src/main/assets/EARTH-L1.csv"
        val earthL2 = "src/main/assets/EARTH-L2.csv"
        val earthL3 = "src/main/assets/EARTH-L3.csv"
        val earthL4 = "src/main/assets/EARTH-L4.csv"
        val earthL5 = "src/main/assets/EARTH-L5.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val l0 = EarthLBRReader.earthLBRTermsReader(t, earthL0)
        val l1 = EarthLBRReader.earthLBRTermsReader(t, earthL1)
        val l2 = EarthLBRReader.earthLBRTermsReader(t, earthL2)
        val l3 = EarthLBRReader.earthLBRTermsReader(t, earthL3)
        val l4 = EarthLBRReader.earthLBRTermsReader(t, earthL4)
        val l5 = EarthLBRReader.earthLBRTermsReader(t, earthL5)
        
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
    
        val earthB0 = "src/main/assets/EARTH-B0.csv"
        val earthB1 = "src/main/assets/EARTH-B1.csv"
        val earthB2 = "src/main/assets/EARTH-B2.csv"
        val earthB3 = "src/main/assets/EARTH-B3.csv"
        val earthB4 = "src/main/assets/EARTH-B4.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val b0 = EarthLBRReader.earthLBRTermsReader(t, earthB0)
        val b1 = EarthLBRReader.earthLBRTermsReader(t, earthB1)
        val b2 = EarthLBRReader.earthLBRTermsReader(t, earthB2)
        val b3 = EarthLBRReader.earthLBRTermsReader(t, earthB3)
        val b4 = EarthLBRReader.earthLBRTermsReader(t, earthB4)
        
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
    
        val earthR0 = "src/main/assets/EARTH-R0.csv"
        val earthR1 = "src/main/assets/EARTH-R1.csv"
        val earthR2 = "src/main/assets/EARTH-R2.csv"
        val earthR3 = "src/main/assets/EARTH-R3.csv"
        val earthR4 = "src/main/assets/EARTH-R4.csv"
        val earthR5 = "src/main/assets/EARTH-R5.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val r0 = EarthLBRReader.earthLBRTermsReader(t, earthR0)
        val r1 = EarthLBRReader.earthLBRTermsReader(t, earthR1)
        val r2 = EarthLBRReader.earthLBRTermsReader(t, earthR2)
        val r3 = EarthLBRReader.earthLBRTermsReader(t, earthR3)
        val r4 = EarthLBRReader.earthLBRTermsReader(t, earthR4)
        val r5 = EarthLBRReader.earthLBRTermsReader(t, earthR5)
        
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
