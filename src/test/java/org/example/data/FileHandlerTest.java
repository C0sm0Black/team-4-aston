package org.example.data;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @TempDir
    Path tempDir;

    @Nested
    @DisplayName("saveToFile tests")
    class SaveToFileTests {

        @Test
        @DisplayName("Should save users to file successfully")
        void shouldSaveUsersToFileSuccessfully() throws IOException {

            Path file = tempDir.resolve("users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();

            users.add(new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

            users.add(new User.Builder()
                    .name("Bob")
                    .password("pass4567")
                    .email("bob@mail.com")
                    .build());

            FileHandler.saveToFile(users, file.toString());

            List<String> lines = Files.readAllLines(file);
            assertEquals(2, lines.size());
            assertEquals("Alice;password123;alice@mail.com", lines.get(0));
            assertEquals("Bob;pass4567;bob@mail.com", lines.get(1));

        }

        @Test
        @DisplayName("Should create file if it doesn't exist")
        void shouldCreateFileIfNotExists() throws IOException {

            Path file = tempDir.resolve("new_users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();
            users.add(new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

            FileHandler.saveToFile(users, file.toString());

            assertTrue(Files.exists(file));
            List<String> lines = Files.readAllLines(file);
            assertEquals(1, lines.size());

        }

        @Test
        @DisplayName("Should append to existing file")
        void shouldAppendToExistingFile() throws IOException {

            Path file = tempDir.resolve("users.txt");

            // Первая запись
            CustomLinkedList<User> users1 = new CustomLinkedList<>();

            users1.add(new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

            FileHandler.saveToFile(users1, file.toString());

            // Вторая запись (добавление)
            CustomLinkedList<User> users2 = new CustomLinkedList<>();

            users2.add(new User.Builder()
                    .name("Bob")
                    .password("pass4567")
                    .email("bob@mail.com")
                    .build());

            FileHandler.saveToFile(users2, file.toString());

            List<String> lines = Files.readAllLines(file);
            assertEquals(2, lines.size());
            assertEquals("Alice;password123;alice@mail.com", lines.get(0));
            assertEquals("Bob;pass4567;bob@mail.com", lines.get(1));

        }

        @Test
        @DisplayName("Should create directories if they don't exist")
        void shouldCreateDirectoriesIfNotExist() throws IOException {

            Path file = tempDir.resolve("subdir").resolve("nested").resolve("users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();

            users.add(new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

            FileHandler.saveToFile(users, file.toString());

            assertTrue(Files.exists(file));
            assertTrue(Files.exists(file.getParent()));

        }

        @Test
        @DisplayName("Should save multiple users to file")
        void shouldSaveMultipleUsers() throws IOException {
            Path file = tempDir.resolve("users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();

            for (int i = 0; i < 10; i++) {

                users.add(new User.Builder()
                        .name("User" + i)
                        .password("pass" + i + "123")
                        .email("user" + i + "@mail.com")
                        .build());

            }

            FileHandler.saveToFile(users, file.toString());

            List<String> lines = Files.readAllLines(file);
            assertEquals(10, lines.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("User" + i + ";pass" + i + "123;user" + i + "@mail.com", lines.get(i));
            }

        }

        @Test
        @DisplayName("Should handle empty list")
        void shouldHandleEmptyList() throws IOException {

            Path file = tempDir.resolve("empty_users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();

            FileHandler.saveToFile(users, file.toString());

            assertTrue(Files.exists(file));
            List<String> lines = Files.readAllLines(file);
            assertEquals(0, lines.size());

        }

        @Test
        @DisplayName("Should handle users with special characters")
        void shouldHandleUsersWithSpecialCharacters() throws IOException {

            Path file = tempDir.resolve("users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();

            users.add(new User.Builder()
                    .name("Alice-Smith")
                    .password("pass!@#123")
                    .email("alice+test@mail.com")
                    .build());

            users.add(new User.Builder()
                    .name("Bob_John")
                    .password("pass_456")
                    .email("bob.john@mail.com")
                    .build());

            FileHandler.saveToFile(users, file.toString());

            List<String> lines = Files.readAllLines(file);
            assertEquals(2, lines.size());
            assertEquals("Alice-Smith;pass!@#123;alice+test@mail.com", lines.get(0));
            assertEquals("Bob_John;pass_456;bob.john@mail.com", lines.get(1));

        }

        @Test
        @DisplayName("Should handle users with spaces in fields")
        void shouldHandleUsersWithSpaces() throws IOException {

            Path file = tempDir.resolve("users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();

            users.add(new User.Builder()
                    .name("Alice Wonderland")
                    .password("pass 123")
                    .email("alice@mail.com")
                    .build());

            FileHandler.saveToFile(users, file.toString());

            List<String> lines = Files.readAllLines(file);
            assertEquals(1, lines.size());
            assertEquals("Alice Wonderland;pass 123;alice@mail.com", lines.get(0));

        }

        @Test
        @DisplayName("Should not create file when parent is null (current directory)")
        void shouldHandleNullParent() throws IOException {

            // Создаем файл в текущей директории (без родительских директорий)
            Path file = tempDir.resolve("simple_users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();

            users.add(new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

            FileHandler.saveToFile(users, file.toString());

            assertTrue(Files.exists(file));
            List<String> lines = Files.readAllLines(file);
            assertEquals(1, lines.size());

        }

        @Test
        @DisplayName("Should preserve order of users")
        void shouldPreserveOrderOfUsers() throws IOException {

            Path file = tempDir.resolve("users.txt");

            CustomLinkedList<User> users = new CustomLinkedList<>();
            users.add(new User.Builder().name("Third").password("passs3").email("third@mail.com").build());
            users.add(new User.Builder().name("First").password("passs1").email("first@mail.com").build());
            users.add(new User.Builder().name("Second").password("passs2").email("second@mail.com").build());

            FileHandler.saveToFile(users, file.toString());

            List<String> lines = Files.readAllLines(file);
            assertEquals(3, lines.size());
            assertEquals("Third;passs3;third@mail.com", lines.get(0));
            assertEquals("First;passs1;first@mail.com", lines.get(1));
            assertEquals("Second;passs2;second@mail.com", lines.get(2));

        }

    }

    @Nested
    @DisplayName("Exception handling tests")
    class ExceptionHandlingTests {

        @Test
        @DisplayName("Should throw IOException when filename contains invalid characters")
        void shouldThrowExceptionWhenFilenameInvalid() {

            CustomLinkedList<User> users = new CustomLinkedList<>();

            users.add(new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

            // Используем путь с недопустимыми символами для Windows
            // или путь, который точно вызовет ошибку
            String invalidPath = "\\/:*?\"<>|.txt"; // Недопустимые символы в Windows

            assertThrows(java.nio.file.InvalidPathException.class, () -> FileHandler.saveToFile(users, invalidPath));

        }

        @Test
        @DisplayName("Should throw IOException when path is a directory")
        void shouldThrowExceptionWhenPathIsDirectory() throws IOException {

            CustomLinkedList<User> users = new CustomLinkedList<>();

            users.add(new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

            // Создаем директорию
            Path dir = tempDir.resolve("test_dir");
            Files.createDirectory(dir);

            // Пытаемся сохранить в директорию (а не в файл)
            assertThrows(IOException.class, () -> FileHandler.saveToFile(users, dir.toString()));

        }

        @Test
        @DisplayName("Should throw IOException when filename is null")
        void shouldThrowExceptionWhenFilenameIsNull() {

            CustomLinkedList<User> users = new CustomLinkedList<>();

            users.add(new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

            assertThrows(NullPointerException.class, () -> FileHandler.saveToFile(users, null));

        }

        @Test
        @DisplayName("Should throw IOException when users is null")
        void shouldThrowExceptionWhenUsersIsNull() {

            assertThrows(NullPointerException.class, () -> FileHandler.saveToFile(null, "users.txt"));

        }

    }

}