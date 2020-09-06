package com.example.demo.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GenericAnimal {


    private Map<Class<?>, Animal<?>>  map;

    @Autowired
    public  GenericAnimal(Set<Animal<?>>   animals ){


        Map<Class<?>, Animal<?>>  newMap= new HashMap<>(animals.size());


        animals.forEach( animal -> newMap.put(animal.getClass(),animal));


        this.map= Collections.unmodifiableMap(newMap);





    }
}
