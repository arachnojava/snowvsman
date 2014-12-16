package com.mhframework.platform.pc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.mhframework.core.io.MHTextFile;
import com.mhframework.core.io.MHTextFile.Mode;

public class MHPCTextFile extends MHTextFile 
{
    private RandomAccessFile randomAccessFile;


    public MHPCTextFile (final String filename, Mode mode)
    {
    	super(filename);
    	
        try
        {
            randomAccessFile = new RandomAccessFile(getFile(), "rw");

            if (mode == Mode.REWRITE)
                randomAccessFile.setLength(0);
        }
        catch (final FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        } 
        catch (final IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    
    public void close()
    {
        try
        {
            randomAccessFile.close();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }


    public void write(String data)
    {
        data = data.replaceAll("\n", System.getProperty("line.separator"));

        try
        {
            randomAccessFile.writeBytes(data + System.getProperty("line.separator"));
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }


    public void append(final String data)
    {
        try
        {
            randomAccessFile.seek(randomAccessFile.length());
            write(data);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }


    public String readLine()
    {
        String line;
        try
        {
            line = randomAccessFile.readLine();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
            line = e.getMessage();
        }

        return line;
    }


    @Override
    protected void finalize() throws Throwable
    {
        close();
    }
}
