
package tonyInterfaces;

public interface BoardAPI {

	
	public NamedLocation getSquare (int index);
	public PrivateProperty getProperty (int index);
	public PrivateProperty getProperty (String shortName);
	public boolean isProperty (int index); 
	public boolean isProperty (String shortName);
	public boolean isSite (String shortName);
	public boolean isVehicle (String shortName);
	public boolean isUtility (String shortName);
	
}
