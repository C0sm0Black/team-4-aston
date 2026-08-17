package org.example.sort.factory;

import org.example.entity.User;
import org.example.enums.NumericField;
import org.example.enums.SortAlgorithm;
import org.example.sort.EvenOddBubbleSortStrategy;
import org.example.sort.EvenOddInsertionSortStrategy;
import org.example.sort.EvenOddQuickSortStrategy;
import org.example.sort.SortStrategy;

import java.util.function.ToIntFunction;

/**
 * Фабрика для создания стратегий сортировки с четными/нечетными значениями.
 * Реализует паттерн Factory.
 */
public class EvenOddSortFactory {

    /**
     * Приватный конструктор для предотвращения создания экземпляров.
     */
    private EvenOddSortFactory() {
    }

    /**
     * Создает стратегию сортировки с четными/нечетными значениями.
     *
     * @param algorithm - алгоритм сортировки
     * @param numericField - числовое поле для определения четности
     * @return SortStrategy - созданная стратегия
     */
    public static SortStrategy createStrategy(SortAlgorithm algorithm, NumericField numericField) {

        ToIntFunction<User> extractor = getExtractor(numericField);

        return switch (algorithm) {

            case BUBBLE_SORT -> new EvenOddBubbleSortStrategy(extractor);
            case QUICK_SORT -> new EvenOddQuickSortStrategy(extractor);
            case INSERTION_SORT -> new EvenOddInsertionSortStrategy(extractor);

        };

    }

    /**
     * Получает функцию извлечения числового значения из пользователя.
     *
     * @param field - числовое поле
     * @return ToIntFunction<User> - функция извлечения
     */
    private static ToIntFunction<User> getExtractor(NumericField field) {

        return switch (field) {

            case NAME_LENGTH -> user -> user.getName().length();
            case PASSWORD_LENGTH -> user -> user.getPassword().length();
            case EMAIL_LENGTH -> user -> user.getEmail().length();

        };

    }

}
