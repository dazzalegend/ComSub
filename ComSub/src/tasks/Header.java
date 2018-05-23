package tasks;

import java.io.File;

import config.Config;
import file.ReadF;
import interfaces.UserIO;

public class Header 
{
	UserIO uio;
	public Header(UserIO _uio, Config _config)
	{
		uio = _uio;
		ReadF phAddToFile = new ReadF(_uio, "the personalised header to be inserted on"); //determine what file user wants ph (personal header) inserted onto
		check((String)_config.getDetailSetting("personalheader")); //check the file to use (which contains ph
	}
	
	private void check(String _phPath)
	{
		String[] yn = {"y","n"};
		uio.writeS("Please confirm \'" +_phPath + "\' to be used as personalised header template? (Y/N)", true);
		String confirm = uio.readS(yn, true);
		if(confirm.equals("n"))
		{
			String headPath = uio.readSNoLimit(true, "personalied header's template");
		} 
	}
	//check if preset header
	//Add header to userfile if not
	//Ask file to add header to
	//apply
}
