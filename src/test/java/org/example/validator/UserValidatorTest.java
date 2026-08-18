package org.example.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Nested
    @DisplayName("validateAll tests")
    class ValidateAllTests {

        @Test
        @DisplayName("Should validate all fields successfully")
        void shouldValidateAllFieldsSuccessfully() {

            assertDoesNotThrow(() ->
                    UserValidator.validateAll("Alice", "password123", "alice@mail.com"));

        }

        @Test
        @DisplayName("Should throw exception when name is invalid")
        void shouldThrowExceptionWhenNameInvalid() {

            assertThrows(IllegalArgumentException.class, () ->
                    UserValidator.validateAll("", "password123", "alice@mail.com"));

        }

        @Test
        @DisplayName("Should throw exception when password is invalid")
        void shouldThrowExceptionWhenPasswordInvalid() {

            assertThrows(IllegalArgumentException.class, () ->
                    UserValidator.validateAll("Alice", "123", "alice@mail.com"));

        }

        @Test
        @DisplayName("Should throw exception when email is invalid")
        void shouldThrowExceptionWhenEmailInvalid() {

            assertThrows(IllegalArgumentException.class, () ->
                    UserValidator.validateAll("Alice", "password123", "invalidemail"));

        }

    }

    @Nested
    @DisplayName("validateName tests")
    class ValidateNameTests {

        @Test
        @DisplayName("Should validate valid name")
        void shouldValidateValidName() {

            assertDoesNotThrow(() ->
                    UserValidator.validateName("Alice"));

        }

        @Test
        @DisplayName("Should throw exception when name is null")
        void shouldThrowExceptionWhenNameIsNull() {

            assertThrows(IllegalArgumentException.class, () -> UserValidator.validateName(null));

        }

        @Test
        @DisplayName("Should throw exception when name is empty")
        void shouldThrowExceptionWhenNameIsEmpty() {

            assertThrows(IllegalArgumentException.class, () -> UserValidator.validateName(""));

        }

        @Test
        @DisplayName("Should throw exception when name contains only spaces")
        void shouldThrowExceptionWhenNameIsOnlySpaces() {

            assertThrows(IllegalArgumentException.class, () -> UserValidator.validateName("   "));

        }

        @Test
        @DisplayName("Should throw exception when name is too long (more than 50 chars)")
        void shouldThrowExceptionWhenNameIsTooLong() {

            String longName = "A".repeat(51);
            assertThrows(IllegalArgumentException.class, () -> UserValidator.validateName(longName));

        }

        @Test
        @DisplayName("Should accept name with exactly 50 characters")
        void shouldAcceptNameWithExactly50Characters() {

            String name = "A".repeat(50);
            assertDoesNotThrow(() -> UserValidator.validateName(name));

        }

        @Test
        @DisplayName("Should accept name with spaces")
        void shouldAcceptNameWithSpaces() {

            assertDoesNotThrow(() -> UserValidator.validateName("Alice Wonderland"));

        }
    }

    @Nested
    @DisplayName("validatePassword tests")
    class ValidatePasswordTests {

        @Test
        @DisplayName("Should validate valid password")
        void shouldValidateValidPassword() {

            assertDoesNotThrow(() -> UserValidator.validatePassword("password123"));

        }

        @Test
        @DisplayName("Should throw exception when password is null")
        void shouldThrowExceptionWhenPasswordIsNull() {

            assertThrows(IllegalArgumentException.class, () -> UserValidator.validatePassword(null));

        }

        @Test
        @DisplayName("Should throw exception when password is empty")
        void shouldThrowExceptionWhenPasswordIsEmpty() {

            assertThrows(IllegalArgumentException.class, () -> UserValidator.validatePassword(""));

        }

        @Test
        @DisplayName("Should throw exception when password contains only spaces")
        void shouldThrowExceptionWhenPasswordIsOnlySpaces() {

            assertThrows(IllegalArgumentException.class, () -> UserValidator.validatePassword("   "));

        }

        @Test
        @DisplayName("Should throw exception when password is less than 6 characters")
        void shouldThrowExceptionWhenPasswordIsTooShort() {

            assertThrows(IllegalArgumentException.class, () -> UserValidator.validatePassword("12345"));

        }

        @Test
        @DisplayName("Should accept password with exactly 6 characters")
        void shouldAcceptPasswordWithExactly6Characters() {
            assertDoesNotThrow(() -> UserValidator.validatePassword("123456"));
        }

        @Test
        @DisplayName("Should accept password with special characters")
        void shouldAcceptPasswordWithSpecialCharacters() {
            assertDoesNotThrow(() -> UserValidator.validatePassword("pass!@#$%"));
        }

    }

    @Nested
    @DisplayName("validateEmail tests")
    class ValidateEmailTests {

        @Test
        @DisplayName("Should validate valid email")
        void shouldValidateValidEmail() {
            assertDoesNotThrow(() -> UserValidator.validateEmail("alice@mail.com"));
        }

        @Test
        @DisplayName("Should throw exception when email is null")
        void shouldThrowExceptionWhenEmailIsNull() {
            assertThrows(IllegalArgumentException.class, () -> UserValidator.validateEmail(null));
        }

        @Test
        @DisplayName("Should throw exception when email is empty")
        void shouldThrowExceptionWhenEmailIsEmpty() {
            assertThrows(IllegalArgumentException.class, () -> UserValidator.validateEmail(""));
        }

        @Test
        @DisplayName("Should throw exception when email contains only spaces")
        void shouldThrowExceptionWhenEmailIsOnlySpaces() {
            assertThrows(IllegalArgumentException.class, () -> UserValidator.validateEmail("   "));
        }

        @Test
        @DisplayName("Should throw exception when email does not contain @")
        void shouldThrowExceptionWhenEmailDoesNotContainAt() {
            assertThrows(IllegalArgumentException.class, () -> UserValidator.validateEmail("alice.mail.com"));
        }

        @Test
        @DisplayName("Should accept email with plus sign")
        void shouldAcceptEmailWithPlusSign() {
            assertDoesNotThrow(() -> UserValidator.validateEmail("alice+test@mail.com"));
        }

        @Test
        @DisplayName("Should accept email with dot")
        void shouldAcceptEmailWithDot() {
            assertDoesNotThrow(() -> UserValidator.validateEmail("alice.smith@mail.com"));
        }

        @Test
        @DisplayName("Should accept email with underscore")
        void shouldAcceptEmailWithUnderscore() {
            assertDoesNotThrow(() -> UserValidator.validateEmail("alice_smith@mail.com"));
        }

        @Test
        @DisplayName("Should accept email with hyphen")
        void shouldAcceptEmailWithHyphen() {
            assertDoesNotThrow(() -> UserValidator.validateEmail("alice-smith@mail.com"));
        }

        @Test
        @DisplayName("Should accept email with numbers")
        void shouldAcceptEmailWithNumbers() {
            assertDoesNotThrow(() -> UserValidator.validateEmail("alice123@mail.com"));
        }

        @Test
        @DisplayName("Should accept email with subdomain")
        void shouldAcceptEmailWithSubdomain() {
            assertDoesNotThrow(() -> UserValidator.validateEmail("alice@sub.domain.com"));
        }

        @Test
        @DisplayName("Should accept email with domain having hyphen")
        void shouldAcceptEmailWithDomainHavingHyphen() {
            assertDoesNotThrow(() -> UserValidator.validateEmail("alice@my-domain.com"));
        }

    }

}