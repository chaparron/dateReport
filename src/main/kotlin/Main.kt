import java.time.Instant
import java.time.ZoneOffset

//private const val PATTERN_FORMAT = "yyyyMMdd"
fun main() {
    val instant: Instant = Instant.parse("2022-10-05T19:15:30.00Z")
    print(instant.reportDay())
}
private fun Instant.reportDay(): String {
    return this.toWorkingDate().formatDate()
}
private fun Instant.toWorkingDate(): Instant {
    var instant = this
    if (this.atZone(ZoneOffset.UTC).hour > 17)  instant = instant.plusSeconds(86400)
    while (instant.isWorkingDay()) {
        instant = instant.plusSeconds(86400)
    }
    return instant
}
private fun Instant.isWorkingDay(): Boolean {
    val holidays = listOf(
        "2022-10-01",
        "2022-10-06",
        "2022-10-07",
        "2022-10-08"
    )
    return this.toString().split("T")[0] in holidays
}
private fun Instant.formatDate():String {
    val (year, month, day) = this.toString().split("T")[0].split("-")
    return("$year$month$day")
//    val localDateTime = LocalDateTime.ofInstant(this, ZoneOffset.UTC)
//    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
//    return localDateTime.format(formatter)
}