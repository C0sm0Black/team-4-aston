package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortStrategiesTest {

    public static void main(String[] args) {
        testBubbleSortByName();
        testQuickSortByEmail();
        testInsertionSortByPassword();
        testEvenOddSort();
        System.out.println("Все тесты пройдены успешно!");
    }

    private static User createUser(String name, String password, String email) {
        return new User.Builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
    }

    private static void assertSortedByName(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            if (users.get(i).getName().compareTo(users.get(i + 1).getName()) > 0) {
                throw new AssertionError("Список не отсортирован по имени");
            }
        }
    }

    private static void assertSortedByEmail(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            if (users.get(i).getEmail().compareTo(users.get(i + 1).getEmail()) > 0) {
                throw new AssertionError("Список не отсортирован по email");
            }
        }
    }

    private static void assertSortedByPasswordLengthThenLex(List<User> users) {
        for (int i = 0; i < users.size() - 1; i++) {
            int len1 = users.get(i).getPassword().length();
            int len2 = users.get(i + 1).getPassword().length();
            if (len1 > len2 || (len1 == len2 && users.get(i).getPassword().compareTo(users.get(i + 1).getPassword()) > 0)) {
                throw new AssertionError("Список не отсортирован по паролю (длина+лексикографически)");
            }
        }
    }

    private static void testBubbleSortByName() {
        List<User> users = new ArrayList<>(Arrays.asList(
                createUser("Иван", "pass123", "ivan@mail.ru"),
                createUser("Анна", "secret1", "anna@mail.ru"),
                createUser("Петр", "qwerty12", "petr@mail.ru")
        ));

        SortStrategy strategy = new BubbleSortStrategy(SortField.NAME);
        strategy.sort(users);
        assertSortedByName(users);
        System.out.println("BubbleSort (имя) - OK");
    }

    private static void testQuickSortByEmail() {
        List<User> users = new ArrayList<>(Arrays.asList(
                createUser("Иван", "pass123", "ivan@mail.ru"),
                createUser("Анна", "secret1", "anna@mail.ru"),
                createUser("Петр", "qwerty12", "petr@mail.ru")
        ));

        SortStrategy strategy = new QuickSortStrategy(SortField.EMAIL);
        strategy.sort(users);
        assertSortedByEmail(users);
        System.out.println("QuickSort (email) - OK");
    }

    private static void testInsertionSortByPassword() {
        List<User> users = new ArrayList<>(Arrays.asList(
                createUser("Иван", "pass123", "ivan@mail.ru"),
                createUser("Анна", "secret1", "anna@mail.ru"),
                createUser("Петр", "qwerty12", "petr@mail.ru")
        ));

        SortStrategy strategy = new InsertionSortStrategy(SortField.PASSWORD);
        strategy.sort(users);
        assertSortedByPasswordLengthThenLex(users);
        System.out.println("InsertionSort (пароль) - OK");
    }

    private static void testEvenOddSort() {
        List<User> users = new ArrayList<>(Arrays.asList(
                createUser("Иван", "pass123", "ivan@mail.ru"),
                createUser("Анна", "secret1", "anna@mail.ru"),
                createUser("Петр", "qwerty12", "petr@mail.ru"),
                createUser("Мария", "abc", "maria@mail.ru"),
                createUser("Олег", "xy", "oleg@mail.ru")
        ));

        List<User> original = new ArrayList<>(users);

        SortStrategy strategy = new EvenOddSortStrategy();
        strategy.sort(users);

        for (int i = 0; i < users.size(); i++) {
            if (original.get(i).getPassword().length() % 2 != 0) {
                if (users.get(i) != original.get(i)) {
                    throw new AssertionError("Элемент с нечётной длиной пароля изменил позицию");
                }
            }
        }

        List<User> evenUsers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (original.get(i).getPassword().length() % 2 == 0) {
                evenUsers.add(users.get(i));
            }
        }
        for (int i = 0; i < evenUsers.size() - 1; i++) {
            User u1 = evenUsers.get(i);
            User u2 = evenUsers.get(i + 1);
            if (u1.getPassword().length() > u2.getPassword().length()
                    || (u1.getPassword().length() == u2.getPassword().length()
                        && u1.getName().compareTo(u2.getName()) > 0)) {
                throw new AssertionError("Чётные элементы не отсортированы по длине пароля");
            }
        }
        System.out.println("EvenOddSort - OK");
    }
}