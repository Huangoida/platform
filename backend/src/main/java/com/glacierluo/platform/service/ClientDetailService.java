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
        OauthClient oauthClient;
        try{
           oauthClient=oauthClientRepostiory.findByclientId(s);
        }catch (Exception e){
            throw new ClientRegistrationException("没有找到client");
        }
       if (oauthClient == null){
           throw new ClientRegistrationException("clientId无效");
       }
        BaseClientDetails baseClientDetails= new BaseClientDetails(oauthClient.getClientId(),oauthClient.getResourceIds(),oauthClient.getScopes(),oauthClient.getGrantTypes(),oauthClient.getAuthorities(),oauthClient.getRedirectUri());
        baseClientDetails.setClientSecret(oauthClient.getClientKey());
        return baseClientDetails;
    }
}
