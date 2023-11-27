package com.example.bigdata.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserGenerator {

    private static final String[] FIRST_NAMES = {"Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hannah", "Ivy", "Jack"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Lee", "Davis", "Wong", "Chen", "Kim", "Patel", "Garcia"};


    public static List<User> generateUsers(int count) {

        List<User> users = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            User user = new User();

            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

            user.setName(firstName + " " + lastName);
            int age = random.nextInt(41) + 10;
            user.setAge(age);

            String[] capitals = {"Tokyo", "London", "Paris", "New York", "Beijing", "Moscow", "Sydney", "Cairo", "Rome", "Rio de Janeiro"};
            String capital = capitals[random.nextInt(capitals.length)];
            user.setAddress(capital);

            users.add(user);

        }
        return users;
    }

}
