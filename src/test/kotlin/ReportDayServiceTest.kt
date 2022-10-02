import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import reposistory.HolidayRespository
import java.time.Instant
import java.time.temporal.ChronoUnit

@ExtendWith(MockitoExtension::class)
internal class ReportDayServiceTest{

    @Mock
    private lateinit var holidayRepository: HolidayRespository
    @InjectMocks
    private lateinit var sut: ReportDayService

    @Test
    fun `should return String length of 8 chars convertible to Int with a valid instant`() {
        // Given
        val instant: Instant = Instant.parse("2022-10-05T09:15:30.00z")
        whenever(holidayRepository.findWorkingDay(instant)).thenReturn(Instant.parse("2022-10-05T19:15:30.00z"))
        // When
        val workDay = sut.reportDay(instant)
        // Then
        assertTrue(workDay.length == 8 && workDay.toIntOrNull() != null)
        verify(holidayRepository).findWorkingDay(instant)
    }
    @Test
    fun `should should return the same day giving a working day at 10hrs`() {
        // Given
        val instant: Instant = Instant.parse("2022-10-05T09:15:30.00z")
        whenever(holidayRepository.findWorkingDay(instant)).thenReturn(Instant.parse("2022-10-05T09:15:30.00z"))
        // When
        val workDay = sut.reportDay(instant)
        // Then
        val expected = "20221005"
        assertEquals(expected, workDay)
        verify(holidayRepository).findWorkingDay(instant)
    }
    @Test
    fun `should should return next day giving a working day at 19hrs`() {
        // Given
        val instant = Instant.parse("2022-09-05T20:30:00.00z")
        whenever(holidayRepository.findWorkingDay(instant.plus(1, ChronoUnit.DAYS)))
            .thenReturn(instant.plus(1, ChronoUnit.DAYS))
        // When
        val workDay = sut.reportDay(instant)
        // Then
        val expected = "20220906"
        assertEquals(expected, workDay)
        verify(holidayRepository).findWorkingDay(Instant.parse("2022-09-06T20:30:00.00z"))

    }
    @Test
    fun `should should return 3 days later giving a working day at 19hrs followed by 2 holidays`() {
        // Given
        val instant = Instant.parse("2022-09-05T20:30:00.00z")
        whenever(holidayRepository.findWorkingDay(Instant.parse("2022-09-06T20:30:00.00z")))
            .thenReturn(Instant.parse("2022-09-08T20:30:00.00z"))
        // When
        val workDay = sut.reportDay(instant)
        // Then
        val expected = "20220908"
        assertEquals(expected, workDay)
        verify(holidayRepository).findWorkingDay(Instant.parse("2022-09-06T20:30:00.00z"))
    }
    @Test
    fun `Should return the same using reportDayFromPeriods than reportDay`() {
        // Given
        whenever(holidayRepository.findWorkingDay(Instant.parse("0123-03-03T09:05:07.00z")))
            .thenReturn(Instant.parse("0123-03-03T09:05:07.00z"))
        // When
        val workingDay = sut.reportDay(Instant.parse("0123-03-03T09:05:07.00z"))
        val workingDayFromPeriods = sut.reportDayFromPeriods(123,3,3,9,5,7)
        // Then
        assertEquals(workingDay, workingDayFromPeriods)
    }
}