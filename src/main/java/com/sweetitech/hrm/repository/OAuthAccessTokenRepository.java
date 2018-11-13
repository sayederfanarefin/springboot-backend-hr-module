package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.authentication.OAuthAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface OAuthAccessTokenRepository extends CrudRepository<OAuthAccessToken, String> {

    OAuthAccessToken getFirstByUserName(String username);

    void deleteByUserName(String username);

}
