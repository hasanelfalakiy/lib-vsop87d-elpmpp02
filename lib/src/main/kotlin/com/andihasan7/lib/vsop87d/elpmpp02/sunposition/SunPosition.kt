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
 
package com.andihasan7.lib.vsop87d.elpmpp02.sunposition

import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.tan
import kotlin.math.pow
import kotlin.mod
import com.andihasan7.lib.vsop87d.elpmpp02.earthposition.EarthPosition
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.UnitType
import com.andihasan7.lib.vsop87d.elpmpp02.Nutation
import com.andihasan7.lib.vsop87d.elpmpp02.correction.Correction

object SunPosition {
    
    // Sun Geocentric Coordinate 
    
    /**
    * Sun True Geocentric Longitude FK5 System default in degree
    *
    * @param jd, Julian Day
    * @param deltaT, in second 
    * @param unitType, degree or radian
    *
    * @return sunTrueGeocentricLongitude FK5 System degree or radian
    */
    fun sunTrueGeocentricLongitude(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val earthHeliocentricLongitude = EarthPosition.earthHeliocentricLongitude(jd, deltaT, UnitType.DEGREES)
        val earthHeliocentricLatitude = EarthPosition.earthHeliocentricLatitude(jd, deltaT, UnitType.DEGREES)
        val jce = TimeUtil.julianType(jd, deltaT, JulianType.JCE)
        val sunTrueGeocentricLatitude = -earthHeliocentricLatitude
        val thetaZero = (earthHeliocentricLongitude + 180).mod(360.0)
        val lambdaP = thetaZero - 1.397 * jce - 0.00031 * jce.pow(2)
        val deltaTheta = (-0.09033 + 0.03916 * (cos(Math.toRadians(lambdaP)) + sin(Math.toRadians(lambdaP))) * tan(sunTrueGeocentricLatitude)) / 3600.0
        
        val sunTrueGeocentricLongitude = (thetaZero + deltaTheta).mod(360.0)
        
        return when (unitType) {
            UnitType.DEGREES -> sunTrueGeocentricLongitude
            UnitType.RADIANS -> (Math.toRadians(sunTrueGeocentricLongitude))
        }
    }
    
    /**
    * Sun True Geocentric Latitude FK5 System default in degree
    * can be considered apparent 
    * 
    * @param jd, Julian Day
    * @param deltaT, in second 
    * @param unitType, degree or radian
    *
    * @return sunTrueGeocentricLatitude in degree or radian
    */
    fun sunTrueGeocentricLatitude(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val earthHelioLon = EarthPosition.earthHeliocentricLongitude(jd, deltaT, UnitType.DEGREES)
        val earthHelioLat = EarthPosition.earthHeliocentricLatitude(jd, deltaT, UnitType.DEGREES)
        val jce = TimeUtil.julianType(jd, deltaT, JulianType.JCE)
        val betaZero = -earthHelioLat
        val theta = (earthHelioLon + 180).mod(360.0)
        val lambdaP = theta - 1.397 * jce - 0.00031 * jce.pow(2)
        val deltaBeta = (0.03916 * (cos(Math.toRadians(lambdaP)) - sin(Math.toRadians(lambdaP)))) / 3600.0
        val beta = betaZero + deltaBeta
        
        return when (unitType) {
            UnitType.DEGREES -> beta
            UnitType.RADIANS -> (Math.toRadians(beta))
        }
    }
    
    /**
    * Sun Apparent Geocentric Longitude default in degree
    *
    * @param jd, Julian Day
    * @param deltaT, in second 
    * @param unitType, degree or radian
    * 
    * @return sunApparentGeocentricLongitude in degree or radian
    */
    fun sunApparentGeocentricLongitude(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val sunTrueGeocentricLongitude = sunTrueGeocentricLongitude(jd, deltaT)
        val deltaPsi = Nutation.nutationInLonAndObliquity(jd, deltaT)[0]
        val abr = Correction.abration(jd, deltaT) / 3600.0
        
        val sunApparentGeocentricLongitude = (sunTrueGeocentricLongitude + deltaPsi + abr).mod(360.0)
        
        return when (unitType) {
            UnitType.DEGREES -> sunApparentGeocentricLongitude
            UnitType.RADIANS -> (Math.toRadians(sunApparentGeocentricLongitude))
        }
    }
    
