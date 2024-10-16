package com.MDM.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MDM.demo.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
