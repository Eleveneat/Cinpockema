package com.c09.cinpockema.entities.repositories;

import org.springframework.data.repository.CrudRepository;

import com.c09.cinpockema.entities.Session;

public interface SessionRepository extends CrudRepository<Session, Long> {
	Session findByToken(String token);
}

