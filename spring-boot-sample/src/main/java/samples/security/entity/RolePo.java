package samples.security.entity;

import java.io.Serializable;

@lombok.Data
public class RolePo implements Serializable {

    private String id;

    private String roleName;

    private String description;
}
