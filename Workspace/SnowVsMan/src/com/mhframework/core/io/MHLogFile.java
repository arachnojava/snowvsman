package com.mhframework.core.io;

import java.util.Calendar;

import com.mhframework.platform.MHPlatform;

/**
 * 
 * 
 * 
 * @author Michael Henson
 *
 */
public class MHLogFile extends MHTextFile
{
	// TODO:  Does this class need to inherit from MHTextFile?
	private MHTextFile file;
	
    public MHLogFile(final String filename)
    {
    	super(filename);
        file = MHPlatform.openTextFile(filename, Mode.APPEND);
    }


    public void write(final String data)
    {
    	file.write(getTimestamp()+data);
    }


    private String getTimestamp()
    {
        final Calendar c    = Calendar.getInstance();
        final String year   = c.get(Calendar.YEAR)+"";
        final String month  = twoDigits(c.get(Calendar.MONTH) + 1);
        final String day    = twoDigits(c.get(Calendar.DAY_OF_MONTH));
        final String hour   = twoDigits(c.get(Calendar.HOUR));
        final String minute = twoDigits(c.get(Calendar.MINUTE));
        final String second = twoDigits(c.get(Calendar.SECOND));

        return "[" + year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second + "] ";
    }


    private String twoDigits(final int number)
    {
        if (number < 10)
            return "0"+number;

        return number+"";
    }


	@Override
	public void close() 
	{
		file.close();
	}


	@Override
	public void append(String data) 
	{
		file.append(getTimestamp()+data);
	}


	@Override
	public String readLine() 
	{
		return file.readLine();
	}


	@Override
	protected void finalize() throws Throwable 
	{
		close();
	}
	
	
	
}


