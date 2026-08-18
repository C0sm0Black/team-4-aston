package org.example.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class CustomLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Внутренний класс узла списка.
     */
    private static class Node<T> {

        T data;
        Node<T> next;
        Node<T> prev;

        /**
         * Конструктор узла.
         *
         * @param data - данные узла
         */
        Node(T data) {
            this.data = data;
        }

    }

    /**
     * Конструктор по умолчанию.
     * Создает пустой список.
     */
    public CustomLinkedList() {

        this.head = null;
        this.tail = null;
        this.size = 0;

    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element - элемент для добавления
     */
    public void add(T element) {

        Node<T> newNode = new Node<>(element);

        if (head == null) {

            head = newNode;
            tail = newNode;

        } else {

            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;

        }

        size++;

    }

    /**
     * Добавляет элемент в начало списка.
     *
     * @param element - элемент для добавления
     */
    public void addFirst(T element) {

        Node<T> newNode = new Node<>(element);

        if (head == null) {

            head = newNode;
            tail = newNode;

        } else {

            newNode.next = head;
            head.prev = newNode;
            head = newNode;

        }

        size++;

    }

    /**
     * Добавляет элемент по указанному индексу.
     *
     * @param index   - индекс для вставки
     * @param element - элемент для добавления
     * @throws IndexOutOfBoundsException - если индекс некорректен
     */
    public void add(int index, T element) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            addFirst(element);
            return;
        }

        if (index == size) {

            add(element);
            return;

        }

        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(element);

        newNode.prev = current.prev;
        newNode.next = current;
        current.prev.next = newNode;
        current.prev = newNode;

        size++;

    }

    /**
     * Получает элемент по индексу.
     *
     * @param index - индекс элемента
     * @return T - элемент
     * @throws IndexOutOfBoundsException - если индекс некорректен
     */
    public T get(int index) {
        return getNode(index).data;
    }

    /**
     * Устанавливает элемент по индексу.
     *
     * @param index   - индекс элемента
     * @param element - новое значение
     * @throws IndexOutOfBoundsException - если индекс некорректен
     */
    public void set(int index, T element) {

        Node<T> node = getNode(index);
        node.data = element;

    }

    /**
     * Удаляет узел из списка.
     *
     * @param node - узел для удаления
     * @return T - данные удаленного узла
     */
    private T removeNode(Node<T> node) {

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        size--;
        return node.data;

    }

    /**
     * Удаляет элемент по индексу.
     *
     * @param index - индекс элемента
     * @return T - удаленный элемент
     * @throws IndexOutOfBoundsException - если индекс некорректен
     */
    public T remove(int index) {

        Node<T> node = getNode(index);
        return removeNode(node);

    }

    /**
     * Удаляет первое вхождение элемента.
     *
     * @param element - элемент для удаления
     * @return boolean - true если элемент удален
     */
    public boolean remove(T element) {

        Node<T> current = head;

        while (current != null) {

            // Безопасная проверка на null
            if (element == null) {

                if (current.data == null) {

                    removeNode(current);
                    return true;

                }

            } else {

                if (element.equals(current.data)) {

                    removeNode(current);
                    return true;

                }

            }

            current = current.next;

        }

        return false;

    }


    /**
     * Удаляет все элементы, удовлетворяющие условию.
     *
     * @param filter - условие для удаления
     * @return boolean - true если хотя бы один элемент удален
     */
    public boolean removeIf(Predicate<? super T> filter) {

        boolean removed = false;
        Node<T> current = head;

        while (current != null) {

            Node<T> next = current.next;

            if (filter.test(current.data)) {

                removeNode(current);
                removed = true;

            }

            current = next;

        }

        return removed;

    }

    /**
     * Очищает список.
     */
    public void clear() {

        head = null;
        tail = null;
        size = 0;

    }

    /**
     * Проверяет, содержит ли список элемент.
     *
     * @param element - элемент для проверки
     * @return boolean - true если элемент найден
     */
    public boolean contains(T element) {

        return indexOf(element) != -1;

    }

    /**
     * Возвращает индекс первого вхождения элемента.
     *
     * @param element - элемент для поиска
     * @return int - индекс элемента или -1
     */
    public int indexOf(T element) {

        Node<T> current = head;
        int index = 0;

        while (current != null) {

            if (element == null) {

                if (current.data == null) {
                    return index;
                }

            } else {

                if (element.equals(current.data)) {
                    return index;
                }

            }

            current = current.next;
            index++;

        }

        return -1;

    }

    /**
     * Возвращает размер списка.
     *
     * @return int - количество элементов
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуст ли список.
     *
     * @return boolean - true если список пуст
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Преобразует список в массив.
     *
     * @param array - массив для заполнения
     * @return T[] - массив элементов
     */
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] array) {

        if (array.length < size) {
            array = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), size);
        }

        Node<T> current = head;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    /**
     * Создает последовательный поток элементов.
     *
     * @return Stream<T> - последовательный поток
     */
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Создает параллельный поток элементов.
     *
     * @return Stream<T> - параллельный поток
     */
    public Stream<T> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    /**
     * Возвращает узел по индексу.
     *
     * @param index - индекс узла
     * @return Node<T> - узел
     * @throws IndexOutOfBoundsException - если индекс некорректен
     */
    private Node<T> getNode(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current;

        if (index < size / 2) {

            current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

        } else {

            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }

        }

        return current;
    }

    /**
     * Возвращает итератор по списку.
     *
     * @return Iterator<T> - итератор
     */
    @Override
    public Iterator<T> iterator() {

        return new Iterator<>() {

            private CustomLinkedList.Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                T data = current.data;
                current = current.next;
                return data;

            }

        };

    }

    /**
     * Применяет действие к каждому элементу.
     *
     * @param action - действие для выполнения
     */
    public void forEach(Consumer<? super T> action) {

        Node<T> current = head;

        while (current != null) {

            action.accept(current.data);
            current = current.next;

        }

    }

    /**
     * Создает список из потока элементов.
     *
     * @param stream - поток элементов
     * @return CustomLinkedList<T> - новый список
     */
    public static <T> CustomLinkedList<T> fromStream(Stream<T> stream) {
        return stream.collect(toCustomLinkedList());
    }

    /**
     * Создает список из массива элементов.
     *
     * @param elements - массив элементов
     * @return CustomLinkedList<T> - новый список
     */
    @SafeVarargs
    public static <T> CustomLinkedList<T> of(T... elements) {

        CustomLinkedList<T> list = new CustomLinkedList<>();

        for (T element : elements) {
            list.add(element);
        }
        return list;

    }

    /**
     * Возвращает коллектор для сбора в CustomLinkedList.
     *
     * @return Collector<T, ?, CustomLinkedList<T>> - коллектор
     */
    public static <T> Collector<T, ?, CustomLinkedList<T>> toCustomLinkedList() {

        return Collector.of(

                CustomLinkedList::new,
                CustomLinkedList::add,
                (left, right) -> {

                    left.addAll(right);
                    return left;

                },

                Collector.Characteristics.IDENTITY_FINISH

        );

    }

    /**
     * Фильтрует элементы и возвращает новую коллекцию.
     *
     * @param predicate - условие фильтрации
     * @return CustomLinkedList<T> - отфильтрованная коллекция
     */
    public CustomLinkedList<T> filter(Predicate<? super T> predicate) {

        return stream()
                .filter(predicate)
                .collect(toCustomLinkedList());

    }


    /**
     * Добавляет все элементы из потока.
     *
     * @param stream - поток элементов
     */
    public void addAll(Stream<T> stream) {
        stream.forEach(this::add);
    }

    /**
     * Добавляет все элементы из другой коллекции.
     *
     * @param other - другая коллекция
     */
    public void addAll(CustomLinkedList<T> other) {

        for (T element : other) {
            add(element);
        }

    }

    /**
     * Возвращает сплитератор по списку.
     *
     * @return Spliterator<T> - сплитератор
     */
    @Override
    public Spliterator<T> spliterator() {

        return Spliterators.spliterator(iterator(), size,
                Spliterator.ORDERED | Spliterator.SIZED);

    }

    /**
     * Возвращает строковое представление списка.
     *
     * @return String - строковое представление
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;

        while (current != null) {

            sb.append(current.data);

            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;

        }

        sb.append("]");
        return sb.toString();

    }

}
