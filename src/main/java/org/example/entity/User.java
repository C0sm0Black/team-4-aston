package org.example.entity;

import java.util.Objects;


/**
 * Класс User представляет пользователя системы.
 * Реализует паттерн Builder через внутренний класс UserBuilder.
 */
public class User {

    private final String name;
    private final String password;
    private final String email;

    private User(Builder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
    }

    // Билдер
    public static class Builder {

        private String name;
        private String password;
        private String email;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {

            // Валидация при построении
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }

            if (password == null || password.length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters");
            }

            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }

            return new User(this);

        }

    }

    // Геттеры
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }

    // Сравнение
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return Objects.equals(name, user.name) &&
               Objects.equals(password, user.password) &&
               Objects.equals(email, user.email);

    }

    // Хэш
    @Override
    public int hashCode() { return Objects.hash(name, password, email); }

    // В строку
    @Override
    public String toString() {
        return String.format("User{name='%s', email='%s', password='***'}", name, email);
    }

}
