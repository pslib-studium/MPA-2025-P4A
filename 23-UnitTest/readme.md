# Jednotkové testy

Jednotkové testy představují základní nástroj pro ověřování správnosti jednotlivých částí kódu v izolaci. Jejich hlavním cílem je zajistit, aby jednotlivé komponenty systému fungovaly dle očekávání, což výrazně přispívá k robustnosti a udržovatělnosti softwaru.

## 1. Základní nastavení projektu

Pro psaní jednotkových testů se v Android vývoji nejčastěji využívá framework JUnit, přičemž pro simulaci závislostí lze použít Mockito. Pro zajištění funkcionality těchto knihoven je nutné upravit soubor `build.gradle` v rámci modulu aplikace:

```
dependencies {
    // Knihovna pro jednotkové testy
    testImplementation 'junit:junit:4.13.2'
    // Mockito pro tvorbu mock objektů
    testImplementation 'org.mockito:mockito-core:4.0.0'
    
    // Pro podporu Kotlinu
    testImplementation 'org.mockito.kotlin:mockito-kotlin:4.0.0'
    // AssertJ (volitelné)
    testImplementation 'org.assertj:assertj-core:3.21.0'
}
```

## 2. Vytvoření testovacích tříd

Testovací třídy se standardně umístěují do adresáře `src/test/java`. Je vhodné pojmenovat je podle tříd, které testují. Například pro tříu `Calculator` bude odpovídající testovací třída `CalculatorTest`.

Anotace `@Test` označuje metody, které představují samostatné testovací případy.

Metoda `assertEquals` ověřuje, zda je očekávaný výstup shodný se skutečným výsledkem výpočtu.

### Soubor: `Calculator.kt`

```
class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun subtract(a: Int, b: Int): Int {
        return a - b
    }
}
```

### Implementace testovací třídy

#### Soubor: `CalculatorTest.kt`

```
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
```

## 3. Použití Mockito pro mockování závislostí

Pokud testovaná třída závisí na jiných třídách (například databázových nebo síťových službách), je vhodné použít nástroje pro simulaci těchto závislostí. Mockito umožňuje vytvořit mock objekty, které simulují chování skutečných závislostí.

### Soubor: `UserRepository.kt`

```
interface UserRepository {
    fun getUserName(userId: Int): String
}
```

### Soubor: `UserManager.kt`

```
class UserManager(private val repository: UserRepository) {
    fun welcomeMessage(userId: Int): String {
        val userName = repository.getUserName(userId)
        return "Welcome, $userName!"
    }
}
```

Při testování není žádoucí, aby metoda `getUserName` volala skutečný repozitář, proto se místo něj použije mock:

### Soubor: `UserManagerTest.kt`

```
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
```

## 4. Spouštění testů v Android Studiu

### Možnosti spuštění testů

-   **Přes kontextovou nabídku**: Kliknutím pravým tlačítkem myši na testovací tříu nebo metodu a výběrem možnosti „Run ‘TestName’“.
    
-   **Přes nástrojové okno „Project“**: V adresáři `src/test/java` kliknout pravým tlačítkem na testovací soubor a zvolit „Run Test“.
    
-   **Použitím klávesové zkratky**: Například `Ctrl + Shift + F10` v prostředí Android Studia.