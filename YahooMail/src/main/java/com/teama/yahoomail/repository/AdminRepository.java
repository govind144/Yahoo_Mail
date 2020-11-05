package com.teama.yahoomail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teama.yahoomail.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
	public Admin findByEmail(String email);
}
