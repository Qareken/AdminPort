package uzkor.aziz.adminIntra.AdminPort.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uzkor.aziz.adminIntra.AdminPort.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
