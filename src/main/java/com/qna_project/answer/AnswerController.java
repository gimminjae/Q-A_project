package com.qna_project.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("answer")
@RequiredArgsConstructor
public class AnswerController {
    private AnswerService answerService;
}
