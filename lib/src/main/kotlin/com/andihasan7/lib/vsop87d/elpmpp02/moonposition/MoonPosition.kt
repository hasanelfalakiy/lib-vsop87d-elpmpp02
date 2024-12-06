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

import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.asin
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.math.pow
import com.andihasan7.lib.vsop87d.elpmpp02.correction.Correction
import com.andihasan7.lib.vsop87d.elpmpp02.earthposition.EarthPosition
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.MoonAltType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.PositionType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.Nutation
import com.andihasan7.lib.vsop87d.elpmpp02.moonterms.*
import com.andihasan7.lib.vsop87d.elpmpp02.readerutil.MoonLBRReader
import com.andihasan7.lib.vsop87d.elpmpp02.sunposition.SunPosition
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil


object MoonPosition {
    
    // Moon Geocentric Coordinate
    /**
    * Moon Geocentric Longitude default in true and degrees
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param positionType True or Apparent
    * @param unitType Degrees or Radians
    *
    * @return 
    */
    fun moonGeocentricLongitude(jd: Double, deltaT: Double = 0.0, positionType: PositionType = PositionType.TRUE, unitType: UnitType = UnitType.DEGREES): Double {
        
        val moonL000 = MOON_L0_00.moon_L0_00
        val moonL001 = MOON_L0_01.moon_L0_01
        val moonL002 = MOON_L0_02.moon_L0_02
        val moonL003 = MOON_L0_03.moon_L0_03
        val moonL004 = MOON_L0_04.moon_L0_04
        val moonL005 = MOON_L0_05.moon_L0_05
        val moonL006 = MOON_L0_06.moon_L0_06
        val moonL007 = MOON_L0_07.moon_L0_07
        val moonL008 = MOON_L0_08.moon_L0_08
        val moonL009 = MOON_L0_09.moon_L0_09
        val moonL010 = MOON_L0_10.moon_L0_10
        val moonL011 = MOON_L0_11.moon_L0_11
        val moonL012 = MOON_L0_12.moon_L0_12
        val moonL1 = MOON_L1.moon_L1
        val moonL2 = MOON_L2.moon_L2
        val moonL3 = MOON_L3.moon_L3
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JCE)
        val deltaPsi = Nutation.nutationInLonAndObliquity(jd, deltaT)[0]

        val l000 = MoonLBRReader.moonLBRReader(t, moonL000)
        val l001 = MoonLBRReader.moonLBRReader(t, moonL001)
        val l002 = MoonLBRReader.moonLBRReader(t, moonL002)
        val l003 = MoonLBRReader.moonLBRReader(t, moonL003)
        val l004 = MoonLBRReader.moonLBRReader(t, moonL004)
        val l005 = MoonLBRReader.moonLBRReader(t, moonL005)
        val l006 = MoonLBRReader.moonLBRReader(t, moonL006)
        val l007 = MoonLBRReader.moonLBRReader(t, moonL007)
        val l008 = MoonLBRReader.moonLBRReader(t, moonL008)
        val l009 = MoonLBRReader.moonLBRReader(t, moonL009)
        val l010 = MoonLBRReader.moonLBRReader(t, moonL010)
        val l011 = MoonLBRReader.moonLBRReader(t, moonL011)
        val l012 = MoonLBRReader.moonLBRReader(t, moonL012)
        val l0 = l000 + l001 + l002 + l003 + l004 + l005 + l006 + l007 + l008 + l009 + l010 + l011 + l012
        val l1 = MoonLBRReader.moonLBRReader(t, moonL1)
        val l2 = MoonLBRReader.moonLBRReader(t, moonL2)
        val l3 = MoonLBRReader.moonLBRReader(t, moonL3)

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
        
