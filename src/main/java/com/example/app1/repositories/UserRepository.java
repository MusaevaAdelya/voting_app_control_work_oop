package com.example.app1.repositories;

import com.example.app1.entities.Candidate;
import com.example.app1.entities.User;
import com.example.app1.enums.CandidateStatus;
import com.example.app1.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "select u.role from User u where u.email=:email")
    Optional<UserRoles> findRoleByEmail(String email);

    @Query(value="select u from User u where u.candidate.id=:candidateId and u.candidate.status=:status ")
    List<User> getVoters(Long candidateId, CandidateStatus status);

    @Query(value="select u.candidate from User u where u.email=:email and u.candidate.status=:status")
    Optional<Candidate> findVotedCandidate(String email,CandidateStatus status);
}