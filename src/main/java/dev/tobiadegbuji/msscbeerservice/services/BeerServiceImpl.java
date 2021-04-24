package dev.tobiadegbuji.msscbeerservice.services;

import dev.tobiadegbuji.msscbeerservice.domain.Beer;
import dev.tobiadegbuji.msscbeerservice.exceptions.NotFoundException;
import dev.tobiadegbuji.msscbeerservice.mappers.BeerMapper;
import dev.tobiadegbuji.msscbeerservice.repository.BeerRepo;
import dev.tobiadegbuji.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {


    private final BeerRepo beerRepo;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getById(UUID id) {
        return beerMapper.beerToBeerDto(beerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException()));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
         beerRepo.save(beerMapper.beerDtoToBeer(beerDto));
         return beerDto;
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepo.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepo.save(beer));

    }
}
