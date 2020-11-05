package com.teama.yahoomail.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.teama.yahoomail.entity.UserDetail;


@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
	public List<UserDetail> findByBirthDay(LocalDate birthDay);

	public UserDetail findByEmail(String email);
	
	/*
	 * @Query(value = "from UserDetail u " +
	 * "where day(u.birthday) = day(CURRENT_DATE) " +
	 * "and month(u.birthday) = month(CURRENT_DATE)") public List<UserDetail>
	 * findUsersWithBirthDayToday();
	 */
	@Query(value="from UserDetail u where day(u.birthDay) = day(CURRENT_DATE) and month(u.birthDay) = month(CURRENT_DATE) and u.status=true")
	public List<UserDetail> usersWithBirthDayToday();
}
