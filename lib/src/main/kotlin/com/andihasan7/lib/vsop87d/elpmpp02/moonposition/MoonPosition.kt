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
 
package com.andihasan7.lib.vsop87d.elpmpp02.moonposition

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.pow
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PositionType
import com.andihasan7.lib.vsop87d.elpmpp02.Nutation
import com.andihasan7.lib.vsop87d.elpmpp02.readerutil.MoonLBRReader
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil


object MoonPosition {
    
    // Moon Geocentric Coordinate
    /**
    * Moon Geocentric Longitude default in true and degrees
    *
    * @param jd, Julian Day
    * @param deltaT, in arc second
    * @param positionType, True or Apparent
    * @param unitType, Degrees or Radians
    *
    * @return 
    */
    fun moonGeocentricLongitude(jd: Double, deltaT: Double = 0.0, positionType: PositionType = PositionType.TRUE, unitType: UnitType = UnitType.DEGREES): Double {
        
        val MOON_L0 = "src/main/assets/MOON-L0.csv"
        val MOON_L1 = "src/main/assets/MOON-L1.csv"
        val MOON_L2 = "src/main/assets/MOON-L2.csv"
        val MOON_L3 = "src/main/assets/MOON-L3.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JCE)
        val deltaPsi = Nutation.nutationInLonAndObliquity(jd, deltaT)[0]
        
        val l0 = MoonLBRReader.moonLBRTermsReader(t, MOON_L0)
        val l1 = MoonLBRReader.moonLBRTermsReader(t, MOON_L1)
        val l2 = MoonLBRReader.moonLBRTermsReader(t, MOON_L2)
        val l3 = MoonLBRReader.moonLBRTermsReader(t, MOON_L3)
        
        val l = l0 + l1 * t + l2 * t.pow(2) + l3 * t.pow(3)
        val w0 = 3.81034409083088
        val w1 = 8399.68473007193
        val w2 = -0.0000331895204255009
        val w3 = 3.11024944910606E-08
        val w4 = -2.03282376489228E-10
        val w = w0 + w1 * t + w2 * t.pow(2) + w3 * t.pow(3) + w4 * t.pow(4)
        
        val p0 = 0.0
        val p1 = 5028.79695
        val p2 = 1.112
        val p3 = 0.000077
        val p4 = -0.00002353
        val p = p0 + p1 * t + p2 * t.pow(2) + p3 * t.pow(3) + p4 * t.pow(4)
        
        val lambdaAp = (Math.toDegrees(w) + (l / 3600.0) + (p / 3600)).mod(360.0)
        val lambdaApRad = Math.toRadians(lambdaAp)
        
        val abr = -0.00019524 - 0.00001059 * sin(Math.toRadians(225 + 477198.9 * t))
        
        val lambda = lambdaAp + deltaPsi + abr
        val lambdaRad = Math.toRadians(lambda)
        
        return when (unitType) {
            UnitType.DEGREES -> when (positionType) {
                PositionType.TRUE -> lambdaAp
                PositionType.APPARENT -> lambda
            }
            UnitType.RADIANS -> when (positionType) {
                PositionType.TRUE -> lambdaApRad
                PositionType.APPARENT -> lambdaRad
            }
        }
    }
    
    /**
    * Moon Geocentric Latitude default in true and degrees
    *
    * @param jd, Julian Day
    * @param deltaT, in arc second
    * @param positionType, True or Apparent
    * @param unitType, Degrees or Radians
    *
    * @return 
    */
    fun moonGeocentricLatitude(jd: Double, deltaT: Double = 0.0, positionType: PositionType = PositionType.TRUE, unitType: UnitType = UnitType.DEGREES): Double {
        
        val MOON_B0 = "src/main/assets/MOON-B0.csv"
        val MOON_B1 = "src/main/assets/MOON-B1.csv"
        val MOON_B2 = "src/main/assets/MOON-B2.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JCE)
        
        val b0 = MoonLBRReader.moonLBRTermsReader(t, MOON_B0)
        val b1 = MoonLBRReader.moonLBRTermsReader(t, MOON_B1)
        val b2 = MoonLBRReader.moonLBRTermsReader(t, MOON_B2)
        
        val betaAp = (b0 + b1 * t + b2 * t.pow(2)) / 3600
        val betaApRad = Math.toRadians(betaAp)
        
        val abr = -0.00001754 * sin(Math.toRadians(183.3 + 483202 * t))
        
        val beta = betaAp + abr
        val betaRad = Math.toRadians(beta)
        
        return when (unitType) {
            UnitType.DEGREES -> when (positionType) {
                PositionType.TRUE -> betaAp
                PositionType.APPARENT -> beta
            }
            UnitType.RADIANS -> when (positionType) {
                PositionType.TRUE -> betaApRad
                PositionType.APPARENT -> betaRad
            }
        }
    }
    
    /**
    * Moon Geocentric Distance default in true and KM
    *
    * @param jd, Julian Day
    * @param deltaT, in arc second
    * @param positionType, True or Apparent
    * @param distanceType, KM, AU, or ER
    *
    * @return 
    */
    fun moonGeocentricDistance(jd: Double, deltaT: Double = 0.0, positionType: PositionType = PositionType.TRUE, distanceType: DistanceType = DistanceType.KM): Double {
        
        val MOON_R0 = "src/main/assets/MOON-R0.csv"
        val MOON_R1 = "src/main/assets/MOON-R1.csv"
        val MOON_R2 = "src/main/assets/MOON-R2.csv"
        val MOON_R3 = "src/main/assets/MOON-R3.csv"
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JCE)
        
        val r0 = MoonLBRReader.moonLBRTermsReader(t, MOON_R0)
        val r1 = MoonLBRReader.moonLBRTermsReader(t, MOON_R1)
        val r2 = MoonLBRReader.moonLBRTermsReader(t, MOON_R2)
        val r3 = MoonLBRReader.moonLBRTermsReader(t, MOON_R3)
        
        // r true km
        val rTrueKM = r0 + r1 * t + r2 * t.pow(2) + r3 * t.pow(3)
        val rTrueAU = rTrueKM / 149597870.7
        val rTrueER = rTrueKM / 6371.0
        
        val abr = 0.0708 * cos(Math.toRadians(225.0 + 477198.9 * t))
        
        // apparent km
        val rAppaKM = rTrueKM + abr
        val rAppaAU = rAppaKM / 149597870.7
        val rAppaER = rAppaKM / 6371.0
        
        return when (positionType) {
            PositionType.TRUE -> when (distanceType) {
                DistanceType.KM -> rTrueKM
                DistanceType.AU -> rTrueAU
                DistanceType.ER -> rTrueER
                // else -> throw IllegalArgumentException("Invalid input $distanceType, see the documentation")
            }
            PositionType.APPARENT -> when (distanceType) {
                DistanceType.KM -> rAppaKM
                DistanceType.AU -> rAppaAU
                DistanceType.ER -> rAppaER
            }
            // else -> throw IllegalArgumentException("Invalid input $positionType, see the documentation")
        }
    }
    
}