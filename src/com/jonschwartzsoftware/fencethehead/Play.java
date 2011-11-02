package com.jonschwartzsoftware.fencethehead;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

public class Play extends Activity implements OnTouchListener {

	float pTRX, pTLX, pBRX, pBLX, pLOX, pLTX;
	float pTRY, pTLY, pBRY, pBLY, pLOY, pLTY;
	int score;
	int time;
	CountDownTimer theTime;
	Bitmap headL, headR;
	PlayArea ourPlayArea;
	Paint line;
	SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		ourPlayArea = new PlayArea(this);
		ourPlayArea.setOnTouchListener(this);

		settings = getSharedPreferences("highscore", 0);
		int whichHead = settings.getInt("usehead", 0);
		score = 0;
		// Play field coordinates

		pTLX = 0;
		pTLY = 0;
		pTRX = 0;
		pTRY = 0;
		pBLX = 0;
		pBLY = 0;
		pBRX = 0;
		pBRY = 0;

		// Line Coordinates

		pLOX = 0;
		pLOY = 0;
		pLTX = 0;
		pLTY = 0;

		// Drawing Resources

		if (whichHead == 1){
		headL = BitmapFactory.decodeResource(getResources(),
				R.drawable.paul_head_facing_left);
		headR = BitmapFactory.decodeResource(getResources(),
				R.drawable.paul_head_facing_right);
		} else {
			headL = BitmapFactory.decodeResource(getResources(),
					R.drawable.toby_head_facing_left);
			headR = BitmapFactory.decodeResource(getResources(),
					R.drawable.toby_head_facing_right);
		}
		line = new Paint();
		line.setColor(Color.rgb(149, 90, 0));
		line.setTextSize(30);
		line.setStrokeWidth(4);
		
		// score
		
		score = 0;		
		time = 12;
		
		theTime = new CountDownTimer(12000,1000){

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				Bundle scorebundle = new Bundle();
				Intent showScore = new Intent(Play.this, Score.class);
				scorebundle.putInt("score", score);
				showScore.putExtras(scorebundle);
				startActivity(showScore);
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				time -= 1;
			}}.start();

		setContentView(ourPlayArea);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourPlayArea.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ourPlayArea.resume();
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			pLOX = event.getX();
			pLOY = event.getY();
			score++;
			break;
		case MotionEvent.ACTION_UP:
			pLTX = event.getX();
			pLTY = event.getY();
			
			//

			break;
		// case MotionEvent.ACTION_MOVE:
		// canvas.drawLine(pL1X, pL1Y, event.getX(),event.getY(), line);
		// break;
		}

		return true;
	}

	public class PlayArea extends SurfaceView implements Runnable {

		SurfaceHolder ourHolder;
		Thread ourThread = null;
		Boolean isRunning = false;
		Canvas canvas;

		public PlayArea(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			ourHolder = getHolder();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);

		}

		public void resume() {
			// TODO Auto-generated method stub
			isRunning = true;
			ourThread = new Thread(this);
			ourThread.start();
		}

		public void pause() {
			// TODO Auto-generated method stub
			isRunning = false;
			while (true) {
				try {
					ourThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			ourThread = null;
		}

		public void run() {
			// TODO Auto-generated method stub
			while (isRunning) {
				if (!ourHolder.getSurface().isValid())
					continue;

				canvas = ourHolder.lockCanvas();
				canvas.drawRGB(0, 0, 0);
				if (pLOX != 0 && pLOY != 0) {
					if (pLOX > canvas.getWidth() / 2) {
						canvas.drawBitmap(headL, pLOX - headL.getWidth() / 2,
								pLOY - headL.getHeight() / 2, null);
					} else {
						canvas.drawBitmap(headR, pLOX - headL.getWidth() / 2,
								pLOY - headL.getHeight() / 2, null);
					}
				}
				canvas.drawText("Score: "+score+" Time: "+time, canvas.getWidth()/2-40, canvas.getHeight()-40, line);
				//canvas.drawLine(pLOX, pLOY, pLTX, pLTY, line);
				ourHolder.unlockCanvasAndPost(canvas);

			}
		}

	}

}
