import `interface`.Holiday
import `interface`.isWorkingDay
import java.time.Instant
import java.time.ZoneOffset

class InstantToWorkingDate {
    fun reportDay(instant: Instant): String {
        return instant.toWorkingDate().formatDate()
    }
    private fun Instant.toWorkingDate(): Instant {
        var instant = this
        if (this.atZone(ZoneOffset.UTC).hour > 17)  instant = instant.plusSeconds(86400)
        while (Holiday().isWorkingDay(instant)) {
            instant = instant.plusSeconds(86400)
        }
        return instant
    }
    private fun Instant.formatDate():String {
        val (year, month, day) = this.toString().split("T")[0].split("-")
        return("$year$month$day")
    }
}