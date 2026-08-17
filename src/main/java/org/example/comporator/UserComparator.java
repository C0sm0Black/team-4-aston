package org.example.comporator;

import org.example.entity.User;

import java.util.Comparator;

/**
 * Компаратор для сортировки пользователей по всем 3 полям.
 * Использует цепочку: имя -> email -> пароль.
 */
public class UserComparator implements Comparator<User> {

    /**
     * Статический компаратор с цепочкой сравнения.
     * Сначала сравнивает по имени, затем по email, затем по паролю.
     */
    private static final Comparator<User> COMPARATOR =
            Comparator.comparing(User::getName)
                    .thenComparing(User::getEmail)
                    .thenComparing(User::getPassword);

    /**
     * Сравнивает двух пользователей.
     *
     * @param user1 - первый пользователь
     * @param user2 - второй пользователь
     * @return int - отрицательное число, если user1 < user2;
     * 0, если user1 == user2;
     * положительное число, если user1 > user2
     */
    @Override
    public int compare(User user1, User user2) {
        return COMPARATOR.compare(user1, user2);
    }

}
