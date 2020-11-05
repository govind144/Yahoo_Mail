package com.teama.yahoomail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teama.yahoomail.entity.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email,String>{

}