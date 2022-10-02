import reposistory.HolidayRespository
import java.time.Instant
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class ReportDayService (
    private val HolidayRespository: HolidayRespository
    ) {
    fun reportDayFromPeriods(year:Int, month:Int, day:Int, hour:Int, min:Int = 0, sec: Int = 0): String {
        val formatYear = String.format("%04d", year)
        val formatMonth = String.format("%02d", month)
        val formatDay = String.format("%02d", day)
        val formatHour = String.format("%02d", hour)
        val formatMin = String.format("%02d", min)
        val formatSec = String.format("%02d", sec)
        val instant = Instant.parse("$formatYear-$formatMonth-${formatDay}T$formatHour:$formatMin:$formatSec.00z")
        return reportDay(instant)
    }
    fun reportDay(instant: Instant): String {
        return instant.toWorkingDate().formatDate()
    }
    private fun Instant.toWorkingDate(): Instant {
        return if (this.getInstantHour() > 17)
            HolidayRespository.findWorkingDay(this.plusOneDay())
        else HolidayRespository.findWorkingDay(this)
    }
    private fun Instant.formatDate():String {
        val (year, month, day) = this.toString().split("T")[0].split("-")
        return("$year$month$day")
    }
    private fun Instant.plusOneDay(): Instant {
        return this.plus(1, ChronoUnit.DAYS)
    }
    private fun Instant.getInstantHour(): Int {
        return(this.toString().split("T")[1].split(":")[0].toInt())
    }
}