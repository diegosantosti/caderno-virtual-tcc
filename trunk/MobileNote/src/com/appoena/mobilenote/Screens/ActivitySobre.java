package com.appoena.mobilenote.Screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.appoena.mobilenote.R;

public class ActivitySobre extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Toast.makeText(getApplicationContext(), "Dedo tocou a tela",  
                Toast.LENGTH_LONG).show();
		if (event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null) {
			View view = new View(this);
	        view.getParent().requestDisallowInterceptTouchEvent(true);
	    }
	    return super.onTouchEvent(event);
	}

}
