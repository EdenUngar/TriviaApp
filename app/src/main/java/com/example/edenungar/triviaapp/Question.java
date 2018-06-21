package com.example.edenungar.triviaapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

    private String question;
    private String correctAnswer;
    private String wrongAnswerOne;
    private String wrongAnswerTwo;
    private String wrongAnswerThree;

    public Question(String question, String correctAnswer, String wrongAnswerOne, String wrongAnswerTwo, String wrongAnswerThree) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswerOne = wrongAnswerOne;
        this.wrongAnswerTwo = wrongAnswerTwo;
        this.wrongAnswerThree = wrongAnswerThree;
    }

    //every time we make a question object it will be saved in the parcel format
    //packages all of our information in neat packages
    protected Question(Parcel in) {
        question = in.readString();
        correctAnswer = in.readString();
        wrongAnswerOne = in.readString();
        wrongAnswerTwo = in.readString();
        wrongAnswerThree = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };


    //do not need setters because questions and answers will not change
    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongAnswerOne() {
        return wrongAnswerOne;
    }

    public String getWrongAnswerTwo() {
        return wrongAnswerTwo;
    }

    public String getWrongAnswerThree() {
        return wrongAnswerThree;
    }


    //part of implementing parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeString(correctAnswer);
        parcel.writeString(wrongAnswerOne);
        parcel.writeString(wrongAnswerTwo);
        parcel.writeString(wrongAnswerThree);
    }
}
