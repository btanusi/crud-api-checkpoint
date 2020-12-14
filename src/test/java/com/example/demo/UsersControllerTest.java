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

import static org.hamcrest.core.IsEqual.equalTo;
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

    @Test
    @Order(7)
    @Transactional
    @Rollback
    public void testAuthenticate() throws Exception {
        User user = new User();
        user.setId(9L);
        user.setEmail("angelica@example.com");
        user.setPassword("1234");
        repository.save(user);

        MockHttpServletRequestBuilder request = post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"angelica@example.com\",\"password\": \"1234\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated", equalTo(true)))
                .andExpect(jsonPath("$.user.id", equalTo(9) ))
                .andExpect(jsonPath("$.user.email", equalTo("angelica@example.com") ))
        ;
    }

    @Test
    @Order(8)
    @Transactional
    @Rollback
    public void testAuthenticateFails() throws Exception {
        User user = new User();
        user.setId(10L);
        user.setEmail("angelica@example.com");
        user.setPassword("1234");
        repository.save(user);

        MockHttpServletRequestBuilder request = post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"angelica@example.com\",\"password\": \"password\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated", equalTo(false)))
        ;
    }
}
