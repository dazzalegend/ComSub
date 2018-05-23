package interfaces;

public interface UserIO 
{
	public String readS(String[] _valid, boolean _caseSensitive);
	public String readSNoLimit(boolean _confirm, String inputReason);
	public int readI(int _min, int _max);
	public void writeS(String _output, boolean _LF);
	public void writeI(int _output, boolean _LF);
	public void writeC(char _output, boolean _LF);
}
