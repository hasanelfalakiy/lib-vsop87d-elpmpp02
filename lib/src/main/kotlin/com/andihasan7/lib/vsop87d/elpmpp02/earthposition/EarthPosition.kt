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
    * Earth Heliocentric Longitude default in degrees
    * 
    * @param jd, Julian Day
    * @param deltaT
    *
    * @return eartHeliocentricLongitude
    */
    fun earthHeliocentricLongitude(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
    
        val EARTH_L0 = "src/main/assets/EARTH-L0.csv"
        val EARTH_L1 = "src/main/assets/EARTH-L1.csv"
        val EARTH_L2 = "src/main/assets/EARTH-L2.csv"
        val EARTH_L3 = "src/main/assets/EARTH-L3.csv"
        val EARTH_L4 = "src/main/assets/EARTH-L4.csv"
        val EARTH_L5 = "src/main/assets/EARTH-L5.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val l0 = EarthLBRReader.earthLBRTermsReader(t, EARTH_L0)
        val l1 = EarthLBRReader.earthLBRTermsReader(t, EARTH_L1)
        val l2 = EarthLBRReader.earthLBRTermsReader(t, EARTH_L2)
        val l3 = EarthLBRReader.earthLBRTermsReader(t, EARTH_L3)
        val l4 = EarthLBRReader.earthLBRTermsReader(t, EARTH_L4)
        val l5 = EarthLBRReader.earthLBRTermsReader(t, EARTH_L5)
        
        val _eartHeliocentricLongitude = (l0 + l1 * t + l2 * t.pow(2) + l3 * t.pow(3) + l4 * t.pow(4) + l5 * t.pow(5)).mod(360.0)
        
        return when (unitType) {
            UnitType.DEGREES -> (Math.toDegrees(_eartHeliocentricLongitude)).mod(360.0)
            UnitType.RADIANS -> _eartHeliocentricLongitude
        }
        
    }
    
    /**
    * Earth Heliocentric Latitude default in degrees
    * 
    * @param jd, Julian Day
    * @param deltaT
    *
    * @return eartHeliocentricLatitude
    */
    fun earthHeliocentricLatitude(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
    
        val EARTH_B0 = "src/main/assets/EARTH-B0.csv"
        val EARTH_B1 = "src/main/assets/EARTH-B1.csv"
        val EARTH_B2 = "src/main/assets/EARTH-B2.csv"
        val EARTH_B3 = "src/main/assets/EARTH-B3.csv"
        val EARTH_B4 = "src/main/assets/EARTH-B4.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val b0 = EarthLBRReader.earthLBRTermsReader(t, EARTH_B0)
        val b1 = EarthLBRReader.earthLBRTermsReader(t, EARTH_B1)
        val b2 = EarthLBRReader.earthLBRTermsReader(t, EARTH_B2)
        val b3 = EarthLBRReader.earthLBRTermsReader(t, EARTH_B3)
        val b4 = EarthLBRReader.earthLBRTermsReader(t, EARTH_B4)
        
        val _eartHeliocentricLatitude = b0 + b1 * t + b2 * t.pow(2) + b3 * t.pow(3) + b4 * t.pow(4)
        
        return when (unitType) {
            UnitType.DEGREES -> (Math.toDegrees(_eartHeliocentricLatitude)).mod(360.0)
            UnitType.RADIANS -> _eartHeliocentricLatitude
        }
    }
    
    /**
    * Earth Radius Vector is the same as Sun Geocentric Distance in AU unit
    * 
    * @param jd, Julian Day
    * @param deltaT
    *
    * @return eartRadiusVector
    */
    fun earthRadiusVector(jd: Double, deltaT: Double = 0.0, distanceType: DistanceType): Double {
    
        val EARTH_R0 = "src/main/assets/EARTH-R0.csv"
        val EARTH_R1 = "src/main/assets/EARTH-R1.csv"
        val EARTH_R2 = "src/main/assets/EARTH-R2.csv"
        val EARTH_R3 = "src/main/assets/EARTH-R3.csv"
        val EARTH_R4 = "src/main/assets/EARTH-R4.csv"
        val EARTH_R5 = "src/main/assets/EARTH-R5.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        
        val r0 = EarthLBRReader.earthLBRTermsReader(t, EARTH_R0)
        val r1 = EarthLBRReader.earthLBRTermsReader(t, EARTH_R1)
        val r2 = EarthLBRReader.earthLBRTermsReader(t, EARTH_R2)
        val r3 = EarthLBRReader.earthLBRTermsReader(t, EARTH_R3)
        val r4 = EarthLBRReader.earthLBRTermsReader(t, EARTH_R4)
        val r5 = EarthLBRReader.earthLBRTermsReader(t, EARTH_R5)
        
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
