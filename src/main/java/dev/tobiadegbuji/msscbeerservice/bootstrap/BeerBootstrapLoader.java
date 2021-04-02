package dev.tobiadegbuji.msscbeerservice.bootstrap;

import dev.tobiadegbuji.msscbeerservice.domain.Beer;
import dev.tobiadegbuji.msscbeerservice.repository.BeerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//Class runs when spring context runs
@Component
public class BeerBootstrapLoader implements CommandLineRunner {

    private final BeerRepo beerRepo;

    public BeerBootstrapLoader(BeerRepo beerRepo) {
        this.beerRepo = beerRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepo.count() == 0) {
            beerRepo.save(Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .upc(33701000001L)
                    .price(new BigDecimal("12.59"))
                    .minOnHand(12)
                    .build()
            );
            beerRepo.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .upc(43101000002L)
                    .price(new BigDecimal("11.95"))
                    .minOnHand(12)
                    .build()
            );
        }
    }


}
