
public class Kappa {

	private boolean inHouse;
	private boolean active;
	private int deck;
	private int count;
	
	public Kappa() {
		
	}
	public Kappa(boolean i, boolean a, int d) {
		inHouse = i;
		active = a;
		deck = d;
		count = 0;
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
	public void incrementCount() {
		count++;
	}
	
	public String toString() {
		return active+" "+inHouse+" "+deck;
	}

}
