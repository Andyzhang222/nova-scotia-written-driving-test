import com.example.novascotiawrittendrivingtest.helper.ValidateRegister
import org.junit.Test
import org.junit.Assert.*

class RegisterActivityTest {

    @Test
    fun `email validation returns true for valid email`() {
        val validEmail = "test@example.com"
        assertTrue(ValidateRegister.validateEmail(validEmail))
    }

    @Test
    fun `email validation returns false for invalid email`() {
        val invalidEmail = "testexample.com"
        assertFalse(ValidateRegister.validateEmail(invalidEmail))
    }

    @Test
    fun `password format validation returns true for valid password`() {
        val validPassword = "Password123"
        assertTrue(ValidateRegister.validatePwdFormat(validPassword))
    }

    @Test
    fun `password format validation returns false for invalid password`() {
        val invalidPassword = "password"
        assertFalse(ValidateRegister.validatePwdFormat(invalidPassword))
    }
}
