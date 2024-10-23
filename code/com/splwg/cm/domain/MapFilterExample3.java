package com.splwg.cm.domain;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapFilterExample3 {

    // Generic Map filterByValue, with predicate
    public static <Integer, String> Set<Entry<Integer, String>> filterByValue(Map<Integer, String> map, Predicate<String> predicate) {
        return map.entrySet()
                .stream()
                .filter(x -> predicate.test(x.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).entrySet();
    }

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Iinode");
        map.put(4, "Linode");
        map.put(7, "Linode");
        map.put(2, "heroku");
        map.put(3, "aws");

        //  {1=linode.com}
        Set<Entry<Integer, String>> filteredMap = filterByValue(map, x -> x.equalsIgnoreCase("Linode") && x.startsWith("L ") );
        System.out.println(filteredMap);

        
       
        
        // {1=linode, 3=aws}
//        Map<Integer, String> filteredMap2 = filterByValue(map, x -> (x.contains("aws") || x.contains("linode")));
//        System.out.println(filteredMap2);
//
//        // {3=aws}
//        Map<Integer, String> filteredMap3 = filterByValue(map, x -> (x.length() <= 5));
//        System.out.println(filteredMap3);

    }

}
