package com.agh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class test {
    public static void main(String[] args){
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> a = Arrays.asList(1, 1, 1);
        List<Integer> b = Arrays.asList(2,1,2);
        List<Integer> c = Arrays.asList(3,2,1);
        results.add(c);
        results.add(b);
        results.add(a);
        Comparator<List<Integer>> comparing = Comparator.comparing(l -> l.get(1));
        comparing = comparing.thenComparing(l -> l.get(2));
        results.sort(comparing);

        System.out.println(results);
    }


}
