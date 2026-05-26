package com.bajaj.bfhl.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BfhlControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMethod() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code", is(1)));
    }

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")))
                .andExpect(jsonPath("$.message", containsString("healthy")));
    }

    @Test
    public void testExampleA() throws Exception {
        String jsonRequest = "{\"data\": [\"a\", \"1\", \"334\", \"4\", \"R\", \"$\"]}";
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("divyansh_porwal_23092005")))
                .andExpect(jsonPath("$.email", is("divyanshporwal230951@acropolis.in")))
                .andExpect(jsonPath("$.roll_number", is("0827CS231085")))
                .andExpect(jsonPath("$.odd_numbers", contains("1")))
                .andExpect(jsonPath("$.even_numbers", contains("334", "4")))
                .andExpect(jsonPath("$.alphabets", contains("A", "R")))
                .andExpect(jsonPath("$.special_characters", contains("$")))
                .andExpect(jsonPath("$.sum", is("339")))
                .andExpect(jsonPath("$.concat_string", is("Ra")));
    }

    @Test
    public void testExampleB() throws Exception {
        String jsonRequest = "{\"data\": [\"2\", \"a\", \"y\", \"4\", \"&\", \"-\", \"*\", \"5\", \"92\", \"b\"]}";
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", contains("5")))
                .andExpect(jsonPath("$.even_numbers", contains("2", "4", "92")))
                .andExpect(jsonPath("$.alphabets", contains("A", "Y", "B")))
                .andExpect(jsonPath("$.special_characters", contains("&", "-", "*")))
                .andExpect(jsonPath("$.sum", is("103")))
                .andExpect(jsonPath("$.concat_string", is("ByA")));
    }

    @Test
    public void testExampleC() throws Exception {
        String jsonRequest = "{\"data\": [\"A\", \"ABCD\", \"DOE\"]}";
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", contains("A", "ABCD", "DOE")))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("EoDdCbAa")));
    }

    @Test
    public void testValidationErrorMissingData() throws Exception {
        String jsonRequest = "{}";
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Validation failed")));
    }

    @Test
    public void testInvalidJsonFormat() throws Exception {
        String jsonRequest = "{\"data\": invalid_json}";
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.message", containsString("Invalid request body format")));
    }
}
