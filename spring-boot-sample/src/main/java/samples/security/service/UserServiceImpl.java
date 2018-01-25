package samples.security.service;

import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import samples.security.dao.UserDao;
import samples.security.entity.UserPo;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.*;
import static org.springframework.util.DigestUtils.md5DigestAsHex;
import static org.thymeleaf.util.StringUtils.randomAlphanumeric;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TimeBasedGenerator timeBasedGenerator;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public UserPo getUser(String userName) {
        return userDao.selectOneByName(userName);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<UserPo> getUsers() {
        List<UserPo> users = new ArrayList<>();
        Iterable<UserPo> it = userDao.selectMany();
        for (UserPo userPo : it) {
            users.add(userPo);
        }
        return users;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void createUser(UserPo userPo) {
        Assert.notNull(userPo, "user不能为null");
        String userId = remove(timeBasedGenerator.generate().toString(), "-");
        userPo.setId(userId);
        userPo.setPassword(randomAlphanumeric(10));
        userPo.setPasswordSalt(randomAlphanumeric(128));
        userDao.insertOne(userPo);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateUser(UserPo userPo) {
        Assert.notNull(userPo, "user不能为null");
        if (isBlank(userPo.getPassword())) {
            UserPo managed = userDao.selectOneById(userPo.getId());
            if (managed != null) {
                userPo.setPassword(managed.getPassword());
            }
        } else {
            userPo.setPassword(md5DigestAsHex(userPo.getPassword().getBytes()));
        }
        userDao.updateOne(userPo);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteUser(String userName) {
        Assert.notNull(trimToNull(userName), "userName不能为空");
        userDao.deleteOne(userName);
    }
}
