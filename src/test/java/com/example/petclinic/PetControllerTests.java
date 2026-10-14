package com.example.petclinic;

import com.example.petclinic.entity.Owner;
import com.example.petclinic.entity.Pet;
import com.example.petclinic.entity.PetType;
import com.example.petclinic.repository.OwnerRepository;
import com.example.petclinic.repository.PetRepository;
import com.example.petclinic.repository.PetTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PetControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    PetRepository petRepository;
    @Autowired
    PetTypeRepository petTypeRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Test
    void listsAllPets() throws Exception {
        mockMvc.perform(get("/api/v1/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(13));
    }

    @Test
    void filtersByTypeWithinYears() throws Exception {
        mockMvc.perform(get("/api/v1/pets").param("year", "100").param("type", "dog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void typeIsCaseInsensitive() throws Exception {
        mockMvc.perform(get("/api/v1/pets").param("year", "100").param("type", "CAT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void excludesPetsOlderThanYears() throws Exception {
        mockMvc.perform(get("/api/v1/pets").param("year", "1").param("type", "dog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void includesPetBornWithinYears() throws Exception {
        PetType cat = petTypeRepository.findAll().stream()
                .filter(t -> t.getName().equals("cat")).findFirst().orElseThrow();
        Owner owner = ownerRepository.findAll().get(0);
        Pet kitten = new Pet();
        kitten.setName("Kitten");
        kitten.setBirthDate(LocalDateTime.now().minusMonths(6));
        kitten.setType(cat);
        kitten.setOwner(owner);
        petRepository.saveAndFlush(kitten);

        mockMvc.perform(get("/api/v1/pets").param("year", "1").param("type", "cat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Kitten"));
    }

    @Test
    void yearOnlyIsRejected() throws Exception {
        mockMvc.perform(get("/api/v1/pets").param("year", "3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void typeOnlyIsRejected() throws Exception {
        mockMvc.perform(get("/api/v1/pets").param("type", "dog"))
                .andExpect(status().isBadRequest());
    }
}
