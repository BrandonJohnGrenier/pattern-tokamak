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

package fm.pattern.tokamak.authorization;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class OAuth2AuthorizationContext implements AuthorizationContextProvider {

	public OAuth2AuthorizationContext() {

	}

	public boolean isAuthenticated() {
		OAuth2Authentication oauth = oauth2Authentication();
		return oauth == null ? false : oauth.isAuthenticated();
	}

	public Set<String> getScopes() {
		OAuth2Authentication oauth = oauth2Authentication();
		if (oauth == null) {
			return new HashSet<String>();
		}

		Set<String> scope = oauth.getOAuth2Request().getScope();
		return scope == null ? new HashSet<String>() : scope;
	}

	public Set<String> getAuthorities() {
		OAuth2Authentication oauth = oauth2Authentication();
		if (oauth == null) {
			return new HashSet<String>();
		}

		Collection<GrantedAuthority> authorities = oauth.getAuthorities();
		return authorities == null ? new HashSet<String>() : authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toSet());
	}

	public Set<String> getRoles() {
		OAuth2Authentication oauth = oauth2Authentication();
		if (oauth == null) {
			return new HashSet<String>();
		}

		if (oauth.isClientOnly()) {
			return new HashSet<String>();
		}

		Authentication userAuthentication = oauth.getUserAuthentication();

		Collection<? extends GrantedAuthority> authorities = userAuthentication.getAuthorities();
		return authorities == null ? new HashSet<String>() : authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toSet());
	}

	public String getUsername() {
		OAuth2Authentication oauth = oauth2Authentication();
		if (oauth == null) {
			return null;
		}

		if (oauth.isClientOnly()) {
			return null;
		}

		Authentication userAuthentication = oauth.getUserAuthentication();
		return (String) userAuthentication.getPrincipal();
	}
	
	private static OAuth2Authentication oauth2Authentication() {
		Authentication authentication = getContext().getAuthentication();
		if (!(authentication instanceof OAuth2Authentication)) {
			return null;
		}
		return (OAuth2Authentication) authentication;
	}

}
