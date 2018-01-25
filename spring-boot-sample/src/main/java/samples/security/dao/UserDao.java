package samples.security.dao;

import samples.security.entity.User;

import java.util.List;

public interface UserDao {

    List<User> selectMany();

    void saveOne(User user);

    void deleteOne(String username);
}