    /**
    * Sun Apparent Geocentric Semidiameter, s
    *
    * @param jd, Julian Day
    * @param deltaT, in second 
    *
    * @return sunApparentGeoSemidiameter
    */
    fun sunApparentGeoSemidiameter(jd: Double, deltaT: Double): Double {
        
        val r = EarthPosition.earthRadiusVector(jd, deltaT, DistanceType.AU)
        val s = 0.266563888889 / r
        return s
    }
    
    /**
    * Sun Apparent Geocentric Right Ascension FK5 System default in degree, a
    *
    * @param jd, Julian Day
    * @param deltaT, in second 
    * @param unitType, degree or radian
    *
    * @return sunApparentGeoRightAscension in degree or radian
    */
    fun sunApparentGeoRightAscension(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lambda = sunApparentGeocentricLongitude(jd, deltaT)
        val beta = sunTrueGeocentricLatitude(jd, deltaT)
        val epsilon = Nutation.trueObliquityOfEcliptic(jd, deltaT)
        
        val raDeg = (Math.toDegrees(atan2(sin(Math.toRadians(lambda)) * cos(Math.toRadians(epsilon)) - tan(Math.toRadians(beta)) * sin(Math.toRadians(epsilon)), cos(Math.toRadians(lambda))))).mod(360.0)
        val raRad = Math.toRadians(raDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> raDeg
            UnitType.RADIANS -> raRad
        }
    }
    
    /**
    * Sun Apparent Geocentric Declination default in degree, d
    * 
    * @param jd, Julian Day
    * @param deltaT, in second
    * @param unitType, degree or radian
    *
    * @return sun geocentric declination in degree or radian
    */
    fun sunApparentGeoDeclination(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lambda = sunApparentGeocentricLongitude(jd, deltaT)
        val beta = sunTrueGeocentricLatitude(jd, deltaT)
        val epsilon = Nutation.trueObliquityOfEcliptic(jd, deltaT)
        
        val sunDecDeg = (Math.toDegrees(asin(sin(Math.toRadians(beta)) * cos(Math.toRadians(epsilon)) + cos(Math.toRadians(beta)) * sin(Math.toRadians(epsilon)) * sin(Math.toRadians(lambda))))).mod(360.0)
        val sunDecRad = Math.toRadians(sunDecDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> sunDecDeg
            UnitType.RADIANS -> sunDecRad
        }
    }
    
    /**
    * Greenwich Mean Sidereal Time default in degree, GMST
    *
    * @param jd, Julian Day
    * @param unitType, degree or radian
    *
    * @return gmst, in degree or radian
    */
    fun greenwichMeanSiderealTime(jd: Double, unitType: UnitType = UnitType.DEGREES): Double {
        
        val jc = TimeUtil.julianType(jd, 0.0, JulianType.JC)
        val gmstDeg = (280.46061837 + 360.98564736629 * (jd - 2451545.0) + 0.000387933 * jc.pow(2) - (jc.pow(3) / 38710000)).mod(360.0)
        val gmstRad = Math.toRadians(gmstDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> gmstDeg
            UnitType.RADIANS -> gmstRad
        }
    }
    
    /**
    * Greenwich Apparent Sidereal Time default in degree, GAST
    *
    * @param jd, Julian Day
    * @param deltaT, in second
    * @param unitType, degree or radian
    *
    * @return gast, in degree or radian
    */
    fun greenwichApparentSiderealTime(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val gmst = greenwichMeanSiderealTime(jd)
        val deltaPsi = Nutation.nutationInLonAndObliquity(jd, deltaT)[0]
        val epsilon = Nutation.trueObliquityOfEcliptic(jd, deltaT)
        
        val gastDeg = (gmst + deltaPsi * cos(Math.toRadians(epsilon))).mod(360.0)
        val gastRad = Math.toRadians(gastDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> gastDeg
            UnitType.RADIANS -> gastRad
        }
    }
    
