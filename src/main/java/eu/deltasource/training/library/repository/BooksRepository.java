package eu.deltasource.training.library.repository;

import eu.deltasource.training.library.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends CrudRepository<Book, Long> {
}
