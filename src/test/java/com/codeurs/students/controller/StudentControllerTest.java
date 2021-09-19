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
@Tag("StudentControllerTest")
@DisplayName("Unit testing of student controller endpoints")
public class StudentControllerTest {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mvc;

    private static JSONObject json;

    @AfterAll
    @BeforeAll
    public void clearDatabase() {
        this.studentRepository.deleteAll();
        json = null;
    }

    @Test
    @Order(value = 0)
    @DisplayName("Create a student")
    public void testThatWeCanCreateStudent() throws Exception {
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
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Philemon")))
                .andExpect(jsonPath("$.lastName", is("Globlehi")))
                .andExpect(jsonPath("$.email", is("philemon.globlehi@gmail.com")))
                .andReturn()
                ;
        json = new JSONObject(result.getResponse().getContentAsString());
    }

    @Test
    @Order(value = 1)
    @DisplayName("Read the details of a student with id")
    public void testThatWeCanReadStudent() throws Exception {
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
    @DisplayName("Show list of students")
    public void testThatWeCanShowListStudents() throws Exception {
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
    @DisplayName("Update of the student's information with id")
    public void testThatWeCanUpdateStudent() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .put("/api/v1/rest/students/" + json.getInt("id"))
                        .content("{"
                                + 	"\"id\":" + json.getInt("id") + ","
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
    @DisplayName("Delete of the student with id")
    public void testThatWeCanDeleteStudent() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/v1/rest/students/" + json.getInt("id"))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(value = 5)
    @DisplayName("Wrong student id")
    public void testThatWeCanNotFoundStudent() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/rest/students/" + json.getInt("id"))
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student with id " + json.getInt("id") + " not found!")));
    }
}
