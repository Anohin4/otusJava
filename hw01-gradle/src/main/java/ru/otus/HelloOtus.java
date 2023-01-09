package ru.otus;

import com.google.common.collect.Lists;
import java.util.ArrayList;

public class HelloOtus {

    public static void main(String[] args) {
        ArrayList<Integer> integers = Lists.newArrayList(3, null, 4, 7,
                8, null, 7);
        System.out.println(integers);
    }
}