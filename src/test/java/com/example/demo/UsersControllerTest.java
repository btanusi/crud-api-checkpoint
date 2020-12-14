package com.example.demo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository repository;

    @Test
    @Order(1)
    @Transactional
    @Rollback
    public void testGetLesson() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("bob@example.com");
        user.setPassword("bob");
        repository.save(user);

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("eliza@example.com");
        user2.setPassword("eliza");
        repository.save(user2);

        MockHttpServletRequestBuilder request = get("/users")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(1) ))
                .andExpect(jsonPath("$[0].email", equalTo("bob@example.com") ))
                .andExpect(jsonPath("$[1].id", equalTo(2) ))
                .andExpect(jsonPath("$[1].email", equalTo("eliza@example.com") ))
        ;
    }

    @Test
    @Order(2)
    @Transactional
    @Rollback
    public void testCreateLesson() throws Exception {
        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"john@example.com\",\"password\": \"something-secret\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(3)))
                .andExpect(jsonPath("$.email", equalTo("john@example.com") ))
        ;
    }

    @Test
    @Order(3)
    @Transactional
    @Rollback
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setId(4L);
        user.setEmail("john@example.com");
        user.setPassword("john");
        repository.save(user);

        MockHttpServletRequestBuilder request = get("/users/4")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(4)))
                .andExpect(jsonPath("$.email", equalTo("john@example.com") ))
        ;
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback
    public void testPatchUserWithPassword() throws Exception {
        User user = new User();
        user.setId(5L);
        user.setEmail("eliza@example.com");
        user.setPassword("eliza");
        repository.save(user);

        MockHttpServletRequestBuilder request = patch("/users/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"john@example.com\",\"password\": \"1234\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(5) ))
                .andExpect(jsonPath("$.email", equalTo("john@example.com") ));
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback
    public void testPatchUser() throws Exception {
        User user = new User();
        user.setId(6L);
        user.setEmail("eliza@example.com");
        user.setPassword("eliza");
        repository.save(user);

        MockHttpServletRequestBuilder request = patch("/users/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"john@example.com\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(6) ))
                .andExpect(jsonPath("$.email", equalTo("john@example.com") ));
    }

    @Test
    @Order(6)
    @Transactional
    @Rollback
    public void testDeleteUserById() throws Exception {
        User user = new User();
        user.setId(7L);
        user.setEmail("eliza@example.com");
        user.setPassword("eliza");
        repository.save(user);

        User user2 = new User();
        user2.setId(8L);
        user2.setEmail("eliza@example.com");
        user2.setPassword("eliza");
        repository.save(user2);

        MockHttpServletRequestBuilder request = delete("/users/7")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", equalTo(1) ))
        ;
    }


    /*
    @Test
    @Transactional
    @Rollback
    public void testCreateLesson() throws Exception {
        MockHttpServletRequestBuilder request = post("/lessons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"MyTitle\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("MyTitle") ));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetLessonBetweenDates() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(2L);
        lesson.setTitle("Dependency Injection");
        lesson.setDeliveredOn(new Date(2014-1900, 3-1, 17));
        repository.save(lesson);

        Lesson lesson2 = new Lesson();
        lesson2.setId(3L);
        lesson2.setTitle("Transactions");
        lesson2.setDeliveredOn(new Date(2015-1900, 3-1, 17));
        repository.save(lesson2);

        MockHttpServletRequestBuilder request = get("/lessons/between?date1=2014-01-01&date2=2017-12-31")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(2)))
                .andExpect(jsonPath("$[0].title", equalTo("Dependency Injection")))
                .andExpect(jsonPath("$[0].deliveredOn", equalTo("2014-03-17")))
                .andExpect(jsonPath("$[1].id", equalTo(3)))
                .andExpect(jsonPath("$[1].title", equalTo("Transactions")))
                .andExpect(jsonPath("$[1].deliveredOn", equalTo("2015-03-17")));
    }

    @Test
    @Transactional
    @Rollback
    public void testPatchLesson() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(4L);
        lesson.setTitle("Requests and Responses");
        repository.save(lesson);
        MockHttpServletRequestBuilder request = patch("/lessons/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"MyTitle\",\"deliveredOn\": \"2017-04-12\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(4) ))
                .andExpect(jsonPath("$.title", equalTo("MyTitle") ));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetLessonByTitle() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(5L);
        lesson.setTitle("SQL");
        repository.save(lesson);

        MockHttpServletRequestBuilder request = get("/lessons/find/SQL")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(5) ))
                .andExpect(jsonPath("$.title", equalTo("SQL")));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetLesson() throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(6L);
        lesson.setTitle("Requests and Responses");
        repository.save(lesson);

        MockHttpServletRequestBuilder request = get("/lessons/6")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(6) ))
                .andExpect(jsonPath("$.title", equalTo("Requests and Responses") ));
    }
     */
}
