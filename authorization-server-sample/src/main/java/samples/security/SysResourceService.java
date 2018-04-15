package samples.security;

import java.util.Collection;

public interface SysResourceService {

    Collection<SysResource> getSysResources(String relativeURI);

    Collection<SysResource> getAllSysResources();
}
