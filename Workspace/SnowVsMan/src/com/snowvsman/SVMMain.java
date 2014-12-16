package com.snowvsman;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Bundle;

import com.mhframework.MHScreen;
import com.mhframework.MHVideoSettings;
import com.mhframework.platform.android.MHAndroidActivity;
import com.mhframework.platform.android.MHAndroidPlatform;


public class SVMMain extends MHAndroidActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Initialize the Android activity.
		super.onCreate(savedInstanceState);

		// Create the game's start screen.
		//SVMGameScreen.getInstance().loadMapFile("Level00.lime");
		MHScreen startingScreen = new SVMMainMenuScreen();
		SVMMainMenuScreen.IMAGES_DIR = "images/";
		
		// Configure video settings.
		MHVideoSettings displaySettings = new MHVideoSettings();
		displaySettings.displayWidth = 800;
		displaySettings.displayHeight = 480;

		// Launch the game on the Android platform.
		MHAndroidPlatform.run(this, startingScreen, displaySettings);
	}
}
