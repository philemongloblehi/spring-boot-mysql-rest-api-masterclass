package com.codeurs.students.controller;

import com.codeurs.students.repository.StudentRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.hamcrest.Matchers.is;

/**
 * @author Philémon Globléhi <philemon.globlehi@gmail.com>
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mvc;

    private static JSONObject json;

    @BeforeAll
    public void setUp() {
        studentRepository.deleteAll();
        json = null;
    }

    @AfterAll
    public void tearDown() {
        studentRepository.deleteAll();
        json = null;
    }

    @Test
    @Order(value = 0)
    public void testThatCanCreateStudents() throws Exception {
        MvcResult result = this.mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/rest/students")
                        .content("{"
                                + 	"\"firstName\": \"Philemon\","
                                + 	"\"lastName\": \"Globlehi\","
                                + 	"\"email\": \"philemon.globlehi@gmail.com\""
                                + "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is("Philemon")))
                .andExpect(jsonPath("$.lastName", is("Globlehi")))
                .andExpect(jsonPath("$.email", is("philemon.globlehi@gmail.com")))
                .andReturn()
                ;
        json = new JSONObject(result.getResponse().getContentAsString());
    }

    @Test
    @Order(value = 1)
    public void testThatCanReadStudents() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/rest/students/" + json.getInt("id"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(json.getInt("id"))))
                .andExpect(jsonPath("$.firstName", is("Philemon")))
                .andExpect(jsonPath("$.lastName", is("Globlehi")))
                .andExpect(jsonPath("$.email", is("philemon.globlehi@gmail.com")))
        ;
    }

    @Test
    @Order(value = 2)
    public void testThatCanListStudents() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/rest/students")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(json.getInt("id"))))
                .andExpect(jsonPath("$.[0].firstName", is("Philemon")))
                .andExpect(jsonPath("$.[0].lastName", is("Globlehi")))
                .andExpect(jsonPath("$.[0].email", is("philemon.globlehi@gmail.com")))
        ;
    }

    @Test
    @Order(value = 3)
    public void testThatCanUpdateStudents() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .put("/api/v1/rest/students/" + json.getInt("id"))
                        .content("{"
                                + 	"\"firstName\": \"Douglas\","
                                + 	"\"lastName\": \"MBiamdou\","
                                + 	"\"email\": \"MBiamdou.douglas@objis.com\""
                                + "}"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Douglas")))
                .andExpect(jsonPath("$.lastName", is("MBiamdou")))
                .andExpect(jsonPath("$.email", is("MBiamdou.douglas@objis.com")))
        ;
    }

    @Test
    @Order(value = 4)
    public void testThatCanDeleteStudents() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/rest/students/" + json.getInt("id"))
        )
                .andExpect(status().isNoContent());
    }
}
