/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fm.pattern.jwt.server.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import fm.pattern.jwt.server.model.Account;
import fm.pattern.jwt.server.service.AccountService;
import fm.pattern.microstructure.Result;

@Component
public class CustomJwtTokenEnhancer extends JwtAccessTokenConverter {

	@Value("${oauth2.issuer}")
	private String issuer;
	
	private final AccountService accountService;

	@Autowired
	public CustomJwtTokenEnhancer(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);

		Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		additionalInformation.put("sub", authentication.getOAuth2Request().getClientId());
		additionalInformation.put("iss", issuer);
		
		Authentication userAuthentication = authentication.getUserAuthentication();
		if (userAuthentication == null) {
			customAccessToken.setAdditionalInformation(additionalInformation);
			return super.enhance(customAccessToken, authentication);
		}

		AuthenticatedAccount account = getUser(userAuthentication);
		if (account == null) {
			customAccessToken.setAdditionalInformation(additionalInformation);
			return super.enhance(customAccessToken, authentication);
		}

		additionalInformation.put("sub", account.getIdentfifier());

		customAccessToken.setAdditionalInformation(additionalInformation);
		return super.enhance(customAccessToken, authentication);
	}

	private AuthenticatedAccount getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof String) {
			String username = (String) authentication.getPrincipal();
			Result<Account> result = accountService.findByUsername(username);
			return result.accepted() ? new AuthenticatedAccount(result.getInstance()) : null;
		}
		return (AuthenticatedAccount) authentication.getPrincipal();
	}

}
