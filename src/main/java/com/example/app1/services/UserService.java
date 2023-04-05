package com.example.app1.services;

import com.example.app1.config.SecurityConfig;
import com.example.app1.dto.RegisterForm;
import com.example.app1.entities.Candidate;
import com.example.app1.entities.User;
import com.example.app1.enums.CandidateStatus;
import com.example.app1.enums.UserRoles;
import com.example.app1.repositories.CandidateRepository;
import com.example.app1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;

    public void register(RegisterForm user) {
        log.info("registering user");
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        userRepository.save(User.builder()
                .email(user.getEmail())
                .password(SecurityConfig.encoder().encode(user.getPassword()))
                .build());

        SecurityConfig.authWithHttpServletRequest(request, user.getEmail(), user.getPassword());
    }


    public boolean isAdmin(Authentication authentication) {
        String email=authentication.getName();
        UserRoles role=userRepository.findRoleByEmail(email).orElseThrow(()->new UsernameNotFoundException("not found "+email));
        return role == UserRoles.ADMIN;
    }

    public boolean hasAlreadyVoted(String email) {
        Optional<Candidate> votedCandidate=userRepository.findVotedCandidate(email, CandidateStatus.ACTIVE);
        return votedCandidate.isPresent();
    }

    public void vote(String email, Long candidateId) {
        User user=userRepository.findByEmail(email).orElseThrow();
        Candidate candidate=candidateRepository.findById(candidateId).orElseThrow();
        user.setCandidate(candidate);
        userRepository.save(user);
    }

    @SneakyThrows
    public void renewVoting() {
        FileUtils.cleanDirectory(new File("files"));
        candidateRepository.deleteCandidates();
    }
}
