package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @PostMapping("")
    public User create(@RequestBody User user) {
        return this.repository.save(user);
    }

    @GetMapping("/{id}")
    public Optional<User> specific(@PathVariable Long id) {
        return this.repository.findById(id);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user){
        User toUpdate = this.repository.findById(id).get();
        toUpdate.setEmail(user.getEmail());
        toUpdate.setPassword(user.getPassword());
        return this.repository.save(toUpdate);
    }

    @DeleteMapping("/{id}")
    public Map<String, Integer> delete(@PathVariable Long id) {
        this.repository.deleteById(id);
        int counter = 0;
        for (Object i : this.repository.findAll()) {
            counter++;
        }
        Map<String, Integer> count = new HashMap<String, Integer>();
        count.put("count", counter);
        return count;
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
