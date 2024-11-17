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
import kotlin.math.sqrt
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
    fun sunApparentGeoSemidiameter(jd: Double, deltaT: Double = 0.0): Double {
        
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
    
    /**
    * Sun Equatorial Horizontal Parallax 
    * @param jd, Julian Day
    * @param deltaT, in arcsecond
    *
    * @return phi Sun Equatorial Horizontal Parallax
    */
    fun sunEquatorialHorizontalParallax(jd: Double, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val er = EarthPosition.earthRadiusVector(jd, deltaT, DistanceType.AU)
        val phiDeg = 8.794 / (er * 3600)
        val phiRad = Math.toRadians(phiDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> phiDeg
            UnitType.RADIANS -> phiRad
        }
    }
    
    /**
    * Sun term n in radian
    *
    * @param jd, Julian Day
    * @param longitude of observer
    * @param latitude of observer
    * @param elevation of observer
    * @param deltaT, in arcsecond
    *
    * @return n in radian
    */
    fun sunTermN(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0): Double {
        
        val lambda = sunApparentGeocentricLongitude(jd, deltaT)
        val beta = sunTrueGeocentricLatitude(jd, deltaT)
        val theta = localApparentSiderealTime(jd, lon, deltaT)
        val x = Correction.termX(lat, elev)
        val phi = sunEquatorialHorizontalParallax(jd, deltaT)
        val n = cos(Math.toRadians(lambda)) * cos(Math.toRadians(beta)) - x * sin(Math.toRadians(phi)) * cos(Math.toRadians(theta))
        
        return n
    }
    
    /**
    * Parallax in the Sun Right Ascension default in degree
    *
    * @param jd, Julian Day
    * @param longitude of observer
    * @param latitude of observer
    * @param elevation of observer
    * @param deltaT, in arcsecond
    * @param unitType, degrees or radians
    *
    * @return deltaAlpha in degree or radian
    */
    fun parallaxInTheSunRightAscension(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val x = Correction.termX(lat, elev)
        val phi = sunEquatorialHorizontalParallax(jd, deltaT)
        val lha = sunGeoLocalHourAngle(jd, lon, deltaT)
        val dec = sunApparentGeoDeclination(jd, deltaT)
        
        val deltaAlphaDeg = Math.toDegrees(atan2(-x * sin(Math.toRadians(phi)) * sin(Math.toRadians(lha)), cos(Math.toRadians(dec)) - x * sin(Math.toRadians(phi)) * cos(Math.toRadians(lha))))
        val deltaAlphaRad = Math.toRadians(deltaAlphaDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> deltaAlphaDeg
            UnitType.RADIANS -> deltaAlphaRad
        }
    }
    
    /**
    * Parallax in the Sun Altitude default in degree
    *
    * @param jd, Julian Day
    * @param longitude of observer
    * @param latitude of observer
    * @param elevation of observer
    * @param deltaT, in arcsecond
    * @param unitType
    *
    * @return p in degree or radian
    */
    fun parallaxInTheSunAltitude(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val x = Correction.termX(lat, elev)
        val y = Correction.termY(lat, elev)
        val h = sunGeoAltitude(jd, lon, lat, deltaT)
        val phi = sunEquatorialHorizontalParallax(jd, deltaT)
        
        val pDeg = Math.toDegrees(asin(sqrt(y.pow(2) + x.pow(2)) * sin(Math.toRadians(phi)) * cos(Math.toRadians(h))))
        val pRad = Math.toRadians(pDeg)
        
        return when (unitType) {
            UnitType.DEGREES -> pDeg
            UnitType.RADIANS -> pRad
        }
    }
    
    /**
    * Atmospheric Refraction form Airless Altitude in arc minute
    *
    * @param airlessAltitude
    * @param pressure, 
    * @param temperature
    *
    * @return atmospheric refraction
    */
    fun atmosphericRefractionFromAirlessAltitude(airlessAltitude: Double, pressure: Double = 1010.0, temperature: Double = 10.0): Double {
        
        val rDeg = (1.02 / tan(Math.toRadians(airlessAltitude + 10.3 / (airlessAltitude + 5.11))) * pressure / 1010.0 * 283.0 / (273.0 + temperature) + 0.0019279204034639303) / 60.0
        
        return rDeg
    }
    
    /**
    * Sun Topocentric Longitude, lambda apostrophe
    *
    * @param jd, Julian Day
    * @param longitude of observer
    * @param latitude of observer
    * @param elevation of observer
    * @param deltaT, in arcsecond
    * @param unitType
    *
    * @return lambdaP in degree or radian
    */
    fun sunTopoLongitude(jd: Double, lon: Double, lat: Double, elev: Double = 0.0, deltaT: Double = 0.0, unitType: UnitType = UnitType.DEGREES): Double {
        
        val lambda = sunApparentGeocentricLongitude(jd, deltaT)
        val beta = sunTrueGeocentricLatitude(jd, deltaT)
        val theta = localApparentSiderealTime(jd, lon, deltaT)
        val phi = sunEquatorialHorizontalParallax(jd, deltaT)
        val epsilon = Nutation.trueObliquityOfEcliptic(jd, deltaT)
        val x = Correction.termX(lat, elev)
        val y = Correction.termY(lat, elev)
        val n = sunTermN(jd, lon, lat, elev, deltaT)
        
        val lambdaPDeg = (Math.toDegrees(atan2((sin(Math.toRadians(lambda)) * cos(Math.toRadians(beta)) - sin(Math.toRadians(phi)) * (y * sin(Math.toRadians(epsilon)) + x * cos(Math.toRadians(epsilon)) * sin(Math.toRadians(theta)))), n))).mod(360.0)
        val lambdaPRad = Math.toRadians(lambdaPDeg)
        return when (unitType) {
            UnitType.DEGREES -> lambdaPDeg
            UnitType.RADIANS -> lambdaPRad
        }
        
    }
    
    
    
}
