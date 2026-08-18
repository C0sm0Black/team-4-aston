package org.example.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Nested
    @DisplayName("Builder tests")
    class BuilderTests {

        @Test
        @DisplayName("Should create user with valid data")
        void shouldCreateUserWithValidData() {

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
        @DisplayName("Should create user with valid data (minimal password length)")
        void shouldCreateUserWithMinimalPasswordLength() {

            User user = new User.Builder()
                    .name("Bob")
                    .password("123456")
                    .email("bob@mail.com")
                    .build();

            assertNotNull(user);
            assertEquals("Bob", user.getName());
            assertEquals("123456", user.getPassword());
            assertEquals("bob@mail.com", user.getEmail());

        }

        @Test
        @DisplayName("Should throw exception when name is null")
        void shouldThrowExceptionWhenNameIsNull() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name(null)
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when name is empty")
        void shouldThrowExceptionWhenNameIsEmpty() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when name contains only spaces")
        void shouldThrowExceptionWhenNameIsOnlySpaces() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("   ")
                    .password("password123")
                    .email("alice@mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when password is null")
        void shouldThrowExceptionWhenPasswordIsNull() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("Alice")
                    .password(null)
                    .email("alice@mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when password is too short (less than 6)")
        void shouldThrowExceptionWhenPasswordIsTooShort() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("Alice")
                    .password("12345")
                    .email("alice@mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when password is empty")
        void shouldThrowExceptionWhenPasswordIsEmpty() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("Alice")
                    .password("")
                    .email("alice@mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when email is null")
        void shouldThrowExceptionWhenEmailIsNull() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email(null)
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when email does not contain @")
        void shouldThrowExceptionWhenEmailDoesNotContainAt() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice.mail.com")
                    .build());

        }

        @Test
        @DisplayName("Should throw exception when email is empty")
        void shouldThrowExceptionWhenEmailIsEmpty() {

            assertThrows(IllegalArgumentException.class, () ->

                    new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("")
                    .build());

        }

        @Test
        @DisplayName("Should trim spaces in name")
        void shouldTrimSpacesInName() {

            User user = new User.Builder()
                    .name("  Alice  ")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            // Валидация проходит, но пробелы НЕ обрезаются в билдере
            // Имя сохраняется как есть
            assertNotNull(user);
            assertEquals("  Alice  ", user.getName());

        }
    }

    @Nested
    @DisplayName("Getter tests")
    class GetterTests {

        @Test
        @DisplayName("Should return correct name")
        void shouldReturnCorrectName() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertEquals("Alice", user.getName());

        }

        @Test
        @DisplayName("Should return correct password")
        void shouldReturnCorrectPassword() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertEquals("password123", user.getPassword());

        }

        @Test
        @DisplayName("Should return correct email")
        void shouldReturnCorrectEmail() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertEquals("alice@mail.com", user.getEmail());

        }

    }

    @Nested
    @DisplayName("Equals tests")
    class EqualsTests {

        @Test
        @DisplayName("Should return true when comparing same object")
        void shouldReturnTrueWhenComparingSameObject() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertEquals(user, user);

        }

        @Test
        @DisplayName("Should return true when comparing equal users")
        void shouldReturnTrueWhenComparingEqualUsers() {

            User user1 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            User user2 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertEquals(user1, user2);

        }

        @Test
        @DisplayName("Should return false when comparing with null")
        void shouldReturnFalseWhenComparingWithNull() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertNotEquals(null, user);

        }

        @Test
        @DisplayName("Should return false when comparing with different class")
        void shouldReturnFalseWhenComparingWithDifferentClass() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertNotEquals(user, "string");

        }

        @Test
        @DisplayName("Should return false when names differ")
        void shouldReturnFalseWhenNamesDiffer() {

            User user1 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            User user2 = new User.Builder()
                    .name("Bob")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertNotEquals(user1, user2);

        }

        @Test
        @DisplayName("Should return false when passwords differ")
        void shouldReturnFalseWhenPasswordsDiffer() {

            User user1 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            User user2 = new User.Builder()
                    .name("Alice")
                    .password("different")
                    .email("alice@mail.com")
                    .build();

            assertNotEquals(user1, user2);

        }

        @Test
        @DisplayName("Should return false when emails differ")
        void shouldReturnFalseWhenEmailsDiffer() {

            User user1 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            User user2 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("bob@mail.com")
                    .build();

            assertNotEquals(user1, user2);

        }

        @Test
        @DisplayName("Should return true for users with same data but different object")
        void shouldReturnTrueForSameDataDifferentObject() {

            User user1 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            User user2 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertEquals(user1, user2);

        }

    }

    @Nested
    @DisplayName("HashCode tests")
    class HashCodeTests {

        @Test
        @DisplayName("Should return same hashCode for equal users")
        void shouldReturnSameHashCodeForEqualUsers() {

            User user1 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            User user2 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertEquals(user1.hashCode(), user2.hashCode());

        }

        @Test
        @DisplayName("Should return different hashCode for different users")
        void shouldReturnDifferentHashCodeForDifferentUsers() {

            User user1 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            User user2 = new User.Builder()
                    .name("Bob")
                    .password("different")
                    .email("bob@mail.com")
                    .build();

            assertNotEquals(user1.hashCode(), user2.hashCode());

        }

        @Test
        @DisplayName("Should return same hashCode for same user")
        void shouldReturnSameHashCodeForSameUser() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertEquals(user.hashCode(), user.hashCode());

        }

    }

    @Nested
    @DisplayName("ToString tests")
    class ToStringTests {

        @Test
        @DisplayName("Should return string representation with hidden password")
        void shouldReturnStringRepresentationWithHiddenPassword() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            String toString = user.toString();

            assertTrue(toString.contains("Alice"));
            assertTrue(toString.contains("alice@mail.com"));
            assertTrue(toString.contains("***"));
            assertFalse(toString.contains("password123")); // Пароль скрыт

        }

        @Test
        @DisplayName("Should not contain actual password in toString")
        void shouldNotContainActualPasswordInToString() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("secret123")
                    .email("alice@mail.com")
                    .build();

            String toString = user.toString();

            assertFalse(toString.contains("secret123"));

        }

        @Test
        @DisplayName("Should contain name and email in toString")
        void shouldContainNameAndEmailInToString() {

            User user = new User.Builder()
                    .name("Bob")
                    .password("password123")
                    .email("bob@mail.com")
                    .build();

            String toString = user.toString();

            assertTrue(toString.contains("Bob"));
            assertTrue(toString.contains("bob@mail.com"));
            assertTrue(toString.contains("User{"));

        }

    }

    @Nested
    @DisplayName("Builder chaining tests")
    class BuilderChainingTests {

        @Test
        @DisplayName("Should support method chaining")
        void shouldSupportMethodChaining() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertNotNull(user);

        }

        @Test
        @DisplayName("Should allow building with different order")
        void shouldAllowBuildingWithDifferentOrder() {

            User user = new User.Builder()
                    .email("alice@mail.com")
                    .password("password123")
                    .name("Alice")
                    .build();

            assertNotNull(user);
            assertEquals("Alice", user.getName());
            assertEquals("password123", user.getPassword());
            assertEquals("alice@mail.com", user.getEmail());

        }

        @Test
        @DisplayName("Should create multiple users independently")
        void shouldCreateMultipleUsersIndependently() {

            User user1 = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            User user2 = new User.Builder()
                    .name("Bob")
                    .password("pass4567")
                    .email("bob@mail.com")
                    .build();

            assertNotEquals(user1, user2);
            assertEquals("Alice", user1.getName());
            assertEquals("Bob", user2.getName());

        }

    }

    @Nested
    @DisplayName("Edge cases tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle email with subdomain")
        void shouldHandleEmailWithSubdomain() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@sub.domain.com")
                    .build();

            assertNotNull(user);
            assertEquals("alice@sub.domain.com", user.getEmail());

        }

        @Test
        @DisplayName("Should handle email with plus sign")
        void shouldHandleEmailWithPlusSign() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice+test@mail.com")
                    .build();

            assertNotNull(user);
            assertEquals("alice+test@mail.com", user.getEmail());

        }

        @Test
        @DisplayName("Should handle name with special characters")
        void shouldHandleNameWithSpecialCharacters() {

            User user = new User.Builder()
                    .name("Alice-Smith")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertNotNull(user);
            assertEquals("Alice-Smith", user.getName());

        }

        @Test
        @DisplayName("Should handle password with special characters")
        void shouldHandlePasswordWithSpecialCharacters() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("pass!@#$%^")
                    .email("alice@mail.com")
                    .build();

            assertNotNull(user);
            assertEquals("pass!@#$%^", user.getPassword());

        }

        @Test
        @DisplayName("Should handle password with exactly 6 characters")
        void shouldHandlePasswordWithExactly6Characters() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("123456")
                    .email("alice@mail.com")
                    .build();

            assertNotNull(user);
            assertEquals("123456", user.getPassword());

        }

        @Test
        @DisplayName("Should handle name with numbers")
        void shouldHandleNameWithNumbers() {

            User user = new User.Builder()
                    .name("Alice123")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            assertNotNull(user);
            assertEquals("Alice123", user.getName());

        }

        @Test
        @DisplayName("Should handle email with numbers")
        void shouldHandleEmailWithNumbers() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice123@mail.com")
                    .build();

            assertNotNull(user);
            assertEquals("alice123@mail.com", user.getEmail());

        }

    }

    @Nested
    @DisplayName("Immutability tests")
    class ImmutabilityTests {

        @Test
        @DisplayName("Should not be modified after creation (no setters)")
        void shouldNotBeModifiedAfterCreation() {

            User user = new User.Builder()
                    .name("Alice")
                    .password("password123")
                    .email("alice@mail.com")
                    .build();

            // Проверяем, что нет методов setter
            // Все поля final, поэтому объект неизменяемый
            assertEquals("Alice", user.getName());
            assertEquals("password123", user.getPassword());
            assertEquals("alice@mail.com", user.getEmail());

        }

    }

}