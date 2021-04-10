package dev.tobiadegbuji.msscbeerservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tobiadegbuji.msscbeerservice.web.model.BeerDto;
import dev.tobiadegbuji.msscbeerservice.web.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs //Automatically configures RestDocs MockMvc Setup
@WebMvcTest
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String beerDtoJson;

    @BeforeEach
    void setup() throws JsonProcessingException {
        BeerDto beerDto = BeerDto.builder()
                .beerName("Corona")
                .price(new BigDecimal("15.99"))
                .beerStyle(BeerStyle.WHEAT)
                .upc(15411515L)
                .build();
        beerDtoJson = objectMapper.writeValueAsString(beerDto);
    }

    @Test
    void getBeerById() throws Exception {
        //Make sure to use the RestDocReq Builders rather than servlet.request.MockMvcRequestBuilders
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/beer/{beerId}", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("v1/beer", pathParameters(
                        parameterWithName("beerId").description("UUID of beer to be retrieved")
                )));
    }

    @Test
    void saveBeer() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders
                .put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }


}