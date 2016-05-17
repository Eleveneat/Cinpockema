package com.c09.cinpockema.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.jpa.criteria.expression.SearchedCaseExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.entities.Session;
import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.entities.repositories.SessionRepository;
import com.c09.cinpockema.entities.repositories.UserRepository;

@Service
public class SessionService {
	
	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public User getCurrentUser() {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext()
		    .getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		} else {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			Session session = sessionRepository.findByToken(userDetails.getUsername());
			if (session != null) {
				return session.getUser();
			} else {
				return userRepository.findByUsername(userDetails.getUsername());
			}
		}
	}
	
	public Session login() {
		User currentUser = getCurrentUser();
		if (currentUser != null) {
			Session session = new Session();
			String token = UUID.randomUUID().toString();
			session.setToken(token);
			currentUser.addSession(session);
			return sessionRepository.save(session);
		} else {
			return null;
		}
	}
	
	public void logout() {
		// TODO Actually, I don't know what should I do.
	}
	
	public Session findSessionByToken(String token) {
		return sessionRepository.findByToken(token);
	}
	
}
