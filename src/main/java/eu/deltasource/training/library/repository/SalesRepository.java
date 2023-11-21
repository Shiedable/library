package eu.deltasource.training.library.repository;

import eu.deltasource.training.library.model.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends CrudRepository<Sale, Long> {
}
