package org.example.menu;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class UserTablePrinterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should print users table with correct format")
    void shouldPrintUsersTableWithCorrectFormat() {

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

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        // Проверяем наличие данных пользователей
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("alice@mail.com"));
        assertTrue(output.contains("Bob"));
        assertTrue(output.contains("bob@mail.com"));

        // Проверяем, что пароль скрыт звездочками (8 штук)
        assertTrue(output.contains("********"));
        assertFalse(output.contains("password123"));
        assertFalse(output.contains("pass4567"));

        // Проверяем наличие разделителей
        assertTrue(output.contains("+-----+-"));

        // Проверяем индексы
        assertTrue(output.contains("1"));
        assertTrue(output.contains("2"));

    }

    @Test
    @DisplayName("Should print table with single user")
    void shouldPrintTableWithSingleUser() {

        CustomLinkedList<User> users = new CustomLinkedList<>();

        users.add(new User.Builder()
                .name("Alice")
                .password("password123")
                .email("alice@mail.com")
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("alice@mail.com"));
        assertTrue(output.contains("1"));
        assertTrue(output.contains("********"));
        assertFalse(output.contains("2"));

    }

    @Test
    @DisplayName("Should print empty table for empty list")
    void shouldPrintEmptyTableForEmptyList() {

        CustomLinkedList<User> users = new CustomLinkedList<>();

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        // Проверяем, что вывод не пустой
        assertNotNull(output);

        // Проверяем, что есть разделители
        assertTrue(output.contains("+-----+-"));

    }

    @Test
    @DisplayName("Should hide passwords with 8 asterisks")
    void shouldHidePasswordsWith8Asterisks() {

        CustomLinkedList<User> users = new CustomLinkedList<>();

        users.add(new User.Builder()
                .name("Alice")
                .password("secret123")
                .email("alice@mail.com")
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        assertTrue(output.contains("********"));
        assertFalse(output.contains("secret123"));

    }

    @Test
    @DisplayName("Should handle users with long names")
    void shouldHandleUsersWithLongNames() {

        CustomLinkedList<User> users = new CustomLinkedList<>();
        String longName = "VeryLongNameThatIsVeryVeryLong";

        users.add(new User.Builder()
                .name(longName)
                .password("password123")
                .email("alice@mail.com")
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        assertTrue(output.contains(longName));
        assertTrue(output.contains("alice@mail.com"));

    }

    @Test
    @DisplayName("Should handle users with long emails")
    void shouldHandleUsersWithLongEmails() {

        CustomLinkedList<User> users = new CustomLinkedList<>();
        String longEmail = "verylongemailaddress@verylongdomain.com";

        users.add(new User.Builder()
                .name("Alice")
                .password("password123")
                .email(longEmail)
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        assertTrue(output.contains("Alice"));
        assertTrue(output.contains(longEmail));

    }

    @Test
    @DisplayName("Should handle users with special characters in name")
    void shouldHandleUsersWithSpecialCharactersInName() {

        CustomLinkedList<User> users = new CustomLinkedList<>();

        users.add(new User.Builder()
                .name("Alice-Smith")
                .password("password123")
                .email("alice@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Bob_John")
                .password("pass4567")
                .email("bob@mail.com")
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        assertTrue(output.contains("Alice-Smith"));
        assertTrue(output.contains("Bob_John"));

    }

    @Test
    @DisplayName("Should handle users with special characters in email")
    void shouldHandleUsersWithSpecialCharactersInEmail() {

        CustomLinkedList<User> users = new CustomLinkedList<>();

        users.add(new User.Builder()
                .name("Alice")
                .password("password123")
                .email("alice+test@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Bob")
                .password("pass4567")
                .email("bob.john@mail.com")
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        assertTrue(output.contains("alice+test@mail.com"));
        assertTrue(output.contains("bob.john@mail.com"));

    }


    @Test
    @DisplayName("Should handle users with names containing spaces")
    void shouldHandleUsersWithNamesContainingSpaces() {

        CustomLinkedList<User> users = new CustomLinkedList<>();

        users.add(new User.Builder()
                .name("Alice Wonderland")
                .password("password123")
                .email("alice@mail.com")
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        assertTrue(output.contains("Alice Wonderland"));
    }

    @Test
    @DisplayName("Should handle large number of users")
    void shouldHandleLargeNumberOfUsers() {

        CustomLinkedList<User> users = new CustomLinkedList<>();
        for (int i = 0; i < 100; i++) {

            users.add(new User.Builder()
                    .name("User" + i)
                    .password("passsss" + i)
                    .email("user" + i + "@mail.com")
                    .build());

        }

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        assertTrue(output.contains("User0"));
        assertTrue(output.contains("User99"));
        assertTrue(output.contains("1"));
        assertTrue(output.contains("100"));

    }

    @Test
    @DisplayName("Should print with correct table structure")
    void shouldPrintWithCorrectTableStructure() {

        CustomLinkedList<User> users = new CustomLinkedList<>();

        users.add(new User.Builder()
                .name("Alice")
                .password("password123")
                .email("alice@mail.com")
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();
        String[] lines = output.split("\\r?\\n");

        // Проверяем, что есть разделитель перед и после данных
        assertTrue(lines.length >= 3);

        // Проверяем структуру: первая строка - разделитель, последняя - разделитель
        assertTrue(lines[0].startsWith("+"));
        assertTrue(lines[lines.length - 1].startsWith("+"));

    }

    @Test
    @DisplayName("Should print password column with exactly 8 characters width")
    void shouldPrintPasswordColumnWithExactly8CharactersWidth() {

        CustomLinkedList<User> users = new CustomLinkedList<>();

        users.add(new User.Builder()
                .name("Alice")
                .password("password123")
                .email("alice@mail.com")
                .build());

        UserTablePrinter.printUsers(users);

        String output = outContent.toString();

        // Проверяем, что пароль отображается как 8 звездочек
        assertTrue(output.contains("********"));

        // Проверяем, что длина строки с паролем содержит 8 символов
        String[] lines = output.split("\\r?\\n");
        for (String line : lines) {

            if (line.contains("Alice")) {
                // Проверяем, что в строке есть 8 звездочек подряд
                assertTrue(line.matches(".*\\| \\*{8} \\|.*"));
                break;

            }

        }

    }

}