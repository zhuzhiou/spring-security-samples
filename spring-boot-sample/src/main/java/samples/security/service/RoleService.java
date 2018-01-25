package samples.security.service;

import samples.security.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    void saveRole(Role role);

    void deleteRole(String role);
}
