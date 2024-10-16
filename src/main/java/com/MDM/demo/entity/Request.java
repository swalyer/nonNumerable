package com.MDM.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String requesterName;
    @Column(nullable = false)
    private String requesterEmail;
    @Column(nullable = false)
    private String requestType;
    @Column(nullable = false)
    private LocalDateTime requestedDate; 
    @Column(nullable = false)
    private String status;
    @Column(length = 500)
    private String additionalInfo; 


    private LocalDate vacationStartDate;
    private LocalDate vacationEndDate;


    @Column(length = 500)
    private String unpaidLeaveReason;
    private LocalDate unpaidLeaveStartDate;
    private LocalDate unpaidLeaveEndDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public LocalDate getVacationStartDate() {
        return vacationStartDate;
    }

    public void setVacationStartDate(LocalDate vacationStartDate) {
        this.vacationStartDate = vacationStartDate;
    }

    public LocalDate getVacationEndDate() {
        return vacationEndDate;
    }

    public void setVacationEndDate(LocalDate vacationEndDate) {
        this.vacationEndDate = vacationEndDate;
    }

    public String getUnpaidLeaveReason() {
        return unpaidLeaveReason;
    }

    public void setUnpaidLeaveReason(String unpaidLeaveReason) {
        this.unpaidLeaveReason = unpaidLeaveReason;
    }

    public LocalDate getUnpaidLeaveStartDate() {
        return unpaidLeaveStartDate;
    }

    public void setUnpaidLeaveStartDate(LocalDate unpaidLeaveStartDate) {
        this.unpaidLeaveStartDate = unpaidLeaveStartDate;
    }

    public LocalDate getUnpaidLeaveEndDate() {
        return unpaidLeaveEndDate;
    }

    public void setUnpaidLeaveEndDate(LocalDate unpaidLeaveEndDate) {
        this.unpaidLeaveEndDate = unpaidLeaveEndDate;
    }
}
