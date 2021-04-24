package dev.tobiadegbuji.msscbeerservice.web.controller;

import dev.tobiadegbuji.msscbeerservice.services.BeerService;
import dev.tobiadegbuji.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId) {
        return new ResponseEntity<>(beerService.getById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> saveBeer(@Valid @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto),HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeerById(@PathVariable UUID beerId, @Valid @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(beerService.updateBeer(beerId,beerDto), HttpStatus.NO_CONTENT);
    }


}
