# 🚀 User Sorting Application

Приложение для управления пользователями с возможностью сортировки различными алгоритмами.

---

## 📋 Описание проекта

Консольное приложение для управления коллекцией пользователей с реализацией различных алгоритмов сортировки. Приложение позволяет загружать данные из разных источников, сортировать их различными способами и сохранять результаты.

### Основной функционал:
- ✅ Загрузка пользователей (ручной ввод, генерация, из файла)
- ✅ Добавление пользователей к существующей коллекции
- ✅ Сортировка 6 алгоритмами (Bubble, Insertion, Quick, Even/Odd варианты)
- ✅ Отображение пользователей в виде таблицы
- ✅ Сохранение в файл (с добавлением)
- ✅ Многопоточный подсчет вхождений
- ✅ Кастомная коллекция CustomLinkedList
- ✅ Паттерн Strategy для алгоритмов сортировки
- ✅ Паттерн Builder для создания пользователей

---

## 🛠️ Технологии

- **Java 17** (Corretto 17.0.19)
- **JUnit 5** - тестирование
- **Maven** - сборка проекта
- **Git** - контроль версий

---

## 📂 Структура проекта

```
team-4-aston/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           ├── Application.java
│   │   │           ├── collection/
│   │   │           │   └── CustomLinkedList.java
│   │   │           ├── comparator/
│   │   │           │   └── UserComparator.java
│   │   │           ├── data/
│   │   │           │   ├── DataInputHandler.java
│   │   │           │   └── FileHandler.java
│   │   │           ├── entity/
│   │   │           │   └── User.java
│   │   │           ├── enums/
│   │   │           │   ├── MenuOption.java
│   │   │           │   ├── NumericField.java
│   │   │           │   └── SortAlgorithm.java
│   │   │           ├── menu/
│   │   │           │   ├── Menu.java
│   │   │           │   ├── MenuFileHandler.java
│   │   │           │   └── UserTablePrinter.java
│   │   │           ├── sort/
│   │   │           │   ├── SortStrategy.java
│   │   │           │   ├── BubbleSortStrategy.java
│   │   │           │   ├── InsertionSortStrategy.java
│   │   │           │   ├── QuickSortStrategy.java
│   │   │           │   ├── EvenOddBubbleSortStrategy.java
│   │   │           │   ├── EvenOddInsertionSortStrategy.java
│   │   │           │   ├── EvenOddQuickSortStrategy.java
│   │   │           │   └── factory/
│   │   │           │       └── EvenOddSortFactory.java
│   │   │           └── validator/
│   │   │               └── UserValidator.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── org/
│               └── example/
│                   ├── ApplicationTest.java
│                   ├── collection/
│                   │   └── CustomLinkedListTest.java
│                   ├── comparator/
│                   │   └── UserComparatorTest.java
│                   ├── data/
│                   │   ├── DataInputHandlerTest.java
│                   │   └── FileHandlerTest.java
│                   ├── entity/
│                   │   └── UserTest.java
│                   ├── menu/
│                   │   ├── MenuFileHandlerTest.java
│                   │   └── UserTablePrinterTest.java
│                   ├── sort/
│                   │   ├── BubbleSortStrategyTest.java
│                   │   ├── InsertionSortStrategyTest.java
│                   │   ├── QuickSortStrategyTest.java
│                   │   ├── EvenOddBubbleSortStrategyTest.java
│                   │   ├── EvenOddInsertionSortStrategyTest.java
│                   │   ├── EvenOddQuickSortStrategyTest.java
│                   │   └── factory/
│                   │       └── EvenOddSortFactoryTest.java
│                   └── validator/
│                       └── UserValidatorTest.java
├── pom.xml
└── README.md
```

---

## 🚀 Запуск приложения

### Требования:
- Java 17+
- Maven 3.6+

### Сборка и запуск:

```bash
# Клонирование репозитория
git clone https://github.com/your-username/team-4-aston.git
cd team-4-aston

# Сборка проекта
mvn clean compile

# Запуск приложения
mvn exec:java -Dexec.mainClass="org.example.Application"

# Или через JAR
mvn clean package
java -jar target/team-4-aston-1.0-SNAPSHOT.jar
```

---

## 📖 Меню приложения

```
🚀 Запуск приложения "Сортировка пользователей"
============================================================

Меню:
1. Ручной ввод пользователей
2. Генерация случайных пользователей
3. Загрузка из файла
4. Добавить пользователей
5. Выбрать стратегию сортировки
6. Выполнить сортировку
7. Показать пользователей
8. Сохранить в файл
9. Подсчитать количество
0. Выход
```

---

## 🔧 Алгоритмы сортировки

### Обычные сортировки:
- **Bubble Sort** - пузырьковая сортировка
- **Insertion Sort** - сортировка вставками
- **Quick Sort** - быстрая сортировка

