import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val PATTERN_FORMAT = "yyyyMMdd"

fun main() {
    val instant: Instant = Instant.parse("2022-10-05T19:15:30.00Z")
    print(instant.reportDay())
}

private fun Instant.reportDay(): String {
    val localDateTime = LocalDateTime.ofInstant(this, ZoneOffset.UTC)
    return localDateTime.workingDate().isHoliday().formatDate()
}

private fun LocalDateTime.workingDate(): LocalDateTime {
    return if (this.hour < 18) this else this.plusDays(1)
}

private fun LocalDateTime.isHoliday(): LocalDateTime {
    val holidays = listOf(
        "2022-10-01",
        "2022-10-02",
        "2022-10-06",
        "2022-10-07",
        "2022-10-08",
        "2022-10-11"
    )
    var counter = 0L
    while (true) {
        if ((this.toLocalDate().plusDays(counter).toString()) in holidays) counter++ else break
    }
    return this.plusDays(counter)
}

private fun LocalDateTime.formatDate():String {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
    return this.format(formatter)
}