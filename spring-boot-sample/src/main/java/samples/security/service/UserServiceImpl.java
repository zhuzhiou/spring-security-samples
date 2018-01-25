package samples.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import samples.security.dao.UserDao;
import samples.security.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.trimToNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Iterable<User> it = userDao.selectMany();
        for (User user : it) {
            users.add(user);
        }
        return users;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveUser(User user) {
        Assert.notNull(user, "user不能为null");
        userDao.saveOne(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteUser(String username) {
        Assert.notNull(trimToNull(username), "username不能为空");
        userDao.deleteOne(username);
    }
}
