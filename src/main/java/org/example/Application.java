package org.example;

import org.example.collection.CustomLinkedList;
import org.example.comporator.UserComparator;
import org.example.entity.User;
import org.example.enums.NumericField;
import org.example.enums.SortAlgorithm;
import org.example.menu.Menu;
import org.example.menu.MenuFileHandler;
import org.example.menu.UserTablePrinter;
import org.example.sort.BubbleSortStrategy;
import org.example.sort.InsertionSortStrategy;
import org.example.sort.QuickSortStrategy;
import org.example.sort.SortStrategy;
import org.example.sort.factory.EvenOddSortFactory;

import java.util.Scanner;

public class Application {

    // Константы для меню
    private static final int MANUAL_INPUT = 1;
    private static final int RANDOM_INPUT = 2;
    private static final int FILE_INPUT = 3;
    private static final int ADD_USERS = 4;
    private static final int SELECT_STRATEGY = 5;
    private static final int EXECUTE_SORT = 6;
    private static final int SHOW_USERS = 7;
    private static final int SAVE_TO_FILE = 8;
    private static final int COUNT_OCCURRENCES = 9;
    private static final int EXIT = 0;


    // Состояние приложения
    private CustomLinkedList<User> users;

    private SortStrategy sortStrategy;
    private final DataInputHandler dataInputHandler;
    private final Menu menu;
    private final Scanner scanner;
    private boolean isRunning;
    private int arraySize;
    private boolean isDataLoaded;


