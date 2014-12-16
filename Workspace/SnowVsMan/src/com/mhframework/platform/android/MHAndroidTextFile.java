package com.mhframework.platform.android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.mhframework.core.io.MHTextFile;

@SuppressLint("WorldReadableFiles")
public class MHAndroidTextFile extends MHTextFile 
{
	// TODO: Make platform function to instantiate this class.
	
	private BufferedReader inputReader;
	private BufferedWriter outputWriter;

	public MHAndroidTextFile(String filename, Mode mode) 
	{
		super(filename);
		
		if (mode == Mode.READ)
		{
			
			try 
			{
				InputStream in = MHAndroidApplication.getContext().getAssets().open(filename);
				//FileInputStream in = MHAndroidApplication.getContext().openFileInput(filename);
			    inputReader = new BufferedReader(new InputStreamReader(in));
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		}
		else //if (mode == Mode.APPEND)
		{
			try 
			{
				//File f = new File(MHAndroidApplication.getContext().getExternalFilesDir(null), filename);
				
				File path = Environment.getExternalStorageDirectory();
				File file = new File(path, filename);
				path.mkdirs();
				
//				if (!getName().startsWith("files/"))
//					filename = "files/" + filename;
//				if (!f.exists())
//					f.createNewFile();
					
				OutputStream outputStream = new FileOutputStream(file);//MHAndroidApplication.getContext().openFileOutput(filename, Context.MODE_APPEND | Context.MODE_WORLD_READABLE);
				outputWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
				System.out.println("ERROR - FileNotFoundException:  Failed to open file " + filename + " for output.");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				System.out.println("ERROR - IOException:  Failed to open file " + filename + " for output.");
			}
		}
	}

	
	@Override
	public void close() 
	{
		try
		{
			if (inputReader != null)
				inputReader.close();
		
			if (outputWriter != null)
				outputWriter.close();
		}
		catch (IOException ioe)
		{
			System.out.println("IOException thrown while closing file " + getName() + ".");
		}
	}

	
	@Override
	public void write(String data) 
	{	
		try {
			outputWriter.write(data);
		} catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("ERROR - IOException:  Error writing to file " + getName() + ".");
		}
	}

	@Override
	public void append(String data) 
	{
		try 
		{
			outputWriter.append(data);
		} catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("ERROR - IOException:  Error appending to file " + getName() + ".");
		}
	}

	@Override
	public String readLine() 
	{
		if (inputReader == null) return null;
		
		String inputString = null;
		//StringBuffer stringBuffer = new StringBuffer();
		
		try 
		{
		    inputString = inputReader.readLine();
//		    stringBuffer = new StringBuffer();
//		    while (inputString != null) 
//		    {
//		        stringBuffer.append(inputString + "\n");
//			    inputString = inputReader.readLine();
//		    }
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
		
		return inputString; //stringBuffer.toString();
	}


	@Override
	protected void finalize() throws Throwable 
	{
		close();
	}


}
