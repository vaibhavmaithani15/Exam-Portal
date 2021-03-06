package com.cognizant.normal.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cognizant.normal.feing.AdminClient;
import com.cognizant.normal.feing.AuthClient;
import com.cognizant.normal.models.QuestionDTO;
import com.cognizant.normal.models.QuizDTO;
import com.cognizant.normal.models.ResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/question")
@AllArgsConstructor
@CrossOrigin("*")
public class QuestionController {
    private AdminClient adminClient;
    private AuthClient authClient;
    
    //get all question of any quiz according to maxQuestion value only for normal user
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> getQuestionByQuiz(@PathVariable ("quizId") Long quizId,@RequestHeader(name = "Authorization", required = true)String token) throws Exception{
    	
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
    	List<QuestionDTO> questions=adminClient.getQuestionByQuiz(quizId, token);//convert this to quiz dto ryt now it is in json/application
    	System.out.println(questions);
    	return ResponseEntity.ok().body(questions);
    }
    
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<QuestionDTO> questions,@RequestHeader(name = "Authorization", required = true)String token) throws Exception{
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
    	ResultDTO result=adminClient.evalQuiz(questions,token);
    	return ResponseEntity.ok().body(result);
    }
}
