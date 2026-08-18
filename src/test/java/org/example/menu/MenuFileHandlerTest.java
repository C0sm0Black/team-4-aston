package org.example.menu;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuFileHandlerTest {

    @TempDir
    Path tempDir;

    private CustomLinkedList<User> users;
    private String filename;

    @BeforeEach
    void setUp() {

        users = new CustomLinkedList<>();

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

        filename = tempDir.resolve("users.txt").toString();

    }

    @Test
    @DisplayName("Should save users to file successfully")
    void shouldSaveUsersToFileSuccessfully() throws IOException {

        MenuFileHandler.saveToFile(users, filename);

        List<String> lines = Files.readAllLines(Path.of(filename));
        assertEquals(2, lines.size());
        assertEquals("Alice;password123;alice@mail.com", lines.get(0));
        assertEquals("Bob;pass4567;bob@mail.com", lines.get(1));

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when filename is null")
    void shouldThrowExceptionWhenFilenameIsNull() {

        assertThrows(IllegalArgumentException.class, () -> MenuFileHandler.saveToFile(users, null));

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when users is null")
    void shouldThrowExceptionWhenUsersIsNull() {

        assertThrows(IllegalArgumentException.class, () -> MenuFileHandler.saveToFile(null, filename));

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when users is empty")
    void shouldThrowExceptionWhenUsersIsEmpty() {

        CustomLinkedList<User> emptyUsers = new CustomLinkedList<>();

        assertThrows(IllegalArgumentException.class, () -> MenuFileHandler.saveToFile(emptyUsers, filename));

    }


    @Test
    @DisplayName("Should throw IOException when cannot write to file")
    void shouldThrowIOExceptionWhenCannotWriteToFile() throws IOException {

        // Создаем файл и делаем его read-only
        Path readOnlyFile = tempDir.resolve("readonly.txt");
        Files.writeString(readOnlyFile, "test");
        assertTrue(readOnlyFile.toFile().setReadOnly());

        assertThrows(IOException.class, () -> MenuFileHandler.saveToFile(users, readOnlyFile.toString()));

        // Восстанавливаем права
        assertTrue(readOnlyFile.toFile().setWritable(true));

    }

    @Test
    @DisplayName("Should append to existing file")
    void shouldAppendToExistingFile() throws IOException {

        // Первая запись
        MenuFileHandler.saveToFile(users, filename);

        // Вторая коллекция пользователей
        CustomLinkedList<User> moreUsers = new CustomLinkedList<>();
        moreUsers.add(new User.Builder()
                .name("Charlie")
                .password("pass7890")
                .email("charlie@mail.com")
                .build());

        // Вторая запись (добавление)
        MenuFileHandler.saveToFile(moreUsers, filename);

        List<String> lines = Files.readAllLines(Path.of(filename));
        assertEquals(3, lines.size());
        assertEquals("Alice;password123;alice@mail.com", lines.get(0));
        assertEquals("Bob;pass4567;bob@mail.com", lines.get(1));
        assertEquals("Charlie;pass7890;charlie@mail.com", lines.get(2));

    }

    @Test
    @DisplayName("Should create directories if they don't exist")
    void shouldCreateDirectoriesIfNotExist() throws IOException {

        Path nestedFile = tempDir.resolve("subdir").resolve("nested").resolve("users.txt");

        MenuFileHandler.saveToFile(users, nestedFile.toString());

        assertTrue(Files.exists(nestedFile));
        assertTrue(Files.exists(nestedFile.getParent()));

    }

    @Test
    @DisplayName("Should create file if it doesn't exist")
    void shouldCreateFileIfNotExists() throws IOException {

        Path newFile = tempDir.resolve("new_users.txt");

        MenuFileHandler.saveToFile(users, newFile.toString());

        assertTrue(Files.exists(newFile));
        List<String> lines = Files.readAllLines(newFile);
        assertEquals(2, lines.size());

    }

    @Test
    @DisplayName("Should have correct error message for null filename")
    void shouldHaveCorrectErrorMessageForNullFilename() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                MenuFileHandler.saveToFile(users, null));

        assertEquals("Имя файла не может быть пустым!", exception.getMessage());

    }

    @Test
    @DisplayName("Should have correct error message for null users")
    void shouldHaveCorrectErrorMessageForNullUsers() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                MenuFileHandler.saveToFile(null, filename));

        assertEquals("Список пользователей не может быть null!", exception.getMessage());

    }

    @Test
    @DisplayName("Should have correct error message for empty users")
    void shouldHaveCorrectErrorMessageForEmptyUsers() {

        CustomLinkedList<User> emptyUsers = new CustomLinkedList<>();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                MenuFileHandler.saveToFile(emptyUsers, filename));

        assertEquals("Список пользователей пуст!", exception.getMessage());

    }

}