        val lambdaAp = (Math.toDegrees(w) + l / 3600.0 + p / 3600).mod(360.0)
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
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param positionType True or Apparent
    * @param unitType Degrees or Radians
    *
    * @return 
    */
    fun moonGeocentricLatitude(jd: Double, deltaT: Double = 0.0, positionType: PositionType = PositionType.TRUE, unitType: UnitType = UnitType.DEGREES): Double {
        
        val moonB000 = MOON_B0_00.moon_B0_00
        val moonB001 = MOON_B0_01.moon_B0_01
        val moonB002 = MOON_B0_02.moon_B0_02
        val moonB003 = MOON_B0_03.moon_B0_03
        val moonB004 = MOON_B0_04.moon_B0_04
        val moonB005 = MOON_B0_05.moon_B0_05
        val moonB006 = MOON_B0_06.moon_B0_06
        val moonB007 = MOON_B0_07.moon_B0_07
        val moonB1 = MOON_B1.moon_B1
        val moonB2 = MOON_B2.moon_B2
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JCE)

        val b000 = MoonLBRReader.moonLBRReader(t, moonB000)
        val b001 = MoonLBRReader.moonLBRReader(t, moonB001)
        val b002 = MoonLBRReader.moonLBRReader(t, moonB002)
        val b003 = MoonLBRReader.moonLBRReader(t, moonB003)
        val b004 = MoonLBRReader.moonLBRReader(t, moonB004)
        val b005 = MoonLBRReader.moonLBRReader(t, moonB005)
        val b006 = MoonLBRReader.moonLBRReader(t, moonB006)
        val b007 = MoonLBRReader.moonLBRReader(t, moonB007)
        val b0 = b000 + b001 + b002 + b003 + b004 + b005 + b006 + b007
        val b1 = MoonLBRReader.moonLBRReader(t, moonB1)
        val b2 = MoonLBRReader.moonLBRReader(t, moonB2)

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
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param positionType True or Apparent
    * @param distanceType KM, AU, or ER
    *
    * @return 
    */
    fun moonGeocentricDistance(jd: Double, deltaT: Double = 0.0, positionType: PositionType = PositionType.TRUE, distanceType: DistanceType = DistanceType.KM): Double {
        
        val moonR000 = MOON_R0_00.moon_R0_00
        val moonR001 = MOON_R0_01.moon_R0_01
        val moonR002 = MOON_R0_02.moon_R0_02
        val moonR003 = MOON_R0_03.moon_R0_03
        val moonR004 = MOON_R0_04.moon_R0_04
        val moonR005 = MOON_R0_05.moon_R0_05
        val moonR006 = MOON_R0_06.moon_R0_06
        val moonR007 = MOON_R0_07.moon_R0_07
        val moonR008 = MOON_R0_08.moon_R0_08
        val moonR009 = MOON_R0_09.moon_R0_09
        val moonR010 = MOON_R0_10.moon_R0_10
        val moonR011 = MOON_R0_11.moon_R0_11
        val moonR012 = MOON_R0_12.moon_R0_12
        val moonR1 = MOON_R1.moon_R1
        val moonR2 = MOON_R2.moon_R2
        val moonR3 = MOON_R3.moon_R3
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JCE)

        val r000 = MoonLBRReader.moonLBRReader(t, moonR000)
        val r001 = MoonLBRReader.moonLBRReader(t, moonR001)
        val r002 = MoonLBRReader.moonLBRReader(t, moonR002)
        val r003 = MoonLBRReader.moonLBRReader(t, moonR003)
        val r004 = MoonLBRReader.moonLBRReader(t, moonR004)
        val r005 = MoonLBRReader.moonLBRReader(t, moonR005)
        val r006 = MoonLBRReader.moonLBRReader(t, moonR006)
        val r007 = MoonLBRReader.moonLBRReader(t, moonR007)
        val r008 = MoonLBRReader.moonLBRReader(t, moonR008)
        val r009 = MoonLBRReader.moonLBRReader(t, moonR009)
        val r010 = MoonLBRReader.moonLBRReader(t, moonR010)
        val r011 = MoonLBRReader.moonLBRReader(t, moonR011)
        val r012 = MoonLBRReader.moonLBRReader(t, moonR012)
        val r0 = r000 + r001 + r002 + r003 + r004 + r005 + r006 + r007 + r008 + r009 + r010 + r011 + r012
        val r1 = MoonLBRReader.moonLBRReader(t, moonR1)
        val r2 = MoonLBRReader.moonLBRReader(t, moonR2)
        val r3 = MoonLBRReader.moonLBRReader(t, moonR3)

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
    
