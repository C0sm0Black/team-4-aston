package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.util.Comparator;
import java.util.function.ToIntFunction;

/**
 * Сортировка пузырьком, которая сортирует только объекты с четными значениями числового поля.
 * Объекты с нечетными значениями остаются на своих исходных позициях.
 */
public class EvenOddBubbleSortStrategy implements SortStrategy {

    private final ToIntFunction<User> numericFieldExtractor;

    /**
     * Конструктор стратегии.
     *
     * @param numericFieldExtractor - функция извлечения числового значения
     */
    public EvenOddBubbleSortStrategy(ToIntFunction<User> numericFieldExtractor) {
        this.numericFieldExtractor = numericFieldExtractor;
    }

    /**
     * Сортирует только объекты с четными значениями числового поля.
     * Объекты с нечетными значениями остаются на своих позициях.
     *
     * @param users - список пользователей для сортировки
     * @param comparator - компаратор для сравнения
     */
    @Override
    public void sort(CustomLinkedList<User> users, Comparator<User> comparator) {

        int n = users.size();

        for (int i = 0; i < n - 1; i++) {

            // Пропускаем нечетные значения
            if (isOdd(users.get(i))) {
                continue;
            }

            for (int j = i + 1; j < n; j++) {

                // Сортируем только четные значения
                if (isEven(users.get(j)) &&
                        comparator.compare(users.get(i), users.get(j)) > 0) {
                    swap(users, i, j);

                }

            }

        }

    }

    /**
     * Проверяет, является ли значение четным.
     *
     * @param user - пользователь
     * @return boolean - true если значение четное
     */
    private boolean isEven(User user) {
        return numericFieldExtractor.applyAsInt(user) % 2 == 0;
    }

    /**
     * Проверяет, является ли значение нечетным.
     *
     * @param user - пользователь
     * @return boolean - true если значение нечетное
     */
    private boolean isOdd(User user) {
        return numericFieldExtractor.applyAsInt(user) % 2 != 0;
    }

    /**
     * Меняет местами два элемента списка.
     *
     * @param users - список пользователей
     * @param i - индекс первого элемента
     * @param j - индекс второго элемента
     */
    private void swap(CustomLinkedList<User> users, int i, int j) {

        User temp = users.get(i);
        users.set(i, users.get(j));
        users.set(j, temp);

    }

    @Override
    public String getStrategyName() {
        return "EvenOddBubbleSort";
    }

}