### Сортировки с четными значениями:
- **Even Odd Bubble Sort** - сортирует только четные элементы
- **Even Odd Insertion Sort** - сортирует только четные элементы
- **Even Odd Quick Sort** - сортирует только четные элементы

---

## 👤 Класс User

```java
User user = new User.Builder()
        .name("Алексей")
        .password("securePass123")
        .email("alexey@mail.com")
        .build();
```

### Валидация:
- ✅ Имя: не null, не пустое, максимум 50 символов
- ✅ Пароль: не null, минимум 6 символов
- ✅ Email: не null, формат с @

---

## 📁 Формат файла

Файл должен содержать данные в формате:

```
Имя;Пароль;Email
Алексей Смирнов;securePass123;alexey@mail.ru
Иван Петров;pass4567;ivan@mail.ru
```

---

## 🧪 Запуск тестов

```bash
# Запуск всех тестов
mvn test

# Запуск конкретного тестового класса
mvn test -Dtest=UserTest

# Запуск с отчетом
mvn test surefire-report:report
```

---

## 👥 Участники проекта

| Участник                                            | Роль         | Ветка          |
|-----------------------------------------------------|--------------|----------------|
| [Коренев Владимир](https://github.com/C0sm0Black)   | Team Lead    | core-model     |
| [Присталов Ярослав](https://github.com/Ogr2049)     | Разработчик  | data-input     |
| [Колодяжный Валерий](https://github.com/Valerio33)  | Разработчик  | sort-straties  |
| [Чижов Никита](https://github.com/Valerio33)        | Разработчик  | file-output-ui |

---

## 🔄 Ветки и мержи

```bash
# Создание ветки
git checkout -b feature/your-feature

# После завершения работы
git add .
git commit -m "Описание изменений"
git push origin feature/your-feature

# Создание Pull Request в main
# После ревью и апрува - мерж
```

---

## 📊 Диаграмма классов (упрощенная)

```
┌─────────────────────────────────────────────┐
│              Application                    │
├─────────────────────────────────────────────┤
│ - users: CustomLinkedList<User>             │
│ - sortStrategy: SortStrategy                │
│ + run(): void                               │
│ + main(String[]): void                      │
└─────────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────┐
│         CustomLinkedList<T>                 │
├─────────────────────────────────────────────┤
│ - head: Node<T>                             │
│ - tail: Node<T>                             │
│ - size: int                                 │
│ + add(T): void                              │
│ + get(int): T                               │
│ + remove(int): T                            │
│ + size(): int                               │
│ + isEmpty(): boolean                        │
└─────────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────┐
│               User                          │
├─────────────────────────────────────────────┤
│ - name: String                              │
│ - password: String                          │
│ - email: String                             │
│ + getName(): String                         │
│ + getPassword(): String                     │
│ + getEmail(): String                        │
└─────────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────┐
│         SortStrategy (interface)            │
├─────────────────────────────────────────────┤
│ + sort(CustomLinkedList<User>, Comparator): │
│ + getStrategyName(): String                 │
└─────────────────────────────────────────────┘
                    ▲
        ┌───────────┼───────────┐
        │           │           │
┌───────┴───────┐ ┌─┴───────────┴┐ ┌──────────┴───────┐
│BubbleSort     │ │InsertionSort │ │QuickSort         │
└───────────────┘ └──────────────┘ └──────────────────┘
```

---

## 📝 Пример использования

```bash
# Запуск приложения
mvn exec:java -Dexec.mainClass="org.example.Application"

# Выбор пункта 2 - Генерация случайных пользователей
Введите количество пользователей: 5

# Выбор пункта 5 - Выбор стратегии
Выберите тип сортировки:
1. Обычная
2. Четные/Нечетные
Выберите: 1

Доступные алгоритмы:
1. Bubble Sort
2. Insertion Sort  
3. Quick Sort
Выберите: 3

# Выбор пункта 6 - Выполнить сортировку

# Выбор пункта 7 - Показать пользователей

# Выбор пункта 8 - Сохранить в файл
Введите имя файла: output/users.txt

# Выбор пункта 0 - Выход
```

---

## 📋 Реализованные требования

### Обязательные:
- ✅ Паттерн Strategy для сортировки
- ✅ Паттерн Builder для User
- ✅ Валидация данных
- ✅ Сортировка по всем 3 полям
- ✅ Кастомная коллекция
- ✅ Компаратор для сортировки
- ✅ Заполнение через Stream API
- ✅ Работа в цикле до выхода
- ✅ Выбор длины массива
- ✅ Загрузка из файла, рандом, вручную

### Дополнительные:
- ✅ Even/Odd сортировки
- ✅ Сохранение в файл (append)
- ✅ Многопоточный подсчет вхождений
- ✅ Юнит-тесты

---

## 🐛 Известные проблемы

1. При работе с файлами необходимо указывать полный путь или путь относительно корня проекта
2. Для корректной работы с русскими символами файл должен быть в UTF-8

---


## 🤝 Контакты

По всем вопросам обращаться к участникам проекта.