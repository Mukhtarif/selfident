package a123.com.minatbakatidentificator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a123.com.minatbakatidentificator.DBHelper.dbhelper;
import a123.com.minatbakatidentificator.Model.segment1;

public class Playing extends AppCompatActivity implements View.OnClickListener {


    List<segment1> questionPlay = new ArrayList<>(); //total Question
    dbhelper db;
    int index=0,score=0,scorea=0,scoreb=0,scorec=0,thisQuestion=0,totalQuestion;
    /*String mode="";*/

    //Control
    Button btnA,btnB,btnC;
    TextView txtQuestion,question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        //Get Data from MainActivity
        /*Bundle extra = getIntent().getExtras();
        if(extra != null)
            mode=extra.getString("MODE");*/

        db = new dbhelper(this);

        txtQuestion = (TextView)findViewById(R.id.txtQuestion);
        question = (TextView) findViewById(R.id.question);
        btnA=(Button)findViewById(R.id.btnAnswerA);
        btnB=(Button)findViewById(R.id.btnAnswerB);
        btnC=(Button)findViewById(R.id.btnAnswerC);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        questionPlay = db.getAllQuestion();
        totalQuestion = questionPlay.size();
    }

    private void showQuestion(int index) {
        if(index < totalQuestion){
            thisQuestion++;
            txtQuestion.setText(String.format("%d/%d",thisQuestion,totalQuestion));

            question.setText(questionPlay.get(index).getQuestion());
            btnA.setText(questionPlay.get(index).getAnswerA());
            btnB.setText(questionPlay.get(index).getAnswerB());
            btnC.setText(questionPlay.get(index).getAnswerC());
        }
        else{
            Intent intent = new Intent(this,Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("A",scorea);
            dataSend.putInt("B",scoreb);
            dataSend.putInt("C",scorec);
            dataSend.putInt("TOTAL",totalQuestion);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {


        if(index < totalQuestion){
            Button clickedButton = (Button)v;
            if(clickedButton.getText().equals(questionPlay.get(index).getAnswerA()))
            {
                /*score+=10; // increase score
                correctAnswer++ ; //increase correct answer*/
                scorea++;
                showQuestion(++index);
            }
            else if(clickedButton.getText().equals(questionPlay.get(index).getAnswerB()))
            {
                scoreb++;
                showQuestion(++index);
            }
            else
            {
                scorec++;
                showQuestion(++index);
            }
           /*
            else
                showQuestion(++index); // If choose right , just go to next question*/

            /*txtScore.setText(String.format("%d",score));*/
        }

    }
}