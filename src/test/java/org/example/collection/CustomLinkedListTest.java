package org.example.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CustomLinkedListTest {

    private CustomLinkedList<String> list;

    @BeforeEach
    void setUp() {
        list = new CustomLinkedList<>();
    }

    @Nested
    @DisplayName("Constructor tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create empty list")
        void shouldCreateEmptyList() {

            assertTrue(list.isEmpty());
            assertEquals(0, list.size());
            assertEquals("[]", list.toString());

        }

    }

    @Nested
    @DisplayName("Add methods tests")
    class AddTests {

        @Test
        @DisplayName("Should add element to end of list")
        void shouldAddElementToEnd() {

            list.add("first");
            list.add("second");

            assertEquals(2, list.size());
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("[first, second]", list.toString());

        }

        @Test
        @DisplayName("Should add element to beginning of list")
        void shouldAddElementToBeginning() {

            list.add("second");
            list.addFirst("first");

            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("[first, second]", list.toString());

        }

        @Test
        @DisplayName("Should add element at specific index")
        void shouldAddElementAtIndex() {

            list.add("first");
            list.add("third");
            list.add(1, "second");

            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));

        }

        @Test
        @DisplayName("Should add element at index 0 using add method")
        void shouldAddAtIndexZero() {

            list.add("second");
            list.add(0, "first");

            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));

        }

        @Test
        @DisplayName("Should add element at index equal to size")
        void shouldAddAtIndexEqualToSize() {

            list.add("first");
            list.add(1, "second");

            assertEquals(2, list.size());
            assertEquals("second", list.get(1));

        }

        @Test
        @DisplayName("Should throw exception when adding at invalid index")
        void shouldThrowExceptionWhenAddingAtIndexInvalid() {

            list.add("first");
            list.add("second");

            assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "test"));
            assertThrows(IndexOutOfBoundsException.class, () -> list.add(10, "test"));

        }

    }

    @Nested
    @DisplayName("Get and Set methods tests")
    class GetSetTests {

        @Test
        @DisplayName("Should get element at specific index")
        void shouldGetElementAtIndex() {

            list.add("first");
            list.add("second");
            list.add("third");

            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));

        }

        @Test
        @DisplayName("Should throw exception when getting element at invalid index")
        void shouldThrowExceptionWhenGettingAtIndexInvalid() {

            list.add("first");
            list.add("second");
            list.add("third");

            assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));

        }

        @Test
        @DisplayName("Should set element at specific index")
        void shouldSetElementAtIndex() {

            list.add("first");
            list.add("old");
            list.add("third");
            list.set(1, "new");

            assertEquals("new", list.get(1));
            assertEquals("[first, new, third]", list.toString());

        }

        @Test
        @DisplayName("Should throw exception when setting element at invalid index")
        void shouldThrowExceptionWhenSettingAtIndexInvalid() {

            list.add("first");
            list.add("second");
            list.add("third");

            assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "test"));
            assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "test"));

        }

    }

    @Nested
    @DisplayName("Remove methods tests")
    class RemoveTests {

        @Test
        @DisplayName("Should remove element at specific index")
        void shouldRemoveElementAtIndex() {

            list.add("first");
            list.add("second");
            list.add("third");

            String removed = list.remove(1);

            assertEquals("second", removed);
            assertEquals(2, list.size());
            assertEquals("first", list.get(0));
            assertEquals("third", list.get(1));
            assertEquals("[first, third]", list.toString());

        }

        @Test
        @DisplayName("Should remove first element")
        void shouldRemoveFirstElement() {

            list.add("first");
            list.add("second");
            list.add("third");

            String removed = list.remove(0);

            assertEquals("first", removed);
            assertEquals(2, list.size());
            assertEquals("second", list.get(0));
            assertEquals("third", list.get(1));

        }

        @Test
        @DisplayName("Should remove last element")
        void shouldRemoveLastElement() {

            list.add("first");
            list.add("second");
            list.add("third");

            String removed = list.remove(2);

            assertEquals("third", removed);
            assertEquals(2, list.size());
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));

        }

        @Test
        @DisplayName("Should remove element by value")
        void shouldRemoveElementByValue() {

            list.add("first");
            list.add("second");
            list.add("third");

            boolean removed = list.remove("second");

            assertTrue(removed);
            assertEquals(2, list.size());
            assertEquals("first", list.get(0));
            assertEquals("third", list.get(1));

        }

        @Test
        @DisplayName("Should return false when removing non-existing element")
        void shouldReturnFalseWhenRemovingNonExisting() {

            list.add("first");
            list.add("second");

            boolean removed = list.remove("nonexistent");

            assertFalse(removed);
            assertEquals(2, list.size());

        }

        @Test
        @DisplayName("Should remove only first occurrence")
        void shouldRemoveOnlyFirstOccurrence() {

            list.add("first");
            list.add("duplicate");
            list.add("second");
            list.add("duplicate");
            list.add("third");

            boolean removed = list.remove("duplicate");

            assertTrue(removed);
            assertEquals(4, list.size());
            assertEquals("[first, second, duplicate, third]", list.toString());

        }

        @Test
        @DisplayName("Should remove elements matching predicate")
        void shouldRemoveIfPredicateMatches() {

            list.add("apple");
            list.add("banana");
            list.add("grape");
            list.add("cherry");

            boolean removed = list.removeIf(s -> s.contains("a"));

            assertTrue(removed);
            assertEquals(1, list.size());
            assertEquals("cherry", list.get(0));

        }

        @Test
        @DisplayName("Should return false when removeIf removes nothing")
        void shouldReturnFalseWhenRemoveIfRemovesNothing() {

            list.add("apple");
            list.add("banana");

            boolean removed = list.removeIf(s -> s.length() > 10);

            assertFalse(removed);
            assertEquals(2, list.size());

        }

        @Test
        @DisplayName("Should clear list")
        void shouldClearList() {

            list.add("first");
            list.add("second");
            list.add("third");

            list.clear();

            assertTrue(list.isEmpty());
            assertEquals(0, list.size());
            assertEquals("[]", list.toString());

        }

        @Test
        @DisplayName("Should throw exception when removing at invalid index")
        void shouldThrowExceptionWhenRemovingAtIndexInvalid() {

            list.add("first");
            list.add("second");
            list.add("third");

            assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));

        }

    }

    @Nested
    @DisplayName("Search methods tests")
    class SearchTests {

        @Test
        @DisplayName("Should return true when list contains element")
        void shouldReturnTrueWhenContainsElement() {

            list.add("apple");
            list.add("banana");
            list.add("cherry");

            assertTrue(list.contains("banana"));

        }

        @Test
        @DisplayName("Should return false when list does not contain element")
        void shouldReturnFalseWhenDoesNotContainElement() {

            list.add("apple");
            list.add("banana");

            assertFalse(list.contains("cherry"));

        }

        @Test
        @DisplayName("Should find index of first occurrence")
        void shouldFindIndexOfFirstOccurrence() {

            list.add("apple");
            list.add("banana");
            list.add("cherry");
            list.add("banana");

            assertEquals(1, list.indexOf("banana"));
            assertEquals(2, list.indexOf("cherry"));

        }

        @Test
        @DisplayName("Should return -1 when element not found")
        void shouldReturnMinusOneWhenElementNotFound() {

            list.add("apple");
            list.add("banana");

            assertEquals(-1, list.indexOf("cherry"));

        }
    }

    @Nested
    @DisplayName("Size and empty checks tests")
    class SizeEmptyTests {

        @Test
        @DisplayName("Should return correct size")
        void shouldReturnCorrectSize() {

            assertEquals(0, list.size());
            list.add("first");
            assertEquals(1, list.size());
            list.add("second");
            assertEquals(2, list.size());
            list.remove(0);
            assertEquals(1, list.size());

        }

        @Test
        @DisplayName("Should return true when list is empty")
        void shouldReturnTrueWhenEmpty() {
            assertTrue(list.isEmpty());
        }

        @Test
        @DisplayName("Should return false when list is not empty")
        void shouldReturnFalseWhenNotEmpty() {

            list.add("first");
            assertFalse(list.isEmpty());

        }

    }

    @Nested
    @DisplayName("ToArray method tests")
    class ToArrayTests {

        @Test
        @DisplayName("Should convert list to array")
        void shouldConvertToArray() {

            list.add("first");
            list.add("second");
            list.add("third");

            String[] array = list.toArray(new String[0]);

            assertArrayEquals(new String[]{"first", "second", "third"}, array);

        }

        @Test
        @DisplayName("Should fill existing array when it's large enough")
        void shouldFillExistingArray() {

            list.add("first");
            list.add("second");

            String[] array = new String[5];
            String[] result = list.toArray(array);

            assertSame(array, result);
            assertEquals("first", array[0]);
            assertEquals("second", array[1]);
            assertNull(array[2]);

        }

        @Test
        @DisplayName("Should create new array when provided array is too small")
        void shouldCreateNewArrayWhenProvidedTooSmall() {

            list.add("first");
            list.add("second");
            list.add("third");

            String[] array = new String[2];
            String[] result = list.toArray(array);

            assertNotSame(array, result);
            assertArrayEquals(new String[]{"first", "second", "third"}, result);

        }

        @Test
        @DisplayName("Should handle empty list to array")
        void shouldHandleEmptyListToArray() {

            String[] array = list.toArray(new String[0]);
            assertEquals(0, array.length);

        }

    }

    @Nested
    @DisplayName("Stream operations tests")
    class StreamTests {

        @Test
        @DisplayName("Should create sequential stream")
        void shouldCreateSequentialStream() {

            list.add("first");
            list.add("second");
            list.add("third");

            Object[] array = list.stream().toArray();

            assertArrayEquals(new String[]{"first", "second", "third"}, array);

        }

        @Test
        @DisplayName("Should create parallel stream")
        void shouldCreateParallelStream() {

            list.add("first");
            list.add("second");
            list.add("third");

            Object[] array = list.parallelStream().sorted().toArray();

            assertArrayEquals(new String[]{"first", "second", "third"}, array);

        }

        @Test
        @DisplayName("Should filter elements and return new list")
        void shouldFilterElements() {

            list.add("apple");
            list.add("banana");
            list.add("grape");
            list.add("cherry");

            CustomLinkedList<String> filtered = list.filter(s -> s.contains("a"));

            assertEquals(3, filtered.size());
            assertEquals("apple", filtered.get(0));
            assertEquals("banana", filtered.get(1));
            assertEquals("grape", filtered.get(2));
            assertEquals(4, list.size()); // Original unchanged

        }

        @Test
        @DisplayName("Should add all elements from stream")
        void shouldAddAllFromStream() {

            Stream<String> stream = Stream.of("first", "second", "third");

            list.addAll(stream);

            assertEquals(3, list.size());
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));

        }

        @Test
        @DisplayName("Should create list from stream")
        void shouldCreateListFromStream() {

            Stream<String> stream = Stream.of("first", "second", "third");

            CustomLinkedList<String> newList = CustomLinkedList.fromStream(stream);

            assertEquals(3, newList.size());
            assertEquals("first", newList.get(0));
            assertEquals("second", newList.get(1));
            assertEquals("third", newList.get(2));

        }

        @Test
        @DisplayName("Should add all elements from another list")
        void shouldAddAllFromAnotherList() {

            CustomLinkedList<String> other = new CustomLinkedList<>();
            other.add("second");
            other.add("third");

            list.add("first");
            list.addAll(other);

            assertEquals(3, list.size());
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
            assertEquals("third", list.get(2));

        }

        @Test
        @DisplayName("Should apply forEach action")
        void shouldApplyForEachAction() {

            list.add("first");
            list.add("second");
            list.add("third");

            StringBuilder result = new StringBuilder();
            list.forEach(result::append);

            assertEquals("firstsecondthird", result.toString());

        }

    }

    @Nested
    @DisplayName("Iterator tests")
    class IteratorTests {

        @Test
        @DisplayName("Should iterate over all elements")
        void shouldIterateOverAllElements() {

            list.add("first");
            list.add("second");
            list.add("third");

            Iterator<String> iterator = list.iterator();

            assertTrue(iterator.hasNext());
            assertEquals("first", iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals("second", iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals("third", iterator.next());
            assertFalse(iterator.hasNext());

        }

        @Test
        @DisplayName("Should throw exception when next called on empty iterator")
        void shouldThrowExceptionWhenNextOnEmptyIterator() {

            Iterator<String> iterator = list.iterator();

            assertThrows(NoSuchElementException.class, iterator::next);

        }

        @Test
        @DisplayName("Should support enhanced for loop")
        void shouldSupportEnhancedForLoop() {

            list.add("first");
            list.add("second");
            list.add("third");

            java.util.List<String> result = new java.util.ArrayList<>();

            for (String s : list) {
                result.add(s);
            }

            assertEquals(3, result.size());
            assertEquals("first", result.get(0));
            assertEquals("second", result.get(1));
            assertEquals("third", result.get(2));

        }

    }

    @Nested
    @DisplayName("Spliterator tests")
    class SpliteratorTests {

        @Test
        @DisplayName("Should create spliterator with correct characteristics")
        void shouldCreateSpliteratorWithCorrectCharacteristics() {

            list.add("first");
            list.add("second");
            list.add("third");

            Spliterator<String> spliterator = list.spliterator();

            assertTrue(spliterator.hasCharacteristics(Spliterator.ORDERED));
            assertTrue(spliterator.hasCharacteristics(Spliterator.SIZED));
            assertEquals(3, spliterator.estimateSize());

        }

    }

    @Nested
    @DisplayName("Factory methods tests")
    class FactoryMethodsTests {

        @Test
        @DisplayName("Should create list from varargs")
        void shouldCreateListFromVarargs() {

            CustomLinkedList<String> newList = CustomLinkedList.of("first", "second", "third");

            assertEquals(3, newList.size());
            assertEquals("first", newList.get(0));
            assertEquals("second", newList.get(1));
            assertEquals("third", newList.get(2));

        }

        @Test
        @DisplayName("Should create empty list from empty varargs")
        void shouldCreateEmptyListFromEmptyVarargs() {

            CustomLinkedList<String> newList = CustomLinkedList.of();

            assertTrue(newList.isEmpty());
            assertEquals(0, newList.size());

        }

        @Test
        @DisplayName("Should collect stream to CustomLinkedList")
        void shouldCollectStreamToCustomLinkedList() {

            Stream<String> stream = Stream.of("first", "second", "third");

            CustomLinkedList<String> collected = stream.collect(CustomLinkedList.toCustomLinkedList());

            assertEquals(3, collected.size());
            assertEquals("first", collected.get(0));
            assertEquals("second", collected.get(1));
            assertEquals("third", collected.get(2));

        }
    }

    @Nested
    @DisplayName("ToString tests")
    class ToStringTests {

        @Test
        @DisplayName("Should return empty string representation for empty list")
        void shouldReturnEmptyStringRepresentation() {
            assertEquals("[]", list.toString());
        }

        @Test
        @DisplayName("Should return correct string representation")
        void shouldReturnCorrectStringRepresentation() {

            list.add("first");
            list.add("second");
            list.add("third");

            assertEquals("[first, second, third]", list.toString());

        }

        @Test
        @DisplayName("Should handle null values in string representation")
        void shouldHandleNullValuesInStringRepresentation() {

            CustomLinkedList<String> listWithNull = new CustomLinkedList<>();
            listWithNull.add("first");
            listWithNull.add(null);
            listWithNull.add("third");

            assertEquals("[first, null, third]", listWithNull.toString());

        }

    }

    @Nested
    @DisplayName("Edge cases tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle operations on single element")
        void shouldHandleSingleElement() {

            list.add("only");

            assertEquals(1, list.size());
            assertEquals("only", list.get(0));
            assertTrue(list.contains("only"));

            String removed = list.remove(0);
            assertEquals("only", removed);
            assertTrue(list.isEmpty());

        }

        @Test
        @DisplayName("Should handle null elements in list")
        void shouldHandleNullElements() {

            list.add(null);
            list.add("not null");
            list.add(null);

            assertEquals(3, list.size());
            assertNull(list.get(0));
            assertNull(list.get(2));
            assertTrue(list.contains(null));

            // Remove null element (this should work)
            boolean removed = list.remove(null);
            assertTrue(removed);
            assertEquals(2, list.size());
            assertEquals("not null", list.get(0));
            assertNull(list.get(1));

        }

        @Test
        @DisplayName("Should handle duplicate elements")
        void shouldHandleDuplicateElements() {

            list.add("duplicate");
            list.add("unique");
            list.add("duplicate");

            assertEquals(3, list.size());
            assertEquals(0, list.indexOf("duplicate"));

            boolean removed = list.remove("duplicate");
            assertTrue(removed);
            assertEquals(2, list.size());
            assertEquals("unique", list.get(0));
            assertEquals("duplicate", list.get(1));

        }

        @Test
        @DisplayName("Should handle large number of elements")
        void shouldHandleLargeNumberOfElements() {
            for (int i = 0; i < 1000; i++) {
                list.add("element" + i);
            }

            assertEquals(1000, list.size());
            assertEquals("element0", list.get(0));
            assertEquals("element500", list.get(500));
            assertEquals("element999", list.get(999));

        }

    }

}