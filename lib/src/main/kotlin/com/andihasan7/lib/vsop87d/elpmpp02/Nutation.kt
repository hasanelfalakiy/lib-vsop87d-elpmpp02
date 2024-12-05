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
 
package com.andihasan7.lib.vsop87d.elpmpp02


import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.nutationterms.Nutation2000b
import kotlin.math.pow
import com.andihasan7.lib.vsop87d.elpmpp02.readerutil.NutationReader

/**
* Nutation
*
*/
object Nutation {
    
    /**
    * Nutation in Longitude, deltaPsi
    * Nutation of Obliquity, deltaEpsilon
    * model of IAU 2000B
    *
    * @param jd Julian Day
    * @param deltaT in arc second
    *
    * @return doubleArrayOf(deltaPsi, deltaEp) degree unit
    */
    fun nutationInLonAndObliquity(jd: Double, deltaT: Double = 0.0): DoubleArray {
        
        val nut = Nutation2000b.nutation2000b
        
        // t is the same as jce
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JCE)
    
        // delaunay argument count (L, L', F, D, omega)
        /*
        * Mean Anomaly of the Moon, L in degree
        */
        val lDeg = (485868.249036 + 1717915923.2178 * t + 31.8792 * t.pow(2) + 0.051635 * t.pow(3) - 0.00024470 * t.pow(4)) / 3600.0
    
        /*
        * Mean Anomaly of the Moon, L in radian
        */
        val lRad = Math.toRadians((lDeg).mod(360.0))
        
        /*
        * Anomaly of the Sun, L' in degree
        */
        val l1Deg = (1287104.79305 + 129596581.0481 * t - 0.5532 * t.pow(2) + 0.000136 * t.pow(3) - 0.00001149 * t.pow(4)) / 3600.0
        
        /*
        * Anomaly of the Sun, L' in radian
        */
        val l1Rad = Math.toRadians((l1Deg).mod(360.0))
        
        /*
        * Mean argument of the latitude of the Moon, F in degree
        */
        val fDeg = (335779.526232 + 1739527262.8478 * t - 12.7512 * t.pow(2) - 0.001037 * t.pow(3) + 0.00000417 * t.pow(4)) / 3600.0
        
        /*
        * Mean argument of the latitude of the Moon, F in radian
        */
        val fRad = Math.toRadians((fDeg).mod(360.0))
        
        /*
        * Mean Elongation of the Moon from the Sun, D in degree
        */
        val dDeg = (1072260.70369 + 1602961601.2090 * t - 6.3706 * t.pow(2) + 0.006593 * t.pow(3) - 0.00003169 * t.pow(4)) / 3600.0
        
        /*
        * Mean Elongation of the Moon from the Sun, D in radian
        */
        val dRad = Math.toRadians((dDeg).mod(360.0))
        
        /*
        * Mean Longitude of the Ascending Node of the Moon, omega in degree
        */
        val omegaDeg = (450160.398036 - 6962890.5431 * t + 7.4722 * t.pow(2) + 0.007702 * t.pow(3) - 0.00005939 * t.pow(4)) / 3600.0
        
        /*
        * Mean Longitude of the Ascending Node of the Moon, omega in radian
        */
        val omegaRad = Math.toRadians((omegaDeg).mod(360.0))
        
        val deltaPsi = NutationReader.nutationInLongitudeReader(
            t,
            lRad,
            l1Rad,
            fRad,
            dRad,
            omegaRad,
            nut
        ) / 36000000000.0
        
        val deltaEpsilon = NutationReader.nutationInObliquityReader(
            t,
            lRad,
            l1Rad,
            fRad,
            dRad,
            omegaRad,
            nut
        ) / 36000000000.0
        
        
        return doubleArrayOf(deltaPsi, deltaEpsilon)
    }
    
    /**
    * Mean Obliquity of Ecliptic, epsilon zero
    *
    * @param jme Julian Millenium Ephemeris
    *
    * @return mean obliquity of ecliptic
    */
    fun meanObliquityOfEcliptic(jd: Double, deltaT: Double = 0.0): Double {
        
        val u = TimeUtil.julianType(jd, deltaT, JulianType.JME) / 10.0
        val epsilonZero = 23 + 26.0 / 60 + 21.448 / 3600 + (-4680.93 * u - 1.55 * u.pow(2) + 1999.25 * u.pow(3) - 51.38 * u.pow(4) - 249.67 * u.pow(5) - 39.05 * u.pow(6) + 7.12 * u.pow(7) + 27.87 * u.pow(8) + 5.79 * u.pow(9) + 2.45 * u.pow(10)) / 3600
        
        return epsilonZero
    }
    
    /**
    * True Obliquity of Ecliptic, epsilon
    *
    * @param nutationInObliquity
    * @param meanObliquityOfEcliptic
    * 
    * @return trueObliquityOfEcliptic
    */
    fun trueObliquityOfEcliptic(jd: Double, deltaT: Double): Double {
    
        val meanObliquityOfEcliptic = meanObliquityOfEcliptic(jd, deltaT)
        val nutationInObliquity = nutationInLonAndObliquity(jd, deltaT)[1]
        
        return meanObliquityOfEcliptic + nutationInObliquity
    }
    
}
