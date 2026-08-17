package org.example;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataInputHandler {

    public int getArraySize(Scanner scanner) {

        System.out.print("Введите количество пользователей для генерации/ввода: ");

        while (true) {

            try {

                int size = Integer.parseInt(scanner.nextLine().trim());

                if (size > 0) {
                    return size;
                }

                System.out.print("Размер должен быть больше 0. Повторите ввод: ");

            } catch (NumberFormatException e) {
                System.out.print("Ошибка: введите целое число: ");
            }

        }

    }


    public CustomLinkedList<User> handleInput(Scanner scanner) {

        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine().trim();

        try {

            return readUsersFromFile(filePath);

        } catch (IOException e) {

            System.err.println("❌ Ошибка чтения файла: " + e.getMessage());
            return new CustomLinkedList<>();

        }

    }

    public CustomLinkedList<User> manualInput(Scanner scanner, int size) {

        return IntStream.range(0, size)
                .mapToObj(i -> {

                    System.out.println("\n--- Ввод данных пользователя #" + (i + 1) + " ---");

                    while (true) {

                        try {


                            System.out.print("Имя: ");
                            String name = scanner.nextLine().trim();

                            System.out.print("Пароль (минимум 6 символов): ");
                            String password = scanner.nextLine().trim();

                            System.out.print("Email: ");
                            String email = scanner.nextLine().trim();

                            UserValidator.validateAll(name, password, email);

                            return new User.Builder()
                                    .name(name)
                                    .password(password)
                                    .email(email)
                                    .build();

                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Ошибка ввода: " + e.getMessage() + ". Пожалуйста, повторите ввод для этого пользователя.");
                        }

                    }

                })
                .collect(CustomLinkedList.toCustomLinkedList());

    }

    public CustomLinkedList<User> generateRandomUsers(int size) {

        String[] names = {
                "Андрей Павлович",
                "Иван Семёнович",
                "Цезарь Станиславович",
                "Лилиана Весс",
                "Робаут Жиллиман",
                "Яррик Газкуллович",
                "Игорь Макетович"
        };

        String[] domains = {"inquisition.emp", "mtg.com", "hell.uk", "mail.ru"};

        return IntStream.range(0, size)
                .mapToObj(i -> {

                    String name = names[(int) (Math.random() * names.length)] + " " + i;
                    String password = "Lucky" + (100000 + (int) (Math.random() * 900000));
                    String email = "hero_" + i + "@" + domains[(int) (Math.random() * domains.length)];

                    return new User.Builder()
                            .name(name)
                            .password(password)
                            .email(email)
                            .build();

                })
                .collect(CustomLinkedList.toCustomLinkedList());

    }

    public CustomLinkedList<User> readUsersFromFile(String filename) throws IOException {

        try (Stream<String> lines = Files.lines(Path.of(filename))) {

            return lines
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> line.split(";"))
                    .filter(parts -> {

                        if (parts.length != 3) {

                            System.out.println("⚠️ Пропущена строка (неверный формат): " + String.join(",", parts));
                            return false;

                        }

                        return true;

                    })
                    .map(parts -> {

                        String name = parts[0].trim();
                        String password = parts[1].trim();
                        String email = parts[2].trim();

                        // Валидируем данные из файла
                        UserValidator.validateAll(name, password, email);

                        return new User.Builder()
                                .name(name)
                                .password(password)
                                .email(email)
                                .build();

                    })
                    .collect(CustomLinkedList.toCustomLinkedList());

        }

    }

}