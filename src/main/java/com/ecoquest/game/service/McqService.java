package com.ecoquest.game.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoquest.game.model.Mcq;
import com.ecoquest.game.repository.McqRepository;

import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

@Service
public class McqService {

    @Autowired
    McqRepository mcqRepository;

    public JSONObject calculateTotalMarks(int[] answers) {

    List<Mcq> mcqAnswers = mcqRepository.getCorrectAnswerList();
    int questionNo;
    int crtAnswer;
    List<Integer> answerStatus = new ArrayList<>();
    List<String> generalFeedbackList = new ArrayList<>();
    List<String> specicificFeedbackList = new ArrayList<>();

    int totalMarks = 0;
System.out.println("1st step");
    for (int i = 0; i < answers.length; i++) {
        System.out.println("2nd step");
        questionNo = mcqAnswers.get(i).get_id();
        crtAnswer = mcqAnswers.get(i).getCrtAnswer();
        System.out.println("3rd step");
        generalFeedbackList.add(mcqAnswers.get(i).getGeneralFeedback());
        System.out.println("4th step");
        specicificFeedbackList.add(mcqAnswers.get(i).getSpecificFeedback()[crtAnswer-1]);
        System.out.println("5th step");
        if (answers[questionNo - 1] == crtAnswer) {
            totalMarks++;
            answerStatus.add(1);
        } else {
            answerStatus.add(0);
        }
    }

    
    JSONObject result = new JSONObject();
    result.put("totalMarks", totalMarks);
    result.put("answerStatus", new JSONArray(answerStatus));
    result.put("generalFeedback", new JSONArray(generalFeedbackList));

    result.put("specificFeedback", new JSONArray(specicificFeedbackList));

   
    return result;
}
}