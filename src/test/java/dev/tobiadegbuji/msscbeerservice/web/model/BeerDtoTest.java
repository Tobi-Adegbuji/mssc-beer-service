package dev.tobiadegbuji.msscbeerservice.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@Slf4j
@JsonTest
@ActiveProfiles("kebab") //Will look for snake properties file
class BeerDtoTest {


    @Autowired
    ObjectMapper objectMapper;

    BeerDto beerDto;

    @BeforeEach
    void setUp() {
        beerDto = BeerDto.builder()
                .beerName("Corona")
                .price(new BigDecimal("15.99"))
                .beerStyle(BeerStyle.WHEAT)
                .upc(15411515L)
                .build();
    }


    @Test
    void testSerializeDto() throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(beerDto);
        System.out.println(jsonString);
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        String jsonString = "{\"version\":null,\"created-date\":null,\"last-modified-date\":null,\"beer-name\":\"Corona\",\"beer-style\":\"WHEAT\",\"upc\":15411515,\"price\":15.99,\"quantity-on-hand\":null,\"beer-id\":null}\n";
        BeerDto dto = objectMapper.readValue(jsonString, BeerDto.class);
        //Notice how snake or kebab case was applied from our properties file. There are several property strategies you can choose from.
        //Search for properties naming strategy class to see the different strategies
        System.out.println(dto);

    }
}