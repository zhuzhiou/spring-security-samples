package samples.security;

import java.io.Serializable;

public class SysResource implements Serializable {

    private String pattern;

    private String access;

    SysResource(String pattern, String access) {
        this.pattern = pattern;
        this.access = access;
    }

    public String getPattern() {
        return pattern;
    }

    public String getAccess() {
        return access;
    }
}
