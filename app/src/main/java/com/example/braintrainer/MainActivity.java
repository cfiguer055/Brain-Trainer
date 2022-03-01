package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton; //default: invisible
    TextView timerTextView, questionTextView, scoreTextView; //default: visible
    Button option1, option2, option3, option4; //default: visible
    TextView gameOverTextView; //default: invisible
    Button playAgain; //default: invisible

    CountDownTimer countDownTimer;


    Random rand = new Random();


    boolean gameInProgress = false;

    int firstNum, secondNum;
    int opNum;
    String operator = "";

    int totalQuestions = 0, numCorrect = 0;


    private void gameSetVisibility(boolean b) {

        if(b) {

            timerTextView.setVisibility(View.VISIBLE);
            questionTextView.setVisibility(View.VISIBLE);
            scoreTextView.setVisibility(View.VISIBLE);

            option1.setVisibility(View.VISIBLE);
            option2.setVisibility(View.VISIBLE);
            option3.setVisibility(View.VISIBLE);
            option4.setVisibility(View.VISIBLE);

        } else {
            timerTextView.setVisibility(View.INVISIBLE);
            questionTextView.setVisibility(View.INVISIBLE);
            scoreTextView.setVisibility(View.INVISIBLE);

            option1.setVisibility(View.INVISIBLE);
            option2.setVisibility(View.INVISIBLE);
            option3.setVisibility(View.INVISIBLE);
            option4.setVisibility(View.INVISIBLE);

        }
    }


    public void generateQuestion() {

        Log.i("start generateQuestion(): ", "PASS");

        //1=+, 2=-, 3=*, 4=%
        opNum = rand.nextInt(4) + 1;

        if(opNum == 1) {
            firstNum = rand.nextInt(500);
            secondNum = rand.nextInt(500);
            operator = "+";

        } else if(opNum == 2) {
            secondNum = rand.nextInt(1000);
            firstNum = rand.nextInt(1000 - secondNum) + secondNum;
            operator = "-";

        } else if(opNum == 3) {
            firstNum = rand.nextInt(31);
            secondNum = rand.nextInt(32);
            operator = "*";

        } else /*operator == 4*/{
            firstNum = rand.nextInt(1000);

            do {
                secondNum = rand.nextInt();
            } while(firstNum % secondNum != 0);
            operator = "%";
        }

        Log.i("generate numbers to be calculated: ", "PASS");

        questionTextView.setText(firstNum + " " + operator + " " + secondNum);
        //generateAnswers(firstNum, secondNum, opNum);
    }


    public void generateAnswers(int num1, int num2, int op) {
        int correctAnswer;
        int wrongAnswer1, wrongAnswer2, wrongAnswer3;
        int answerTag1, answerTag2, answerTag3, answerTag4;
        int correctTag;

        boolean identicalNum = true;

        Log.i("start generateAnswers() ", "PASS");
        //MAYBE DO BOOLEAN FOR CONDITIONS
        if(op == 1) {
            correctAnswer = num1 + num2;

            do {
                wrongAnswer1 = (int) (((double) num1 * rand.nextDouble()) + ((double) num1 * rand.nextDouble()));
                wrongAnswer2 = (int) (((double) num1 * rand.nextDouble()) + ((double) num1 * rand.nextDouble()));
                wrongAnswer3 = (int) (((double) num1 * rand.nextDouble()) + ((double) num1 * rand.nextDouble()));
                Log.i("ca, wa1, wa2, wa3: ", correctAnswer + " " + wrongAnswer1 + " " + wrongAnswer2 + " " + wrongAnswer3);
                identicalNum = identicalNum(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
            } while(identicalNum);

        } else if(op == 2) {
            correctAnswer = num1 - num2;

            do {
                wrongAnswer1 = (int) (((double) num1 * rand.nextDouble()) - ((double) num1 * rand.nextDouble()));
                wrongAnswer2 = (int) (((double) num1 * rand.nextDouble()) - ((double) num1 * rand.nextDouble()));
                wrongAnswer3 = (int) (((double) num1 * rand.nextDouble()) - ((double) num1 * rand.nextDouble()));
                Log.i("ca, wa1, wa2, wa3: ", correctAnswer + " " + wrongAnswer1 + " " + wrongAnswer2 + " " + wrongAnswer3);
                identicalNum = identicalNum(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
            } while(identicalNum);

        } else if(op == 3) {
            correctAnswer = num1 * num2;

            do {
                wrongAnswer1 = (int) (((double) num1 * rand.nextDouble()) * ((double) num1 * rand.nextDouble()));
                wrongAnswer2 = (int) (((double) num1 * rand.nextDouble()) * ((double) num1 * rand.nextDouble()));
                wrongAnswer3 = (int) (((double) num1 * rand.nextDouble()) * ((double) num1 * rand.nextDouble()));
                Log.i("ca, wa1, wa2, wa3: ", correctAnswer + " " + wrongAnswer1 + " " + wrongAnswer2 + " " + wrongAnswer3);
                identicalNum = identicalNum(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
            } while(identicalNum);

        } else /*op == 4*/{
            correctAnswer = num1 / num2;

            do {
                wrongAnswer1 = (int) (((double) num1 * rand.nextDouble()) / ((double) num1 * rand.nextDouble()));
                wrongAnswer2 = (int) (((double) num1 * rand.nextDouble()) / ((double) num1 * rand.nextDouble()));
                wrongAnswer3 = (int) (((double) num1 * rand.nextDouble()) / ((double) num1 * rand.nextDouble()));
                Log.i("ca, wa1, wa2, wa3: ", correctAnswer + " " + wrongAnswer1 + " " + wrongAnswer2 + " " + wrongAnswer3);
                identicalNum = identicalNum(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
            } while(identicalNum);

        }
        identicalNum = true;

        Log.i("generate correct and wrong answers ", "PASS");

        do {
            answerTag1 = rand.nextInt(4) + 1;
            answerTag2 = rand.nextInt(4) + 1;
            answerTag3 = rand.nextInt(4) + 1;
            answerTag4 = rand.nextInt(4) + 1;
            Log.i("a1, a3, a3, a4: ", answerTag1 + " " + answerTag2 + " " + answerTag3 + " " + answerTag4);

            identicalNum = identicalNum(answerTag1, answerTag2, answerTag3, answerTag4);
        } while(identicalNum);

        Log.i("ensure answers are placed in different tags: ", "PASS");

        correctTag = rand.nextInt(4) + 1;

        if(correctTag == answerTag1) {
            option1.setText(String.valueOf(correctAnswer));
            option2.setText(String.valueOf(wrongAnswer1));
            option3.setText(String.valueOf(wrongAnswer2));
            option4.setText(String.valueOf(wrongAnswer3));
        } else if(correctTag == answerTag2) {
            option1.setText(String.valueOf(wrongAnswer1));
            option2.setText(String.valueOf(correctAnswer));
            option3.setText(String.valueOf(wrongAnswer2));
            option4.setText(String.valueOf(wrongAnswer3));
        } else if(correctTag == answerTag3) {
            option1.setText(String.valueOf(wrongAnswer1));
            option2.setText(String.valueOf(wrongAnswer2));
            option3.setText(String.valueOf(correctAnswer));
            option4.setText(String.valueOf(wrongAnswer3));
        } else {
            option1.setText(String.valueOf(wrongAnswer1));
            option2.setText(String.valueOf(wrongAnswer2));
            option3.setText(String.valueOf(wrongAnswer3));
            option4.setText(String.valueOf(correctAnswer));
        }
        Log.i("set text for answer grid: ", "PASS");
    }


    public boolean identicalNum(int num1, int num2, int num3, int num4) {
        Log.i("start identicalNum(): ", "PASS");

        if(num1 == num2 || num1 == num3 || num1 == num4) {
            return true;
        }
        if(num2 == num3 || num2 == num4) {
            return true;
        }
        if(num3 == num4) {
            return true;
        }

        return false;
    }


    public void chooseAnswer(View view) {


        generateQuestion();
    }


    public void go(View view) {

        goButton.setVisibility(View.INVISIBLE);
        gameSetVisibility(true);

        gameInProgress = true;

        generateQuestion();
        generateAnswers(firstNum, secondNum, opNum);

        countDownTimer = new CountDownTimer(30000 + 100, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("Seconds Left!", String.valueOf(l / 1000));
                timerTextView.setText(String.valueOf((int) l / 1000));
            }

            @Override
            public void onFinish() {
                gameInProgress = false;
            }
        }.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        goButton = (Button) findViewById(R.id.goButton);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        option4 = (Button) findViewById(R.id.option4);

        gameOverTextView = (TextView) findViewById(R.id.gameOverTextView);
        playAgain = (Button) findViewById(R.id.playAgain);

        gameSetVisibility(false);

        //"GO!" will only appear once
        goButton = (Button) findViewById(R.id.goButton);
        goButton.setVisibility(View.VISIBLE);

    }
}