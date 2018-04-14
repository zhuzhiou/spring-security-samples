package samples.security.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author zhuzhiou
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * FIXME 示例工程，生产系统需从数据库加载数据，辅以缓存。
     * @param username 用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        switch (username) {
            case "admin":
                user = new User("admin", "admin", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
                break;
            case "user":
                user = new User("user", "user", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
                break;
            case "sample1":
                user = new User("sample1", "sample1", false, false, false, true, Collections.EMPTY_LIST);
                break;
            case "sample2":
                user = new User("sample2", "sample2", true, false, false, true, Collections.EMPTY_LIST);
                break;
            case "sample3":
                user = new User("sample3", "sample3", true, true, false, true, Collections.EMPTY_LIST);
                break;
            case "sample4":
                user = new User("sample4", "sample4", true, true, true, false, Collections.EMPTY_LIST);
                break;
        }
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
