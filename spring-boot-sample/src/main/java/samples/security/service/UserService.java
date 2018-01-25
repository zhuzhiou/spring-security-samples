package samples.security.service;

import samples.security.entity.UserPo;

import java.util.List;

public interface UserService {

    UserPo getUser(String userName);

    List<UserPo> getUsers();

    void createUser(UserPo userPo);

    void updateUser(UserPo userPo);

    void deleteUser(String userName);
}
