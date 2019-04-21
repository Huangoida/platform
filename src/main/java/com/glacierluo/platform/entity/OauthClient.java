package com.glacierluo.platform.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class OauthClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String clientName;
    private String clientPassword;
    private String resourceIds;
    private String scopes;
    private String grantTypes;
    private String authorities;


}
