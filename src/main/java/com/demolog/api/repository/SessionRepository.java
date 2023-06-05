package com.demolog.api.repository;

import com.demolog.api.domain.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {

}
