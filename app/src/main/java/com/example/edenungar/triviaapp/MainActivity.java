package com.example.edenungar.triviaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements QuestionCreatorFragment.Callback {

    private QuestionCreatorFragment questionCreatorFragment;
    private QuizFragment quizFragment;
    private List<Question> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //just put "this" because this is an activity and the fragment is not
        ButterKnife.bind(this);

        questionsList = new ArrayList<>();
    }

    //whatever you put in this method will be executed
    @OnClick(R.id.add_question_button)
    protected void addQuestionClicked() {

        questionCreatorFragment = QuestionCreatorFragment.newInstance();

        questionCreatorFragment.attachParent(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, questionCreatorFragment).commit();

    }

    @Override
    public void questionSaved(Question question) {

        //adds new questions to ArrayList
        questionsList.add(question);

        //shows a Toast to confirm question has been added to list
        Toast.makeText(this, "question saved!", Toast.LENGTH_SHORT).show();

        //removes the fragment from the FrameLayout
        getSupportFragmentManager().beginTransaction().remove(questionCreatorFragment).commit();

    }

    @OnClick(R.id.take_quiz_button)
    public void takeQuizClicked() {

        if (questionsList.isEmpty()) {
            //handle toast for if there are no questions saved
            Toast.makeText(this, "there are no questions saved", Toast.LENGTH_SHORT).show();
        } else {
            //launch fragment, pass in a parcelable array
            quizFragment = QuizFragment.newInstance();

            //will attach to parent later

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, quizFragment).commit();

            Bundle
        }

    }

}
