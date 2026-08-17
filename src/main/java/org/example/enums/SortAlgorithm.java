package org.example.enums;

public enum SortAlgorithm {

    BUBBLE_SORT(1, "Bubble Sort (пузырьковая)"),
    QUICK_SORT(2, "Quick Sort (быстрая)"),
    INSERTION_SORT(3, "Insertion Sort (вставками)");

    private final int code;
    private final String displayName;

    /**
     * Конструктор enum.
     *
     * @param code        - числовой код алгоритма
     * @param displayName - отображаемое имя
     */
    SortAlgorithm(int code, String displayName) {

        this.code = code;
        this.displayName = displayName;

    }

    /**
     * Возвращает код алгоритма.
     *
     * @return int - код алгоритма
     */
    public int getCode() {
        return code;
    }

    /**
     * Возвращает отображаемое имя.
     *
     * @return String - отображаемое имя
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Находит алгоритм по коду.
     *
     * @param code - код алгоритма
     * @return SortAlgorithm - найденный алгоритм или null
     */
    public static SortAlgorithm fromCode(int code) {

        for (SortAlgorithm algorithm : values()) {

            if (algorithm.getCode() == code) {
                return algorithm;
            }

        }

        return null;

    }

}
