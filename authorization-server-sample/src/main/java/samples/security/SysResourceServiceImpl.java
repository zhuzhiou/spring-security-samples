package samples.security;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Service
public class SysResourceServiceImpl implements SysResourceService {

    private List<SysResource> sysResources = new ImmutableList.Builder<SysResource>()
            .add(new SysResource("/sample1*", "PERMIT_ALL"))
            .add(new SysResource("/sample2*", "ROLE_ADMIN"))
            .add(new SysResource("/sample3*", "IS_AUTHENTICATED_FULLY"))
            .add(new SysResource("/sample4*", "ROLE_ADMIN"))
            .add(new SysResource("/sample4*", "ROLE_USER"))
            .build();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<SysResource> getSysResources(final String relativeURI) {
        return Collections2.filter(sysResources, (SysResource sysResource) -> antPathMatcher.match(sysResource.getPattern(), relativeURI));
    }

    @Override
    public Collection<SysResource> getAllSysResources() {
        return sysResources;
    }
}
