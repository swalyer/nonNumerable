package com.MDM.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MDM.demo.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}
