package org.example.enums;

/**
 * Enum с числовыми полями для сортировки четных/нечетных значений.
 */
public enum NumericField {

    NAME_LENGTH(1, "Длина имени"),
    PASSWORD_LENGTH(2, "Длина пароля"),
    EMAIL_LENGTH(3, "Длина email");

    private final int code;
    private final String displayName;

    /**
     * Конструктор enum.
     *
     * @param code - числовой код поля
     * @param displayName - отображаемое имя
     */
    NumericField(int code, String displayName) {

        this.code = code;
        this.displayName = displayName;

    }

    /**
     * Возвращает код поля.
     *
     * @return int - код поля
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
     * Находит поле по коду.
     *
     * @param code - код поля
     * @return NumericField - найденное поле или null
     */
    public static NumericField fromCode(int code) {

        for (NumericField field : values()) {

            if (field.getCode() == code) {
                return field;
            }

        }

        return null;

    }

}

