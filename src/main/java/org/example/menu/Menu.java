package org.example.menu;

public class Menu {

    public void showMainMenu() {

        System.out.println();
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│              ГЛАВНОЕ МЕНЮ                │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ 1. Ручной ввод пользователей             │");
        System.out.println("│ 2. Случайная генерация пользователей     │");
        System.out.println("│ 3. Загрузка пользователей из файла       │");
        System.out.println("│ 4. Добавить пользователей к существующим │");
        System.out.println("│ 5. Выбор стратегии сортировки            │");
        System.out.println("│ 6. Выполнение сортировки                 │");
        System.out.println("│ 7. Отображение пользователей             │");
        System.out.println("│ 8. Сохранение пользователей в файл       │");
        System.out.println("│ 9. Подсчет вхождения элемента            │");
        System.out.println("│ 0. Выход из программы                    │");
        System.out.println("└──────────────────────────────────────────┘");
        System.out.print("Выберите пункт (1–9) (0. Выход из программы): ");

    }

    public void showSortStrategyMenu(){

        System.out.println("1. Обычная сортировка (по 3 полям)");
        System.out.println("2. Сортировка четных/нечетных значений");
        System.out.println("Выберите тип: ");

    }





}
