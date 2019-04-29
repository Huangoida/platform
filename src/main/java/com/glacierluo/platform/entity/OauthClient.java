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
    private String clientName;
    private String clientKey;
    private String resourceIds;
    private String scopes;
    private String grantTypes;
    private String authorities;
    private String redirectUri;


}
