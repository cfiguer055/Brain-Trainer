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
    TextView gameStatus; //default: visible
    Button playAgain; //default: visible

    CountDownTimer countDownTimer;


    Random rand = new Random();


    boolean gameInProgress = false;

    //generateQuestion()
    int firstNum, secondNum;
    int opNum;
    String operator = "";

    //generateAnswer()
    int correctAnswer;
    int wrongAnswer1, wrongAnswer2, wrongAnswer3;
    int answerTag1, answerTag2, answerTag3, answerTag4;
    int correctTag;

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

            gameStatus.setVisibility(View.VISIBLE);
            playAgain.setVisibility(View.VISIBLE);

        } else {
            timerTextView.setVisibility(View.INVISIBLE);
            questionTextView.setVisibility(View.INVISIBLE);
            scoreTextView.setVisibility(View.INVISIBLE);

            option1.setVisibility(View.INVISIBLE);
            option2.setVisibility(View.INVISIBLE);
            option3.setVisibility(View.INVISIBLE);
            option4.setVisibility(View.INVISIBLE);

            gameStatus.setVisibility(View.INVISIBLE);
            playAgain.setVisibility(View.INVISIBLE);

        }
    }


    private void updateScore() {

        scoreTextView.setText(String.valueOf(numCorrect) + "/" + String.valueOf(totalQuestions));
    }


    public void generateQuestion() {

        Log.i("start generateQuestion(): ", "PASS");

        //1=+, 2=-, 3=*, 4=%
        opNum = rand.nextInt(4) + 1;
        Log.i("operator: ", String.valueOf(opNum));

        if(opNum == 1) {
            firstNum = rand.nextInt(500);
            secondNum = rand.nextInt(500);
            operator = "+";
            Log.i("first, second, op: ", String.valueOf(firstNum) + ", " +  String.valueOf(secondNum) + ", " + String.valueOf(operator));

        } else if(opNum == 2) {
            secondNum = rand.nextInt(1000);
            firstNum = rand.nextInt(1000 - secondNum) + secondNum;
            operator = "-";
            Log.i("first, second, op: ", String.valueOf(firstNum) + ", " + String.valueOf(secondNum) + ", " + String.valueOf(operator));

        } else if(opNum == 3) {
            firstNum = rand.nextInt(31);
            secondNum = rand.nextInt(32);
            operator = "*";
            Log.i("first, second, op: ", String.valueOf(firstNum) + ", " + String.valueOf(secondNum) + ", " + String.valueOf(operator));

        } else if(opNum == 4) {

            do {
                firstNum = rand.nextInt(900) + 101;
                Log.i("first num: ", String.valueOf(firstNum));
            } while(firstNum % 2 != 0 && firstNum % 5 != 0);

            if(firstNum % 2 == 0 && firstNum % 5 != 0) {
                do {
                    secondNum = rand.nextInt(100) + 1;
                    Log.i("second num: ", String.valueOf(secondNum));
                } while (secondNum % 2 != 0 || firstNum % secondNum != 0);
            } else {
                do {
                    secondNum = rand.nextInt(100) + 1;
                    Log.i("second num: ", String.valueOf(secondNum));
                } while (secondNum % 5 != 0 || firstNum % secondNum != 0);
            }

            operator = "%";
            Log.i("first, second, op: ", String.valueOf(firstNum) + ", " + String.valueOf(secondNum) + ", " + String.valueOf(operator));

        }

        Log.i("generate numbers to be calculated: ", "PASS");

        questionTextView.setText(firstNum + " " + operator + " " + secondNum);

        generateAnswers(firstNum, secondNum, opNum);
    }


    public void generateAnswers(int num1, int num2, int op) {

        boolean identicalNum = true;

        Log.i("start generateAnswers() ", "PASS");
        //MAYBE DO BOOLEAN FOR CONDITIONS
        if(op == 1) {
            correctAnswer = num1 + num2;

        } else if(op == 2) {
            correctAnswer = num1 - num2;

        } else if(op == 3) {
            correctAnswer = num1 * num2;

        } else /*op == 4*/{
            correctAnswer = num1 / num2;

        }

        do {
            wrongAnswer1 = rand.nextInt(1000);
            wrongAnswer2 = rand.nextInt(1000);
            wrongAnswer3 = rand.nextInt(1000);
            Log.i("ca, wa1, wa2, wa3: ", correctAnswer + " " + wrongAnswer1 + " " + wrongAnswer2 + " " + wrongAnswer3);
            identicalNum = identicalNum(correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
        } while(identicalNum);

        Log.i("generate correct and wrong answers ", "PASS");

        correctTag = rand.nextInt(4) + 1;

        if(correctTag == 1) {
            option1.setText(String.valueOf(correctAnswer));
            option2.setText(String.valueOf(wrongAnswer1));
            option3.setText(String.valueOf(wrongAnswer2));
            option4.setText(String.valueOf(wrongAnswer3));
        } else if(correctTag == 2) {
            option1.setText(String.valueOf(wrongAnswer1));
            option2.setText(String.valueOf(correctAnswer));
            option3.setText(String.valueOf(wrongAnswer2));
            option4.setText(String.valueOf(wrongAnswer3));
        } else if(correctTag == 3) {
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

    //its a button...no need to call method
    public void chooseAnswer(View view) {
        int selectedAnswer;

        Button selectedButton = (Button) view;

        selectedAnswer = Integer.parseInt(String.valueOf(selectedButton.getTag()));

        if(selectedAnswer == correctTag) {
            numCorrect++;
            gameStatus.setText("Correct!");
        } else {
            gameStatus.setText("Wrong :(");
        }

        totalQuestions++;
        updateScore();
        //resetNums();
        generateQuestion();
        //generateAnswers(firstNum, secondNum, opNum);
    }


    public void resetNums() {
        //generateQuestion()
        firstNum = 0;
        secondNum = 0;
        opNum = 0;
        operator = "";

        //generateAnswer()
        correctAnswer = 0;
        wrongAnswer1 = 0;
        wrongAnswer2 = 0;
        wrongAnswer3 = 0;
        answerTag1 = 0;
        answerTag2 = 0;
        answerTag3 = 0;
        answerTag4 = 0;
        correctTag = 0;

        numCorrect = 0;
        totalQuestions = 0;
    }


    public void answerClickability() {
        if(gameInProgress) {
            option1.setEnabled(true);
            option2.setEnabled(true);
            option3.setEnabled(true);
            option4.setEnabled(true);
        } else {
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            option4.setEnabled(false);
        }
    }


    public void resetTimer() {

        resetNums();
        updateScore();
        timerTextView.setText("30s");
        gameStatus.setText("Done!");
        countDownTimer.cancel();
        gameInProgress = false;
        playAgain.setText("PLAY AGAIN");
        answerClickability();
    }


    public void go(View view) {

        if(gameInProgress) {
            gameInProgress = false;
            answerClickability();
            resetTimer();
        } else {

            gameInProgress = true;
            gameStatus.setText("");
            answerClickability();
            goButton.setVisibility(View.INVISIBLE);
            gameSetVisibility(true);
            playAgain.setText("RESET");
            generateQuestion();

            countDownTimer = new CountDownTimer(30000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    Log.i("Seconds Left!", String.valueOf(l / 1000));
                    timerTextView.setText(String.valueOf((int) l / 1000) + "s");
                }

                @Override
                public void onFinish() {
                    gameInProgress = false;
                    gameStatus.setText("Done!");
                    answerClickability();
                    //resetTimer();
                    gameInProgress = true;
                }
            }.start();
        }
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

        gameStatus = (TextView) findViewById(R.id.gameStatusTextView);
        playAgain = (Button) findViewById(R.id.resetTimer);

        gameSetVisibility(false);

        //"GO!" will only appear once
        goButton = (Button) findViewById(R.id.goButton);
        goButton.setVisibility(View.VISIBLE);

    }
}