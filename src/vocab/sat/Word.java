package vocab.sat;

public class Word 
{
	private String name;
	private String quickDef;
	private String fullDef;
	private boolean isChecked = false;
	public Word(String n, String qD, String fD)
	{
		name = n;
		quickDef =qD; 
		fullDef =fD; 
	}
	public String getName()
	{
		return name;
	}
	public String getQuickDef()
	{
		return quickDef;
	}
	public String getFullDef()
	{
		return fullDef;
	}
	public void setChecked(boolean c)
	{
		isChecked =c;
	}
	public Boolean getCheckedState()
	{
		return isChecked;
	}

}
