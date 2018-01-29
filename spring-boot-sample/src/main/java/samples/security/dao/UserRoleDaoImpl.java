package samples.security.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertOne(String userId, String roleId) {
        jdbcTemplate.update("insert into sys_user_role(USER_ID, ROLE_ID) values(?, ?)", userId, roleId);
    }

    @Override
    public void deleteMany(By by) {
        if (by instanceof ByUserId) {
            jdbcTemplate.update("delete from sys_user_role where USER_ID = ?", ((ByUserId) by).getUserId());
        }
        if (by instanceof ByRoleId) {
            jdbcTemplate.update("delete from sys_user_role where USER_ID = ?", ((ByRoleId) by).getRoleId());
        }
    }
}
