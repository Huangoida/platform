package com.glacierluo.platform.service;

import com.glacierluo.platform.entity.OauthClient;
import com.glacierluo.platform.repository.OauthClientRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailService implements ClientDetailsService {

    @Autowired
    private OauthClientRepostiory oauthClientRepostiory;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {

        OauthClient oauthClient=oauthClientRepostiory.findByclientName(s);
        BaseClientDetails baseClientDetails= new BaseClientDetails(oauthClient.getClientName(),oauthClient.getResourceIds(),oauthClient.getScopes(),oauthClient.getGrantTypes(),oauthClient.getAuthorities());
        baseClientDetails.setClientSecret(oauthClient.getClientPassword());
        return baseClientDetails;
    }
}
