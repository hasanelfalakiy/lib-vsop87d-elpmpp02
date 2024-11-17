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
 
package com.andihasan7.lib.vsop87d.elpmpp02.correction

import kotlin.math.atan
import kotlin.math.tan
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.pow
import com.andihasan7.lib.vsop87d.elpmpp02.earthposition.EarthPosition
import com.andihasan7.lib.vsop87d.elpmpp02.enum.JulianType
import com.andihasan7.lib.vsop87d.elpmpp02.enum.DistanceType
import com.andihasan7.lib.vsop87d.elpmpp02.timeutil.TimeUtil

object Correction {
    
    /**
    * Abration in arcsecond
    * 
    * @param jd, Julian Day
    * @param deltaT, in arcsecond
    *
    * @return abr // in arcseconds (detik busur), Before displaying the DMS format, divide it by 3600 first.
    *
    */
    fun abration(jd: Double, deltaT: Double = 0.0): Double {
        
        val t = TimeUtil.julianType(jd, deltaT, JulianType.JME)
        val r = EarthPosition.earthRadiusVector(jd, deltaT, DistanceType.AU)
        val deltaLambda = 3548.330 + 
            118.568 * sin(Math.toRadians(87.5287 + 359993.7286 * t)) +
            2.476 * sin(Math.toRadians(85.0561 + 719987.4571 * t)) +
            1.376 * sin(Math.toRadians(27.8502 + 4452671.1152 * t)) +
            0.119 * sin(Math.toRadians(73.1375 + 450368.8564 * t)) +
            0.114 * sin(Math.toRadians(337.2264 + 329644.6718 * t)) +
            0.086 * sin(Math.toRadians(222.5400 + 659289.3436 * t)) +
            0.078 * sin(Math.toRadians(162.8136 + 9224659.7915 * t)) +
            0.054 * sin(Math.toRadians(82.5823 + 1079981.1857 * t)) +
            0.052 * sin(Math.toRadians(171.5189 + 225184.4282 * t)) +
            0.034 * sin(Math.toRadians(30.3214 + 4092677.3866 * t)) +
            0.033 * sin(Math.toRadians(119.8105 + 337181.4711 * t)) +
            0.023 * sin(Math.toRadians(247.5418 + 299295.6151 * t)) +
            0.023 * sin(Math.toRadians(325.1526 + 315559.5560 * t)) +
            0.021 * sin(Math.toRadians(155.1241 + 675553.2846 * t)) +
            7.311 * t * sin(Math.toRadians(333.4515 + 359993.7286 * t)) +
            0.305 * t * sin(Math.toRadians(330.9814 + 719987.4571 * t)) +
            0.010 * t * sin(Math.toRadians(328.5170 + 1079981.1857 * t)) +
            0.309 * t.pow(2) * sin(Math.toRadians(241.4518 + 359993.7286 * t)) +
            0.021 * t.pow(2) * sin(Math.toRadians(205.0482 + 719987.4571 * t)) +
            0.004 * t.pow(2) * sin(Math.toRadians(297.8610 + 4452671.1152 * t)) +
            0.010 * t.pow(3) * sin(Math.toRadians(154.7066 + 359993.7286 * t))
        
        val abr = -0.005775518 * r * deltaLambda
        
        return abr
    }
    
    /**
    * term u in radian
    * 
    * @param latitude of observer
    * @return u in radian
    */
    fun termU(lat: Double): Double {
        val u = atan(0.99664719 * tan(Math.toRadians(lat)))
        return u
    }
    
    /**
    * term x in radian
    * 
    * @param latitude of observer
    * @param elevation of observer
    *
    * @return x in radian
    */
    fun termX(lat: Double, elev: Double = 0.0): Double {
        
        val u = termU(lat)
        val x = cos(u) + (elev / 6378140.0) * cos(Math.toRadians(lat))
        return x
    }
    
    /**
    * term y in radian
    * 
    * @param latitude of observer
    * @param elevation of observer
    *
    * @return y in radian
    */
    fun termY(lat: Double, elev: Double = 0.0): Double {
        
        val u = termU(lat)
        val y = 0.99664719 * sin(u) + (elev / 6378140.0) * sin(Math.toRadians(lat))
        
        return y
    }
    
    
}
