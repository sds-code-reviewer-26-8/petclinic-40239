package com.example.petclinic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OwnerControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void listsAllOwners() throws Exception {
        mockMvc.perform(get("/api/v1/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }

    @Test
    void filtersByLastNamePrefix() throws Exception {
        mockMvc.perform(get("/api/v1/owners").param("lastname", "Dav"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].lastName").value("Davis"));
    }

    @Test
    void filterIsCaseInsensitive() throws Exception {
        mockMvc.perform(get("/api/v1/owners").param("lastname", "davis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void returnsEmptyListWhenNoMatch() throws Exception {
        mockMvc.perform(get("/api/v1/owners").param("lastname", "kim"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void returnsOwnerDetail() throws Exception {
        mockMvc.perform(get("/api/v1/owners/{id}", firstOwnerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Franklin"));
    }

    @Test
    void returnsNotFoundForUnknownOwner() throws Exception {
        mockMvc.perform(get("/api/v1/owners/{id}", 99999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Owner does not exist"));
    }

    private long firstOwnerId() throws Exception {
        String body = mockMvc.perform(get("/api/v1/owners").param("lastname", "Franklin"))
                .andReturn().getResponse().getContentAsString();
        return new com.fasterxml.jackson.databind.ObjectMapper().readTree(body).get(0).get("id").asLong();
    }
}
