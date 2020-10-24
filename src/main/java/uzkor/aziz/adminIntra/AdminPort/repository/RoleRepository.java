package uzkor.aziz.adminIntra.AdminPort.repository;

import org.springframework.data.repository.CrudRepository;
import uzkor.aziz.adminIntra.AdminPort.domain.security.Role;
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);


}