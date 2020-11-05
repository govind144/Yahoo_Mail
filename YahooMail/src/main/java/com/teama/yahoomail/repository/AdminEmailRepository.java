package com.teama.yahoomail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teama.yahoomail.entity.AdminEmailRecord;

public interface AdminEmailRepository extends JpaRepository<AdminEmailRecord,String> {

}
