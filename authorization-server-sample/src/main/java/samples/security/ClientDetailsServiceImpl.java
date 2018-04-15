package samples.security;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;

public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails clientDetails = null;
        if ("test".equalsIgnoreCase(clientId)) {
            clientDetails = new BaseClientDetails(clientId, "practice", "trust", "authorization_code,client_credentials", "ROLE_CLIENT,ROLE_TRUSTED_CLIENT");
            clientDetails.setClientSecret("test");
            //直接授权，无需用户选择允许或拒绝。
            clientDetails.setAutoApproveScopes(Arrays.asList("trust"));
        }
        if (clientDetails == null) {
            throw new NoSuchClientException("客户端不存在");
        }
        return clientDetails;
    }
}
