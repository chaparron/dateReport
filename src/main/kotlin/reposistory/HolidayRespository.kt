package reposistory

import java.time.Instant

interface HolidayRespository {
    fun findWorkingDay(instant: Instant): Instant
}