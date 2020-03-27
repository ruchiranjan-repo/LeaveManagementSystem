package com.leavemanagement.leavemanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leavemanagement.leavemanagementsystem.entity.AvailedLeave;

@Repository
public interface AvailedLeaveRepository extends JpaRepository<AvailedLeave,Long> {
	
	List<AvailedLeave> findByUserId(Long id);

}
