package com.example.edenungar.triviaapp;

import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements QuestionCreatorFragment.Callback, QuizFragment.QuizCallback {

    private QuestionCreatorFragment questionCreatorFragment;
    private QuizFragment quizFragment;
    private List<Question> questionsList;

    public static final String QUESTIONS_LIST = "questions_list";

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

            Bundle bundle = new Bundle();

            bundle.putParcelableArrayList(QUESTIONS_LIST, (ArrayList<? extends Parcelable>) questionsList);

        }

    }

    @Override
    public void quizFinished(int correctAnswers) {

        //removes the fragment from the FrameLayout
        //put quizFragment not QuizFragment bc you only call the instance of the fragment we made not calling the actual class
        getSupportFragmentManager().beginTransaction().remove(quizFragment).commit();

        showQuizResultsAlertDialog(correctAnswers);

    }

    //this message shows our alert dialog
    private void showQuizResultsAlertDialog(int correctAnswers) {

        //instantiate an AlertDialog.Builder with its constructor
        //put this instead of getActivity bc we're in the main not a fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.quiz_finished)
                .setMessage(getString(R.string.number_of_correct_answers, correctAnswers))
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

    }

    @OnClick(R.id.delete_quiz_button)
    protected void deleteQuizClicked() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_quiz_alert)
                .setMessage(R.string.are_you_sure_you_want_to_delete)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete quiz and close AD
                        questionsList.clear();
                        dialogInterface.dismiss();
                        Toast.makeText(MainActivity.this, "quiz deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //closes AD but DOESN'T delete quiz
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}
