package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;
import org.example.comporator.UserComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvenOddInsertionSortStrategyTest {

    private EvenOddInsertionSortStrategy strategy;
    private UserComparator comparator;

    @BeforeEach
    void setUp() {
        // Используем длину имени как числовое поле для проверки четности/нечетности
        strategy = new EvenOddInsertionSortStrategy(user -> user.getName().length());
        comparator = new UserComparator();
    }

    @Test
    @DisplayName("Should sort only even values by name -> email -> password and return correct strategy name")
    void shouldSortOnlyEvenValuesAndReturnStrategyName() {

        // Подготовка данных
        // Имена: Charlie (7 - нечетное), Alice (5 - нечетное), Bob (3 - нечетное), Anna (4 - четное), John (4 - четное)
        CustomLinkedList<User> users = new CustomLinkedList<>();
        users.add(new User.Builder()
                .name("Charlie")      // 7 символов - нечетное
                .password("pass7890")
                .email("charlie@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Alice")        // 5 символов - нечетное
                .password("password123")
                .email("alice@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Bob")          // 3 символа - нечетное
                .password("pass4567")
                .email("bob@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Anna")         // 4 символа - четное
                .password("pass1234")
                .email("anna@mail.com")
                .build());

        users.add(new User.Builder()
                .name("John")         // 4 символа - четное
                .password("pass5678")
                .email("john@mail.com")
                .build());

        // Выполняем сортировку
        strategy.sort(users, comparator);

        // Проверка: нечетные элементы остаются на своих позициях
        assertEquals("Charlie", users.get(0).getName());
        assertEquals("Alice", users.get(1).getName());
        assertEquals("Bob", users.get(2).getName());

        // Проверка сортировки четных элементов между собой (Anna и John)
        // По имени: Anna < John
        assertEquals("Anna", users.get(3).getName());
        assertEquals("John", users.get(4).getName());

        // Проверка метода getStrategyName()
        assertEquals("EvenOddInsertionSort", strategy.getStrategyName());
        assertNotNull(strategy.getStrategyName());

    }

}