    public Application() {

        this.users = new CustomLinkedList<>();

        this.sortStrategy = null;
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

    /**
     * Получение выбора пользователя с валидацией
     */
    private int getUserChoice() {

        while (true) {

            try {

                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                if (choice >= 0 && choice <= 9) {
                    return choice;
                }

                System.out.print("❌ Введите число от " + EXIT + " до 9: ");

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

            case MANUAL_INPUT -> handleManualInput();
            case RANDOM_INPUT -> handleRandomInput();
            case FILE_INPUT -> handleFileInput();
            case ADD_USERS -> handleAddUsers();
            case SELECT_STRATEGY -> handleSelectStrategy();
            case EXECUTE_SORT -> handleExecuteSort();
            case SHOW_USERS -> handleShowUsers();
            case SAVE_TO_FILE -> handleSaveToFile();
            case COUNT_OCCURRENCES -> handleCountOccurrences();
            case EXIT -> handleExit();

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
     * Обрабатывает добавление пользователей к существующим.
     * Пользователь выбирает способ добавления и данные добавляются в конец коллекции.
     */
    private void handleAddUsers() {

        System.out.println("➕ Добавить пользователей к существующим");
        System.out.println("-".repeat(40));

        if (!isDataLoaded) {
            System.out.println("⚠️ Сначала загрузите данные пользователей!");
            waitForEnter();
            return;
        }

        System.out.println("\nВыберите способ добавления:");
        System.out.println("1. Добавить вручную");
        System.out.println("2. Добавить случайных");
        System.out.println("3. Добавить из файла");
        System.out.print("Выберите способ: ");

        int choice;

        try {

            choice = Integer.parseInt(scanner.nextLine().trim());

        } catch (NumberFormatException e) {

            System.out.println("❌ Неверный выбор");
            waitForEnter();
            return;

        }

        CustomLinkedList<User> newUsers;
        arraySize = dataInputHandler.getArraySize(scanner);

        switch (choice) {

            case 1 -> {

                System.out.println("\n📝 ДОБАВЛЕНИЕ ВРУЧНУЮ");

                newUsers = dataInputHandler.manualInput(scanner, arraySize);

            }

            case 2 -> {

                System.out.println("\n🎲 ДОБАВЛЕНИЕ СЛУЧАЙНЫХ");

                newUsers = dataInputHandler.generateRandomUsers(arraySize);

            }
            case 3 -> {

                System.out.println("\n📂 ДОБАВЛЕНИЕ ИЗ ФАЙЛА");
                newUsers = dataInputHandler.handleInput(scanner);

            }

            default -> {

                System.out.println("❌ Неверный выбор способа");
                waitForEnter();
                return;

            }

        }

        if (!newUsers.isEmpty()) {

            // Добавляем новых пользователей к существующим
            users.addAll(newUsers);

            System.out.println("\n✅ Добавлено " + newUsers.size() + " пользователей");
            System.out.println("📊 Всего пользователей: " + users.size());
            UserTablePrinter.printUsers(users);

        } else {
            System.out.println("⚠️ Не удалось добавить данные");
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

        System.out.println("\nВыберите тип сортировки:");
        System.out.println("1. Обычная сортировка (по 3 полям)");
        System.out.println("2. Сортировка четных/нечетных значений");
        System.out.print("Выберите тип: ");

        int sortType;

        try {

            sortType = Integer.parseInt(scanner.nextLine().trim());

        } catch (NumberFormatException e) {

            System.out.println("❌ Неверный выбор");
            waitForEnter();
            return;

        }

        if (sortType == 1) {
            handleRegularSortSelection();
        } else if (sortType == 2) {
            handleEvenOddSortSelection();
        } else {
            System.out.println("❌ Неверный выбор типа сортировки");
        }

        waitForEnter();

    }

    /**
     * Обрабатывает выбор обычной стратегии сортировки.
     */
    private void handleRegularSortSelection() {

        printSortMenu();
        SortAlgorithm algorithm = getSortAlgorithm();

        if (algorithm == null) {

            System.out.println("❌ Неверный выбор алгоритма");
            return;

        }

        sortStrategy = createSortStrategy(algorithm);
        System.out.println("✅ Выбрана стратегия: " + algorithm.getDisplayName());
        System.out.println("✅ Сортировка будет по: имя -> email -> пароль ");

    }

    /**
     * Обрабатывает выбор стратегии сортировки четных/нечетных значений.
     */
    private void handleEvenOddSortSelection() {

        printSortMenu();
        SortAlgorithm algorithm = getSortAlgorithm();

        if (algorithm == null) {
            System.out.println("❌ Неверный выбор алгоритма");
            return;
        }

        printNumericFieldMenu();
        NumericField numericField = getNumericField();

        if (numericField == null) {
            System.out.println("❌ Неверный выбор числового поля");
            return;
        }

        sortStrategy = EvenOddSortFactory.createStrategy(algorithm, numericField);
        System.out.println("✅ Выбрана стратегия: " + algorithm.getDisplayName() + " для четных значений " + numericField.getDisplayName());
    }

    /**
     * Получает выбранное числовое поле.
     *
     * @return NumericField - выбранное поле или null
     */
    private NumericField getNumericField() {

        try {

            int choice = Integer.parseInt(scanner.nextLine().trim());
            return NumericField.fromCode(choice);

        } catch (NumberFormatException e) {
            return null;
        }

    }

    /**
     * Выводит меню доступных числовых полей.
     */
    private void printNumericFieldMenu() {

        System.out.println("\nВыберите числовое поле:");

        for (NumericField field : NumericField.values()) {
            System.out.printf("%d. %s%n", field.getCode(), field.getDisplayName());
        }

        System.out.print("Выберите поле: ");

    }

    /**
     * Создает стратегию сортировки на основе выбранного алгоритма.
     *
     * @param algorithm - выбранный алгоритм
     * @return SortStrategy - созданная стратегия
     */
    private SortStrategy createSortStrategy(SortAlgorithm algorithm) {

        return switch (algorithm) {

            case BUBBLE_SORT -> new BubbleSortStrategy();
            case QUICK_SORT -> new QuickSortStrategy();
            case INSERTION_SORT -> new InsertionSortStrategy();

        };

    }

    /**
     * Выводит меню доступных алгоритмов сортировки.
     */
    private void printSortMenu() {

        System.out.println("\nДоступные алгоритмы сортировки:");

        for (SortAlgorithm algorithm : SortAlgorithm.values()) {
            System.out.printf("%d. %s%n", algorithm.getCode(), algorithm.getDisplayName());
        }

        System.out.print("Выберите алгоритм: ");

    }

    /**
     * Получает выбранный алгоритм сортировки.
     *
     * @return SortAlgorithm - выбранный алгоритм или null
     */
    private SortAlgorithm getSortAlgorithm() {

        try {

            int choice = Integer.parseInt(scanner.nextLine().trim());
            return SortAlgorithm.fromCode(choice);

        } catch (NumberFormatException e) {
            return null;
        }

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

            UserComparator comparator = new UserComparator();

            System.out.println("⏳ Сортировка...");
            sortStrategy.sort(users, comparator);

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

            System.out.println("  • Имена: " + users.stream().map(User::getName).distinct().count() + " уникальных");

            System.out.println("  • Почтовые домены: " + users.stream().map(u -> u.getEmail().split("@")[1]).distinct().count() + " уникальных");

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

            MenuFileHandler.saveToFile(users, filename);
            System.out.println("✅ Данные сохранены в: " + filename);

        } catch (Exception e) {
            System.err.println("❌ Ошибка сохранения: " + e.getMessage());
        }

        waitForEnter();

    }

    /**
     * Подсчитывает количество вхождений элемента в коллекцию.
     * Пользователь выбирает поле для поиска.
     * Использует многопоточность через Thread.
     */
    private void handleCountOccurrences() {

        System.out.println("\n🔍 ПОДСЧЕТ ВХОЖДЕНИЙ");
        System.out.println("-".repeat(40));

        if (!isDataLoaded || users.isEmpty()) {

            System.out.println("⚠️ Нет данных для поиска!");
            waitForEnter();
            return;

        }

        // Выбор поля для поиска
        System.out.println("\nВыберите поле для поиска:");
        System.out.println("1. По имени");
        System.out.println("2. По паролю");
        System.out.println("3. По email");
        System.out.print("Выберите поле: ");

        int fieldChoice;

        try {

            fieldChoice = Integer.parseInt(scanner.nextLine().trim());

        } catch (NumberFormatException e) {

            System.out.println("❌ Неверный выбор");
            waitForEnter();
            return;

        }

        if (fieldChoice < 1 || fieldChoice > 3) {

            System.out.println("❌ Неверный выбор поля");
            waitForEnter();
            return;

        }

        System.out.print("Введите значение для поиска: ");
        String searchValue = scanner.nextLine().trim();

        // Многопоточный подсчет вхождений
        long count = countOccurrencesMultithreaded(users, fieldChoice, searchValue);

        String fieldName = switch (fieldChoice) {

            case 1 -> "имени";
            case 2 -> "паролю";
            case 3 -> "email";
            default -> throw new IllegalStateException("Unexpected value: " + fieldChoice);

        };

        System.out.println("✅ Найдено пользователей по " + fieldName + " '" + searchValue + "': " + count);

        waitForEnter();

    }

    /**
     * Многопоточный метод подсчета вхождений элемента в коллекцию.
     * Разделяет коллекцию на части и обрабатывает каждую в отдельном потоке.
     *
     * @param users       - коллекция пользователей
     * @param fieldChoice - поле для поиска (1 - имя, 2 - пароль, 3 - email)
     * @param searchValue - значение для поиска
     * @return long - количество вхождений
     */
    private long countOccurrencesMultithreaded(CustomLinkedList<User> users, int fieldChoice, String searchValue) {

        int threadCount = Math.min(Runtime.getRuntime().availableProcessors(), 4);
        int size = users.size();

        // Массив для хранения результатов каждого потока
        long[] results = new long[threadCount];
        Thread[] threads = new Thread[threadCount];

        int chunkSize = (int) Math.ceil((double) size / threadCount);

        // Создаем и запускаем потоки
        for (int i = 0; i < threadCount; i++) {

            final int threadIndex = i;
            final int start = i * chunkSize;
            final int end = Math.min((i + 1) * chunkSize, size);

            if (start < end) {

                threads[i] = new Thread(() -> results[threadIndex] = countInRange(users, start, end, fieldChoice, searchValue), "CounterThread-" + (i + 1));

                threads[i].start();

            }

        }

        // Ожидаем завершения всех потоков
        for (Thread thread : threads) {

            if (thread != null) {

                try {

                    thread.join();

                } catch (InterruptedException e) {

                    System.err.println("❌ Поток прерван: " + e.getMessage());
                    Thread.currentThread().interrupt();

                }

            }

        }

        // Суммируем результаты
        long totalCount = 0;

        for (long result : results) {
            totalCount += result;
        }

        return totalCount;

    }

    /**
     * Подсчитывает вхождения в заданном диапазоне коллекции.
     *
     * @param users       - коллекция пользователей
     * @param start       - начальный индекс
     * @param end         - конечный индекс (исключительно)
     * @param fieldChoice - поле для поиска
     * @param searchValue - значение для поиска
     * @return long - количество вхождений в диапазоне
     */
    private long countInRange(CustomLinkedList<User> users, int start, int end, int fieldChoice, String searchValue) {

        long count = 0;

        for (int i = start; i < end; i++) {

            User user = users.get(i);

            String fieldValue = switch (fieldChoice) {

                case 1 -> user.getName();
                case 2 -> user.getPassword();
                case 3 -> user.getEmail();
                default -> throw new IllegalStateException("Unexpected value: " + fieldChoice);

            };

            if (fieldValue.equals(searchValue)) {
                count++;
            }

        }

        return count;

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

                    MenuFileHandler.saveToFile(users, filename); // Реализовать класс и данный метод для сохранения в файл
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
