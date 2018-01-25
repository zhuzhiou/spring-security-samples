package samples.security.service;

import samples.security.entity.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    void saveUser(User user);

    void deleteUser(String username);
}
