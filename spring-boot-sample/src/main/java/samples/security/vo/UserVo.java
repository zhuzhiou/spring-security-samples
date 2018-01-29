package samples.security.vo;

import java.time.LocalDateTime;

@lombok.Data
public class UserVo {

    private String id;

    private String userName;

    private String password;

    private String gender;

    private String[] roles;

    private LocalDateTime createTime;

    private LocalDateTime lastUpdateTime;
}
