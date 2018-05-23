package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Used for the storage, retrieval and actioning of the configuration for the application
 * Specifically, user settings such as directory, font size (when using GUI) and application information such as version.
 * Created 26-March-2018
 * @author dwoff1
 */
public class Config 
{ 
	private static Map userSettings = new HashMap<String, Object>(); //the user settings like name, debugging etc
	private static Map appDetails = new HashMap<String, Object>(); //the App details such as version
	
	/**
	 * fill user settings & App details for use.
	 * @param _userSettings - A scanner for retrieval of values from the user 'profile' file set in ReadF
	 * @param _AppDetails - A scanner for retrieval of details for the app, file set in ReadF
	 */
//------------(Constructors)-----------------------------
	/*
	 * 	private static boolean debugging = false;
	private static int runlevel = 0; //number related to interface type, higher more advanced, ie 0 = console, 1 = basic gui, 2 = debug gui, 3 = advanced gui
	private static int maxRunLevel = 0; //highest runlevel implemented at this time
	private static boolean readSuccess = false;
	 */
	/**
	 * using Default Constructore to set default values for the program/user info
	 */
	public Config()
	{//using default constructor to set default values.
		//addToMap(int _dataType, Map _storeOn, String _key, String _value);
		addToMap(0, userSettings, "mapName", "UserSettings");
		addToMap(3, appDetails, "mapName", "AppDetails");
		
		addToMap(0, userSettings, "debugging", "false");
		addToMap(2, userSettings, "runlevel", "0");
		addToMap(2, appDetails, "maxrunlevel", "0"); //UPDATE - may require updating as the code changes
		addToMap(3, appDetails, "wuserfile", "ComSub-u.txt"); //UPDATE - may require updating as code changes, also w as in windows FS
		addToMap(3, appDetails, "wappfile", "ComSub-a.txt"); //UPDATE - may require updating as code changes, also w as in windows FS
		addToMap(3, appDetails, "old-wuserfile", "ComSub-u.txt"); //UPDATE - may require updating as code changes, also w as in windows FS - old allows retrieval of default
		addToMap(3, appDetails, "old-wappfile", "ComSub-a.txt"); //UPDATE - may require updating as code changes, using project folder location, ^
		addToMap(3, appDetails, "personalheader", "ComSub-ph.txt"); //^
		addToMap(3, appDetails, "appname", "ComSub");
		ArrayList<String> a = new ArrayList<String>();
		a.add("Console");
		a.add("GUI");
		addArrayToMap(appDetails, "runLevels", a);
	}
	
//-------------------(Get Set)------------------------------
	/**
	 * returns a users setting
	 * @param _key
	 * @return
	 */
	public Object getUSetting(String _key)
	{
		return userSettings.get(_key);
	}
	/**
	 * Returns a appDetail
	 * @param _key
	 * @return
	 */
	public Object getADetails(String _key)
	{
		return appDetails.get(_key);
	}
	/**
	 * provided incase it is unknown which map the key/value pair occurs
	 * should test if returning null
	 * @param _key - key to search for value 
	 * @return a object of stored type or null if not found
	 */
	public Object getDetailSetting(String _key)
	{
		if(appDetails.containsKey(_key))
		{
			return appDetails.get(_key);
		}
		else if(userSettings.containsKey(_key))
		{
			return userSettings.get(_key);
		}
		else
		{
			return null;
		}
	}
//-------------------(Misc methods)-------------------------
	/**
	 * Adds a given key/value pair to specified map
	 * @param _dataType - the data type the value is 0-bool, 1-float, 2-int, 3-string
	 * @param _storeOn - map to store this info on
	 * @param _key - the key for the map (string)
	 * @param _value - value to be stored as a string (datatype will then be used to parse to that type)
	 * @return errorlist (of key/values not added) for debugging purposes
	 */
	private ArrayList addToMap(int _dataType, Map _storeOn, String _key, String _value)
	{
		ArrayList errorList = new ArrayList<String>();
		try
		{
			switch(_dataType)
			{
				case 0: //boolean
						_storeOn.put(_key, Boolean.parseBoolean(_value)); //adds a key with string field name on to the supplied map with value, of second arg
						break;
				case 1: //float
						_storeOn.put(_key, Float.parseFloat(_value)); //field name, value
						break;
				case 2: //int
						_storeOn.put(_key, Integer.parseInt(_value)); //field name, value
						break;
				case 3: //string
						_storeOn.put(_key, _value); //field name, value
						break;
				default:
						errorList.add("Unable to add line: " + _dataType + _key + _value);
			}
		}
		catch(NumberFormatException e)
		{
			errorList.add("Unable to add line: " + _dataType + _key + _value);
		}
		return errorList;
	}
	private ArrayList addArrayToMap(Map _storeOn, String _key, ArrayList _value)
	{
		ArrayList errorList = new ArrayList<String>();
		try
		{
			_storeOn.put(_key, _value);
		}
		catch(NumberFormatException e)
		{
			errorList.add("Unable to add line: " +  _key + _value);
		}
		return errorList;
	}
	
