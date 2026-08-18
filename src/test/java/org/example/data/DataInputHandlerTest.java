package org.example.data;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DataInputHandlerTest {

    private DataInputHandler handler;

    @BeforeEach
    void setUp() {
        handler = new DataInputHandler();
    }

    @Nested
    @DisplayName("getArraySize tests")
    class GetArraySizeTests {

        @Test
        @DisplayName("Should return valid size when input is positive integer")
        void shouldReturnValidSize() {

            Scanner scanner = new Scanner("5\n");

            int size = handler.getArraySize(scanner);

            assertEquals(5, size);

        }

        @Test
        @DisplayName("Should retry when input is not a number")
        void shouldRetryWhenInputIsNotNumber() {

            Scanner scanner = new Scanner("abc\n10\n");

            int size = handler.getArraySize(scanner);

            assertEquals(10, size);

        }

        @Test
        @DisplayName("Should retry when input is zero")
        void shouldRetryWhenInputIsZero() {

            Scanner scanner = new Scanner("0\n3\n");

            int size = handler.getArraySize(scanner);

            assertEquals(3, size);

        }

        @Test
        @DisplayName("Should retry when input is negative")
        void shouldRetryWhenInputIsNegative() {

            Scanner scanner = new Scanner("-5\n7\n");

            int size = handler.getArraySize(scanner);

            assertEquals(7, size);

        }
    }

    @Nested
    @DisplayName("manualInput tests")
    class ManualInputTests {
        @Test
        @DisplayName("Should create users with valid input")
        void shouldCreateUsersWithValidInput() {

            String input = "Alice\npassword123\nalice@mail.com\nBob\npass4567\nbob@mail.com\n";
            Scanner scanner = new Scanner(input);

            CustomLinkedList<User> users = handler.manualInput(scanner, 2);

            assertEquals(2, users.size());
            assertEquals("Alice", users.get(0).getName());
            assertEquals("password123", users.get(0).getPassword());
            assertEquals("alice@mail.com", users.get(0).getEmail());
            assertEquals("Bob", users.get(1).getName());
            assertEquals("pass4567", users.get(1).getPassword());
            assertEquals("bob@mail.com", users.get(1).getEmail());

        }

        @Test
        @DisplayName("Should retry on invalid input")
        void shouldRetryOnInvalidInput() {

            String input = "Alice\n123\ninvalid\nAlice\npassword123\nalice@mail.com\n";
            Scanner scanner = new Scanner(input);

            CustomLinkedList<User> users = handler.manualInput(scanner, 1);

            assertEquals(1, users.size());
            assertEquals("Alice", users.get(0).getName());
            assertEquals("password123", users.get(0).getPassword());
            assertEquals("alice@mail.com", users.get(0).getEmail());

        }

        @Test
        @DisplayName("Should retry when password is too short")
        void shouldRetryWhenPasswordTooShort() {

            // Первая попытка: короткий пароль (12345)
            // Вторая попытка: валидный пароль (password123)
            String input = "Alice\n12345\nalice@mail.com\nAlice\npassword123\nalice@mail.com\n";
            Scanner scanner = new Scanner(input);

            CustomLinkedList<User> users = handler.manualInput(scanner, 1);

            assertEquals(1, users.size());
            assertEquals("Alice", users.get(0).getName());
            assertEquals("password123", users.get(0).getPassword());
            assertEquals("alice@mail.com", users.get(0).getEmail());

        }

        @Test
        @DisplayName("Should retry when email is invalid")
        void shouldRetryWhenEmailInvalid() {

            // Первая попытка: невалидный email (invalidemail)
            // Вторая попытка: валидный email (alice@mail.com)
            String input = "Alice\npassword123\ninvalidemail\nAlice\npassword123\nalice@mail.com\n";
            Scanner scanner = new Scanner(input);

            CustomLinkedList<User> users = handler.manualInput(scanner, 1);

            assertEquals(1, users.size());
            assertEquals("Alice", users.get(0).getName());
            assertEquals("password123", users.get(0).getPassword());
            assertEquals("alice@mail.com", users.get(0).getEmail());

        }

        @Test
        @DisplayName("Should retry when name is empty")
        void shouldRetryWhenNameEmpty() {

            // Первая попытка: пустое имя
            // Вторая попытка: валидное имя (Alice)
            String input = "\npassword123\nalice@mail.com\nAlice\npassword123\nalice@mail.com\n";
            Scanner scanner = new Scanner(input);

            CustomLinkedList<User> users = handler.manualInput(scanner, 1);

            assertEquals(1, users.size());
            assertEquals("Alice", users.get(0).getName());
            assertEquals("password123", users.get(0).getPassword());
            assertEquals("alice@mail.com", users.get(0).getEmail());

        }

        @Test
        @DisplayName("Should retry when multiple fields are invalid")
        void shouldRetryWhenMultipleFieldsInvalid() {
            // Несколько попыток с ошибками
            String input = "\n123\ninvalid\nAlice\npassword123\nalice@mail.com\n";
            Scanner scanner = new Scanner(input);

            CustomLinkedList<User> users = handler.manualInput(scanner, 1);

            assertEquals(1, users.size());
            assertEquals("Alice", users.get(0).getName());
            assertEquals("password123", users.get(0).getPassword());
            assertEquals("alice@mail.com", users.get(0).getEmail());

        }

    }

    @Nested
    @DisplayName("readUsersFromFile tests")
    class ReadUsersFromFileTests {
        @TempDir
        Path tempDir;

        @Test
        @DisplayName("Should read users from valid file")
        void shouldReadUsersFromValidFile() throws IOException {

            Path file = tempDir.resolve("users.txt");
            String content = "Alice;password123;alice@mail.com\nBob;pass4567;bob@mail.com\n";
            Files.writeString(file, content);

            CustomLinkedList<User> users = handler.readUsersFromFile(file.toString());

            assertEquals(2, users.size());
            assertEquals("Alice", users.get(0).getName());
            assertEquals("password123", users.get(0).getPassword());
            assertEquals("alice@mail.com", users.get(0).getEmail());
            assertEquals("Bob", users.get(1).getName());
            assertEquals("pass4567", users.get(1).getPassword());
            assertEquals("bob@mail.com", users.get(1).getEmail());

        }

        @Test
        @DisplayName("Should skip empty lines")
        void shouldSkipEmptyLines() throws IOException {

            Path file = tempDir.resolve("users.txt");
            String content = "\nAlice;password123;alice@mail.com\n\nBob;pass4567;bob@mail.com\n";
            Files.writeString(file, content);

            CustomLinkedList<User> users = handler.readUsersFromFile(file.toString());

            assertEquals(2, users.size());

        }

        @Test
        @DisplayName("Should skip lines with invalid format")
        void shouldSkipInvalidFormatLines() throws IOException {

            Path file = tempDir.resolve("users.txt");
            String content = "Alice;password123;alice@mail.com\nInvalid line\nBob;pass4567;bob@mail.com\n";
            Files.writeString(file, content);

            CustomLinkedList<User> users = handler.readUsersFromFile(file.toString());

            assertEquals(2, users.size());

        }

        @Test
        @DisplayName("Should skip lines with invalid data and continue")
        void shouldSkipInvalidDataLines() throws IOException {

            Path file = tempDir.resolve("users.txt");
            String content = "Alice;123;alice@mail.com\nBob;pass4567;bob@mail.com\n";
            Files.writeString(file, content);

            CustomLinkedList<User> users = handler.readUsersFromFile(file.toString());

            // Первая строка с коротким паролем будет пропущена
            assertEquals(1, users.size());
            assertEquals("Bob", users.get(0).getName());

        }

        @Test
        @DisplayName("Should throw IOException when file not found")
        void shouldThrowIOExceptionWhenFileNotFound() {

            assertThrows(IOException.class, () -> handler.readUsersFromFile("nonexistent.txt"));

        }

        @Test
        @DisplayName("Should handle file with leading/trailing spaces")
        void shouldHandleFileWithSpaces() throws IOException {

            Path file = tempDir.resolve("users.txt");
            String content = "  Alice  ;  password123  ;  alice@mail.com  \n  Bob  ;  pass4567  ;  bob@mail.com  \n";
            Files.writeString(file, content);

            CustomLinkedList<User> users = handler.readUsersFromFile(file.toString());

            assertEquals(2, users.size());
            assertEquals("Alice", users.get(0).getName());
            assertEquals("password123", users.get(0).getPassword());
            assertEquals("alice@mail.com", users.get(0).getEmail());

        }

        @Test
        @DisplayName("Should handle file with special characters in data")
        void shouldHandleFileWithSpecialCharacters() throws IOException {

            Path file = tempDir.resolve("users.txt");
            String content = "Alice-Smith;pass!@#123;alice+test@mail.com\nBob;pass_456;bob@mail.com\n";
            Files.writeString(file, content);

            CustomLinkedList<User> users = handler.readUsersFromFile(file.toString());

            assertEquals(2, users.size());
            assertEquals("Alice-Smith", users.get(0).getName());
            assertEquals("pass!@#123", users.get(0).getPassword());
            assertEquals("alice+test@mail.com", users.get(0).getEmail());
            assertEquals("Bob", users.get(1).getName());
            assertEquals("pass_456", users.get(1).getPassword());
            assertEquals("bob@mail.com", users.get(1).getEmail());

        }

        @Test
        @DisplayName("Should handle file with multiple consecutive empty lines")
        void shouldHandleMultipleEmptyLines() throws IOException {
            Path file = tempDir.resolve("users.txt");
            String content = "\n\nAlice;password123;alice@mail.com\n\n\nBob;pass4567;bob@mail.com\n\n";
            Files.writeString(file, content);

            CustomLinkedList<User> users = handler.readUsersFromFile(file.toString());

            assertEquals(2, users.size());
        }

    }

}