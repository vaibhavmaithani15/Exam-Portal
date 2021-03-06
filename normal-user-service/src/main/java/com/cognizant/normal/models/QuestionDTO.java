package com.cognizant.normal.models;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuestionDTO 
{
	
	
	private Long questionId;
    private String content;
    private String image;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @JsonIgnore
    private String answer;
    private String givenAnswer;
    private QuizDTO quiz;
    
}
