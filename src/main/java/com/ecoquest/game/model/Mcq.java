package com.ecoquest.game.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

//the following code maps this model with the collection in mongodb
@Document(collection = "MCQ")
@Data //it removes the boilor plate code for get set methods
public class Mcq{

    @Id // it will denote the variable under this, as primary key.
    private int _id;

    private String question;
    private String[] answers;
    private int crtAnswer;

    @Override
    public String toString() {

        return String.format(
            "mcq[question=%s]", this.question
        );
    }

}