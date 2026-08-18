package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;
import org.example.comporator.UserComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertionSortStrategyTest {

    private InsertionSortStrategy strategy;
    private UserComparator comparator;

    @BeforeEach
    void setUp() {
        strategy = new InsertionSortStrategy();
        comparator = new UserComparator();
    }

    @Test
    @DisplayName("Should sort users by name -> email -> password and return correct strategy name")
    void shouldSortUsersByAllFieldsAndReturnStrategyName() {

        // Подготовка данных
        CustomLinkedList<User> users = new CustomLinkedList<>();
        users.add(new User.Builder()
                .name("Charlie")
                .password("pass7890")
                .email("charlie@mail.com")
                .build());

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

        users.add(new User.Builder()
                .name("Alice")
                .password("zzzzzz")
                .email("alice@mail.com")
                .build());

        users.add(new User.Builder()
                .name("Alice")
                .password("password123")
                .email("bob@mail.com")
                .build());

        // Выполняем сортировку
        strategy.sort(users, comparator);

        // Проверка сортировки по имени
        assertEquals("Alice", users.get(0).getName());
        assertEquals("Alice", users.get(1).getName());
        assertEquals("Alice", users.get(2).getName());
        assertEquals("Bob", users.get(3).getName());
        assertEquals("Charlie", users.get(4).getName());

        // Проверка сортировки по email (при одинаковых именах)
        assertEquals("alice@mail.com", users.get(0).getEmail());
        assertEquals("alice@mail.com", users.get(1).getEmail());
        assertEquals("bob@mail.com", users.get(2).getEmail());

        // Проверка сортировки по паролю (при одинаковых именах и email)
        assertEquals("password123", users.get(0).getPassword());
        assertEquals("zzzzzz", users.get(1).getPassword());

        // Проверка метода getStrategyName()
        assertEquals("InsertionSort", strategy.getStrategyName());
        assertNotNull(strategy.getStrategyName());

    }

}