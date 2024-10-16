package com.MDM.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.MDM.demo.entity.Request;
import com.MDM.demo.entity.User;
import com.MDM.demo.repository.RequestRepository;
import com.MDM.demo.repository.UserRepository;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestDocumentGenerator requestDocumentGenerator;

    @Autowired
    private UserRepository userRepository;

    public Request createRequest(Request request) {
       
        request.setStatus("в ожидании");
        request.setRequestedDate(LocalDateTime.now());

      
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String middleName = user.getMiddleName();
            String personalNumber = user.getPersonalNumber();

        
            requestDocumentGenerator.generateRequestDocument(
                request.getRequestType(),
                firstName,
                lastName,
                middleName,
                personalNumber,
                request.getRequestedDate(),
                request.getAdditionalInfo(),
                request.getVacationStartDate(),
                request.getVacationEndDate(),
                request.getUnpaidLeaveReason(),
                request.getUnpaidLeaveStartDate(),
                request.getUnpaidLeaveEndDate()
            );
        } else {
           
            throw new RuntimeException("Пользователь не найден");
        }

       
        return requestRepository.save(request);
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Request getRequestById(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        return optionalRequest.orElse(null);
    }

    public boolean approveRequest(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            request.setStatus("утверждена");
            requestRepository.save(request);
            return true;
        }
        return false;
    }

    public boolean rejectRequest(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            request.setStatus("отклонена");
            requestRepository.save(request);
            return true;
        }
        return false;
    }
}
