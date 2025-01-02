package com.andihasan7.lib.vsop87d.elpmpp02

import com.andihasan7.lib.vsop87d.elpmpp02.convertutil.ConvertUtil
import com.andihasan7.lib.vsop87d.elpmpp02.prayertimes.PrayerTimes
import kotlin.test.Test

class PrayerTimesTest {

    @Test
    fun prayerTimesTest() {

        val ws = PrayerTimes(
            date = 10,
            month = 1,
            year = 2024,
            latitude = -7.4333333334,
            longitude = 111.43333333334,
            elevation = 150.0,
            timeZone = 7.0,
            ihtiyatDzuhur = 3,
            otherIhtiyat = 2
        )

        val imsak = ws.imsakWD_HMS
        val shubuh = ws.shubuhWD_HMS
        val terbit = ws.terbitWD_HMS
        val dluha = ws.dluhaWD_HMS
        val dzuhur = ws.dzuhurWD_HMS
        val ashar = ws.asharWD_HMS
        val maghrib = ws.maghribWD_HMS
        val isya = ws.isyaWD_HMS
        val tMalam = ws.tengahMalamWD_HMS
        val duaPerTigaMalam = ws.duaPer3MalamWD_HMS
        val rashdul1 = ws.rashdu1HMS
        val rashdul2 = ws.rashdu2HMS
        val selisihJam = ws.selisihJamHMS
        val jarakKeduanya = ws.jarakKeduanya
        val selisihLK = ws.selisihDecLintangKabahDMS
        val selisihLT = ws.selisihDecLintangTempatDMS
        val dek12Noon = ConvertUtil.toDegreeFullRound2(ws.dek)
        val eq12Noon = ConvertUtil.toCounterMMSS2(ws.eq)
        val semi12Noon = ConvertUtil.toDegreeFullRound2(ws.semidiameter)

        println("Imsak   : $imsak")
        println("Shubuh  : $shubuh")
        println("Terbit  : $terbit")
        println("Dluha   : $dluha")
        println("Dzuhur  : $dzuhur")
        println("Ashar   : $ashar")
        println("Maghrib : $maghrib")
        println("Isya    : $isya")
        println("T. Malam: $tMalam")
        println("2/3 Mlm : $duaPerTigaMalam")
        println("rashdu1 : $rashdul1")
        println("rashdu2 : $rashdul2")
        println("")
        println("Selisih Jam: $selisihJam")
        println("Jarak Keduanya: $jarakKeduanya")
        println("Selisih Dek - LK: $selisihLK")
        println("Selisih Dek - LT: $selisihLT")
        println("")
        println("Deklinasi : $dek12Noon")
        println("Equation  : $eq12Noon")
        println("Semidiameter: $semi12Noon")


    }
}