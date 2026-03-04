package cz.pslib.unittests

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class UserManagerTest {

    @Test
    fun testWelcomeMessage() {
        // Vytvoření mock objektu


        val mockRepository = mock(UserRepository::class.java)
        `when`(mockRepository.getUserName(1)).thenReturn("Alice")

        // Injektáž mocku do testované třídy


        val userManager = UserManager(mockRepository)
        val message = userManager.welcomeMessage(1)

        // Ověření výsledku


        assertEquals("Welcome, Alice!", message)

        // Ověření, zda byla metoda volána s daným parametrem


        verify(mockRepository).getUserName(1)
    }
}
