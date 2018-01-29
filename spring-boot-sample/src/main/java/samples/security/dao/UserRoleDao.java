package samples.security.dao;

import java.io.Serializable;

public interface UserRoleDao {

    void insertOne(String userId, String roleId);

    void deleteMany(By by);

    abstract class By {

        public static By userId(String userId) {
            return new ByUserId(userId);
        }

        public static By roleId(String roleId) {
            return new ByRoleId(roleId);
        }
    }

    class ByUserId extends By implements Serializable {

        private final String userId;

        ByUserId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }
    }

    class ByRoleId extends By implements Serializable {

        private final String roleId;

        ByRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getRoleId() {
            return roleId;
        }
    }
}
