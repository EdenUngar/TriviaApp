package com.example.edenungar.triviaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizFragment extends Fragment {

    @BindView(R.id.quiz_question_textview)
    protected TextView quizQuestion;

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

    @OnClick(R.id.first_answer_button)
    protected void firstAnswer(){

    }

    @OnClick(R.id.second_answer_button)
    protected void secondAnswer(){

    }

    @OnClick(R.id.third_answer_button)
    protected void thirdAnswer(){

    }

    @OnClick(R.id.fourth_answer_button)
    protected void fourthAnswer(){

    }

}
