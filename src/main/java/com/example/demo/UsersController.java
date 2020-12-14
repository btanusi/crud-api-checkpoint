package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserRepository repository;

    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<User> all() {
        return this.repository.findAll();
    }

    /*
    @GetMapping("/find/{title}")
    public Lesson findTitle(@PathVariable String title) {
        return this.repository.findByTitle(title);
    }

    @GetMapping("/between") //?date1={date1}&date2={date2}
    public Iterable<Lesson> betweenDates(@RequestParam("date1") String date1, @RequestParam("date2") String date2) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date formattedDate1 = format.parse(date1);
        Date formattedDate2 = format.parse(date2);
        return this.repository.findByDeliveredOnBetween(formattedDate1, formattedDate2);
    }

    @GetMapping("/{id}")
    public Optional<Lesson> specific(@PathVariable Long id) {
        return this.repository.findById(id);
    }

    @PostMapping("")
    public Lesson create(@RequestBody Lesson lesson) {
        return this.repository.save(lesson);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.repository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public Lesson update(@PathVariable Long id, @RequestBody Lesson lesson){
        Lesson toUpdate = this.repository.findById(id).get();
        toUpdate.setTitle(lesson.getTitle());
        toUpdate.setDeliveredOn(lesson.getDeliveredOn());
        return this.repository.save(toUpdate);
    }
    
     */
    
    public static void main(String[] args) {
        SpringApplication.run(UsersController.class, args);
    }
}
