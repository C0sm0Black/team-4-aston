package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Функциональные тесты консольного класса Application.
 *
 * <p>Каждый тест передаёт приложению заранее подготовленные команды через
 * System.in и проверяет результат через System.out либо временный файл.</p>
 */
class ApplicationIntegrationTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private PrintStream originalErr;
    private ByteArrayOutputStream capturedOutput;
    private PrintStream testPrintStream;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        originalErr = System.err;

        capturedOutput = new ByteArrayOutputStream();
        testPrintStream = new PrintStream(
                capturedOutput,
                true,
                StandardCharsets.UTF_8
        );

        System.setOut(testPrintStream);
        System.setErr(testPrintStream);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
        testPrintStream.close();
    }

    @Test
    void shouldFillCollectionManually() {
        String output = runApplication(
                "1",                 // ручной ввод
                "2",                 // размер коллекции
                "Анна",              // пользователь №1
                "secret1",
                "anna@mail.ru",
                "Борис",             // пользователь №2
                "secret2",
                "boris@mail.ru",
                "",                  // Enter после заполнения
                "9",                 // выход
                "n",                 // не сохранять перед выходом
                "y"                  // подтвердить выход
        );

        assertTrue(output.contains("Загружено 2 пользователей"));
        assertTrue(output.contains("Анна"));
        assertTrue(output.contains("Борис"));
    }

    @Test
    void shouldSelectEmailSortStrategy(@TempDir Path tempDir) throws Exception {
        Path inputFile = createUsersFile(tempDir);

        String output = runApplication(
                "3", inputFile.toString(), "", // загрузка из файла
                "4", "3", "",                // сортировка по email
                "9", "n", "y"                 // выход
        );

        assertTrue(output.contains(
                "Выбрана стратегия сортировки: по почте (алфавит)"
        ));
    }

    @Test
    void shouldExecuteNameSorting(@TempDir Path tempDir) throws Exception {
        Path inputFile = createUsersFile(tempDir);

        String output = runApplication(
                "3", inputFile.toString(), "", // загрузка из файла
                "4", "1", "",                // стратегия по имени
                "5", "",                      // выполнить сортировку
                "9", "n", "y"                 // выход
        );

        assertTrue(output.contains("Сортировка выполнена успешно"));

        int sortedSectionStart = output.lastIndexOf(
                "Сортировка выполнена успешно"
        );
        String sortedSection = output.substring(sortedSectionStart);

        assertTrue(sortedSection.indexOf("Анна")
                < sortedSection.indexOf("Борис"));
        assertTrue(sortedSection.indexOf("Борис")
                < sortedSection.indexOf("Яна"));
    }

    @Test
    void shouldDisplayLoadedUsers(@TempDir Path tempDir) throws Exception {
        Path inputFile = createUsersFile(tempDir);

        String output = runApplication(
                "3", inputFile.toString(), "", // загрузка из файла
                "6", "",                      // показать пользователей
                "9", "n", "y"                 // выход
        );

        assertTrue(output.contains("СПИСОК ПОЛЬЗОВАТЕЛЕЙ"));
        assertTrue(output.contains("Всего: 3"));
        assertTrue(output.contains("Анна"));
        assertTrue(output.contains("Борис"));
        assertTrue(output.contains("Яна"));
    }

    @Test
    void shouldSaveUsersToTemporaryFile(@TempDir Path tempDir)
            throws Exception {
        Path inputFile = createUsersFile(tempDir);
        Path outputFile = tempDir.resolve("saved-users.txt");

        String output = runApplication(
                "3", inputFile.toString(), "",  // загрузка из файла
                "7", outputFile.toString(), "", // сохранение
                "9", "n", "y"                  // выход
        );

        assertTrue(output.contains("Данные сохранены в:"));
        assertTrue(Files.exists(outputFile));

        List<String> savedLines = Files.readAllLines(
                outputFile,
                StandardCharsets.UTF_8
        );

        assertEquals(3, savedLines.size());
        assertEquals("Яна;password123;yana@mail.ru", savedLines.get(0));
        assertEquals("Анна;secret123;anna@mail.ru", savedLines.get(1));
        assertEquals("Борис;qwerty123;boris@mail.ru", savedLines.get(2));
    }

    @Test
    void shouldCountNameOccurrences(@TempDir Path tempDir) throws Exception {
        Path inputFile = tempDir.resolve("users-with-duplicates.txt");
        Files.writeString(
                inputFile,
                String.join(System.lineSeparator(),
                        "Анна,password123,anna1@mail.ru",
                        "Борис,secret123,boris@mail.ru",
                        "Анна,qwerty123,anna2@mail.ru"
                ),
                StandardCharsets.UTF_8
        );

        String output = runApplication(
                "3", inputFile.toString(), "", // загрузка из файла
                "8", "1", "Анна", "",       // подсчёт по имени
                "9", "n", "y"                 // выход
        );

        assertTrue(output.contains(
                "Найдено пользователей по имени 'Анна': 2"
        ));
    }

    private String runApplication(String... inputLines) {
        String input = String.join(System.lineSeparator(), inputLines)
                + System.lineSeparator();

        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)
        ));

        new Application().run();
        return capturedOutput.toString(StandardCharsets.UTF_8);
    }

    private Path createUsersFile(Path tempDir) throws Exception {
        Path inputFile = tempDir.resolve("users.txt");
        Files.writeString(
                inputFile,
                String.join(System.lineSeparator(),
                        "Яна,password123,yana@mail.ru",
                        "Анна,secret123,anna@mail.ru",
                        "Борис,qwerty123,boris@mail.ru"
                ),
                StandardCharsets.UTF_8
        );
        return inputFile;
    }
}
