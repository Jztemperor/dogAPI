package com.example.dogapi.contract;

import com.example.dogapi.model.Dog;

import java.util.List;

public interface IDogRepository {
    int save(Dog dog);
    int update(Dog dog);
    Dog findById(int id);
    int delete(int id);
    List<Dog> findAll();
    List<Dog> findByBreed(String breed);
}
