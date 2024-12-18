package com.example.mathstutor;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // define the UI object
    Button startButton;
    TextView resultTextView;
    TextView pointsTextView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;

    ArrayList<Integer> answers = new ArrayList<Integer>();
    // stored the answers , why ? unlimited questions

    int locationOfCorrectAnswer;
    // 4 options, one is the correct answers

    int score = 0;
    // keep track the scored, if the user answer correctly

    int numberOfQuestions = 0;
    // keep track the number of questions answered by the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // when the user launch the app , go to res folder
        // go to layout folder, get activity main xml file
        // get all these UI components and setup on the screen
        startButton = (Button)findViewById(R.id.startButton);
        sumTextView = (TextView)findViewById(R.id.sumTextView);

        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);

        timerTextView = (TextView)findViewById(R.id.timerTextView);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        gameRelativeLayout = (RelativeLayout)findViewById
                (R.id.gameRelativeLayout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // when the user click go button
    public void start(View view) {

        startButton.setVisibility(View.INVISIBLE);  // hide this button
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);  // view these layout and its components
        // display everything in the game relative layout, all textviews and buttons

        playAgain(findViewById(R.id.playAgainButton));  // call this method

    }  // end of start method

    // when the user start the new game or when the user click play again button
    public void playAgain(View view) {


        score = 0;
        // set to 0, because new game no score yet, you have no attempt on any questions yet
        numberOfQuestions = 0;

        timerTextView.setText("30s");  // display 30 sec, each questions only 30 sec
        pointsTextView.setText("0/0");  // display 0 for score
        resultTextView.setText("");   // nothing to display for result
        playAgainButton.setVisibility(View.INVISIBLE);  // hide this button

        generateQuestion();  // call this method to generate questions

        // here
        // we need to add a timer here, the user only has 30 seconds to
        // attempt the quiz
        // within 30 seconds the user must attempt as much as questions
        // correctly to earn the score
        // create a new timer objects here ,act as a stopwatch, runs for 30 sec and tick every 1 sec
        // 30000 ms, 1000ms
        new CountDownTimer(30100, 1000) {

            // override the abstract method, what happen after each tick, each sec
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished / 1000)
                        + "s");  // display the remaining time left starting from 30 sec, 29 sec,...

            }

            // override abstract method, what happen after 30 sec finish
            public void onFinish() {

                // display play again button , display 0 sec, display your score
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your score: " + Integer.toString(score) +
                        "/" + Integer.toString(numberOfQuestions));

                // DIY, hide the buttons, hide the questions, the user cannot play anymore
            }



        }.start();  // start the timer



    }


    // generate question method
    public void generateQuestion() {

        Random rand = new Random();  // create objects to generate random number

        int a = rand.nextInt(21);
        // generate 2 random numbers, int a and b from 1 to 20
        int b = rand.nextInt(21);

        // convert int a and b to string first,
        // display them at the label or textview as a questions a+b
        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrectAnswer = rand.nextInt(4);
        // we have 4 button at here, generate the correct answers
        // the correct answer can be 0,1,2,3 only

        answers.clear();
        // clear the arraylist,
        // why, the previous game questions and answers details

        int incorrectAnswer;
        // keep track which one is wrong answers done by the user

        // here
        // for loop
        // uses for loops here to determine the correct answers,
        // loop 4 times, because 4 buttons
        for (int i=0; i<4; i++) {

            if (i == locationOfCorrectAnswer) {  // if the value match
                answers.add(a + b);
                // add the correct answers to the array list
            }
            else  // the remaining 3 wrong answers
            {
                incorrectAnswer = rand.nextInt(41);  // range 1 to 40
                while (incorrectAnswer == a + b) {
                    incorrectAnswer = rand.nextInt(41);
                }  // end of while loop
                answers.add(incorrectAnswer);  // add to array list also
            }

        }  // end of for loop

        // here
        // code for button , display the answers at the button
        // based on the generated question and answers from above code
        // go to the array list, get all the 4 answers generated and display in the button as text
        // 1 button is correct answers, 3 more buttons is wrong answers

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

        // the above for loop will calculate 4 different answers
        // stored in the answers array list as item 0,1,2,3
        // and then assign these value to all the four buttons
        // array list first item is at index 0 and fourth item is at index 3

    }  // end of generate  question method


    // here
    // when the user click the button to answer the questions
    public void chooseAnswer(View view) {

        // if the user click the correct answers,
        // increase the score by 1, display correct
        // for example button 0 is the correct answer ==
        // location 0 in the answer array list
        if (view.getTag().toString().equals(Integer.toString
                (locationOfCorrectAnswer))) {
            score++;
            resultTextView.setText("Correct!");
        }
        else {
            resultTextView.setText("Wrong!");
            // wrong answers, display wrong
        }

        numberOfQuestions++;
        // increase the number of questions done by the user

        pointsTextView.setText(Integer.toString(score) +
                "/" + Integer.toString(numberOfQuestions));
        // display the scores earned by the user

        generateQuestion();  // generate another questions

    }

}