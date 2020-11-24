package com.example.demo.model.animal;

import com.example.demo.utils.Animal;
import org.springframework.stereotype.Component;

@Component
public class CatAnimal<T extends  Object> implements Animal<T> {
    @Override
    public T testMethod() {

        System.out.println("this a  cat");
        return null;
    }
}
