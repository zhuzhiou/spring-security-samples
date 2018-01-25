package samples.security.service;

import samples.security.entity.RolePo;

import java.util.List;

public interface RoleService {

    RolePo getRole(String roleName);

    List<RolePo> getRoles();

    void createRole(RolePo role);

    void updateRole(RolePo role);

    void deleteRole(String role);
}
