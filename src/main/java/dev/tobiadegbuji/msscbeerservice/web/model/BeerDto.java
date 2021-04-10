package dev.tobiadegbuji.msscbeerservice.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {

    //Realize JsonProperties will override any naming Strategy. Use JsonProperties if you really want to change the name of the json property
    @JsonProperty("beer-id")
    @Null //Req will fail validation if they try to pass this field (Defensive Programming)
    private UUID id;

    @Null
    private Integer version;

    @Null
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate; //Always use UTC when doing web apps

    @Null
    private OffsetDateTime lastModifiedDate;

    @NotBlank
    @Size(min = 3, max = 100)
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @NotNull
    @Positive
    private Long upc;

    @NotNull
    @Positive
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;

    @Positive
    private Integer quantityOnHand;

}
