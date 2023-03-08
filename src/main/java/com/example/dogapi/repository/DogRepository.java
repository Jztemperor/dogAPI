package com.example.dogapi.repository;

import com.example.dogapi.contract.IDogRepository;
import com.example.dogapi.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DogRepository implements IDogRepository {
    @Autowired
    private JdbcTemplate  jdbcTemplate;
    @Override
    public int save(Dog dog) {
        return jdbcTemplate.update("INSERT INTO dogs (name,breed,max_size,color) VALUES (?,?,?,?)",
                new Object[] {dog.getName(),dog.getBreed(),dog.getMax_size(),dog.getColor()});
    }

    @Override
    public int update(Dog dog) {
        return jdbcTemplate.update("UPDATE dogs SET name=?,breed=?,max_size=?,color=? WHERE id=?",
                new Object[]{dog.getName(),dog.getBreed(),dog.getMax_size(),dog.getColor(),dog.getId()});
    }

    @Override
    public Dog findById(int id) {
        try{
            Dog dog = jdbcTemplate.queryForObject("SELECT * FROM dogs WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Dog.class), id);

            return dog;
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM dogs WHERE id=?",id);
    }

    @Override
    public List<Dog> findAll() {
        return jdbcTemplate.query("SELECT * FROM dogs", BeanPropertyRowMapper.newInstance(Dog.class));
    }

    @Override
    public List<Dog> findByBreed(String breed) {
        return jdbcTemplate.query("SELECT * FROM dogs WHERE breed LIKE '%"+breed+"%'", BeanPropertyRowMapper.newInstance(Dog.class));
    }
}
