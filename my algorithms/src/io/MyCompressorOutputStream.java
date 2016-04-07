package io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
* <h1>MyCompressorOutputStream</h1>
* @author  Avi Punes & Ziv Daudi
* @version 1.0
* @since   2015-08-30 
*
*	this class will override the write method of outputstream
*	this class will write array of bytes to outputstream, compressing the 36 first for the level,line,column,start position and goal position
*	after it will write all the maze it self
*	the user will provide the outputstream
*/

public class MyCompressorOutputStream extends OutputStream{

	OutputStream out;
	
	//ctor
	public MyCompressorOutputStream(OutputStream out)
	{
		this.out=out;
	}
	/**
	 * @param b - array of byte to compress and save to outputstream
	 * @throws IOException
	 */
	@Override
	public void write(byte b[]) throws IOException
	{
		for (int i = 0; i < 36; i++)
		{
			out.write(b[i]);
		}
		
		
		int count = 1;
		for (int i = 36; i < b.length; i++)
		{
			
		if (i<b.length-1)
		 {
			if (b[i]==b[i+1])
			{
				count++;
			}
			else
			{
				out.write(b[i]);
				out.write(count);
				count=1;
			}
		  }
		else
		{
			if (count>1&&b[i]==b[i-1])
			{
				out.write(b[i]);
				out.write(count);
				
			}
			else
			{
				count=1;
				out.write(b[i]);
				out.write(count);
			}
		}
		
		}
		
		
			
			
		
		
	
		
		
	}

	@Override
	public void write(int b) throws IOException 
	{
		ByteBuffer bb = ByteBuffer.allocate(4); 
		bb.putInt(b); 
		for (int i = 0; i < 4; i++)
		{
			out.write(bb.array()[i]);
		}
	}
	

}
