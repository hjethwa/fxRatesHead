package com.example.ratesheads;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
	
    private GestureDetectorCompat mDetector; 
    private WindowManager mWindowManager;
    private View mDeleteView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int screenHeight = displaymetrics.heightPixels;
		int screenWidth = displaymetrics.widthPixels;
		
		final WindowManager.LayoutParams headParam = new WindowManager.LayoutParams();
		headParam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		final ImageView headView = new ImageView(this);
		headView .setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		headView .setImageResource(R.drawable.ic_launcher);
		final ViewGroup parent = (ViewGroup)headView .getParent();
		if (parent != null)
		  parent.removeView(headView );
		headParam.format = PixelFormat.RGBA_8888;
		headParam.gravity = Gravity.TOP;
		headParam.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		headParam.width = (parent != null) ? LayoutParams.WRAP_CONTENT : headView .getLayoutParams().width;
		headParam.height = (parent!=null) ? LayoutParams.WRAP_CONTENT : headView .getLayoutParams().height;
//		headParam.x = (screenWidth - headView.getLayoutParams().width) / 2;
//		headParam.y = (screenHeight - headView.getLayoutParams().height) / 2;

		mWindowManager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		mWindowManager.addView(headView ,headParam);

		mDetector = new GestureDetectorCompat(this, this);
		mDetector.setOnDoubleTapListener(this);
		
		mDeleteView = new View(this);
		mDeleteView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 100));
		mDeleteView.setBackgroundColor(Color.RED);
		
		final WindowManager.LayoutParams deleteViewParam = new WindowManager.LayoutParams();
		deleteViewParam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		deleteViewParam.format = PixelFormat.RGBA_8888;
		deleteViewParam.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		deleteViewParam.width = mDeleteView.getLayoutParams().width;
		deleteViewParam.height = mDeleteView.getLayoutParams().height;
		deleteViewParam.y = screenHeight - mDeleteView.getLayoutParams().height;
		mWindowManager.addView(mDeleteView, deleteViewParam);
		mDeleteView.setVisibility(View.GONE);


		headView .setOnTouchListener(new View.OnTouchListener() {
			  private int initialX;
			  private int initialY;
			  private float initialTouchX;
			  private float initialTouchY;

			  @Override 
			  public boolean onTouch(View v, MotionEvent event) {
			    switch (event.getAction()) {
			      case MotionEvent.ACTION_DOWN:
			        initialX = headParam.x;
			        initialY = headParam.y;
			        initialTouchX = event.getRawX();
			        initialTouchY = event.getRawY();
			        return true;
			      case MotionEvent.ACTION_UP:
			    	  hideDeleteButton();
			        return true;
			      case MotionEvent.ACTION_MOVE:
			        headParam.x = initialX + (int) (event.getRawX() - initialTouchX);
			        headParam.y = initialY + (int) (event.getRawY() - initialTouchY);
			        mWindowManager.updateViewLayout(headView , headParam);
			        showDeleteButton();
			        
		        	Log.d("View", headParam.y + " " + deleteViewParam.y);
		        	
		        	if (headParam.y + headParam.height > deleteViewParam.y) {
		        		mWindowManager.removeView(headView);
		        		hideDeleteButton();
		        	}

			        return true;
			    }
			    return false;
			  }
		});
	}
	
	public void showDeleteButton() {
		mDeleteView.setVisibility(View.VISIBLE);
	}
	
	public void hideDeleteButton() {
		mDeleteView.setVisibility(View.GONE);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mDetector.onTouchEvent(event);
		
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
        Log.d("Gestures","onFling: " + e1.toString()+ e2.toString());

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

}