    /**
    * Moon Apparent Geocentric Right Ascension default in degree
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return alpha in degree or radian
    */
    fun moonAppaGeocentricRightAscension(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lambda = moonGeocentricLongitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val beta = moonGeocentricLatitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val epsilon = Nutation.trueObliquityOfEcliptic(jd, deltaT)
        
        val alphaDeg = (Math.toDegrees(atan2(sin(Math.toRadians(lambda)) * cos(Math.toRadians(epsilon)) - tan(Math.toRadians(beta)) * sin(Math.toRadians(epsilon)), cos(Math.toRadians(lambda))))).mod(360.0)
        val alphaRad = Math.toRadians(alphaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> alphaDeg
            UnitType.RADIANS -> alphaRad
        }
    }
    
    /**
    * Moon Apparent Geocentric Declination default in degree
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return delta in degree or radian
    */
    fun moonAppaGeocentricDeclination(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lambda = moonGeocentricLongitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val beta = moonGeocentricLatitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val epsilon = Nutation.trueObliquityOfEcliptic(jd, deltaT)
        
        val deltaDeg = Math.toDegrees(asin(sin(Math.toRadians(beta)) * cos(Math.toRadians(epsilon)) + cos(Math.toRadians(beta)) * sin(Math.toRadians(epsilon)) * sin(Math.toRadians(lambda))))
        val deltaRad = Math.toRadians(deltaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> deltaDeg
            UnitType.RADIANS -> deltaRad
        }
    }
    
    /**
    * Moon Geocentric Greenwich Hour Angle default in degree
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return gha in degree or radian
    */
    fun moonGeoGreenwichHourAngle(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val gast = SunPosition.greenwichApparentSiderealTime(jd, deltaT)
        val alpha = moonAppaGeocentricRightAscension(jd, deltaT)
        
        val ghaDeg = (gast - alpha).mod(360.0)
        val ghaRad = Math.toRadians(ghaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> ghaDeg
            UnitType.RADIANS -> ghaRad
        }
    }
    
    /**
    * Moon Geocentric Local Hour Angle FK5 System default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return lha in degree or radian
    */
    fun moonGeoLocalHourAngle(jd: Double, lon: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val gast = SunPosition.greenwichApparentSiderealTime(jd, deltaT)
        val alpha = moonAppaGeocentricRightAscension(jd, deltaT)
        
        val lhaDeg = (gast + lon - alpha).mod(360.0)
        val lhaRad = Math.toRadians(lhaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> lhaDeg
            UnitType.RADIANS -> lhaRad
        }
    }
    
    /**
    * Moon Geocentric Azimuth default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return az in degree or radian
    */
    fun moonGeoAzimuth(jd: Double, lon: Double, lat: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lha = moonGeoLocalHourAngle(jd, lon, deltaT)
        val dec = moonAppaGeocentricDeclination(jd, deltaT)
        
        val azDeg = (Math.toDegrees(atan2(sin(Math.toRadians(lha)), cos(Math.toRadians(lha)) * sin(Math.toRadians(lat)) - tan(Math.toRadians(dec)) * cos(Math.toRadians(lat)))) + 180).mod(360.0)
        
        val azRad = Math.toRadians(azDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> azDeg
            UnitType.RADIANS -> azRad
        }
    }
    
