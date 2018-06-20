package com.example;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class MyNewClass {
    public static void main(String[] args) {
        System.out.println();
        MyNewClass myClass = new MyNewClass();
        List<String> stringList = Arrays.asList("Rahul", "Pandey", "Ishita", "Sinha");
        Observable.fromIterable(stringList).filter(s -> s.startsWith("R")).forEach(System.out::println);
        Observable.fromIterable(stringList).map(s -> "name: " + s).subscribe(System.out::println);
        Observable<? extends Number> just = Observable.just(
                myClass.add(10, 20),
                myClass.subtract(20, 10),
                myClass.multiply(10, 20),
                myClass.divide(20, 10)
        );
        just.map(x -> "output=" + String.valueOf(x)).subscribe(System.out::println);
        myClass.getObservable().map(x -> "output=" + String.valueOf(x)).subscribe(System.out::println);
        myClass.printSeries(16);
    }


    public void printSeries(int n) {
//        1 2 2 1 3 1 2 2 1 3 1  2  2  1  3  1
//        0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 

        int count = 1, prevCount = 0;
        for (int i = 0; i < n; i++) {
            System.out.print(count + " ");
            if (count == 1) {
                if (prevCount == 2) {
                    prevCount = count;
                    count = 3;
                } else prevCount = count++;
            } else if (count == 3) {
                prevCount = count;
                count = 1;
            } else if (count == 2) {
                if (prevCount == 2) {
                    prevCount = count;
                    count = 1;
                } else {
                    prevCount = count;
                    count = 2;
                }
            }
        }
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int from, int subtractor) {
        return from - subtractor;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int dividend, int divisor) {
        return dividend / divisor;
    }

    public Observable<Object> getObservable() {
        return Observable.create(e -> {
            e.onNext(add(10, 20));
            e.onNext(subtract(20, 10));
            e.onNext(multiply(10, 20));
            e.onNext(divide(20, 10));
            e.onComplete();
        });
    }
}
