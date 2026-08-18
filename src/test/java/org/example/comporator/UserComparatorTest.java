package org.example.comporator;

import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserComparatorTest {

    private UserComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new UserComparator();
    }

    @Test
    @DisplayName("Should compare users by name -> email -> password")
    void shouldCompareUsersByAllFields() {

        // Создаем пользователей с валидными данными
        User user1 = new User.Builder()
                .name("Alice")
                .email("alice@mail.com")  // валидный email
                .password("pass123")       // минимум 6 символов
                .build();

        User user2 = new User.Builder()
                .name("Alice")
                .email("alice@mail.com")
                .password("pass123")
                .build();

        User user3 = new User.Builder()
                .name("Bob")
                .email("bob@mail.com")
                .password("pass456")
                .build();

        User user4 = new User.Builder()
                .name("Alice")
                .email("bob@mail.com")
                .password("pass789")
                .build();

        // Одинаковые пользователи
        assertEquals(0, comparator.compare(user1, user2));

        // Разные имена: Alice < Bob
        assertTrue(comparator.compare(user1, user3) < 0);
        assertTrue(comparator.compare(user3, user1) > 0);

        // Одинаковые имена, разные email: alice@mail.com < bob@mail.com
        assertTrue(comparator.compare(user1, user4) < 0);
        assertTrue(comparator.compare(user4, user1) > 0);

        // Одинаковые имя и email, разные пароли
        User user5 = new User.Builder()
                .name("Alice")
                .email("alice@mail.com")
                .password("aaaaaa")  // минимум 6 символов
                .build();

        User user6 = new User.Builder()
                .name("Alice")
                .email("alice@mail.com")
                .password("zzzzzz")  // минимум 6 символов
                .build();

        assertTrue(comparator.compare(user5, user6) < 0);
        assertTrue(comparator.compare(user6, user5) > 0);

    }

    @Test
    @DisplayName("Should throw NullPointerException when comparing null users")
    void shouldThrowExceptionWhenComparingNull() {

        User user = new User.Builder()
                .name("Alice")
                .email("alice@mail.com")
                .password("pass123")
                .build();

        assertThrows(NullPointerException.class, () -> comparator.compare(null, user));
        assertThrows(NullPointerException.class, () -> comparator.compare(user, null));
        assertThrows(NullPointerException.class, () -> comparator.compare(null, null));

    }

}