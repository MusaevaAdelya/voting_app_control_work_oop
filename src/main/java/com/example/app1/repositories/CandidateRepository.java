package com.example.app1.repositories;

import com.example.app1.entities.Candidate;
import com.example.app1.enums.CandidateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query(value = "select c from Candidate c where c.winner = true and c.status='ACTIVE'")
    Optional<Candidate> findWinner();

    @Query(value="select c from Candidate c where c.status=:status order by c.id desc")
    List<Candidate> findCandidates(CandidateStatus status);

    @Modifying(clearAutomatically = true)
    @Query("update Candidate c set c.status='DELETED' where c.status='ACTIVE'")
    void deleteCandidates();

}
