package org.example.sort.factory;

import org.example.enums.NumericField;
import org.example.enums.SortAlgorithm;
import org.example.sort.EvenOddBubbleSortStrategy;
import org.example.sort.EvenOddInsertionSortStrategy;
import org.example.sort.EvenOddQuickSortStrategy;
import org.example.sort.SortStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvenOddSortFactoryTest {

    @Test
    @DisplayName("Should create correct strategy for each algorithm and numeric field")
    void shouldCreateCorrectStrategy() {

        // Проверка создания BubbleSort стратегии
        SortStrategy bubbleStrategy = EvenOddSortFactory.createStrategy(
                SortAlgorithm.BUBBLE_SORT,
                NumericField.NAME_LENGTH
        );
        assertNotNull(bubbleStrategy);
        assertInstanceOf(EvenOddBubbleSortStrategy.class, bubbleStrategy);
        assertEquals("EvenOddBubbleSort", bubbleStrategy.getStrategyName());

        // Проверка создания InsertionSort стратегии
        SortStrategy insertionStrategy = EvenOddSortFactory.createStrategy(
                SortAlgorithm.INSERTION_SORT,
                NumericField.PASSWORD_LENGTH
        );

        assertNotNull(insertionStrategy);
        assertInstanceOf(EvenOddInsertionSortStrategy.class, insertionStrategy);
        assertEquals("EvenOddInsertionSort", insertionStrategy.getStrategyName());

        // Проверка создания QuickSort стратегии
        SortStrategy quickStrategy = EvenOddSortFactory.createStrategy(
                SortAlgorithm.QUICK_SORT,
                NumericField.EMAIL_LENGTH
        );

        assertNotNull(quickStrategy);
        assertInstanceOf(EvenOddQuickSortStrategy.class, quickStrategy);
        assertEquals("EvenOddQuickSort", quickStrategy.getStrategyName());

        // Проверка работы с разными NumericField
        SortStrategy strategy1 = EvenOddSortFactory.createStrategy(
                SortAlgorithm.BUBBLE_SORT,
                NumericField.NAME_LENGTH
        );

        SortStrategy strategy2 = EvenOddSortFactory.createStrategy(
                SortAlgorithm.BUBBLE_SORT,
                NumericField.PASSWORD_LENGTH
        );

        assertNotNull(strategy1);
        assertNotNull(strategy2);

    }

    @Test
    @DisplayName("Should return correct strategy name for each algorithm")
    void shouldReturnCorrectStrategyName() {

        // BUBBLE_SORT
        SortStrategy bubbleStrategy = EvenOddSortFactory.createStrategy(
                SortAlgorithm.BUBBLE_SORT,
                NumericField.NAME_LENGTH
        );
        assertEquals("EvenOddBubbleSort", bubbleStrategy.getStrategyName());

        // INSERTION_SORT
        SortStrategy insertionStrategy = EvenOddSortFactory.createStrategy(
                SortAlgorithm.INSERTION_SORT,
                NumericField.NAME_LENGTH
        );
        assertEquals("EvenOddInsertionSort", insertionStrategy.getStrategyName());

        // QUICK_SORT
        SortStrategy quickStrategy = EvenOddSortFactory.createStrategy(
                SortAlgorithm.QUICK_SORT,
                NumericField.NAME_LENGTH
        );
        assertEquals("EvenOddQuickSort", quickStrategy.getStrategyName());

    }

    @Test
    @DisplayName("Should handle all NumericField types")
    void shouldHandleAllNumericFieldTypes() {

        // NAME_LENGTH
        SortStrategy strategy1 = EvenOddSortFactory.createStrategy(
                SortAlgorithm.BUBBLE_SORT,
                NumericField.NAME_LENGTH
        );
        assertNotNull(strategy1);

        // PASSWORD_LENGTH
        SortStrategy strategy2 = EvenOddSortFactory.createStrategy(
                SortAlgorithm.BUBBLE_SORT,
                NumericField.PASSWORD_LENGTH
        );
        assertNotNull(strategy2);

        // EMAIL_LENGTH
        SortStrategy strategy3 = EvenOddSortFactory.createStrategy(
                SortAlgorithm.BUBBLE_SORT,
                NumericField.EMAIL_LENGTH
        );
        assertNotNull(strategy3);

    }

}