package com.leavemanagement.leavemanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leavemanagement.leavemanagementsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findById(Long id);

}
