package com.mhframework.platform.android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.mhframework.MHGame;

/********************************************************************
 * Specialization of <code>android.app.Activity</code> that supports
 * the engine's multithreaded game loop.
 */
public class MHAndroidActivity extends Activity
{
	
	
    @Override
	protected void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}


	//handle resume/focus events
    @Override 
    public void onResume() 
    {
        super.onResume();
        MHGame.resume();
    }


    //handle pause/minimize events
    @Override 
    public void onPause() 
    {
        super.onPause();
        MHGame.pause();
    }
}