	/**
	 * At time of writing the 2 possible values for store on are USetting & ADetail
	 * @param _dataType - the data type the value is 0-bool, 1-float, 2-int, 3-string
	 * @param _storeOn
	 * @param _key
	 * @param _value
	 * @return
	 */
	public Object changeMapValue(int _dataType, String _storeOn, String _key, String _value)
	{
		try
		{
			if(_storeOn.equals("USetting") && !userSettings.isEmpty())
			{
				addToMap(_dataType, userSettings, _key, _value);
				
			}
			else if(_storeOn.equals("ADetail") && !appDetails.isEmpty())
			{
				addToMap(_dataType, appDetails, _key, _value);
				
			}
			return getDetailSetting(_key);
		}
		catch(NullPointerException e)
		{
			System.out.println("Key not found or unable to edit entry");
			return null;
		}
	}
	/**
	* A method to populate a given Map with details read from a file using the scanner
	* @param _activeScanner - scanner made to read the file
	* @param _storeOn - map to store details retrieved via the scanner
	* @return a array list of lines unable to be interpreted.
	*/
	public void filesToConfig(Scanner _appDFile, Scanner _userSFile)
	{
		ArrayList errors = new ArrayList<String>();
		errors = interpretFile(_appDFile, appDetails);
		for(int i = 0; i < errors.size(); i++)
		{
			System.out.println("Program discovered below errors on loading details from file.\n" + errors.get(i));
		}
		errors = interpretFile(_userSFile, userSettings);
		for(int i = 0; i < errors.size(); i++)
		{
			System.out.println("Program discovered below errors on loading details from file.\n" + errors.get(i));
		}
	}
	private ArrayList interpretFile(Scanner _activeScanner, Map _storeOn)
	{
		ArrayList errorList = new ArrayList<String>();
		try
		{
			while(_activeScanner.hasNext())
			{
			String line = _activeScanner.nextLine();
			
				if(!line.startsWith("//")) //if a comment do nothing
				{
					//if not a comment assume it is a setting or detail
					//split line into 3 parts: datatype, field, and value
					String fields[] = line.split(":"); //split by the : symbol
					if(fields.length > 0) //ie not blank line
					{
						try
						{
							int dataType = Integer.parseInt(fields[0]);
							if(dataType != 4)
							{
								errorList = addToMap(dataType, _storeOn, fields[1], fields[2]);
							}
							else
							{
								ArrayList<String> arrayList = new ArrayList<String>();
								for(int i; i < fields.length; i++)
								{
									arrayList.add(fields[i]);
								}
								errorList.add(addArrayToMap(_storeOn, fields[1], arrayList))
							}
						}
						catch(NumberFormatException e)
						{
							errorList.add("* Unable to add line: " + fields[0] + ": " + fields[1] + ": " + fields[2]);
						}
					}
				} //end of if (checking if comment)
			} //scanner has next to read
		}
		catch(NullPointerException e)
		{
			if(getADetails("mapName").equals("AppDetails"))
			{
				System.out.println("Unable to read file to configure settings,\nplease confirm \'" + getADetails("wappfile") + "\' is listed in your file system and not blank");
			}
			
		}
		return errorList;
	} //Interpret file method end
} //class end
