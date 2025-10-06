package com.demo.FirstProject.Basic;

import java.util.List;

public class CollectionsClass {


    public void getListName(List<String> list){
        for(String name:list){
            System.out.println(name.toUpperCase());
        }
    }

    //Stream API
    public void getfilteredName(List<String> list){
        list.stream()
                .filter(name-> name.startsWith("Abhi"))
                .forEach(System.out::println);
    }
}
