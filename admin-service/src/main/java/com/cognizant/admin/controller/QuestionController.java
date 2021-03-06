package com.cognizant.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cognizant.admin.feing.AuthClient;
import com.cognizant.admin.models.Question;
import com.cognizant.admin.models.Quiz;
import com.cognizant.admin.models.Result;
import com.cognizant.admin.service.QuestionService;
import com.cognizant.admin.service.QuizService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/question")
@AllArgsConstructor
@CrossOrigin("*")
public class QuestionController {
    private QuestionService questionService;
    private QuizService quizService;
    private AuthClient authClient;
    private Result result;
    @PostMapping("/")
    public ResponseEntity<?> addQuestion(@RequestBody Question question,@RequestHeader(name = "Authorization", required = true)String token) throws Exception {
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
        question.setImage("default.png");
        return ResponseEntity.ok().body(questionService.addQuestion(question));
    }

    @PutMapping("/")
    public ResponseEntity<?> updateQuestion(@RequestBody Question question,@RequestHeader(name = "Authorization", required = true)String token) throws Exception {
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
        return ResponseEntity.ok().body(questionService.addQuestion(question));
    }
    //get all question of any quiz according to maxQuestion value use only for normal user
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<?> getQuestionByQuiz(@PathVariable ("quizId") Long quizId,@RequestHeader(name = "Authorization", required = true)String token) throws Exception{
    	System.out.println("------------Inside Admin Question Controller----------");
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
    	Quiz quiz = quizService.getQuiz(quizId);
        Set<Question> questions = quiz.getQuestions();
        List<Question> list=new ArrayList<Question>(questions);
        if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
            list=list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1));
        }
        Collections.shuffle(list);
        return ResponseEntity.ok().body(list);
    }
    
     
    
    

	@GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable ("questionId") Long questionId,@RequestHeader(name = "Authorization", required = true)String token) throws Exception{
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
    	return ResponseEntity.ok().body(questionService.getQuestion(questionId));
    }
    @DeleteMapping("/{questionId}")
    public void deletQuestion(@PathVariable ("questionId") Long questionId,@RequestHeader(name = "Authorization", required = true)String token) throws Exception{
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
    	questionService.deleteQuestion(questionId);
    }
    //get all the question without any constraints for admin
    @GetMapping("/questionAll/{quizId}")
    public ResponseEntity<?> getAllQustionsByQuiz(@PathVariable ("quizId") Long quizId,@RequestHeader(name = "Authorization", required = true)String token) throws Exception{
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
    	Quiz quiz=quizService.getQuiz(quizId);
        Set<Question> questions=quiz.getQuestions();
        List<Question> list=new ArrayList<Question>(questions);
        Collections.shuffle(list);
        return ResponseEntity.ok().body(list);
    }
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions,@RequestHeader(name = "Authorization", required = true)String token) throws Exception{
    	double marksGot=0;
    	int correctAnswer=0;
    	int attempted=0;
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new Exception("Token is either expired or invalid...");
		}
    	for (Question question : questions) {
    		Question qs = questionService.getQuestion(question.getQuestionId());
    		if(qs.getAnswer().equals(question.getGivenAnswer())) {
    			correctAnswer++;
    			double marksSingle=Double .parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
    			marksGot+=marksSingle;
    		}
    		if(question.getGivenAnswer()!=null) {
    			attempted++;
    		}
		}
    	
    	result.setMarksGot(marksGot);
    	result.setCorrectAnswer(correctAnswer);
    	result.setAttempted(attempted);
    	return ResponseEntity.ok().body(result);
    }
}
