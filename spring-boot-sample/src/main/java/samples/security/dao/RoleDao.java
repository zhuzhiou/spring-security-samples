package samples.security.dao;

import samples.security.entity.RolePo;

import java.util.List;

public interface RoleDao {

    RolePo selectOne(String roleName);

    List<RolePo> selectMany();

    void insertOne(RolePo rolePo);

    void updateOne(RolePo rolePo);

    void deleteOne(String roleName);
}
