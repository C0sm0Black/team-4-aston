package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final int MANUAL_INPUT = 1;
    private static final int RANDOM_INPUT = 2;
    private static final int FILE_INPUT = 3;
    private static final int SELECT_STRATEGY = 4;
    private static final int EXECUTE_SORT = 5;
    private static final int SHOW_USERS = 6;
    private static final int SAVE_TO_FILE = 7;
    private static final int EXIT = 8;

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

    public void run() {
        System.out.println("🚀 Запуск приложения \"Сортировка пользователей\"");
        System.out.println("=".repeat(60));

        while (isRunning) {
            try {
                menu.showMainMenu();
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

    private int readIntInRange(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("❌ Введите число от %d до %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("❌ Ошибка: введите целое число: ");
            }
        }
    }

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

    private void handleSelectStrategy() {
        System.out.println("\n🎯 ВЫБОР СТРАТЕГИИ СОРТИРОВКИ");
        System.out.println("-".repeat(40));

        if (!isDataLoaded) {
            System.out.println("⚠️ Сначала загрузите данные пользователей!");
            waitForEnter();
            return;
        }

        System.out.println("Доступные алгоритмы:");
        System.out.println("1. Пузырьковая сортировка");
        System.out.println("2. Быстрая сортировка");
        System.out.println("3. Сортировка вставками");
        System.out.println("4. Even-Odd сортировка (по длине пароля)");
        System.out.print("Выберите алгоритм (1-4): ");
        int algoChoice = readIntInRange(1, 4);

        SortField sortField = null;
        if (algoChoice >= 1 && algoChoice <= 3) {
            System.out.println("\nВыберите поле для сортировки:");
            System.out.println("1. Имя");
            System.out.println("2. Пароль");
            System.out.println("3. Email");
            System.out.print("Выберите поле (1-3): ");
            int fieldChoice = readIntInRange(1, 3);
            sortField = switch (fieldChoice) {
                case 1 -> SortField.NAME;
                case 2 -> SortField.PASSWORD;
                case 3 -> SortField.EMAIL;
                default -> throw new IllegalStateException("Unexpected value: " + fieldChoice);
            };
        }

        SortStrategy strategy = null;
        String strategyName = "";

        switch (algoChoice) {
            case 1 -> {
                strategy = new BubbleSortStrategy(sortField);
                strategyName = "Пузырьковая сортировка (" + sortField + ")";
            }
            case 2 -> {
                strategy = new QuickSortStrategy(sortField);
                strategyName = "Быстрая сортировка (" + sortField + ")";
            }
            case 3 -> {
                strategy = new InsertionSortStrategy(sortField);
                strategyName = "Сортировка вставками (" + sortField + ")";
            }
            case 4 -> {
                strategy = new EvenOddSortStrategy();
                strategyName = "Even-Odd сортировка (по длине пароля)";
            }
            default -> {
                System.out.println("❌ Неверный выбор алгоритма");
                waitForEnter();
                return;
            }
        }

        sortContext.setStrategy(strategy);
        System.out.println("✅ Выбрана стратегия сортировки: " + strategyName);
        waitForEnter();
    }

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

    private void handleShowUsers() {
        System.out.println("\n👥 СПИСОК ПОЛЬЗОВАТЕЛЕЙ");
        System.out.println("-".repeat(40));

        if (!isDataLoaded) {
            System.out.println("⚠️ Сначала загрузите данные пользователей!");
            waitForEnter();
            return;
        }

        UserTablePrinter.printUsers(users);

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
            FileHandler.saveToFile(users, filename);
            System.out.println("✅ Данные сохранены в: " + filename);
        } catch (Exception e) {
            System.err.println("❌ Ошибка сохранения: " + e.getMessage());
        }

        waitForEnter();
    }

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
                    FileHandler.saveToFile(users, filename);
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

    private void waitForEnter() {
        System.out.println("\nНажмите Enter для продолжения...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}