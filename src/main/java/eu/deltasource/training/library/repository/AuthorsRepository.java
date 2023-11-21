package eu.deltasource.training.library.repository;

import eu.deltasource.training.library.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorsRepository extends CrudRepository<Author, Long> {
}
