package com.glacierluo.platform.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class OauthClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    //Client的id
    private String clientId;
    //Client的密码
    private String clientKey;
    //
    private String resourceIds;
    //可授权的访问
    private String scopes;
    //授权方式，统一用授权码方式
    private String grantTypes;

    private String authorities;
    //授权
    private String redirectUri;


}
