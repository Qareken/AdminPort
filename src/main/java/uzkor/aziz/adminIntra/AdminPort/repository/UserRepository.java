package uzkor.aziz.adminIntra.AdminPort.repository;


import org.springframework.data.repository.CrudRepository;
import uzkor.aziz.adminIntra.AdminPort.domain.User;
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}