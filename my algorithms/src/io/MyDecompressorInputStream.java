package io;

import java.io.IOException;
import java.io.InputStream;

/**
*  <h1>MyDeCompressorOutputStream</h1>
* @author  Avi Punes & Ziv Daudi
* @version 1.0
* @since   2015-08-30 
*
*	this class will override the read method of inputstream
*	this class will read array of bytes from input stream, decompressing the 36 first for the level,line,column,start position and goal position
*	after it will read all the maze it self
*	the user will provide the input stream
*/

public class MyDecompressorInputStream extends InputStream {

	
	InputStream in;
	
	public MyDecompressorInputStream(InputStream in)
	{
		this.in=in;
	}
	
	

	/**
	 * @param in - array of byte to decompress and load to to a array
	 * @throws IOException
	 */
	@Override
	public  int read(byte b[]) throws IOException {
		
		for (int i = 0; i <36; i++)
		{
			b[i]=(byte) in.read();
			
		}
		
		for (int i = 36; i < b.length;)
		{
			
			b[i]=(byte) in.read();
			
			int k=in.read();				
			for (int j = 1; j <k; j++)
			{
				b[i+j]=b[i];
			}
			
			i=i+k;
		}
		
		return 0;
		
	}
	
	
	public  int readLevelLineColumn(byte b[]) throws IOException
	{
		for (int i = 0; i <12; i++)
		{
			b[i]=(byte) in.read();
			
		}
		return 0;
	}



	@Override
	public int read() throws IOException {
		byte temp[]=new byte[4];
		for (int i = 0; i <4; i++)
		{
			temp[i]=(byte) in.read();
			
		}
		int value=(temp[0]<<24)&0xff000000|
				(temp[1]<<16)&0x00ff0000|
				(temp[2]<< 8)&0x0000ff00|
				(temp[3]<< 0)&0x000000ff;
		return value;
	}
	

}
