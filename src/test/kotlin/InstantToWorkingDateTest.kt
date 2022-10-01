import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.time.Instant

@ExtendWith(MockitoExtension::class)
class InstantToWorkingDateTest {


    @Test
    fun `should return String length of 8 chars convertible to Int with a valid instant`() {
        // Given
        val instant: Instant = Instant.parse("2022-10-05T19:15:30.00z")
        // When
        val workDay = InstantToWorkingDate().reportDay(instant)
        // Then
        assertTrue(workDay.length == 8 && workDay.toIntOrNull() != null)
    }
    @Test
    fun `should return the same day giving a not holiday at 10hrs`() {
        // Given
        val instant: Instant = Instant.parse("2022-09-05T10:00:00.00z")
        // When
        val workDay = InstantToWorkingDate().reportDay(instant)
        // Then
        assertTrue(workDay == "20220905")
    }
    @Test
    fun `should return next day giving a not holiday at 19hrs`() {
        // Given
        val instant: Instant = Instant.parse("2022-09-05T19:00:00.00z")
        // When
        val workDay = InstantToWorkingDate().reportDay(instant)
        // Then
        assertTrue(workDay == "20220906")
    }
    @Test
    fun `should return same day plus 4 giving a thursday at 19 with friday holiday`() {
        // Given
        val instant: Instant = Instant.parse("2022-10-05T19:00:00.00z")
        // When
        val workDay = InstantToWorkingDate().reportDay(instant)
        // Then
        assertTrue(workDay == "20221009")
    }
}