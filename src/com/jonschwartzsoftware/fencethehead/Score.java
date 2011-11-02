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
	int finalScore;
	Bundle gotScore;
	AdView adview;
	LinearLayout llAd;
	SharedPreferences settings;

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
		playAgain = (Button) findViewById(R.id.bPlayAgain);
		tvscore = (TextView) findViewById(R.id.tvScore);
		scorelabel = (TextView) findViewById(R.id.tvScoreLabel);
		tvhighscore = (TextView) findViewById(R.id.tvHighScore);
		adview = (AdView) findViewById(R.id.adView);
		prize = (ImageView) findViewById(R.id.ivPrize);
		youWin = (TextView) findViewById(R.id.tvYouWon);

		settings = getSharedPreferences("highscore", 0);
		int highscore = settings.getInt("highscore", 0);

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

		switch (finalScore) {
		case 43:
			youWin.setText("You won a Golden Poo!");
			Drawable drawablePoo = res.getDrawable(R.drawable.poo);
			prize.setImageDrawable(drawablePoo);
			break;
		case 81:
			youWin.setText("You won a Bejemmed Turtle!");
			Drawable drawableTurtle = res.getDrawable(R.drawable.turtle);
			prize.setImageDrawable(drawableTurtle);
			break;
		}

		tvhighscore.setText(Integer.toString(highscore));
		adview.loadAd(new AdRequest());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.scoremenu, menu);
		return true;
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Score.this, Play.class);
		startActivity(i);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		adview.destroy();
		super.onDestroy();
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
		}
		return false;
	}

}
