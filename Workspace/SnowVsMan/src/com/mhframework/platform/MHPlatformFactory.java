package com.mhframework.platform;

import com.mhframework.core.io.MHTextFile;
import com.mhframework.core.io.MHTextFile.Mode;
import com.mhframework.platform.event.MHKeyCodes;
import com.mhframework.platform.graphics.MHBitmapImage;
import com.mhframework.platform.graphics.MHColor;
import com.mhframework.platform.graphics.MHFont;

public interface MHPlatformFactory
{
    // MHBitmapImage
    public MHBitmapImage createImage(int width, int height);
    public MHBitmapImage createImage(String filename);
    
    // MHColor
    public MHColor createColor(int r, int g, int b, int a);
    
    // MHGraphicsCanvas
    //public MHGraphicsCanvas createGraphicsCanvas(int width, int height);
    
    // MHSoundManager
	public MHSoundManager getSoundManager();
	
	// MHFont
	public MHFont createFont(String fontName);
	
	// MHKeyCodes
    public MHKeyCodes getKeyCodes();
    
    // Assets directory
	public String getAssetsDirectory();
	
	// MHTextFile
	public MHTextFile openTextFile(String filename, Mode mode);
    
}
