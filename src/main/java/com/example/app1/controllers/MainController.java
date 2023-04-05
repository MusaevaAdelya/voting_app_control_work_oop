package com.example.app1.controllers;

import com.example.app1.dto.NewCandidateDto;
import com.example.app1.services.CandidateService;
import com.example.app1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CandidateService candidateService;
    private final UserService userService;

    @GetMapping("/")
    public String getMainPage(Model model, Authentication authentication){
        model.addAttribute("isAdmin",userService.isAdmin(authentication));
        model.addAttribute("alreadyVoted",userService.hasAlreadyVoted(authentication.getName()));

        if(candidateService.isVotingOver()){
            model.addAttribute("endOfVoting",true);
            model.addAttribute("winner",candidateService.getWinner());
            model.addAttribute("candidates",candidateService.getCandidateDtos(true));
        }else{
            model.addAttribute("endOfVoting",false);
            model.addAttribute("candidates",candidateService.getCandidateDtos());
        }

        return "index";
    }

    @PostMapping("/end-voting")
    public String endVoting(){

        candidateService.endVoting();
        return "redirect:/";
    }

    @PostMapping("/vote")
    public String endVoting(Authentication authentication, @RequestParam Long candidateId){
        userService.vote(authentication.getName(),candidateId);
        return "redirect:/";
    }

    @PostMapping("/renew-voting")
    public String renewVoting(){
        userService.renewVoting();
        return "redirect:/";
    }

    @PostMapping("/add-candidate")
    public String addCandidate(@ModelAttribute NewCandidateDto newCandidate){
        candidateService.addNewCandidate(newCandidate);
        return "redirect:/";
    }


}
