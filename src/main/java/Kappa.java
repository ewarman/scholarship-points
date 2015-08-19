
public class Kappa {

	private boolean inHouse;
	private boolean active;
	private String name;
	private int deck;
	private int count;
	
	public Kappa() {
		
	}
	public Kappa(String n, boolean i, boolean a, int d) {
		name = n;
		inHouse = i;
		active = a;
		deck = d;
		count = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public boolean getInHouse() {
		return inHouse;
	}
	
	public int getDeck() {
		return deck;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int c) {
		count = c;
	}
	
	public String toString() {
		return name+" "+active+" "+inHouse+" "+deck;
	}

}
