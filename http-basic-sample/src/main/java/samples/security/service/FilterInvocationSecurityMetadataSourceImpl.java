package samples.security.service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.Jsr250SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * FIXME 生产环境中这些数据从数据库、缓存中加载，并且匹配规则习惯使用ant路径风格。
 * @author zhuzhiou
 */
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    private List<Resource> resources = new ImmutableList.Builder<Resource>()
            .add(new Resource("/sample1", "jsr250", "PERMIT_ALL"))
            .add(new Resource("/sample2", "role", "ROLE_ADMIN"))
            .add(new Resource("/sample3", "authenticated", "IS_AUTHENTICATED_FULLY"))
            .add(new Resource("/sample4", "role", "ROLE_ADMIN"))
            .add(new Resource("/sample4", "role", "ROLE_USER"))
            .build();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        return Collections2.transform(Collections2.filter(resources, new Predicate<Resource>() {
            @Override
            public boolean apply(Resource resource) {
                return request.getRequestURI().equals(resource.getPath());
            }
        }), new Transformer());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return Collections2.transform(resources, new Transformer());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    private static class Transformer implements Function<Resource, ConfigAttribute> {

        @Override
        public ConfigAttribute apply(Resource resource) {
            ConfigAttribute attribute;
            switch (resource.getVoter()) {
                case "jsr250":
                    attribute = Jsr250SecurityConfig.PERMIT_ALL_ATTRIBUTE;
                    break;
                default:
                    attribute = new SecurityConfig(resource.getAccess());
            }
            return attribute;
        }
    }

    private static class Resource implements Serializable {

        private String path;

        private String voter;

        private String access;

        public Resource(String path, String voter, String access) {
            this.path = path;
            this.voter = voter;
            this.access = access;
        }

        public String getPath() {
            return path;
        }

        public String getVoter() {
            return voter;
        }

        public String getAccess() {
            return access;
        }
    }
}
