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
        System.out.println("│ 4. Выбор стратегии сортировки            │");
        System.out.println("│ 5. Выполнение сортировки                 │");
        System.out.println("│ 6. Отображение пользователей             │");
        System.out.println("│ 7. Сохранение пользователей в файл       │");
        System.out.println("│ 8. Подсчет вхождения элемента            │");
        System.out.println("│ 9. Выход из программы                    │");
        System.out.println("└──────────────────────────────────────────┘");
        System.out.print("Выберите пункт (1–9): ");
    }

    public void showSortStrategyMenu(){
        System.out.println("1. По имени.");
        System.out.println("2. По паролю.");
        System.out.println("3. По почте.");
    }
}
