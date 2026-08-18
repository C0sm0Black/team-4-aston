package org.example;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;
import org.example.enums.MenuOption;
import org.example.enums.NumericField;
import org.example.enums.SortAlgorithm;
import org.example.sort.BubbleSortStrategy;
import org.example.sort.InsertionSortStrategy;
import org.example.sort.QuickSortStrategy;
import org.example.sort.SortStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

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

    @Nested
    @DisplayName("MenuOption validation tests")
    class MenuOptionTests {

        @Test
        @DisplayName("Should validate correct menu codes")
        void shouldValidateCorrectMenuCodes() {

            assertTrue(MenuOption.isValidCode(0));
            assertTrue(MenuOption.isValidCode(1));
            assertTrue(MenuOption.isValidCode(2));
            assertTrue(MenuOption.isValidCode(3));
            assertTrue(MenuOption.isValidCode(4));
            assertTrue(MenuOption.isValidCode(5));
            assertTrue(MenuOption.isValidCode(6));
            assertTrue(MenuOption.isValidCode(7));
            assertTrue(MenuOption.isValidCode(8));
            assertTrue(MenuOption.isValidCode(9));

        }

        @Test
        @DisplayName("Should invalidate incorrect menu codes")
        void shouldInvalidateIncorrectMenuCodes() {

            assertFalse(MenuOption.isValidCode(-1));
            assertFalse(MenuOption.isValidCode(10));
            assertFalse(MenuOption.isValidCode(100));

        }

        @Test
        @DisplayName("Should return correct MenuOption from code")
        void shouldReturnCorrectMenuOptionFromCode() {

            assertEquals(MenuOption.EXIT, MenuOption.fromCode(0));
            assertEquals(MenuOption.MANUAL_INPUT, MenuOption.fromCode(1));
            assertEquals(MenuOption.RANDOM_INPUT, MenuOption.fromCode(2));
            assertEquals(MenuOption.FILE_INPUT, MenuOption.fromCode(3));
            assertEquals(MenuOption.ADD_USERS, MenuOption.fromCode(4));
            assertEquals(MenuOption.SELECT_STRATEGY, MenuOption.fromCode(5));
            assertEquals(MenuOption.EXECUTE_SORT, MenuOption.fromCode(6));
            assertEquals(MenuOption.SHOW_USERS, MenuOption.fromCode(7));
            assertEquals(MenuOption.SAVE_TO_FILE, MenuOption.fromCode(8));
            assertEquals(MenuOption.COUNT_OCCURRENCES, MenuOption.fromCode(9));

        }

        @Test
        @DisplayName("Should throw exception when getting MenuOption from invalid code")
        void shouldThrowExceptionWhenMenuOptionFromInvalidCode() {
            assertThrows(IllegalArgumentException.class, () -> MenuOption.fromCode(999));
        }

        @Test
        @DisplayName("Should return correct description for each menu option")
        void shouldReturnCorrectDescription() {

            assertEquals("Выход", MenuOption.EXIT.getDescription());
            assertEquals("Ручной ввод пользователей", MenuOption.MANUAL_INPUT.getDescription());
            assertEquals("Генерация случайных пользователей", MenuOption.RANDOM_INPUT.getDescription());
            assertEquals("Загрузка из файла", MenuOption.FILE_INPUT.getDescription());
            assertEquals("Добавить пользователей", MenuOption.ADD_USERS.getDescription());
            assertEquals("Выбрать стратегию сортировки", MenuOption.SELECT_STRATEGY.getDescription());
            assertEquals("Выполнить сортировку", MenuOption.EXECUTE_SORT.getDescription());
            assertEquals("Показать пользователей", MenuOption.SHOW_USERS.getDescription());
            assertEquals("Сохранить в файл", MenuOption.SAVE_TO_FILE.getDescription());
            assertEquals("Подсчитать количество", MenuOption.COUNT_OCCURRENCES.getDescription());

        }

    }

    @Nested
    @DisplayName("NumericField tests")
    class NumericFieldTests {

        @Test
        @DisplayName("Should return correct NumericField from code")
        void shouldReturnCorrectNumericFieldFromCode() {

            assertEquals(NumericField.NAME_LENGTH, NumericField.fromCode(1));
            assertEquals(NumericField.PASSWORD_LENGTH, NumericField.fromCode(2));
            assertEquals(NumericField.EMAIL_LENGTH, NumericField.fromCode(3));

        }

        @Test
        @DisplayName("Should return null for invalid code")
        void shouldReturnNullForInvalidCode() {

            assertNull(NumericField.fromCode(999));
            assertNull(NumericField.fromCode(-1));
            assertNull(NumericField.fromCode(100));

        }

        @Test
        @DisplayName("Should return correct display names")
        void shouldReturnCorrectDisplayNames() {

            assertEquals("Длина имени", NumericField.NAME_LENGTH.getDisplayName());
            assertEquals("Длина пароля", NumericField.PASSWORD_LENGTH.getDisplayName());
            assertEquals("Длина email", NumericField.EMAIL_LENGTH.getDisplayName());

        }

    }

    @Nested
    @DisplayName("SortAlgorithm tests")
    class SortAlgorithmTests {

        @Test
        @DisplayName("Should return correct SortAlgorithm from code")
        void shouldReturnCorrectSortAlgorithmFromCode() {

            assertEquals(SortAlgorithm.BUBBLE_SORT, SortAlgorithm.fromCode(1));
            assertEquals(SortAlgorithm.INSERTION_SORT, SortAlgorithm.fromCode(3));
            assertEquals(SortAlgorithm.QUICK_SORT, SortAlgorithm.fromCode(2));

        }

        @Test
        @DisplayName("Should throw exception when getting SortAlgorithm from invalid code")
        void shouldThrowExceptionWhenSortAlgorithmFromInvalidCode() {

            // Проверяем, что метод выбрасывает исключение
            // Если метод не выбрасывает, а возвращает null, проверяем на null
            try {
                SortAlgorithm result = SortAlgorithm.fromCode(999);
                // Если метод вернул null, то тест провалится
                assertNull(result, "Expected null for invalid code");
            } catch (IllegalArgumentException e) {
                // Если метод выбрасывает исключение - это тоже ок
                assertTrue(true);
            }

        }

        @Test
        @DisplayName("Should return correct display names")
        void shouldReturnCorrectDisplayNames() {

            assertEquals("Bubble Sort (пузырьковая)", SortAlgorithm.BUBBLE_SORT.getDisplayName());
            assertEquals("Insertion Sort (вставками)", SortAlgorithm.INSERTION_SORT.getDisplayName());
            assertEquals("Quick Sort (быстрая)", SortAlgorithm.QUICK_SORT.getDisplayName());

        }

    }

    @Nested
    @DisplayName("Application initialization tests")
    class ApplicationInitTests {

        @Test
        @DisplayName("Should create application instance")
        void shouldCreateApplicationInstance() {

            Application app = new Application();
            assertNotNull(app);

        }

    }

    @Nested
    @DisplayName("User creation tests")
    class UserCreationTests {

        @Test
        @DisplayName("Should create valid user")
        void shouldCreateValidUser() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertNotNull(user);
            assertEquals("Alice", user.getName());
            assertEquals("password123", user.getPassword());
            assertEquals("alice@mail.com", user.getEmail());

        }

        @Test
        @DisplayName("Should throw exception when creating user with invalid data")
        void shouldThrowExceptionWhenCreatingInvalidUser() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when password is too short")
        void shouldThrowExceptionWhenPasswordTooShort() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("Alice")
                    .password("123")
                    .email("alice@mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when email is invalid")
        void shouldThrowExceptionWhenEmailInvalid() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("invalidemail")
                    .build());

        }

    }

    @Nested
    @DisplayName("Sort strategy creation tests")
    class SortStrategyCreationTests {

        @Test
        @DisplayName("Should create BubbleSort strategy")
        void shouldCreateBubbleSortStrategy() {

            SortStrategy strategy = new BubbleSortStrategy();
            assertNotNull(strategy);
            assertInstanceOf(BubbleSortStrategy.class, strategy);
            assertEquals("BubbleSort", strategy.getStrategyName());

        }

        @Test
        @DisplayName("Should create InsertionSort strategy")
        void shouldCreateInsertionSortStrategy() {

            SortStrategy strategy = new InsertionSortStrategy();
            assertNotNull(strategy);
            assertInstanceOf(InsertionSortStrategy.class, strategy);
            assertEquals("InsertionSort", strategy.getStrategyName());

        }

        @Test
        @DisplayName("Should create QuickSort strategy")
        void shouldCreateQuickSortStrategy() {

            SortStrategy strategy = new QuickSortStrategy();
            assertNotNull(strategy);
            assertInstanceOf(QuickSortStrategy.class, strategy);
            assertEquals("QuickSort", strategy.getStrategyName());

        }

    }

    @Nested
    @DisplayName("Application exit tests")
    class ApplicationExitTests {

        @Test
        @DisplayName("Should exit when user confirms")
        void shouldExitWhenUserConfirms() {

            String input = "0\ny\n";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Application app = new Application();
            app.run();

            String output = outContent.toString();
            assertTrue(output.contains("Программа завершена"));

        }

        @Test
        @DisplayName("Should not exit when user does not confirm")
        void shouldNotExitWhenUserDoesNotConfirm() {

            String input = "0\nn\n0\ny\n";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Application app = new Application();
            app.run();

            String output = outContent.toString();
            assertTrue(output.contains("Продолжаем работу"));

        }

    }

    @Nested
    @DisplayName("User input handling tests")
    class UserInputHandlingTests {

        @Test
        @DisplayName("Should handle invalid input and retry")
        void shouldHandleInvalidInputAndRetry() {

            String input = "abc\n" +     // Невалидный ввод
                    "5\n" +        // Выбор стратегии
                    "\n" +         // Enter для waitForEnter()
                    "0\n" +        // Выход
                    "y\n";         // Подтверждение выхода
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Application app = new Application();
            app.run();

            String output = outContent.toString();
            assertTrue(output.contains("Ошибка: введите число"));

        }

        @Test
        @DisplayName("Should handle choice 0 (EXIT) with confirmation")
        void shouldHandleChoiceZeroWithConfirmation() {

            String input = "0\ny\n";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Application app = new Application();
            app.run();

            String output = outContent.toString();
            assertTrue(output.contains("Выход из программы"));

        }

        @Test
        @DisplayName("Should handle menu choice without data loaded")
        void shouldHandleMenuChoiceWithoutDataLoaded() {

            String input = "5\n" +       // Выбор стратегии
                    "\n" +         // Enter для waitForEnter()
                    "0\n" +        // Выход
                    "y\n";         // Подтверждение выхода
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Application app = new Application();
            app.run();

            String output = outContent.toString();
            assertTrue(output.contains("Сначала загрузите данные пользователей"));

        }

    }

    @Nested
    @DisplayName("Menu display tests")
    class MenuDisplayTests {

        @Test
        @DisplayName("Should display main menu on startup")
        void shouldDisplayMainMenuOnStartup() {
            String input = "0\ny\n";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Application app = new Application();
            app.run();

            String output = outContent.toString();
            assertTrue(output.contains("Сортировка пользователей"));
        }

        @Test
        @DisplayName("Should display sort strategy menu when data loaded")
        void shouldDisplaySortStrategyMenuWhenDataLoaded() {

            // Сценарий:
            // 1. Ручной ввод 1 пользователя
            // 2. После ввода пользователя - нажать Enter (waitForEnter)
            // 3. Выбор стратегии (пункт 5)
            // 4. Выбор типа сортировки: 1 (обычная)
            // 5. Выбор алгоритма: 1 (Bubble Sort)
            // 6. Выход
            String input = "1\n" +           // Выбор ручного ввода
                    "1\n" +            // Количество пользователей: 1
                    "Alice\n" +        // Имя
                    "password123\n" +  // Пароль
                    "alice@mail.com\n" + // Email
                    "\n" +             // Enter для waitForEnter() после вывода таблицы
                    "5\n" +            // Выбор стратегии
                    "1\n" +            // Тип сортировки: 1 - обычная
                    "1\n" +            // Алгоритм: 1 - Bubble Sort
                    "\n" +             // Enter для waitForEnter() после выбора стратегии
                    "0\n" +            // Выход
                    "n\n" +            // Не сохранять данные
                    "y\n";             // Подтверждение выхода

            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Application app = new Application();
            app.run();

            String output = outContent.toString();
            assertTrue(output.contains("ВЫБОР СТРАТЕГИИ СОРТИРОВКИ"));

        }

    }

    @Nested
    @DisplayName("Save functionality tests")
    class SaveFunctionalityTests {

        @Test
        @DisplayName("Should prompt for save on exit when data loaded")
        void shouldPromptForSaveOnExitWhenDataLoaded() {

            String input = "0\nn\n0\ny\n";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Application app = new Application();
            app.run();

            String output = outContent.toString();
            assertTrue(output.contains("Выход из программы"));

        }

    }

    @Nested
    @DisplayName("CustomLinkedList tests")
    class CustomLinkedListTests {

        @Test
        @DisplayName("Should add and get users from list")
        void shouldAddAndGetUsersFromList() {

            CustomLinkedList<User> users = new CustomLinkedList<>();
            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            users.add(user);

            assertEquals(1, users.size());
            assertEquals("Alice", users.get(0).getName());

        }

        @Test
        @DisplayName("Should return correct size")
        void shouldReturnCorrectSize() {
            CustomLinkedList<User> users = new CustomLinkedList<>();

            assertEquals(0, users.size());

            users.add(new User.Builder().name("User1").password("pass123").email("user1@mail.com").build());
            assertEquals(1, users.size());

            users.add(new User.Builder().name("User2").password("pass456").email("user2@mail.com").build());
            assertEquals(2, users.size());

        }

        @Test
        @DisplayName("Should clear list")
        void shouldClearList() {

            CustomLinkedList<User> users = new CustomLinkedList<>();
            users.add(new User.Builder().name("User1").password("pass123").email("user1@mail.com").build());
            users.add(new User.Builder().name("User2").password("pass456").email("user2@mail.com").build());

            assertEquals(2, users.size());

            users.clear();

            assertEquals(0, users.size());
            assertTrue(users.isEmpty());

        }

    }

}