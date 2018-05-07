package tonyInterfaces;
//A regular square on the board that can't be bought
public class NamedLocation implements Identifiable, Locatable {

	private Locatable leftSquare;
	private Locatable rightSquare;
	private String name;

	NamedLocation(String name, Locatable leftSquare, Locatable rightSquare){
		this.leftSquare = leftSquare;
		this.rightSquare = rightSquare;
		this.name = name;
	}

	@Override
	public Locatable goLeft() {
		// TODO Auto-generated method stub
		return leftSquare;
	}

	@Override
	public Locatable goRight() {
		// TODO Auto-generated method stub
		return rightSquare;
	}

	public void setLeft(Locatable square){
		leftSquare = square;
	}

	public void setRight(Locatable square){
		rightSquare = square;
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public boolean equals (String string) {
		
//		boolean answer = true;
//		String[] words = name.split(" ");
//		String[] prop = string.split(" ");
//		
////		System.out.println("words(prop name) is: ");
////		
////		for(int i=0;i<words.length;i++){
////			System.out.print(words[i]+ " ");
////		}
////		
////		System.out.println("with lenght " + words.length);
////		
////		
////		System.out.println("words(to build name) is: ");
////		
////		for(int i=0;i<prop.length;i++){
////			System.out.print(prop[i]+ " ");
////		}
////		
////		System.out.println("with lenght " + prop.length);
//		
//		
//		if(prop.length != words.length){
//			return false;
//		}
//		
//		
//		if(prop.length>1){
//			for(int i=0;i<prop.length && i < words.length;i++){
//				if(!prop[i].equalsIgnoreCase(words[i])){
//					return false;
//				}
//			}
//		}else{
//			answer = name.equalsIgnoreCase(string);
//		}
		return name.equalsIgnoreCase(string);
	}
	
	public String toString () {
		return name;
	}

}
