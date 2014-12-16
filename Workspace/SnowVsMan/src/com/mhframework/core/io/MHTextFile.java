package com.mhframework.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/********************************************************************
 * 
 * @author Michael Henson
 *
 */
public abstract class MHTextFile
{
	public enum Mode
	{
		READ,
		REWRITE,
		APPEND
	}
    private final File file;

    public MHTextFile (final String filename)
    {
        file = new File(filename);
    }

    
    public File getFile()
    {
    	return file;
    }
    

    public String getName()
    {
        return file.getName();
    }


    public String getAbsolutePath()
    {
        return file.getAbsolutePath();
    }


    public abstract void close();

    public abstract void write(String data);

    public abstract void append(final String data);

    public abstract String readLine();
    

    @Override
    protected void finalize() throws Throwable
    {
        close();
    }
}
