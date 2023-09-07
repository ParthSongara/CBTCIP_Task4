package com.example.cbtcip_task4;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup choicesRadioGroup;
    private Button submitButton;
    private Button replayButton;

    private final String[] questions = {
            "Which gas is most abundant in the Earth's atmosphere?",
            "Who is the author of 'To Kill a Mockingbird'?",
            "What is the largest planet in our solar system?",
            "Which country is known as the 'Land of the Rising Sun'?",
            "What is the capital of Brazil?",
            "Who painted the Mona Lisa?",
            "What is the chemical symbol for gold?",
            "What is the largest ocean on Earth?",
            "Which gas do plants absorb from the atmosphere?",
            "What is the largest desert in the world?"
    };

    private final String[][] answerChoices = {
            {"Oxygen", "Nitrogen", "Carbon dioxide", "Argon"},
            {"Harper Lee", "F. Scott Fitzgerald", "J.K. Rowling", "George Orwell"},
            {"Jupiter", "Saturn", "Neptune", "Earth"},
            {"Japan", "China", "India", "South Korea"},
            {"Brasília", "Rio de Janeiro", "São Paulo", "Salvador"},
            {"Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso", "Michelangelo"},
            {"Au", "Ag", "Fe", "Hg"},
            {"Pacific Ocean", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean"},
            {"Carbon dioxide", "Oxygen", "Nitrogen", "Hydrogen"},
            {"Sahara Desert", "Arctic Desert", "Gobi Desert", "Antarctic Desert"}
    };

    private final int[] correctAnswerIndexes = {1, 0, 0, 0, 0, 0, 0, 0, 2, 0};

    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        choicesRadioGroup = findViewById(R.id.choicesRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        replayButton = findViewById(R.id.replayButton);

        displayQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionTextView.setText(questions[currentQuestionIndex]);

            choicesRadioGroup.removeAllViews();

            for (int i = 0; i < answerChoices[currentQuestionIndex].length; i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(answerChoices[currentQuestionIndex][i]);
                radioButton.setTextSize(16);
                radioButton.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                radioButton.setId(i);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resetRadioButtonBackgrounds();
                    }
                });
                choicesRadioGroup.addView(radioButton);
            }
        } else {
            endQuiz();
            submitButton.setEnabled(false);
            replayButton.setVisibility(View.VISIBLE);
        }
    }

    private void checkAnswer() {
        int selectedAnswerIndex = choicesRadioGroup.indexOfChild(findViewById(choicesRadioGroup.getCheckedRadioButtonId()));
        int correctAnswerIndex = correctAnswerIndexes[currentQuestionIndex];

        RadioButton selectedRadioButton = (RadioButton) findViewById(choicesRadioGroup.getCheckedRadioButtonId());

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            setRadioButtonBackground(selectedRadioButton, R.drawable.correct_radio_button);
        } else {
            Toast.makeText(this, "Incorrect. The correct answer is " + answerChoices[currentQuestionIndex][correctAnswerIndex], Toast.LENGTH_SHORT).show();
            setRadioButtonBackground(selectedRadioButton, R.drawable.incorrect_radio_button);
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < questions.length) {
            displayQuestion();
        } else {
            endQuiz();
            submitButton.setEnabled(false);
            replayButton.setVisibility(View.VISIBLE);
        }
    }

    private void resetRadioButtonBackgrounds() {
        for (int i = 0; i < choicesRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) choicesRadioGroup.getChildAt(i);
            setRadioButtonBackground(radioButton, android.R.color.transparent);
        }
    }

    private void setRadioButtonBackground(RadioButton radioButton, int drawableResource) {
        radioButton.setBackgroundResource(drawableResource);
    }

    private void endQuiz() {
        Toast.makeText(this, "Quiz complete. Score: " + score + " out of " + questions.length, Toast.LENGTH_SHORT).show();
    }

    private void restartGame() {
        currentQuestionIndex = 0;
        score = 0;
        choicesRadioGroup.clearCheck();
        displayQuestion();
        submitButton.setEnabled(true);
        replayButton.setVisibility(View.GONE);
    }
}
