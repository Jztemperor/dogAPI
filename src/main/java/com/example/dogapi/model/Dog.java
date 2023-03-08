package com.example.dogapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dog {
    private int id;
    private String name;
    private String breed;
    private double max_size;
    private String color;
}
