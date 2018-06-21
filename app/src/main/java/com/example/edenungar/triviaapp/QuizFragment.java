package com.example.edenungar.triviaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.edenungar.triviaapp.MainActivity.QUESTIONS_LIST;

public class QuizFragment extends Fragment {

    @BindView(R.id.quiz_question_textview)
    protected TextView quizQuestion;
    //have to bind buttons to be able to change the text (but not next_button bc we don't have to change that)
    @BindView(R.id.first_answer_button)
    protected Button firstAnswerButton;
    @BindView(R.id.second_answer_button)
    protected Button secondAnswerButton;
    @BindView(R.id.third_answer_button)
    protected Button thirdAnswerButton;
    @BindView(R.id.fourth_answer_button)
    protected Button fourthAnswerButton;

    private List<Question> questionsList;
    private Question question;
    private int questionListPosition = 0;
    //variable to count how many correct answers they have for the score
    private int correctAnswers = 0;

    private QuizCallback quizCallback;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    public static QuizFragment newInstance() {

        Bundle args = new Bundle();

        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        questionsList = getArguments().getParcelableArrayList(QUESTIONS_LIST);

        populateQuizContent();

    }

    private void populateQuizContent() {

        question = questionsList.get(questionListPosition);
        quizQuestion.setText(question.getQuestion());

        List<Button> buttonList = new ArrayList<>();
        buttonList.add(firstAnswerButton);
        buttonList.add(secondAnswerButton);
        buttonList.add(thirdAnswerButton);
        buttonList.add(fourthAnswerButton);

        List<String> possibleAnswersList = new ArrayList<>();
        possibleAnswersList.add(question.getCorrectAnswer());
        possibleAnswersList.add(question.getWrongAnswerOne());
        possibleAnswersList.add(question.getWrongAnswerTwo());
        possibleAnswersList.add(question.getWrongAnswerThree());

        //for each loop takes the arrayLists and allows us to randomize what answer goes on which button
        for (Button button : buttonList) {
            //takes a random # out of 4 (the possibleAnswersList.size bc there are 4 possible answers)
            int random = (int) Math.ceil(Math.random() * (possibleAnswersList.size() - 1));

            //sets text to a random answer
            button.setText(possibleAnswersList.get(random));

            //removes the answer we put on a button already so we don't have two of the same answers
            possibleAnswersList.remove(random);
        }

    }

    private void checkAnswer(String answer) {
        //makes it so we go to the next question and the same question will not come up more than once

        disableAnswerButtons();

        questionListPosition++;

        if (question.getCorrectAnswer().equals(answer)) {
            //handles if they choose the right answer
            //changes the question_text_view to correct
            quizQuestion.setText(R.string.correct_answer_text);
            //adds one to their overall correct score
            correctAnswers++;

        } else {
            //changes text to say that they are incorrect and tells them the correct answer
            quizQuestion.setText(getString(R.string.wrong_answer_text, question.getCorrectAnswer()));
        }

    }

    //these take if you click a button and check then with the checkAnswer method
    @OnClick(R.id.first_answer_button)
    protected void buttonOneClicked() {

        checkAnswer(firstAnswerButton.getText().toString());

    }

    @OnClick(R.id.second_answer_button)
    protected void buttonTwoClicked() {

        checkAnswer(secondAnswerButton.getText().toString());

    }

    @OnClick(R.id.third_answer_button)
    protected void buttonThreeClicked() {

        checkAnswer(thirdAnswerButton.getText().toString());

    }

    @OnClick(R.id.fourth_answer_button)
    protected void buttonFourClicked() {

        checkAnswer(fourthAnswerButton.getText().toString());

    }

    @OnClick(R.id.next_button)
    protected void nextButtonClicked() {

        enableAnswerButtons();

        if (questionListPosition <= questionsList.size() - 1) {
            populateQuizContent();
        } else {
            //handles no more questions left in the list. taking user back to the main activity
            quizCallback.quizFinished(correctAnswers);
        }
    }

    //to disable buttons
    private void disableAnswerButtons() {

        firstAnswerButton.setEnabled(false);
        secondAnswerButton.setEnabled(false);
        thirdAnswerButton.setEnabled(false);
        fourthAnswerButton.setEnabled(false);

    }

    //to re-enable buttons
    private void enableAnswerButtons() {

        firstAnswerButton.setEnabled(true);
        secondAnswerButton.setEnabled(true);
        thirdAnswerButton.setEnabled(true);
        fourthAnswerButton.setEnabled(true);

    }

    public void attachParent(QuizCallback quizCallback) {

        this.quizCallback = quizCallback;

    }

    public interface QuizCallback {
        void quizFinished(int correctAnswers);
    }

}
