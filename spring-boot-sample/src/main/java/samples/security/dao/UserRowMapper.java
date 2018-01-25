package samples.security.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import samples.security.entity.UserPo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class UserRowMapper implements RowMapper<UserPo> {

    @Override
    public UserPo mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserPo user = new UserPo();
        user.setId(rs.getString("USER_ID"));
        user.setUserName(rs.getString("USER_NAME"));
        user.setGender(rs.getString("GENDER"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setPasswordSalt(rs.getString("PASSWORD_SALT"));
        Timestamp timestamp = rs.getTimestamp("CREATE_TIME");
        if (timestamp != null) {
            user.setCreateTime(timestamp.toLocalDateTime());
        }
        timestamp = rs.getTimestamp("LAST_UPDATE_TIME");
        if (timestamp != null) {
            user.setLastUpdateTime(timestamp.toLocalDateTime());
        }
        return user;
    }
}
