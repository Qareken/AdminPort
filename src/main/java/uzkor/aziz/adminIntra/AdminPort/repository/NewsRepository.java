package uzkor.aziz.adminIntra.AdminPort.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uzkor.aziz.adminIntra.AdminPort.domain.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

}
