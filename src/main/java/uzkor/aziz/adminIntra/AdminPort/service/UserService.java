package uzkor.aziz.adminIntra.AdminPort.service;



import uzkor.aziz.adminIntra.AdminPort.domain.User;
import uzkor.aziz.adminIntra.AdminPort.domain.security.PasswordResetToken;
import uzkor.aziz.adminIntra.AdminPort.domain.security.UserRole;

import java.util.Set;

public interface UserService {


    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);
}