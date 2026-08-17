package org.example.sort;

import org.example.collection.CustomLinkedList;
import org.example.entity.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * Быстрая сортировка, которая сортирует только объекты с четными значениями числового поля.
 * Объекты с нечетными значениями остаются на своих исходных позициях.
 */
public class EvenOddQuickSortStrategy implements SortStrategy {

    private final ToIntFunction<User> numericFieldExtractor;

    /**
     * Конструктор стратегии.
     *
     * @param numericFieldExtractor - функция извлечения числового значения
     */
    public EvenOddQuickSortStrategy(ToIntFunction<User> numericFieldExtractor) {
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

        // Сортируем только четные элементы
        if (!evenUsers.isEmpty()) {
            quickSort(evenUsers, 0, evenUsers.size() - 1, comparator);
        }

        // Возвращаем отсортированные четные элементы на их позиции
        for (int i = 0; i < evenIndices.size(); i++) {
            users.set(evenIndices.get(i), evenUsers.get(i));
        }
    }



    /**
     * Рекурсивный метод быстрой сортировки.
     *
     * @param users      - список для сортировки
     * @param low        - нижняя граница
     * @param high       - верхняя граница
     * @param comparator - компаратор
     */
    private void quickSort(List<User> users, int low, int high, Comparator<User> comparator) {

        if (low < high) {

            int pi = partition(users, low, high, comparator);
            quickSort(users, low, pi - 1, comparator);
            quickSort(users, pi + 1, high, comparator);

        }

    }

    /**
     * Разделяет список на две части.
     *
     * @param users      - список
     * @param low        - нижняя граница
     * @param high       - верхняя граница
     * @param comparator - компаратор
     * @return int - индекс опорного элемента
     */
    private int partition(List<User> users, int low, int high, Comparator<User> comparator) {

        User pivot = users.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (comparator.compare(users.get(j), pivot) <= 0) {

                i++;
                swap(users, i, j);

            }

        }

        swap(users, i + 1, high);
        return i + 1;

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
     * Меняет местами два элемента.
     *
     * @param users - список
     * @param i     - первый индекс
     * @param j     - второй индекс
     */
    private void swap(List<User> users, int i, int j) {

        User temp = users.get(i);
        users.set(i, users.get(j));
        users.set(j, temp);

    }

    @Override
    public String getStrategyName() {
        return "EvenOddQuickSort";
    }

}
