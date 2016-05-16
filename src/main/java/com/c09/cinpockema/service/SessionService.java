package com.c09.cinpockema.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.entities.Session;
import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.entities.repositories.SessionRepository;

@Service
public class SessionService {
	
	@Autowired
	SessionRepository sessionRepository;
	
	public User getCurrentUser() {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext()
		    .getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		} else {
			return (User) authentication.getPrincipal();
		}
	}
	
	public Session login() {
		User currentUser = getCurrentUser();
		if (currentUser != null) {
			Session session = new Session();
			String token = UUID.randomUUID().toString();
			
			session.setToken(token);
			session.setUser(currentUser);
			return sessionRepository.save(session);
		} else {
			return null;
		}
	}
	
	public Session findSessionByToken(String token) {
		return sessionRepository.findByToken(token);
	}
	
	public boolean isSessionExpired(Session session) {
		long createTime = session.getCreateTime();
		long nowTime = System.currentTimeMillis();
		return (nowTime - createTime) < 30 * 60;
	}
	
}
