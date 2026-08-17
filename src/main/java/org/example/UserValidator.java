package org.example;

public class UserValidator {

    public static void validateAll(String name, String password, String email) {
        validateName(name);
        validatePassword(password);
        validateEmail(email);
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Имя слишком длинное (максимум 50 символов)");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Пароль должен содержать минимум 6 символов");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email не может быть пустым");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Некорректный формат электронной почты");
        }
    }
}