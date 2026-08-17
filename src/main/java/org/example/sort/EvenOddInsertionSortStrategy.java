package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * Сортировка вставками, которая сортирует только объекты с четными значениями числового поля.
 * Объекты с нечетными значениями остаются на своих исходных позициях.
 */
public class EvenOddInsertionSortStrategy implements SortStrategy {

    private final ToIntFunction<User> numericFieldExtractor;

    /**
     * Конструктор стратегии.
     *
     * @param numericFieldExtractor - функция извлечения числового значения
     */
    public EvenOddInsertionSortStrategy(ToIntFunction<User> numericFieldExtractor) {
        this.numericFieldExtractor = numericFieldExtractor;
    }

    /**
     * Сортирует только объекты с четными значениями числового поля.
     *
     * @param users      - список пользователей для сортировки
     * @param comparator - компаратор для сравнения
     */
    @Override
    public void sort(CustomLinkedList<User> users, Comparator<User> comparator) {

        // Сохраняем индексы и значения четных элементов
        List<Integer> evenIndices = new ArrayList<>();
        List<User> evenUsers = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {

            if (isEven(users.get(i))) {

                evenIndices.add(i);
                evenUsers.add(users.get(i));

            }

        }

        // Сортируем только четные элементы вставками
        insertionSort(evenUsers, comparator);

        // Возвращаем отсортированные четные элементы на их позиции
        for (int i = 0; i < evenIndices.size(); i++) {
            users.set(evenIndices.get(i), evenUsers.get(i));
        }
    }

    /**
     * Сортировка вставками для списка.
     *
     * @param users      - список для сортировки
     * @param comparator - компаратор
     */
    private void insertionSort(List<User> users, Comparator<User> comparator) {

        int n = users.size();

        for (int i = 1; i < n; i++) {

            User key = users.get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(users.get(j), key) > 0) {

                users.set(j + 1, users.get(j));
                j--;

            }

            users.set(j + 1, key);

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

    @Override
    public String getStrategyName() {
        return "EvenOddInsertionSort";
    }

}
