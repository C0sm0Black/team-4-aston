package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    // Константы для меню
    private static final int MANUAL_INPUT = 1;
    private static final int RANDOM_INPUT = 2;
    private static final int FILE_INPUT = 3;
    private static final int SELECT_STRATEGY = 4;
    private static final int EXECUTE_SORT = 5;
    private static final int SHOW_USERS = 6;
    private static final int SAVE_TO_FILE = 7;
    private static final int EXIT = 8;

    // Состояние приложения
    // Реализовать классы Menu, DataInputHandler, SortContext !!!
    private List<User> users;
    private SortContext sortContext;
    private DataInputHandler dataInputHandler;
    private Menu menu;
    private Scanner scanner;
    private boolean isRunning;
    private int arraySize;
    private boolean isDataLoaded;

    public Application() {

        this.users = new ArrayList<>();
        this.sortContext = new SortContext();
        this.dataInputHandler = new DataInputHandler();
        this.menu = new Menu();
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        this.arraySize = 0;
        this.isDataLoaded = false;

    }

    /**
     * Запуск приложения
     */
    public void run() {

        System.out.println("🚀 Запуск приложения \"Сортировка пользователей\"");
        System.out.println("=".repeat(60));

        while (isRunning) {

            try {

                menu.showMainMenu(); // Реализовать этот метод для меню
                int choice = getUserChoice();
                handleMenuChoice(choice);

            } catch (Exception e) {

                System.err.println("❌ Ошибка: " + e.getMessage());
                System.out.println("Нажмите Enter для продолжения...");
                scanner.nextLine();

            }
        }

        System.out.println("👋 Программа завершена. До свидания!");
        scanner.close();

    }

    /**
     * Получение выбора пользователя с валидацией
     */
    private int getUserChoice() {

        while (true) {

            try {

                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= EXIT) {
                    return choice;
                }

                System.out.print("❌ Введите число от 1 до " + EXIT + ": ");

            } catch (NumberFormatException e) {
                System.out.print("❌ Ошибка: введите число: ");
            }

        }

    }

    /**
     * Обработка выбора меню
     */
    private void handleMenuChoice(int choice) {

        switch (choice) {

            case MANUAL_INPUT:

                handleManualInput();
                break;

            case RANDOM_INPUT:

                handleRandomInput();
                break;

            case FILE_INPUT:

                handleFileInput();
                break;

            case SELECT_STRATEGY:

                handleSelectStrategy();
                break;

            case EXECUTE_SORT:

                handleExecuteSort();
                break;

            case SHOW_USERS:

                handleShowUsers();
                break;

            case SAVE_TO_FILE:

                handleSaveToFile();
                break;

            case EXIT:

                handleExit();
                break;

            default:

                System.out.println("❌ Неизвестная команда");

        }

    }

    /**
     * Обработка ручного ввода
     */
    private void handleManualInput() {

        System.out.println("\n📝 РУЧНОЙ ВВОД ПОЛЬЗОВАТЕЛЕЙ");
        System.out.println("-".repeat(40));

        arraySize = dataInputHandler.getArraySize(scanner);
        if (arraySize <= 0) return;

        users = dataInputHandler.manualInput(scanner, arraySize);
        isDataLoaded = true;

        System.out.println("✅ Загружено " + users.size() + " пользователей");
        UserTablePrinter.printUsers(users);
        waitForEnter();

    }

    /**
     * Обработка случайного ввода
     */
    private void handleRandomInput() {

        System.out.println("\n🎲 СЛУЧАЙНАЯ ГЕНЕРАЦИЯ ПОЛЬЗОВАТЕЛЕЙ");
        System.out.println("-".repeat(40));

        arraySize = dataInputHandler.getArraySize(scanner);
        if (arraySize <= 0) return;

        users = dataInputHandler.generateRandomUsers(arraySize);
        isDataLoaded = true;

        System.out.println("✅ Сгенерировано " + users.size() + " пользователей");
        UserTablePrinter.printUsers(users);
        waitForEnter();

    }

    /**
     * Обработка ввода из файла
     */
    private void handleFileInput() {
        System.out.println("\n📂 ЗАГРУЗКА ИЗ ФАЙЛА");
        System.out.println("-".repeat(40));

        System.out.print("Введите путь к файлу (например, data/users.txt): ");
        String filename = scanner.nextLine().trim();

        try {

            users = dataInputHandler.readUsersFromFile(filename);
            isDataLoaded = true;
            arraySize = users.size();

            System.out.println("✅ Загружено " + users.size() + " пользователей из файла");
            UserTablePrinter.printUsers(users);

        } catch (Exception e) {

            System.err.println("❌ Ошибка загрузки файла: " + e.getMessage());
            System.out.println("Убедитесь, что файл существует и имеет правильный формат:");
            System.out.println("  имя,пароль,email");
            System.out.println("  Алексей Смирнов,securePass123,alexey@mail.ru");

        }

        waitForEnter();

    }

    /**
     * Обработка выбора стратегии сортировки
     */
    private void handleSelectStrategy() {
        System.out.println("\n🎯 ВЫБОР СТРАТЕГИИ СОРТИРОВКИ");
        System.out.println("-".repeat(40));

        if (!isDataLoaded) {
            System.out.println("⚠️ Сначала загрузите данные пользователей!");
            waitForEnter();
            return;
        }

        menu.showSortStrategyMenu();
        int choice = getUserChoice();

        SortStrategy strategy = null;
        String strategyName = "";

        switch (choice) {
            case 1:
                strategy = new SortByNameStrategy();
                strategyName = "по имени (алфавит)";
                break;
            case 2:
                strategy = new SortByPasswordStrategy();
                strategyName = "по паролю (длина + лексикографически)";
                break;
            case 3:
                strategy = new SortByEmailStrategy();
                strategyName = "по почте (алфавит)";
                break;
            default:
                System.out.println("❌ Неверный выбор стратегии");
                waitForEnter();
                return;
        }

        sortContext.setStrategy(strategy);
        System.out.println("✅ Выбрана стратегия сортировки: " + strategyName);
        waitForEnter();
    }

    /**
     * Обработка выполнения сортировки
     */
    private void handleExecuteSort() {

        System.out.println("\n🔄 ВЫПОЛНЕНИЕ СОРТИРОВКИ");
        System.out.println("-".repeat(40));

        if (!isDataLoaded) {
            System.out.println("⚠️ Сначала загрузите данные пользователей!");
            waitForEnter();
            return;
        }

        if (users.isEmpty()) {
            System.out.println("⚠️ Список пользователей пуст!");
            waitForEnter();
            return;
        }

        try {

            System.out.println("⏳ Сортировка...");
            sortContext.executeSort(users);
            System.out.println("✅ Сортировка выполнена успешно!");
            UserTablePrinter.printUsers(users);

        } catch (IllegalStateException e) {

            System.err.println("❌ " + e.getMessage());
            System.out.println("Пожалуйста, выберите стратегию сортировки (пункт 4)");

        }

        waitForEnter();

    }

    /**
     * Обработка отображения пользователей
     */
    private void handleShowUsers() {

        System.out.println("\n👥 СПИСОК ПОЛЬЗОВАТЕЛЕЙ");
        System.out.println("-".repeat(40));

        if (!isDataLoaded) {

            System.out.println("⚠️ Сначала загрузите данные пользователей!");
            waitForEnter();
            return;

        }

        UserTablePrinter.printUsers(users);

        // Дополнительная статистика
        if (!users.isEmpty()) {

            System.out.println("\n📊 Статистика:");
            System.out.println("  • Всего: " + users.size());

            System.out.println("  • Имена: " + users.stream()
                    .map(User::getName)
                    .distinct()
                    .count() + " уникальных");

            System.out.println("  • Почтовые домены: " + users.stream()
                    .map(u -> u.getEmail().split("@")[1])
                    .distinct()
                    .count() + " уникальных");

        }

        waitForEnter();

    }

    /**
     * Обработка сохранения в файл
     */
    private void handleSaveToFile() {

        System.out.println("\n💾 СОХРАНЕНИЕ В ФАЙЛ");
        System.out.println("-".repeat(40));

        if (!isDataLoaded) {
            System.out.println("⚠️ Сначала загрузите данные пользователей!");
            waitForEnter();
            return;
        }

        if (users.isEmpty()) {
            System.out.println("⚠️ Список пользователей пуст!");
            waitForEnter();
            return;
        }

        System.out.print("Введите имя файла для сохранения (например, output/users.txt): ");
        String filename = scanner.nextLine().trim();

        try {

            FileHandler.saveToFile(users, filename); // Реализовать класс и данный метод для сохранения в файл
            System.out.println("✅ Данные сохранены в: " + filename);

        } catch (Exception e) {
            System.err.println("❌ Ошибка сохранения: " + e.getMessage());
        }

        waitForEnter();

    }

    /**
     * Обработка выхода из программы
     */
    private void handleExit() {

        System.out.println("\n🚪 ВЫХОД ИЗ ПРОГРАММЫ");
        System.out.println("-".repeat(40));

        if (isDataLoaded && !users.isEmpty()) {

            System.out.print("Сохранить данные перед выходом? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("y") || answer.equals("yes")) {
                System.out.print("Введите имя файла: ");
                String filename = scanner.nextLine().trim();

                try {
                    FileHandler.saveToFile(users, filename); // Реализовать класс и данный метод для сохранения в файл
                    System.out.println("✅ Данные сохранены");

                } catch (Exception e) {
                    System.err.println("❌ Ошибка сохранения: " + e.getMessage());
                }

            }

        }

        System.out.print("Вы уверены, что хотите выйти? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            isRunning = false;
        } else {
            System.out.println("Продолжаем работу...");
        }

    }

    /**
     * Ожидание нажатия Enter
     */
    private void waitForEnter() {

        System.out.println("\nНажмите Enter для продолжения...");
        scanner.nextLine();

    }

    /**
     * Точка входа в программу
     */
    public static void main(String[] args) {

        Application app = new Application();
        app.run();

    }

}
