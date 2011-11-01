package com.jonschwartzsoftware.fencethehead;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Score extends Activity implements OnClickListener{

	Button playAgain;
	TextView tvscore, scorelabel, tvhighscore;
	int finalScore;
	Bundle gotScore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.score);
		setup();
	    
	}

	private void setup() {
		// TODO Auto-generated method stub
		playAgain = (Button) findViewById(R.id.bPlayAgain);
		tvscore = (TextView) findViewById(R.id.tvScore);
		scorelabel = (TextView) findViewById(R.id.tvScoreLabel);
		tvhighscore = (TextView) findViewById(R.id.tvHighScore);
		
		SharedPreferences settings = getSharedPreferences("highscore", 0);
	    int highscore = settings.getInt("highscore", 0);
		
		playAgain.setOnClickListener(this);
		finalScore = 0;
		gotScore = getIntent().getExtras();
		finalScore = gotScore.getInt("score");
		tvscore.setText(Integer.toString(finalScore));
		if (finalScore > highscore){
			highscore = finalScore;
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putInt("highscore", highscore);
		    editor.commit();
		}
		tvhighscore.setText(Integer.toString(highscore));
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Score.this, Play.class);
		startActivity(i);
	}

}
