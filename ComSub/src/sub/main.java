package sub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import file.*;
import interfaces.UserIO;
import tasks.Header;
import ConsoleIO.*;
import config.Config;

public class main 
{//this class will act as init & launcher
	private static UserIO uio = new ConsoleIO(); //interface used, defaulting as Console
	private static Config config = new Config(); //config/user info, using defaults
	
	//create generic storage buffers
	private static int intBuffer = 0;
	private static String stringBuffer = " ";
	
	public static void main(String[] args)
	{	
		
		Setup(intBuffer);
		
	}

	/**
	 * setup program, as a bystep launch interface (ie a command or gui)
	 * will confirm files etc on startup
	 * @param _interfaceType = run level ie console or gui etc
	 */
	private static void Setup(int _interfaceType) //CHECK if interface right class or should great a view class
	{
		String fileName = (String) config.getADetails("wappfile"); //preset filename (Appdetails)(below confirm with user)
		String uFileName = (String) config.getADetails("wuserfile"); //preset filename (user details) (below confirm with user)
		
		switch(_interfaceType)
		{
		case 0: 
			consoleSetup(fileName, uFileName);
			startMenu();
			break;
		case 1:
			
			break;
		default:
		}
		//
	}
//--------------(get sets)----------------
		//debug
	public static boolean debugging()
	{
		return (boolean) config.getUSetting("debugging");
	}
	public static boolean toggleDebug()
	{
		if((boolean) config.getUSetting("debugging") == false)
		{
			return ((boolean) config.changeMapValue(0, "USetting", "debugging", "true"));
		}
		else
		{
			return ((boolean) config.changeMapValue(0, "USetting", "debugging", "false"));
		} 
	}
	
//-----------------(setup subs)------------------
	private static void consoleSetup(String _fileName, String _uFileName)
	{
		int filesCorrect = 0;
		String[] validOptions = {"Y", "N"};
		uio.writeS("Please Confirm that \"" + _fileName + "\" is the file address of your Application details? (y/n)", true);
		stringBuffer = uio.readS(validOptions, false);
		if(stringBuffer.equals("Y"))
		{
			filesCorrect +=1;
		}
		uio.writeS("Please Confirm that \"" + _uFileName + "\" is the file address of your user settings? (y/n)", true);
		stringBuffer = uio.readS(validOptions, false);
		if(stringBuffer.equals("Y"))
		{
			filesCorrect +=2;
		}
		if(filesCorrect != 3)
		{
			switch(filesCorrect)
			{
			case 0:
				/*
				uio.writeS("Please provide the filename and address of your userSettings file?\nExample C:\\UserSettings.txt", true);
				_uFileName = uio.readSNoLimit(true, "user settings");

				uio.writeS("Please provide the filename and address of your app data file?\nExample C:\\AppData.txt", true);
				_fileName = uio.readSNoLimit(true, "app data");
				break;
				*/
				_uFileName = enterConfirmFileAddress("userSettings");
				_fileName = enterConfirmFileAddress("App Data");
				break;
			case 1: 
				uio.writeS("Please provide the filename and address of your userSettings file?\nExample C:\\UserSettings.txt", true);
				_uFileName = uio.readSNoLimit(true, "user settings");
				break;
			case 2: 
				uio.writeS("Please provide the filename and address of your app data file?\nExample C:\\AppData.txt", true);
				_fileName = uio.readSNoLimit(true, "app data");
				break;
				
			}
		//commit, note there is a old-wappfile etc for recovery
		config.changeMapValue(3, "ADetail", "wappfile", _fileName);	
		config.changeMapValue(3, "ADetail", "wuserfile", _uFileName);	
		}
		ReadF appFile = new ReadF(_fileName, "config", ":", null);
		ReadF uFile = new ReadF(_uFileName, "config", ":", null);
	
		config.filesToConfig(appFile.getScanner(), uFile.getScanner());
	} //end of method
	
	private static String enterConfirmFileAddress(String _fileType)
	{
		uio.writeS("Please provide the filename and address of your " + _fileType + " file?\nExample C:\\fileName.txt", true);
		return uio.readSNoLimit(true, _fileType);
	}

	private static int startMenu()
	{
		boolean exit = false;
		int runLevel = 0;
		int numMenuOptions = 0;
		while(!exit)
		{
			int menuOption = -1;
			uio.writeS("ComSub Menu\n--------------", true);
			uio.writeS("1. ComSub - Subsitute (subsitute comment to reference)", true); numMenuOptions++;
			uio.writeS("2. ComSub - Copy (Copy comment to external, original untouched)", true); numMenuOptions++;
			uio.writeS("3. ComSub - Insert head (Insert personal header on selected files)", true); numMenuOptions++;
			uio.writeS("4. Options - debug, name etc", true); numMenuOptions++;
			uio.writeS("5. Exit", true); numMenuOptions++;
			
			menuOption = uio.readI(1, numMenuOptions);
			switch(menuOption)
			{
				case 1: 
					break;
				case 2:
					break;
				case 3: Header head = new Header(uio, config);
					break;
				case 4:
					runLevel = optionsMenu();
					break;
				case 5: exit = true;
					break;
				default: uio.writeS("Unable to pair " + menuOption + " to an available option, please re-enter selection \nError: main->startMenu", true);
			}
		}
		return runLevel;
	}
	
	private static int optionsMenu()
	{
		boolean exit = false;
		int numMenuOptions = 0;
		int runLevel = 0;
		while(!exit)
		{
			int menuOption = -1;
			uio.writeS("Options Menu\n--------------", true);
			uio.writeS("1. Enable Debugging", true); numMenuOptions++;
			uio.writeS("2. Change runLevel", true); numMenuOptions++;
			uio.writeS("3. ComSub - Insert head (Insert personal header on selected files)", true); numMenuOptions++;
			uio.writeS("4. Options - debug, name etc", true); numMenuOptions++;
			uio.writeS("5. Exit", true); numMenuOptions++;
			
			menuOption = uio.readI(1, numMenuOptions);
			switch(menuOption)
			{
				case 1: 
					break;
				case 2:
					break;
				case 3: Header head = new Header(uio, config);
					break;
				case 4:
					runLevel = optionsMenu();
					if(runLevel > 0)
					{
						return runLevel; // if user asked for a non commandLine runLevel it will return that number;
					}
					break;
				case 5: exit = true;
					runLevel = -1; //used as a exit flag for main to terminate as well
					break;
				default: uio.writeS("Unable to pair " + menuOption + " to an available option, please re-enter selection \nError: main->startMenu", true);
			}
			return runLevel;
		}
	}
	
	private static int interfaceSelection()
	{
		ArrayList<String> runLevelsArray = (ArrayList) config.getDetailSetting("runLevels");
		int maxRun = runLevelsArray.size();
		uio.writeS("Please enter the corresponding number for which type of interface you wish to use;", true);
		for(int i = 0; i < maxRun; i++)
		{
			uio.writeS("\t (" + i + ") " + runLevelsArray.get(i), true);
		}
		return uio.readI(0, maxRun);
	}
}
	
