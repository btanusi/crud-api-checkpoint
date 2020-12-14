package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/authenticate")
    public Map<String, Object> authenticateUser(@RequestBody Map<String, String> user) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("authenticated", false);
        User checkUser = this.repository.findByEmail(user.get("email"));
        String p1 = checkUser.getPassword();
        String p2 = user.get("password");
        if(p1.equals(p2)){
            json.put("authenticated", true);
            json.put("user", checkUser);
        }
        return json;
    }

    public static void main(String[] args) {
        SpringApplication.run(UsersController.class, args);
    }
}
