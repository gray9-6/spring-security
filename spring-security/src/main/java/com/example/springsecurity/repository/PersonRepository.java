package com.example.springsecurity.repository;

import com.example.springsecurity.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Integer> {
    Person findByUserName(String username);
}
