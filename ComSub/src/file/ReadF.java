package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import interfaces.UserIO;

public class ReadF 
{
	private String fileName;
	private String type;
	private Scanner fileScanner;
	private String delimiter;
	private String action;
	private File fileToRead;
	
	public ReadF(UserIO _uio, String _inputReason)
	{
		//_uio.writeS("Please specify file name and path for " + _inputReason, true);
		String path = _uio.readSNoLimit(true, _inputReason);
		File fileToRead = new File(path);
		
	}
	
	/**
	 * Create determine file to be retrieved and perform relevent action based on what it is for
	 * @param _fileName - name of file to be read
	 * @param _fType - what file is for, ie configuration or persons code (use lower case)
	 * @param _delimiter - what to determine as a 'comment'
	 * @param _action - what action to take on file, ie copy to external document or to substitute with a reference.
	 */
	public ReadF(String _fileName, String _fType, String _delimiter, String _action)
	{
		fileName = _fileName;
		type = _fType; //what the file is for, ie config or the code file comments to be extracted from
		File openedFile = new File(fileName);
		
		try
		{
			fileScanner = new Scanner(openedFile);  //setup the reader
		}
		catch (FileNotFoundException e)
		{
		//	loadError = true;
		//	loadError(fileNameError);
		}
		/*
		if(type.equals("code"))
		{
			codeRead(openedFile, delimiter, action);
		}
		else if(type.equals("config"))
		{
			configRead(openedFile);
		}
		*/
	}
	
	
	
	
	public Scanner getScanner()
	{
		return fileScanner;
	}
	//read type relevent methods
	//code
	/**
	 * reads file, as the user supplied code needing the 'comments' removed, user supplies the comment delimiter
	 * @param _file - file to be worked on
	 * @param _delimiter - what to be replaced or copied
	 * @param _action - what action to take on file, ie copy to external document or to move and substitute with a reference.
	 */
	/*
	private void codeRead(File _file, String _delimiter, String _action)
	{
		
	}
	
	private Scanner configRead(File _openedFile)
	{
		Scanner 
		/* *****Disregard - create obj with each of these types stored, then 'addBoolConfig(k, v);'
		//create returnable storage
		HashMap[] configs = new HashMap[3];
		//add filled hashmaps to storage
		configs[0] = intMap;
		configs[1] = StringMap;
		configs[2] = booleanMap;
		configs[0]
		return configs; //return configuration
		
		return null;
	} */
}
