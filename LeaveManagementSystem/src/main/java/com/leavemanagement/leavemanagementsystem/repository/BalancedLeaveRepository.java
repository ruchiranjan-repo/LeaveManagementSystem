package com.leavemanagement.leavemanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leavemanagement.leavemanagementsystem.entity.BalancedLeave;

@Repository
public interface BalancedLeaveRepository extends JpaRepository<BalancedLeave,Long> {
	
	Optional<BalancedLeave> findByUserId(Long id);

}
