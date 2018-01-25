package samples.security.service;

import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import samples.security.dao.RoleDao;
import samples.security.entity.RolePo;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.remove;

@Repository
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private TimeBasedGenerator timeBasedGenerator;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public RolePo getRole(String roleName) {
        return roleDao.selectOne(roleName);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<RolePo> getRoles() {
        return roleDao.selectMany();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void createRole(RolePo rolePo) {
        Assert.notNull(rolePo, "role must be not null");
        String roleId = remove(timeBasedGenerator.generate().toString(), "-");
        rolePo.setId(roleId);
        roleDao.insertOne(rolePo);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateRole(RolePo rolePo) {
        roleDao.updateOne(rolePo);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteRole(String roleName) {
        roleDao.deleteOne(roleName);
    }
}
