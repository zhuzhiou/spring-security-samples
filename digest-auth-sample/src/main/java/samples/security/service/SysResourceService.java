package samples.security.service;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * FIXME 生产环境中这些数据从数据库、缓存中加载，并且匹配规则习惯使用ant路径风格。
 * @author zhuzhiou
 */
@Service
public class SysResourceService {

    private List<SysResource> sysResources = new ImmutableList.Builder<SysResource>()
            .add(new SysResource("/sample1", "jsr250", "PERMIT_ALL"))
            .add(new SysResource("/sample2", "role", "ROLE_ADMIN"))
            .add(new SysResource("/sample3", "authenticated", "IS_AUTHENTICATED_FULLY"))
            .add(new SysResource("/sample4", "role", "ROLE_ADMIN"))
            .add(new SysResource("/sample4", "role", "ROLE_USER"))
            .build();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public Collection<SysResource> getSysResources(final String path) {
        return Collections2.filter(sysResources, (SysResource sysResource) -> antPathMatcher.match(sysResource.getPattern(), path));
    }

    public Collection<SysResource> getAllSysResources() {
        return sysResources;
    }
}

class SysResource implements Serializable {

    private String pattern;

    private String voter;

    private String access;

    SysResource(String pattern, String voter, String access) {
        this.pattern = pattern;
        this.voter = voter;
        this.access = access;
    }

    public String getPattern() {
        return pattern;
    }

    public String getVoter() {
        return voter;
    }

    public String getAccess() {
        return access;
    }
}