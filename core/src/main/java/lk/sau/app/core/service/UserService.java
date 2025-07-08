package lk.sau.app.core.service;

import jakarta.ejb.Remote;
import lk.sau.app.core.model.User;

@Remote
public interface UserService {

    User getUserById(Long id);
    User getUserByEmail(String email);
    void registerUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    boolean validate(String email, String password);

}
