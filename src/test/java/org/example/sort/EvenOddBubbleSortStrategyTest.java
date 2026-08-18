package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;
import org.example.comporator.UserComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvenOddBubbleSortStrategyTest {

    private EvenOddBubbleSortStrategy strategy;
    private UserComparator comparator;

    @BeforeEach
    void setUp() {

        // Используем длину пароля как числовое поле для проверки четности/нечетности
        strategy = new EvenOddBubbleSortStrategy(user -> user.getName().length());
        comparator = new UserComparator();

    }

    @Test
    @DisplayName("Should sort only even values by name -> email -> password and return correct strategy name")
    void shouldSortOnlyEvenValuesAndReturnStrategyName() {

        // Подготовка данных
        // Пароли: 123456 (6 - четное), 12345 (5 - нечетное), 1234567 (7 - нечетное), 1234 (4 - четное)
        CustomLinkedList<User> users = new CustomLinkedList<>();
        users.add(new User.Builder()
                .name("Charlie")
                .password("1234567")  // 7 - нечетное
                .email("charlie@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Alice")
                .password("123456")   // 6 - четное
                .email("alice@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Bob")
                .password("123455")    // 5 - нечетное
                .email("bob@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Alice")
                .password("123444")     // 4 - четное
                .email("bob@mail.com")
                .build());

        // Выполняем сортировку
        strategy.sort(users, comparator);

        // Проверка: нечетные элементы (Charlie и Bob) остаются на своих позициях
        // Четные элементы (Alice с паролем 123456 и Alice с паролем 1234) сортируются между собой

        // Проверка позиций нечетных элементов (они не должны меняться)
        assertEquals("Charlie", users.get(0).getName());
        assertEquals("1234567", users.get(0).getPassword());

        assertEquals("Bob", users.get(2).getName());
        assertEquals("123455", users.get(2).getPassword());

        // Проверка сортировки четных элементов между собой
        // Alice с паролем 1234 должна быть после Alice с паролем 123456 (по алфавиту email: alice@mail.com < bob@mail.com)
        assertEquals("Alice", users.get(1).getName());
        assertEquals("123456", users.get(1).getPassword());
        assertEquals("alice@mail.com", users.get(1).getEmail());

        assertEquals("Alice", users.get(3).getName());
        assertEquals("123444", users.get(3).getPassword());
        assertEquals("bob@mail.com", users.get(3).getEmail());

        // Проверка метода getStrategyName()
        assertEquals("EvenOddBubbleSort", strategy.getStrategyName());
        assertNotNull(strategy.getStrategyName());

    }

}