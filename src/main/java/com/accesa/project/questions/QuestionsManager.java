package com.accesa.project.questions;

import com.accesa.project.user.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionsManager {
    private QuestionsDAO questionsDAO;
    private SessionManager sessionManager;

    public QuestionsManager() throws SQLException {
        questionsDAO = new QuestionsDAO();
        sessionManager = SessionManager.getInstance();
    }

    public List<String[]> getQuestionAnswersList() throws SQLException {
        List<Question> questionList = questionsDAO.getAllQuestions();
        List<String[]> questionAnswersList = new ArrayList<>();

        for (Question question : questionList) {
            if (question.isApproved() && !questionsDAO.onTheList(sessionManager.getCurrentUserId(), question.getId())) {
                questionList(questionAnswersList, question);
            }
        }

        return questionAnswersList;
    }

    private void questionList(List<String[]> questionAnswersList, Question question) {
        String[] questionAnswers = new String[4];
        questionAnswers[0] = String.valueOf(question.getId());
        questionAnswers[1] = question.getQuestion();
        questionAnswers[2] = String.valueOf(question.getRewards());
        questionAnswers[3] = String.valueOf(question.isAnswer());
        questionAnswersList.add(questionAnswers);
    }

    public List<String[]> getApprovalList() {
        List<Question> questionList = questionsDAO.getAllQuestions();
        List<String[]> approvalList = new ArrayList<>();

        for (Question question : questionList) {
            if (!question.isApproved()) {
                questionList(approvalList, question);
            }
        }
        return approvalList;
    }

    public void interactionWithQuestion(int idUser, int idQuestion, boolean status) throws SQLException {
        questionsDAO.interactions(idUser, idQuestion, status);
    }

    public void approveQuestion(int id) throws SQLException {
        questionsDAO.updateQuestionApproval(id, true);
    }

    public void deleteQuestion(int id) throws SQLException {
        questionsDAO.deleteQuestion(id);
    }

    public void insertQuestion(String question, Boolean answer,  int reward, int idUser) throws SQLException {
        questionsDAO.insertQuestion(question, answer, reward, idUser);
    }
}
