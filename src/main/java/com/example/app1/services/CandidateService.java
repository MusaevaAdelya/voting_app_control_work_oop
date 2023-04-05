package com.example.app1.services;

import com.example.app1.dto.CandidateDto;
import com.example.app1.dto.NewCandidateDto;
import com.example.app1.entities.Candidate;
import com.example.app1.enums.CandidateStatus;
import com.example.app1.repositories.CandidateRepository;
import com.example.app1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final FileStorageImpl fileStorage;

    public boolean isVotingOver() {
        Optional<Candidate> winner=candidateRepository.findWinner();
        return winner.isPresent();

    }

    public List<CandidateDto> getCandidateDtos() {
        return candidateRepository.findCandidates(CandidateStatus.ACTIVE).stream()
                .map(this::getCandidateDto).collect(Collectors.toList());
    }

    public List<CandidateDto> getCandidateDtos(boolean sortedByVotes) {
        List<CandidateDto> candidates= candidateRepository.findCandidates(CandidateStatus.ACTIVE).stream()
                .map(this::getCandidateDto).sorted((c1, c2) -> {
                    return c2.getVotes() - c1.getVotes();
                }).collect(Collectors.toList());

        return candidates;
    }

    public CandidateDto getWinner(){
        Candidate winner=candidateRepository.findWinner().orElseThrow();
        return getCandidateDto(winner);
    }

    public CandidateDto getCandidateDto(Candidate c){
        return CandidateDto.builder()
                .id(c.getId())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .image(c.getImage())
                .winner(c.getWinner())
                .votes(calculateVotes(c))
                .build();
    }

    private Integer calculateVotes(Candidate candidate){
        return userRepository.getVoters(candidate.getId(),CandidateStatus.ACTIVE).size();
    }

    public void endVoting() {
        List<CandidateDto> sortedCandidates= getCandidateDtos(true);
        if(sortedCandidates.size()>0){
            CandidateDto winnerDto=sortedCandidates.get(0);
            Candidate winner=candidateRepository.findById(winnerDto.getId()).orElseThrow();
            winner.setWinner(true);
            candidateRepository.save(winner);
        }

    }

    @SneakyThrows
    public void addNewCandidate(NewCandidateDto newCandidate) {
        String savedImagePath=fileStorage.save(newCandidate.getImage().getInputStream(),newCandidate.getImage().getOriginalFilename());
        Candidate candidate= Candidate.builder()
                .firstName(newCandidate.getFirstName())
                .lastName(newCandidate.getLastName())
                .image(savedImagePath)
                .build();
        candidateRepository.save(candidate);
    }
}
