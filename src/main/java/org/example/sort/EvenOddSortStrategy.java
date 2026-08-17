//package org.example.sort;
//
//import org.example.collection.CustomLinkedList;
//import org.example.entity.User;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//
//public class EvenOddSortStrategy implements SortStrategy {
//
//    @Override
//    public void sort(CustomLinkedList<User> users, Comparator<User> comparator) {
//
//        List<Integer> evenIndices = new ArrayList<>();
//        CustomLinkedList<User> evenUsers = new CustomLinkedList<>();
//
//        for (int i = 0; i < users.size(); i++) {
//
//            User user = users.get(i);
//
//            if (user.getPassword().length() % 2 == 0) {
//                evenIndices.add(i);
//                evenUsers.add(user);
//            }
//
//        }
//
//        insertionSort(evenUsers, comparator);
//
//        for (int i = 0; i < evenIndices.size(); i++) {
//            users.set(evenIndices.get(i), evenUsers.get(i));
//        }
//
//    }
//
//    private void insertionSort(CustomLinkedList<User> list, Comparator<User> comparator) {
//
//        for (int i = 1; i < list.size(); i++) {
//
//            User key = list.get(i);
//            int j = i - 1;
//
//            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
//
//                list.set(j + 1, list.get(j));
//                j--;
//
//            }
//
//            list.set(j + 1, key);
//
//        }
//
//    }
//
//    @Override
//    public String getStrategyName() {
//        return "EvenOddSort";
//    }
//
//}