package com.qna_project;

import com.qna_project.question.Question;
import com.qna_project.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QnAProjectApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testJpa_저장() {
        Question question1 = new Question();
        question1.setSubject("subject1");
        question1.setContent("content1");
        question1.setCreateDate(LocalDateTime.now());
        questionRepository.save(question1);

        Question question2 = new Question();
        question2.setSubject("subject2");
        question2.setContent("content2");
        question2.setCreateDate(LocalDateTime.now());
        questionRepository.save(question2);
    }
    @Test
    void testJpa_조회() {
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList.size()).isEqualTo(2);
    }
    @Test
    void testJpa_조회2() {
        Optional<Question> optionalQuestion = questionRepository.findById(1L);
        assertThat(optionalQuestion.isPresent()).isEqualTo(true);

        Question question = optionalQuestion.get();
        assertThat(question.getSubject()).isEqualTo("subject1");
    }
    @Test
    void testJpa_조회3() {
        Optional<Question> optionalQuestion = questionRepository.findBySubject("subject1");
        assertThat(optionalQuestion.isPresent()).isEqualTo(true);

        Question question = optionalQuestion.get();
        assertThat(question.getId()).isEqualTo(1);
    }
    @Test
    void testJpa_조회4() {
        Optional<Question> optionalQuestion = questionRepository.findBySubjectAndContent("subject1", "content1");
        assertThat(optionalQuestion.isPresent()).isEqualTo(true);

        Question question = optionalQuestion.get();
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    void testJpa_조회5() {
        List<Question> qList = this.questionRepository.findBySubjectLike("subject%");
        Question q = qList.get(0);
        assertEquals("subject1", q.getSubject());
    }
    @Test
    void testJpa_수정() {
        Optional<Question> optionalQuestion = questionRepository.findById(2L);
        assertThat(optionalQuestion.isPresent()).isEqualTo(true);

        Question question = optionalQuestion.get();
        question.setSubject("수정된 제목");
        questionRepository.save(question);
    }
    @Test
    void testJpa_삭제() {
        Optional<Question> optionalQuestion = questionRepository.findById(2L);
        assertThat(optionalQuestion.isPresent()).isEqualTo(true);

        Question question = optionalQuestion.get();
        questionRepository.delete(question);

        assertThat(questionRepository.findAll().size()).isEqualTo(1);
    }


}
