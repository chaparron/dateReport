package reposistory

import java.time.Instant

interface HolidayRepository {
    fun findWorkingDay(instant: Instant): Instant
}