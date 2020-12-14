package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface UserRepository extends CrudRepository<User, Long> {
    /*
    User findByTitle(String title);
    Iterable<User> findByDeliveredOnBetween(Date date1, Date date2);
     */
}
