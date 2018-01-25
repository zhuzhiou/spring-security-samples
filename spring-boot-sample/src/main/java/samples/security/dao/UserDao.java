package samples.security.dao;

import samples.security.entity.UserPo;

import java.util.List;

public interface UserDao {

    UserPo selectOneById(String userId);

    UserPo selectOneByName(String userName);

    List<UserPo> selectMany();

    void insertOne(UserPo userPo);

    void updateOne(UserPo userPo);

    void deleteOne(String userName);
}
