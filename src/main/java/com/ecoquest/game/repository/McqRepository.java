package com.ecoquest.game.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import com.ecoquest.game.model.Mcq;

public interface McqRepository extends MongoRepository<Mcq, Integer> {

    @Query(value = "{}", fields = "{ '_id':1, 'question': 1, 'answers': 1}")
    List<Mcq> getQuestionsAndAnswers();

    @Query(value = "{}", fields = "{ '_id': 1, 'crtAnswer': 1, 'generalFeedback': 1, 'specificFeedback': 1}")
    List<Mcq> getCorrectAnswerList();
    
} 