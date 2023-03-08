package com.example.dogapi.controller;

import com.example.dogapi.model.Dog;
import com.example.dogapi.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DogController {
    @Autowired
    DogRepository dogRepository;

    @PostMapping("/dogs")
    public ResponseEntity<String> createdDog(@RequestBody Dog dog){
        try{
            dogRepository.save(dog);
            return new ResponseEntity<>("Dog was created successfuly!",HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dogs")
    public ResponseEntity<List<Dog>> getAllDogs(){
        try{
            List<Dog> dogs = dogRepository.findAll();

            if(dogs.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(dogs, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable int id){
        Dog dog = dogRepository.findById(id);

        if(!ObjectUtils.isEmpty(dog)){
            return new ResponseEntity<>(dog,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dogs/breeds/{breed}")
    public ResponseEntity<List<Dog>> getDogsByBreed(@PathVariable String breed){
        List<Dog> dogsByBreed = dogRepository.findByBreed(breed);

        if(!dogsByBreed.isEmpty()){
            return new ResponseEntity<>(dogsByBreed, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @PutMapping("/dogs/{id}")
    public ResponseEntity<String> updateDog(@PathVariable int id, @RequestBody Dog dog){
        Dog currDog = dogRepository.findById(id);
        if(!ObjectUtils.isEmpty(currDog)){
            currDog.setId(id);
            currDog.setName(dog.getName());
            currDog.setBreed(dog.getBreed());
            currDog.setMax_size(dog.getMax_size());
            currDog.setColor(dog.getColor());

            dogRepository.update(currDog);
            return new ResponseEntity<>("Dog updated successfuly!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Dog does not exists!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/dogs/{id}")
    public ResponseEntity<String> deleteDog(@PathVariable int id){
        try{
            int deletedRows = dogRepository.delete(id);
            if(deletedRows == 0){
                return new ResponseEntity<>("Cannot find a dog with this id", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Dog was deleted.", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Cannot delete this dog!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
