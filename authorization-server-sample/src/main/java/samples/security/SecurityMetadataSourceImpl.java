package samples.security;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.Jsr250SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author zhuzhiou
 */
@Component
public class SecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Collection<SysResource> sysResources = sysResourceService.getSysResources(request.getRequestURI());
        return Collections2.transform(sysResources, new Transformer());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Collection<SysResource> sysResources = sysResourceService.getAllSysResources();
        return Collections2.transform(sysResources, new Transformer());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    private static class Transformer implements Function<SysResource, ConfigAttribute> {

        @Override
        public ConfigAttribute apply(SysResource sysResource) {
            ConfigAttribute attribute = new SecurityConfig(sysResource.getAccess());
            return attribute;
        }
    }
}