    /**
    * Moon Geocentric Altitude default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return alt in degree or radian
    */
    fun moonGeoAltitude(jd: Double, lon: Double, lat: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lha = moonGeoLocalHourAngle(jd, lon, deltaT)
        val dec = moonAppaGeocentricDeclination(jd, deltaT)
        
        val altDeg = Math.toDegrees(asin(sin(Math.toRadians(lat)) * sin(Math.toRadians(dec)) + cos(Math.toRadians(lat)) * cos(Math.toRadians(dec)) * cos(Math.toRadians(lha))))
        
        val altRad = Math.toRadians(altDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> altDeg
            UnitType.RADIANS -> altRad
        }
    }
    
    /**
    * Moon Equatorial Horizontal Parallax default in degree
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return phi in degree or radian
    */
    fun moonEquatorialHorizontalParallax(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val r = moonGeocentricDistance(jd, deltaT, PositionType.APPARENT, DistanceType.KM)
        
        val phiDeg = Math.toDegrees(asin(6378.14 / r))
        val phiRad = Math.toRadians(phiDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> phiDeg
            UnitType.RADIANS -> phiRad
        }
    }
    
    /**
    * Moon Geocentric Semidiameter default in degree, s
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return s in degree or radian
    */
    fun moonGeoSemidiameter(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val k = 0.272481
        val phi = moonEquatorialHorizontalParallax(jd, deltaT, UnitType.DEGREES)
        
        val sDeg = Math.toDegrees(asin(k * sin(Math.toRadians(phi))))
        val sRad = Math.toRadians(sDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> sDeg
            UnitType.RADIANS -> sRad
        }
    }
    
    /**
    * Moon-Sun Geocentric Elongation default in degree, d
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return d in degree or radian
    */
    fun moonSunGeoElongation(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val deltaSun = SunPosition.sunApparentGeoDeclination(jd, deltaT, UnitType.DEGREES)
        val alphaSun = SunPosition.sunApparentGeoRightAscension(jd, deltaT, UnitType.DEGREES)
        val deltaMoon = moonAppaGeocentricDeclination(jd, deltaT)
        val alphaMoon = moonAppaGeocentricRightAscension(jd, deltaT)
        
        val dDeg = Math.toDegrees(acos(sin(Math.toRadians(deltaSun)) * sin(Math.toRadians(deltaMoon)) + cos(Math.toRadians(deltaSun)) * cos(Math.toRadians(deltaMoon)) * cos(Math.toRadians(alphaSun - alphaMoon))))
        val dRad = Math.toRadians(dDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> dDeg
            UnitType.RADIANS -> dRad
        }
    }
    
    /**
    * Moon Geocentric Phase Angle default in degree, i
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return i in degree or radian
    */
    fun moonGeoPhaseAngle(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val rSun = EarthPosition.earthRadiusVector(jd, deltaT, DistanceType.KM)
        val rMoon = moonGeocentricDistance(jd, deltaT, PositionType.APPARENT, DistanceType.KM)
        val d = moonSunGeoElongation(jd, deltaT)
        
        val iDeg = Math.toDegrees(atan2(rSun * sin(Math.toRadians(d)), rMoon - rSun * cos(Math.toRadians(d))))
        val iRad = Math.toRadians(iDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> iDeg
            UnitType.RADIANS -> iRad
        }
    }
    
    /**
    * Moon Geocentric Disk Illuminated Fraction default in degree, k
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return k in degree or radian
    */
    fun moonGeoDiskIlluminatedFraction(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
    
        val i = moonGeoPhaseAngle(jd, deltaT)
        
        val kDeg = ((1 + cos(Math.toRadians(i))) / 2)
        val kRad = Math.toRadians(kDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> kDeg
            UnitType.RADIANS -> kRad
        }
    }
    
