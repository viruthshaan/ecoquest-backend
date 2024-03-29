package com.ecoquest.game.controller;

import com.ecoquest.game.model.Mcq;
import com.ecoquest.game.repository.McqRepository;
import com.ecoquest.game.service.McqService;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})

@RequestMapping(value = "")

public class McqController {

    @Autowired
    McqRepository mcqRepository;

    @Autowired
    private McqService mcqService;


    // just for redirecting to swagger
    @RequestMapping(value = "/")
    public void redirect(javax.servlet.http.HttpServletResponse response) throws Exception {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/get")
    public List<Mcq> getQuestions() {
  
        return mcqRepository.getQuestionsAndAnswers();

    }

    @GetMapping("/checkAnswers")
    public String checkAnswers(@RequestParam int[] answers) {
        JSONObject correctAnswerWithFeedbacks = mcqService.calculateTotalMarks(answers);

        // Forwarding to the UserProfileController
        return correctAnswerWithFeedbacks.toString();
    }

}
