
package tonyInterfaces;

public interface BoardAPI {

	
	public NamedLocation getSquare (int index);
	public RentalProperty getProperty (int index);
	public RentalProperty getProperty (String shortName);
	public boolean isProperty (int index); 
	public boolean isProperty (String shortName);
	public boolean isSite (String shortName);
	public boolean isStation (String shortName);
	public boolean isUtility (String shortName);
	
}