    /**
    * Moon Geocentric Bright Limb Angle default in degree, x
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return x in degree or radian
    */
    fun moonGeoBrightLimbAngle(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val deltaSun = SunPosition.sunApparentGeoDeclination(jd, deltaT, UnitType.DEGREES)
        val alphaSun = SunPosition.sunApparentGeoRightAscension(jd, deltaT, UnitType.DEGREES)
        val deltaMoon = moonAppaGeocentricDeclination(jd, deltaT)
        val alphaMoon = moonAppaGeocentricRightAscension(jd, deltaT)
        
        val xDeg = (Math.toDegrees(atan2(cos(Math.toRadians(deltaSun)) * sin(Math.toRadians(alphaSun - alphaMoon)), sin(Math.toRadians(deltaSun)) * cos(Math.toRadians(deltaMoon)) - cos(Math.toRadians(deltaSun)) * sin(Math.toRadians(deltaMoon)) * cos(Math.toRadians(alphaSun - alphaMoon))))).mod(360.0)
        val xRad = Math.toRadians(xDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> xDeg
            UnitType.RADIANS -> xRad
        }
    }
    
    /**
    * Moon term n in radian
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    *
    * @return n in radian
    */
    fun moonTermN(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0): Double {
        
        val lambda = moonGeocentricLongitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val beta = moonGeocentricLatitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val theta = SunPosition.localApparentSiderealTime(jd, lon, deltaT)
        val x = Correction.termX(lat, elev)
        val phi = moonEquatorialHorizontalParallax(jd, deltaT, UnitType.DEGREES)
        val n = cos(Math.toRadians(lambda)) * cos(Math.toRadians(beta)) - x * sin(Math.toRadians(phi)) * cos(Math.toRadians(theta))
        
        return n
    }
    
    /**
    * Parallax in the Moon Right Ascension default in degree, deltaAlpha
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arc second
    * @param unitType degree or radian
    * 
    * @return deltaAlpha in degree or radian
    */
    fun parallaxInTheMoonRightAscension(jd: Double, lon: Double, lat: Double, elev: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val x = Correction.termX(lat, elev) 
        val lha = moonGeoLocalHourAngle(jd, lon, deltaT)
        val phi = moonEquatorialHorizontalParallax(jd, deltaT, UnitType.DEGREES)
        val dec = moonAppaGeocentricDeclination(jd, deltaT)
        
        val deltaAlphaDeg = Math.toDegrees(atan2(-x * sin(Math.toRadians(phi)) * sin(Math.toRadians(lha)), cos(Math.toRadians(dec)) - x * sin(Math.toRadians(phi)) * cos(Math.toRadians(lha))))
        val deltaAlphaRad = Math.toRadians(deltaAlphaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> deltaAlphaDeg
            UnitType.RADIANS -> deltaAlphaRad
        }
    }
    
    /**
    * Parallax in the Moon Altitude default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return p in degree or radian
    */
    fun parallaxInTheMoonAltitude(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val x = Correction.termX(lat, elev)
        val y = Correction.termY(lat, elev)
        val h = moonGeoAltitude(jd, lon, lat, deltaT)
        val phi = moonEquatorialHorizontalParallax(jd, deltaT)
        
        val pDeg = Math.toDegrees(asin(sqrt(y.pow(2) + x.pow(2)) * sin(Math.toRadians(phi)) * cos(Math.toRadians(h))))
        val pRad = Math.toRadians(pDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> pDeg
            UnitType.RADIANS -> pRad
        }
    }
    
    // Topocentric coor
    
    /**
    * Moon Topocentric Longitude default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return lambdaP in degree or radian
    */
    fun moonTopoLongitude(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val x = Correction.termX(lat, elev)
        val y = Correction.termY(lat, elev)
        val n = moonTermN(jd, lon, lat, elev, deltaT)
        val epsilon = Nutation.trueObliquityOfEcliptic(jd, deltaT)
        val lambda = moonGeocentricLongitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val beta = moonGeocentricLatitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val theta = SunPosition.localApparentSiderealTime(jd, lon, deltaT)
        val phi = moonEquatorialHorizontalParallax(jd, deltaT)
        
        val lambdaPDeg = (Math.toDegrees(atan2((sin(Math.toRadians(lambda)) * cos(Math.toRadians(beta)) - sin(Math.toRadians(phi)) * (y * sin(Math.toRadians(epsilon)) + x * cos(Math.toRadians(epsilon)) * sin(Math.toRadians(theta)))), n))).mod(360.0)
        val lambdaPRad = Math.toRadians(lambdaPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> lambdaPDeg
            UnitType.RADIANS -> lambdaPRad
        }
    }
    
