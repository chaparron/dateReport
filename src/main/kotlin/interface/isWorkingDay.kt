package `interface`

import java.time.Instant

 fun Instant.isWorkingDay(): Boolean {
    val date = this.toString().split("T")[0]
    val nonWorkingDays = mapOf(
        "2022-10-06" to "Holly friday",
        "2022-10-07" to "Saturday",
        "2022-10-08" to "Sunday",
        "2022-12-18" to "Hacker master birthday"
    )
    return date in nonWorkingDays.keys
}