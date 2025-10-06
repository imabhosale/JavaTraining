package com.demo.FirstProject;

import com.demo.FirstProject.Basic.CollectionsClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class FirstProjectApplication {

	public static void main(String[] args) {

        SpringApplication.run(FirstProjectApplication.class, args);
        System.out.println("First Project Application Started");
//        CollectionsClass ob=new CollectionsClass();
//        ArrayList<String> list=new ArrayList<>();
//        list.add("Abhishek");
//        list.add("Aniket");
//        list.add("Kunal");
//        list.add("raju");
//        list.add("prem");
//        list.add("ram");
//
//        ob.getListName(list);
//        System.out.println("here is the filtered List");
//        ob.getfilteredName(list);
	}

}
