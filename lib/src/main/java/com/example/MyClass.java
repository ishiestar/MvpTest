package com.example;

import java.util.function.Consumer;

public class MyClass {
    public static void main(String[] args) {
        Consumer<String> c = System.out::println;
        c.accept("Hello World!");
    }
}
