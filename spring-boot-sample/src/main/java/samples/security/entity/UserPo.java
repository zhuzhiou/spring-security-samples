package samples.security.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

@lombok.Data
public class UserPo implements Serializable {

    private String id;

    private String userName;

    private String password;

    private String passwordSalt;

    private String gender;

    private LocalDateTime createTime;

    private LocalDateTime lastUpdateTime;
}