    /**
    * Local Apparent Sidereal Time default in degree, LAST
    *
    * @param jd, Julian Day
    * @param longitude, Longitude of Observer
    * @param deltaT in second
    * @param unitType, degree or radian
    *
    * @return last, in degree or radian
    */
    fun localApparentSiderealTime(jd: Double, lon: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val gast = greenwichApparentSiderealTime(jd, deltaT)
        
        val lastDeg = (gast + lon).mod(360.0)
        val lastRad = Math.toRadians(lastDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> lastDeg
            UnitType.RADIANS -> lastRad
        }
    }
    
    /**
    * Sun Geocentric Greenwich Hour Angle default in degree, GHA, Ho
    *
    * @param jd, Julian Day
    * @param deltaT in second
    * @param unitType, degree or radian
    *
    * @return gha, in degree or radian
    */
    fun sunGeoGreenwichHourAngle(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val gast = greenwichApparentSiderealTime(jd, deltaT)
        val ra = sunApparentGeoRightAscension(jd, deltaT)
        
        val ghaDeg = (gast - ra).mod(360.0)
        val ghaRad = Math.toRadians(ghaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> ghaDeg
            UnitType.RADIANS -> ghaRad
        }
    }
    
    /**
    * Sun Geocentric Local Hour Angle FK5 System default in degree, LHA, H
    *
    * @param jd, Julian Day
    * @param longitude, Longitude of Observer
    * @param deltaT in second
    * @param unitType, degree or radian
    *
    * @return lha, in degree or radian
    */
    fun sunGeoLocalHourAngle(jd: Double, lon: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val gast = greenwichApparentSiderealTime(jd, deltaT)
        val ra = sunApparentGeoRightAscension(jd, deltaT)
        
        val lhaDeg = (gast + lon - ra).mod(360.0)
        val lhaRad = Math.toRadians(lhaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> lhaDeg
            UnitType.RADIANS -> lhaRad
        }
    }
    
    /**
    * Sun Geocentric Azimuth Measured from True North default in degree, A
    *
    * @param jd, Julian Day
    * @param longitude, Longitude of Observer
    * @param latitude, Latitude of Observer
    * @param deltaT, in second
    * @param unitType, degree or radian
    *
    * @return sunGeoAzimuth, in degree or radian
    */
    fun sunGeoAzimuth(jd: Double, lon: Double, lat: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lha = sunGeoLocalHourAngle(jd, lon, deltaT)
        val dec = sunApparentGeoDeclination(jd, deltaT)
        
        val azDeg = (Math.toDegrees(atan2(sin(Math.toRadians(lha)), cos(Math.toRadians(lha)) * sin(Math.toRadians(lat)) - tan(Math.toRadians(dec)) * cos(Math.toRadians(lat)))) + 180).mod(360.0)
        val azRad = Math.toRadians(azDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> azDeg
            UnitType.RADIANS -> azRad
        }
    }
    
    /**
    * Sun Geocentric Altitude default in degree, h
    *
    * @param jd, Julian Day
    * @param longitude, Longitude of Observer
    * @param latitude, Latitude of Observer
    * @param deltaT in second
    * @param unitType, degree or radian
    *
    * @return sunGeoAzimuth, in degree or radian
    */
    fun sunGeoAltitude(jd: Double, lon: Double, lat: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lha = sunGeoLocalHourAngle(jd, lon, deltaT)
        val dec = sunApparentGeoDeclination(jd, deltaT)
        
        val altDeg = Math.toDegrees(asin(sin(Math.toRadians(lat)) * sin(Math.toRadians(dec)) + cos(Math.toRadians(lat)) * cos(Math.toRadians(dec)) * cos(Math.toRadians(lha))))
        val altRad = Math.toRadians(altDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> altDeg
            UnitType.RADIANS -> altRad
        }
    }
    
    // Sun Topocentric Coordinate 
    
    
    
    
}
