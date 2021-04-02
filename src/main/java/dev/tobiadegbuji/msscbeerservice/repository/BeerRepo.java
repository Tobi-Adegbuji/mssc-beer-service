package dev.tobiadegbuji.msscbeerservice.repository;

import dev.tobiadegbuji.msscbeerservice.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeerRepo extends PagingAndSortingRepository<Beer, UUID> {
}
