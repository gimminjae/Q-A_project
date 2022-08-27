package com.qna_project;

import com.qna_project.answer.Answer;
import com.qna_project.answer.AnswerRepository;
import com.qna_project.question.Question;
import com.qna_project.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QnAProjectApplicationTests {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testData() {
        for(int i = 0; i < 100; i++) {
            Question question = new Question();
            question.setSubject("subject%d".formatted(i+1));
            question.setContent("content%d".formatted(i+1));
            question.setCreateDate(LocalDateTime.now());
            questionRepository.save(question);
        }
    }

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
    //답변 테스트
    @Test
    void testJpa_답변저장() {
        Optional<Question> oq = this.questionRepository.findById(1L);
        assertThat(oq.isPresent()).isEqualTo(true);
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("answer1");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }
    @Test
    void testJpa_답변조회() {
        Optional<Answer> oa = this.answerRepository.findById(1L);
        assertThat(oa.isPresent()).isEqualTo(true);
        Answer a = oa.get();
        assertEquals(1, a.getQuestion().getId());
    }
    @Transactional
    @Test
    void testJpa_답변조회2() {
        Optional<Question> oq = this.questionRepository.findById(1L);
        assertThat(oq.isPresent()).isEqualTo(true);
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("answer1", answerList.get(0).getContent());
    }
}
