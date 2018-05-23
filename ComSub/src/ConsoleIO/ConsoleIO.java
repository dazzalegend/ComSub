package ConsoleIO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleIO implements interfaces.UserIO
{
	Scanner scan = new Scanner(System.in);
	
	@Override
	public String readS(String[] _valid,  boolean _caseSensitive) 
	{
		boolean success = false;
		String _buffer;
		while(!success)
		{
			try
			{
				_buffer = scan.nextLine();
				String _dBuffer = _buffer.toUpperCase(); //case desenstive buffer, decided small performance sacrifice as assuming user wont enter long strings
														 // also assuming will be desensitive more often then not
				for(int i= 0; i < _valid.length; i++)
				{
					if(!_caseSensitive & _dBuffer.equals(_valid[i].toUpperCase()))
					{
						return _dBuffer;
					}
					//if is case sensitive
					else if(_buffer.equals(_valid[i]))
					{
						return _buffer;
					}
				}
				writeS("(!) Value not expected \nPlease re-enter your selection using one of the below values", true);
			}
			catch(InputMismatchException e)
			{
				writeS("(!) Input either not type String/Char", true);
				writeS("Please re-enter your selection using one of the below values", true);
				scan.nextLine();
			}
			for(int i= 0; i < _valid.length; i++)
			{
				writeS("\t" + _valid[i], true);
			}
		}
		return null;
	}
	
	/**
	 * returns -1 if fails or user doesnt confirm;
	 */
	public String readSNoLimit(boolean _confirm, String inputReason) 
	{
		boolean success = false;
		String _buffer = "-1";
		while(!success)
		{
			try
			{
				if(_confirm)
				{
					//_buffer = scan.next();
					String[] validOptions = {"Y", "N"};
					writeS("Please advise the file and path to be used for " + inputReason, true);
					//writeS("You have advised " + _buffer + " is to be used for " + inputReason + "\nPlease confirm? (Y/N)", true);
					while(!success)
					{
						
						_buffer = scan.nextLine();
						writeS("You have advised \'" + _buffer + "\' is to be used for " + inputReason + "\nPlease confirm? (Y/N)", true);
						String stringBuffer = readS(validOptions, false);
						if(stringBuffer.equals("Y"))
						{
							success = true;
						}
						else
						{
							writeS("You have advised \'" + _buffer + "\' is not correct please re-enter", true);
						}
						
					}
				}
				else
				{
					_buffer = scan.nextLine();
				}
			}
			catch(InputMismatchException e)
			{
				writeS("(!) Input either not type String/Char", true);
				writeS("Please re-enter your selection", true);
				scan.next();
			}
		}
		return _buffer;
	}
	
	@Override
	public int readI(int _min, int _max) 
	{
		boolean success = false;
		int _buffer = -1;
		while(!success)
		{
			try
			{
				//_buffer = scan.nextInt();
				_buffer = Integer.parseInt(scan.nextLine());
				//_buffer = (scan.nextLine());
				
				while(_buffer < _min  || _buffer > _max)
				{
					writeS("(!) Value outside of range " + _min + " to " + _max + "\nPlease re-enter your selection", true);
					_buffer = Integer.parseInt(scan.nextLine());
					
				}
				success = true;
			}
			catch(Exception e)
			{
				writeS("(!) Input either not type Int or outside of range " + _min + " to " + _max, true);
				writeS("Please re-enter your selection", true);
				//scan.nextLine();
			}
		}
		return _buffer;
	}
	
	@Override
	public void writeS(String _output, boolean _LF) 
	{
		if(_LF)
		{
			System.out.println(_output);
		}
		else
		{
			System.out.print(_output);
		}
		
	}

	@Override
	public void writeI(int _output, boolean _LF) 
	{
		if(_LF)
		{
			System.out.println(_output);
		}
		else
		{
			System.out.print(_output);
		}
	}

	@Override
	public void writeC(char _output, boolean _LF) 
	{
		// TODO Auto-generated method stub
		
	}


	

}
