package com.example.demo.utils.animal;

import com.example.demo.utils.Animal;
import org.springframework.stereotype.Component;

@Component
public class DogAnimal<T extends  Object> implements Animal<T> {
    @Override
    public T testMethod() {

        System.out.println("this a dog");
        return null;
    }
}
