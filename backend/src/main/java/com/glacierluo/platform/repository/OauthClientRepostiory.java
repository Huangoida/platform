package com.glacierluo.platform.repository;

import com.glacierluo.platform.entity.OauthClient;
import org.springframework.data.repository.CrudRepository;

public interface OauthClientRepostiory extends CrudRepository<OauthClient,Long> {
    OauthClient findByclientName(String clientName);
}
