package dev.tobiadegbuji.msscbeerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {

    private UUID id;

    private Integer version;

    private OffsetDateTime createdDate; //Always use UTC when doing web apps

    private OffsetDateTime lastModifiedDate;

    private String beerName;

    private BeerStyle beerStyle;

    private Long upc;

    private BigDecimal price;

    private Integer quantityOnHand;

}
