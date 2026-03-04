package cz.pslib.unittests

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorTest {

    // Testovací metoda pro ověření sčítání

    @Test
    fun testAddition() {
        val calculator = Calculator()
        val result = calculator.add(3, 4)
        assertEquals("Sčítání nefunguje správně", 7, result)
    }

    // Testovací metoda pro ověření odčítání

    @Test
    fun testSubtraction() {
        val calculator = Calculator()
        val result = calculator.subtract(10, 4)
        assertEquals("Odčítání nefunguje správně", 6, result)
    }
}
