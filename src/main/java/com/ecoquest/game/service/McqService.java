package com.ecoquest.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoquest.game.model.Mcq;
import com.ecoquest.game.repository.McqRepository;
import java.util.List;

@Service
public class McqService {

    @Autowired
    McqRepository mcqRepository;

    public int calculateTotalMarks(int[] answers) {

        List<Mcq> mcqAnswers = mcqRepository.getCorrectAnswerList();
        int questionNo;
        int crtAnswer;
        int totalMarks = 0;

        for (int i = 0; i < answers.length; i++) {
            questionNo = mcqAnswers.get(i).get_id();
            crtAnswer = mcqAnswers.get(i).getCrtAnswer();
            if (answers[questionNo - 1] == crtAnswer) {
                totalMarks++;
            }
        }
        // Implement your logic to calculate total marks based on answers
        // This is a placeholder; replace it with your actual implementation
        return totalMarks; // Example: 10 marks for correct answers
    }
}