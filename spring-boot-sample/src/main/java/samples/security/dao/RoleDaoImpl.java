package samples.security.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import samples.security.entity.RolePo;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleRowMapper roleRowMapper;

    @Override
    public RolePo selectOne(String roleName) {
        return jdbcTemplate.queryForObject("select * from sys_role where ROLE_NAME = ?", roleRowMapper, roleName);
    }

    @Override
    public List<RolePo> selectMany() {
        return jdbcTemplate.query("select * from sys_role", roleRowMapper);
    }

    @Override
    public void insertOne(RolePo rolePo) {
        jdbcTemplate.update("insert into sys_role(ROLE_ID, ROLE_NAME, DESCRIPTION) values(?, ?, ?)", (PreparedStatement ps) -> {
            ps.setString(1, rolePo.getId());
            ps.setString(2, rolePo.getRoleName());
            ps.setString(3, rolePo.getDescription());
        });
    }

    @Override
    public void updateOne(RolePo rolePo) {
        jdbcTemplate.update("update sys_role set ROLE_NAME = ?, DESCRIPTION = ? where ROLE_ID = ?", (PreparedStatement ps) -> {
            ps.setString(1, rolePo.getRoleName());
            ps.setString(2, rolePo.getDescription());
            ps.setString(3, rolePo.getId());
        });
    }

    @Override
    public void deleteOne(String roleName) {
        jdbcTemplate.update("delete from sys_role where ROLE_NAME = ?", roleName);
    }
}
