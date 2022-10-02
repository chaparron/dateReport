import reposistory.HolidayRepository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ReportDayService (
    private val holidayRepository: HolidayRepository
    ) {
    companion object{
        private const val PATTERN_FORMAT = "yyyyMMdd"
//        private val zoneId = ZoneId.of("Asia/Yerevan")
    }
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
        return instant.workingDate().formatDate()
    }
    private fun Instant.workingDate(): Instant {
        return if (this.getInstantHour() > 17)
            holidayRepository.findWorkingDay(this.plusOneDay())
        else holidayRepository.findWorkingDay(this)
    }
    private fun Instant.formatDate():String {
        val localDateTime = LocalDateTime.ofInstant(this, ZoneOffset.UTC)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
        return localDateTime.format(formatter)
//        val (year, month, day) = this.toString().split("T")[0].split("-")
//        return("$year$month$day")
    }
    private fun Instant.plusOneDay(): Instant {
        return this.plus(1, ChronoUnit.DAYS)
    }
    private fun Instant.getInstantHour(): Int {
        return LocalDateTime.ofInstant(this, ZoneOffset.UTC).hour
        // return(this.toString().split("T")[1].split(":")[0].toInt())
    }
}