package org.example;

public class Menu {

    public void showMainMenu(){
        System.out.println("1. Ручной ввод.");
        System.out.println("2. Случайная генерация файла.");
        System.out.println("3. Загрузка из файла.");
        System.out.println("4. Выбор стратегии сортировки.");
        System.out.println("5. Выполнение сортировки.");
        System.out.println("6. Отображение пользователей.");
        System.out.println("7. Сохранение в файл.");
        System.out.println("8. Выход из программы.");
    }

    public void showSortStrategyMenu(){
        System.out.println("1. По имени.");
        System.out.println("2. По паролю.");
        System.out.println("3. По почте.");
    }
}
