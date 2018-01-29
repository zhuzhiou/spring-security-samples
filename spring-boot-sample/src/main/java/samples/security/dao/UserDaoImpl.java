package samples.security.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import samples.security.entity.UserPo;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;

    @Override
    public UserPo selectOneById(String userId) {
        return jdbcTemplate.queryForObject("select * from sys_user where user_id = ?", userRowMapper, userId);
    }

    @Override
    public List<UserPo> selectMany() {
        return jdbcTemplate.query("select * from sys_user", userRowMapper);
    }

    @Override
    public void insertOne(UserPo userPo) {
        jdbcTemplate.update("insert into sys_user(USER_ID, USER_NAME, GENDER, PASSWORD, CREATE_TIME, LAST_UPDATE_TIME) values(?, ?, ?, ?, now(), now())", (PreparedStatement ps) -> {
            ps.setString(1, userPo.getId());
            ps.setString(2, userPo.getUserName());
            ps.setString(3, userPo.getGender());
            ps.setString(4, userPo.getPassword());
        });
    }

    @Override
    public void updateOne(UserPo userPo) {
        jdbcTemplate.update("update sys_user set USER_NAME = ?, GENDER = ?, PASSWORD = ? where USER_ID = ?", (PreparedStatement ps) -> {
            ps.setString(1, userPo.getUserName());
            ps.setString(2, userPo.getGender());
            ps.setString(3, userPo.getPassword());
            ps.setString(4, userPo.getId());
        });
    }

    @Override
    public void deleteOne(String userName) {
        jdbcTemplate.update("delete from sys_user where USER_NAME = ?", userName);
    }
}
