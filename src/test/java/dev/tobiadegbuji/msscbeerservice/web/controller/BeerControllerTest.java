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
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;


import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//Example of using RestDocs.
//TODO: I want to use OpenAPI instead
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
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .param("isCold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("v1/beer", pathParameters(
                        parameterWithName("beerId").description("UUID of beer to be retrieved")
                        ),
                        requestParameters(
                                parameterWithName("isCold").description("Is Beer Cold Query param") //Example of documenting request parameters
                        ),
                        responseFields(
                                fieldWithPath("beer-id").description("Id of Beer"), //Documenting A specific response field
                                fieldWithPath("version").description("Version number of record"),
                                fieldWithPath("createdDate").description("Date Beer was created"),
                                fieldWithPath("lastModifiedDate").description("Date Beer was last modified"),
                                fieldWithPath("beerName").description("Name of Beer"),
                                fieldWithPath("beerStyle").description("Style of beer"),
                                fieldWithPath("upc").description("Beer upc "),
                                fieldWithPath("price").description("Price of Beer"),
                                fieldWithPath("quantityOnHand").description("Amount of beer left")
                        )
                ));
    }

    @Test
    void saveBeer() throws Exception {

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
        .andDo(MockMvcRestDocumentation.document("v1/beer",
                requestFields(
                        fields.withPath("beer-id").ignored(), //Field is ignored since Client should not pass this as an Id
                        fields.withPath("version").ignored(),
                        fields.withPath("createdDate").ignored(),
                        fields.withPath("lastModifiedDate").ignored(),
                        fields.withPath("beerName").description("Name of the Beer"),
                        fields.withPath("beerStyle").description("Style of beer"),
                        fields.withPath("upc").description("Beer upc"),
                        fields.withPath("price").description("Price of Beer"),
                        fields.withPath("quantityOnHand").description("Amount of beer left")
                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders
                .put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    private static class ConstrainedFields{
        private final ConstraintDescriptions constraintDescriptions;

        public ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path){
            return fieldWithPath(path).attributes(key("constraints")
                    .value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path),". ")));
        }
    }


}