    /**
    * Moon Topocentric Latitude default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return betaP in degree or radian
    */
    fun moonTopoLatitude(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val x = Correction.termX(lat, elev)
        val y = Correction.termY(lat, elev)
        val n = moonTermN(jd, lon, lat, elev, deltaT)
        val epsilon = Nutation.trueObliquityOfEcliptic(jd, deltaT)
        val lambdaP = moonTopoLongitude(jd, lon, lat, elev, deltaT, UnitType.DEGREES)
        val beta = moonGeocentricLatitude(jd, deltaT, PositionType.APPARENT, UnitType.DEGREES)
        val theta = SunPosition.localApparentSiderealTime(jd, lon, deltaT)
        val phi = moonEquatorialHorizontalParallax(jd, deltaT)
        
        val betaPDeg = Math.toDegrees(atan(cos(Math.toRadians(lambdaP)) * (sin(Math.toRadians(beta)) - sin(Math.toRadians(phi)) * (y * cos(Math.toRadians(epsilon)) - x * sin(Math.toRadians(epsilon)) * sin(Math.toRadians(theta)))) / n))
        val betaPRad = Math.toRadians(betaPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> betaPDeg
            UnitType.RADIANS -> betaPRad
        }
    }
    
    /**
    * Moon Topocentric Right Ascension default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return alphaP in degree or radian
    */
    fun moonTopoRightAscension(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val alpha = moonAppaGeocentricRightAscension(jd, deltaT)
        val deltaAlpha = parallaxInTheMoonRightAscension(jd, lon, lat, elev, deltaT)
        
        val alphaPDeg = alpha + deltaAlpha
        val alphaPRad = Math.toRadians(alphaPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> alphaPDeg
            UnitType.RADIANS -> alphaPRad
        }
    }
    
    /**
    * Moon Topocentric Declination default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return deltaP in degree or radian
    */
    fun moonTopoDeclination(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val x = Correction.termX(lat, elev)
        val y = Correction.termY(lat, elev)
        val deltaAlpha = parallaxInTheMoonRightAscension(jd, lon, lat, elev, deltaT)
        val lha = moonGeoLocalHourAngle(jd, lon, deltaT)
        val phi = moonEquatorialHorizontalParallax(jd, deltaT, UnitType.DEGREES)
        val dec = moonAppaGeocentricDeclination(jd, deltaT)
        
        val deltaPDeg = Math.toDegrees(atan2((sin(Math.toRadians(dec)) - y * sin(Math.toRadians(phi))) * cos(Math.toRadians(deltaAlpha)), cos(Math.toRadians(dec)) - x * sin(Math.toRadians(phi)) * cos(Math.toRadians(lha))))
        val deltaPRad = Math.toRadians(deltaPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> deltaPDeg
            UnitType.RADIANS -> deltaPRad
        }
    }
    
    /**
    * Moon Topocentric Semidiameter default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return sP in degree or radian
    */
    fun moonTopoSemidiameter(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lambdaP = moonTopoLongitude(jd, lon, lat, elev, deltaT)
        val betaP = moonTopoLatitude(jd, lon, lat, elev, deltaT)
        val s = moonGeoSemidiameter(jd, deltaT)
        val n = moonTermN(jd, lon, lat, elev, deltaT)
        
        val sPDeg = Math.toDegrees(asin(cos(Math.toRadians(lambdaP)) * cos(Math.toRadians(betaP)) * sin(Math.toRadians(s)) / n))
        val sPRad = Math.toRadians(sPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> sPDeg
            UnitType.RADIANS -> sPRad
        }
    }
    
