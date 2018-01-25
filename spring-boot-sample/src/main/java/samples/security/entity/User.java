package samples.security.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@lombok.Data
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private String id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_SALT")
    private String passwordSalt;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    @Column(name = "LAST_UPDATE_TIME")
    private LocalDateTime lastUpdateTime;
}
