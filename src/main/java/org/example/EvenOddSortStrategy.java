package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenOddSortStrategy implements SortStrategy {
    @Override
    public void sort(List<User> users) {
        List<Integer> evenIndices = new ArrayList<>();
        List<User> evenUsers = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getPassword().length() % 2 == 0) {
                evenIndices.add(i);
                evenUsers.add(user);
            }
        }

        Comparator<User> comparator = Comparator
                .comparingInt((User u) -> u.getPassword().length())
                .thenComparing(User::getName);
        insertionSort(evenUsers, comparator);

        for (int i = 0; i < evenIndices.size(); i++) {
            users.set(evenIndices.get(i), evenUsers.get(i));
        }
    }

    private void insertionSort(List<User> list, Comparator<User> comparator) {
        for (int i = 1; i < list.size(); i++) {
            User key = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    @Override
    public String getStrategyName() {
        return "EvenOddSort (по длине пароля)";
    }
}