    /**
    * Moon Topocentric Greenwich Hour Angle default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return gha in degree or radian
    */
    fun moonTopoGreenwichHourAngle(jd: Double, lon: Double, lat: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val gast = SunPosition.greenwichApparentSiderealTime(jd, deltaT, UnitType.DEGREES)
        val alpha = moonTopoRightAscension(jd, lon, lat, 0.0, deltaT)
        
        val ghaDeg = (gast - alpha).mod(360.0)
        val ghaRad = Math.toRadians(ghaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> ghaDeg
            UnitType.RADIANS -> ghaRad
        }
    }
    
    /**
    * Moon Topocentric Local Hour Angle default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return lhaP in degree or radian
    */
    fun moonTopoLocalHourAngle(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val deltaAlpha = parallaxInTheMoonRightAscension(jd, lon, lat, elev, deltaT)
        val lha = moonGeoLocalHourAngle(jd, lon, deltaT)
        
        val lhaPDeg = lha - deltaAlpha
        val lhaPRad = Math.toRadians(lhaPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> lhaPDeg
            UnitType.RADIANS -> lhaPRad
        }
    }
    
    /**
    * Moon Topocentric Azimuth default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return azmP in degree or radian
    */
    fun moonTopoAzimuth(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val deltaP = moonTopoDeclination(jd, lon, lat, elev, deltaT)
        val lhaP = moonTopoLocalHourAngle(jd, lon, lat, elev, deltaT)
        
        val azmPDeg = (Math.toDegrees(atan2(sin(Math.toRadians(lhaP)), cos(Math.toRadians(lhaP)) * sin(Math.toRadians(lat)) - tan(Math.toRadians(deltaP)) * cos(Math.toRadians(lat)))) + 180).mod(360.0)
        val azmPRad = Math.toRadians(azmPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> azmPDeg
            UnitType.RADIANS -> azmPRad
        }
    }
    
    /**
    * Moon Topocentric Altitude in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param moonAltType see MoonAltType enum class
    * @param temperature in celcius
    * @param pressure in millibars
    *
    * @return moonTopoAltitude in degree
    */
    fun moonTopoAltitude(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, moonAltType: MoonAltType = MoonAltType.AIRLESS_CENTER, temperature: Double = 10.0, pressure: Double = 1010.0): Double {
        
        val deltaP = moonTopoDeclination(jd, lon, lat, elev, deltaT)
        val lhaP = moonTopoLocalHourAngle(jd, lon, lat, elev, deltaT)
        val dip = Correction.dip(elev)
        val s = moonTopoSemidiameter(jd, lon, lat, elev, deltaT)
        
        val hc = Math.toDegrees(asin(sin(Math.toRadians(lat)) * sin(Math.toRadians(deltaP)) + cos(Math.toRadians(lat)) * cos(Math.toRadians(deltaP)) * cos(Math.toRadians(lhaP))))
        val rhc = Correction.atmosphericRefractionFromAirlessAltitude(hc, temperature, pressure)
        val hac = hc + rhc
        val hoc = hac + dip
        
        val hu = hc + s
        val rhu = Correction.atmosphericRefractionFromAirlessAltitude(hu, temperature, pressure)
        val hau = hu + rhu
        val hou = hau + dip
        
        val hl = hc - s
        val rhl = Correction.atmosphericRefractionFromAirlessAltitude(hl, temperature, pressure)
        val hal = hl + rhl
        val hol = hal + dip
        
        return when (moonAltType) {
            MoonAltType.AIRLESS_CENTER -> hc
            MoonAltType.APPARENT_CENTER -> hac
            MoonAltType.OBSERVED_CENTER -> hoc
            MoonAltType.AIRLESS_UPPER -> hu
            MoonAltType.APPARENT_UPPER -> hau
            MoonAltType.OBSERVED_UPPER -> hou
            MoonAltType.AIRLESS_LOWER -> hl
            MoonAltType.APPARENT_LOWER -> hal
            MoonAltType.OBSERVED_LOWER -> hol
            MoonAltType.REFRACTION_CENTER -> rhc
            MoonAltType.REFRACTION_UPPER -> rhu
            MoonAltType.REFRACTION_LOWER -> rhl
        }
    }
    
