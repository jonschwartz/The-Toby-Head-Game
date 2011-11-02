package com.jonschwartzsoftware.fencethehead;

import com.google.ads.*;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Score extends Activity implements OnClickListener {

	Button playAgain;
	TextView tvscore, scorelabel, tvhighscore, youWin;
	ImageView prize;
	int finalScore, whichHead, paulFlag;
	Bundle gotScore;
	AdView adview;
	LinearLayout llAd;
	SharedPreferences settings;

	// MenuItem paulVisible;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.score);
		setup();

	}

	private void setup() {
		// TODO Auto-generated method stub

		adview = (AdView) findViewById(R.id.adView);
		playAgain = (Button) findViewById(R.id.bPlayAgain);
		tvscore = (TextView) findViewById(R.id.tvScore);
		scorelabel = (TextView) findViewById(R.id.tvScoreLabel);
		tvhighscore = (TextView) findViewById(R.id.tvHighScore);
		prize = (ImageView) findViewById(R.id.ivPrize);
		youWin = (TextView) findViewById(R.id.tvYouWon);
		// paulVisible = (MenuItem) findViewById(R.id.miUsePaul);

		adview.loadAd(new AdRequest());

		settings = getSharedPreferences("highscore", 0);
		int highscore = settings.getInt("highscore", 0);
		paulFlag = settings.getInt("paul", 0);
		int poos = settings.getInt("poo", 0);
		int turtles = settings.getInt("turtle", 0);
		whichHead = settings.getInt("usehead", 0);

		playAgain.setOnClickListener(this);
		finalScore = 0;
		gotScore = getIntent().getExtras();
		finalScore = gotScore.getInt("score");
		tvscore.setText(Integer.toString(finalScore));
		if (finalScore > highscore) {
			highscore = finalScore;
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("highscore", highscore);
			editor.commit();
		}

		Resources res = getResources();

		if (finalScore == 43) {
			poos++;
			youWin.setText("You won a Golden Poo!  You have " + poos + " poos!");
			Drawable drawablePoo = res.getDrawable(R.drawable.poo);
			prize.setImageDrawable(drawablePoo);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("poo", poos);
			editor.commit();
		} else if (finalScore == 81) {
			turtles++;
			youWin.setText("You won a Bejemmed Turtle!  You have " + turtles
					+ " turtles!");
			Drawable drawableTurtle = res.getDrawable(R.drawable.turtle);
			prize.setImageDrawable(drawableTurtle);
			SharedPreferences.Editor editorTurtle = settings.edit();
			editorTurtle.putInt("turtle", turtles);
			editorTurtle.commit();
		} else if ((finalScore > 100) && (paulFlag == 0)) {
			youWin.setText("You unlocked the Paul Head!!");
			Drawable drawablePaul = res
					.getDrawable(R.drawable.paul_head_facing_left);
			prize.setImageDrawable(drawablePaul);
			SharedPreferences.Editor editorPaul = settings.edit();
			paulFlag = 1;
			editorPaul.putInt("paul", paulFlag);
			editorPaul.commit();
			// paulVisible.setVisible(true);
		}

		tvhighscore.setText(Integer.toString(highscore));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.scoremenu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if (paulFlag == 1) {
			menu.getItem(1).setVisible(true);
			if (whichHead == 0) {
				menu.getItem(1).setTitle("Use Paul Head");

			} else {
				menu.getItem(1).setTitle("Use Toby Head");

			}
		}

		return super.onPrepareOptionsMenu(menu);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Score.this, Play.class);
		startActivity(i);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		adview.destroy();
		super.onDestroy();
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.miResetScore:
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("highscore", 0);
			editor.commit();
			tvhighscore.setText("0");
			break;
		case R.id.miUsePaul:
			SharedPreferences.Editor editorhead = settings.edit();
			if (whichHead == 0){
			editorhead.putInt("usehead", 1);
			} else {
				editorhead.putInt("usehead", 0);	
			}
			editorhead.commit();
			break;
		}

		return false;
	}
}
