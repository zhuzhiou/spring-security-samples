package samples.security.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@lombok.Data
public class Role implements Serializable {

    @Id
    @Column(name = "ROLE_ID")
    private String id;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "ROLE_NAME")
    private String roleName;
}
