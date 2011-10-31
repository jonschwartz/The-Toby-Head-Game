package com.jonschwartzsoftware.fencethehead;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Score extends Activity implements OnClickListener{

	Button playAgain;
	TextView tvscore, scorelabel;
	int finalScore;
	Bundle gotScore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		setup();
	}

	private void setup() {
		// TODO Auto-generated method stub
		playAgain = (Button) findViewById(R.id.bPlayAgain);
		tvscore = (TextView) findViewById(R.id.tvScore);
		scorelabel = (TextView) findViewById(R.id.tvScoreLabel);
		
		playAgain.setOnClickListener(this);
		finalScore = 0;
		gotScore = getIntent().getExtras();
		finalScore = gotScore.getInt("score");
		tvscore.setText(Integer.toString(finalScore));
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Score.this, Play.class);
		startActivity(i);
	}

}
