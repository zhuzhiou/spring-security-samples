package samples.security.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import samples.security.entity.RolePo;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<RolePo> {

    @Override
    public RolePo mapRow(ResultSet rs, int rowNum) throws SQLException {
        RolePo rolePo = new RolePo();
        rolePo.setId(rs.getString("ROLE_ID"));
        rolePo.setRoleName(rs.getString("ROLE_NAME"));
        rolePo.setDescription(rs.getString("DESCRIPTION"));
        return rolePo;
    }
}
