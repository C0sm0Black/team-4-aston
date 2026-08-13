package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Developer3Tests {

    public static void main(String[] args) {
        System.out.println("🚀 ЗАПУСК ЮНИТ-ТЕСТОВ DEVELOPER 3...\n");

        testUserValidator_ValidData();
        testUserValidator_InvalidEmail();
        testUserValidator_ShortPassword();
        testDataInputHandler_GenerateRandom();
        testDataInputHandler_ReadFromFile();

        testFileHandler_AppendMode();

        System.out.println("\n✅ ВСЕ ТЕСТЫ УСПЕШНО ПРОЙДЕНЫ!");
    }

    private static void testUserValidator_ValidData() {
        try {
            UserValidator.validateAll("Газкулл Трака", "Waaagh123", "boss@ork.com");
            System.out.println("[OK] testUserValidator_ValidData");
        } catch (Exception e) {
            System.err.println("[FAIL] testUserValidator_ValidData: Выброшено исключение там, где данные валидны: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void testUserValidator_InvalidEmail() {
        try {
            UserValidator.validateAll("Магнус", "Red123456", "magnus-no-domain");
            System.err.println("[FAIL] testUserValidator_InvalidEmail: Ошибка! Валидатор пропустил неверный email");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] testUserValidator_InvalidEmail");
        }
    }

    private static void testUserValidator_ShortPassword() {
        try {
            UserValidator.validateAll("Данмер", "123", "dunmer@mail.ru");
            System.err.println("[FAIL] testUserValidator_ShortPassword: Ошибка! Валидатор пропустил короткий пароль");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] testUserValidator_ShortPassword");
        }
    }

    private static void testDataInputHandler_GenerateRandom() {
        DataInputHandler handler = new DataInputHandler();
        List<User> randomUsers = handler.generateRandomUsers(5);

        if (randomUsers.size() == 5 && randomUsers.get(0).getName() != null) {
            System.out.println("[OK] testDataInputHandler_GenerateRandom");
        } else {
            System.err.println("[FAIL] testDataInputHandler_GenerateRandom: Генерация вернула неверный результат");
            System.exit(1);
        }
    }

    private static void testDataInputHandler_ReadFromFile() {
        DataInputHandler handler = new DataInputHandler();
        String testFileName = "test_users_input.txt";

        try {
            Files.writeString(Path.of(testFileName), "Геннадий,Legendary123,odi@mail.ru\nГелия,ShadarKai777,fragile@mtg.com");

            List<User> users = handler.readUsersFromFile(testFileName);
            if (users.size() == 2 && users.get(0).getName().equals("Геннадий")) {
                System.out.println("[OK] testDataInputHandler_ReadFromFile");
            } else {
                System.err.println("[FAIL] testDataInputHandler_ReadFromFile: Ошибка чтения и парсинга файла");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("[FAIL] testDataInputHandler_ReadFromFile: Исключение ввода-вывода " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                Files.deleteIfExists(Path.of(testFileName));
            } catch (IOException ignored) {}
        }
    }

    public static void testFileHandler_AppendMode() {
        String testFileName = "test_append_log.txt";
        try {
            Files.deleteIfExists(Path.of(testFileName));

            List<User> batch1 = List.of(new User.Builder().name("Геннадий").password("Pass123").email("odi@mail.ru").build());
            FileHandler.saveToFile(batch1, testFileName);

            List<User> batch2 = List.of(new User.Builder().name("Гелия").password("Elf777").email("fragile@shadow.com").build());
            FileHandler.saveToFile(batch2, testFileName);

            List<String> resultLines = Files.readAllLines(Path.of(testFileName));

            if (resultLines.size() == 2 && resultLines.get(1).startsWith("Гелия")) {
                System.out.println("[OK] testFileHandler_AppendMode");
            } else {
                System.err.println("[FAIL] testFileHandler_AppendMode: Режим APPEND не сработал. Строки перезаписались.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("[FAIL] testFileHandler_AppendMode: Исключение при записи " + e.getMessage());
            System.exit(1);
        } finally {
            try { Files.deleteIfExists(Path.of(testFileName)); } catch (IOException ignored) {}
        }
    }
}