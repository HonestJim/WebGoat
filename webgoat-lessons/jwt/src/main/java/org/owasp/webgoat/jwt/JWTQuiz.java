package org.owasp.webgoat.jwt;

import org.owasp.webgoat.assignments.AssignmentEndpoint;
import org.owasp.webgoat.assignments.AttackResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

@RestController
public class JWTQuiz extends AssignmentEndpoint {

    private final String[] solutions = {"Solution 1", "Solution 3"};
    private final boolean[] guesses = new boolean[solutions.length];

    @PostMapping("/JWT/quiz")
    @ResponseBody
    public AttackResult completed(@RequestParam String[] question_0_solution, @RequestParam String[] question_1_solution) {
        int correctAnswers = 0;

        if (question_0_solution.length == 0 || question_1_solution.length == 0) {
            return failed(this).feedback("Invalid input").build();
        }
        String[] givenAnswers = {question_0_solution[0], question_1_solution[0]};

        for (int i = 0; i < solutions.length; i++) {
            if (givenAnswers[i].contains(solutions[i])) {
                // answer correct
                correctAnswers++;
                guesses[i] = true;
            } else {
                // answer incorrect
                guesses[i] = false;
            }
        }

        if (correctAnswers == solutions.length) {
            return success(this).build();
        } else {
            return failed(this).build();
        }
    }

    @GetMapping("/JWT/quiz")
    @ResponseBody
    public boolean[] getResults() {
        // Only return this if exposing guesses is safe
        return Arrays.copyOf(this.guesses, this.guesses.length);
    }

}