    /**
    * Moon-Sun Topocentric Elongation default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return dP in degree or radian
    */
    fun moonSunTopoElongation(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val deltaPS = SunPosition.sunTopoDeclination(jd, lon, lat, elev, deltaT)
        val alphaPS = SunPosition.sunTopoRightAscension(jd, lon, lat, elev, deltaT)
        val deltaPM = moonTopoDeclination(jd, lon, lat, elev, deltaT)
        val alphaPM = moonTopoRightAscension(jd, lon, lat, elev, deltaT)
        
        val dPDeg = Math.toDegrees(acos(sin(Math.toRadians(deltaPS)) * sin(Math.toRadians(deltaPM)) + cos(Math.toRadians(deltaPS)) * cos(Math.toRadians(deltaPM)) * cos(Math.toRadians(alphaPS - alphaPM))))
        val dPRad = Math.toRadians(dPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> dPDeg
            UnitType.RADIANS -> dPRad
        }
    }
    
    /**
    * Moon Topocentric Phase Angle default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return iP in degree or radian
    */
    fun moonTopoPhaseAngle(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val rSun = EarthPosition.earthRadiusVector(jd, deltaT, DistanceType.KM)
        val rMoon = moonGeocentricDistance(jd, deltaT, PositionType.APPARENT, DistanceType.KM)
        val d = moonSunTopoElongation(jd, lon, lat, elev, deltaT)
        
        val iPDeg = Math.toDegrees(atan2(rSun * sin(Math.toRadians(d)), rMoon - rSun * cos(Math.toRadians(d))))
        val iPRad = Math.toRadians(iPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> iPDeg
            UnitType.RADIANS -> iPRad
        }
    }
    
    /**
    * Moon Topocentric Disk Illuminated Fraction default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return kP in degree or radian
    */
    fun moonTopoDiskIlluminatedFraction(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val i = moonTopoPhaseAngle(jd, lon, lat, elev, deltaT)
        
        val kPDeg = (1 + cos(Math.toRadians(i))) / 2
        val kPRad = Math.toRadians(kPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> kPDeg
            UnitType.RADIANS -> kPRad
        }
    }
    
    /**
    * Moon Topocentric Bright Limb Angle default in degree
    *
    * @param jd Julian Day
    * @param lon longitude of observer
    * @param lat latitude of observer
    * @param elev elevation of observer
    * @param deltaT in arcsecond
    * @param unitType degree or radian
    *
    * @return xP in degree or radian
    */
    fun moonTopoBrightLimbAngle(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val deltaSun = SunPosition.sunTopoDeclination(jd, lon, lat, elev, deltaT)
        val alphaSun = SunPosition.sunTopoRightAscension(jd, lon, lat, elev, deltaT)
        val deltaMoon = moonTopoDeclination(jd, lon, lat, elev, deltaT)
        val alphaMoon = moonTopoRightAscension(jd, lon, lat, elev, deltaT)
        
        val xPDeg = (Math.toDegrees(atan2(cos(Math.toRadians(deltaSun)) * sin(Math.toRadians(alphaSun - alphaMoon)), sin(Math.toRadians(deltaSun)) * cos(Math.toRadians(deltaMoon)) - cos(Math.toRadians(deltaSun)) * sin(Math.toRadians(deltaMoon)) * cos(Math.toRadians(alphaSun - alphaMoon))))).mod(360.0)
        val xPRad = Math.toRadians(xPDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> xPDeg
            UnitType.RADIANS -> xPRad
        }
    }
}
