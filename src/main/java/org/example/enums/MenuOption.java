package org.example.enums;

/**
 * Перечисление пунктов главного меню.
 */
public enum MenuOption {

    EXIT(0, "Выход"),
    MANUAL_INPUT(1, "Ручной ввод пользователей"),
    RANDOM_INPUT(2, "Генерация случайных пользователей"),
    FILE_INPUT(3, "Загрузка из файла"),
    ADD_USERS(4, "Добавить пользователей"),
    SELECT_STRATEGY(5, "Выбрать стратегию сортировки"),
    EXECUTE_SORT(6, "Выполнить сортировку"),
    SHOW_USERS(7, "Показать пользователей"),
    SAVE_TO_FILE(8, "Сохранить в файл"),
    COUNT_OCCURRENCES(9, "Подсчитать количество");

    private final int code;
    private final String description;

    MenuOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Получить пункт меню по коду.
     *
     * @param code - код пункта
     * @return MenuOption - пункт меню
     * @throws IllegalArgumentException если код не найден
     */
    public static MenuOption fromCode(int code) {

        for (MenuOption option : values()) {

            if (option.code == code) {
                return option;
            }

        }

        throw new IllegalArgumentException("Неизвестный код меню: " + code);

    }

    /**
     * Проверяет, существует ли пункт меню с указанным кодом.
     *
     * @param code - код для проверки
     * @return boolean - true если существует
     */
    public static boolean isValidCode(int code) {

        for (MenuOption option : values()) {

            if (option.code == code) {
                return true;
            }

        }

        return false;

    }

}