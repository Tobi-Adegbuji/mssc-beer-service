package dev.tobiadegbuji.msscbeerservice.mappers;

import dev.tobiadegbuji.msscbeerservice.domain.Beer;
import dev.tobiadegbuji.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

//Notice Mapstruct automatically converts Enum to String effortlessly

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

     BeerDto beerToBeerDto(Beer beer);

     Beer beerDtoToBeer(BeerDto beerDto);

}
