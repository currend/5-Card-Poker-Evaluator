/**
 * Enum for all possible values
 * @author David Curren
 *
 */
public enum Value {
	Two(2), 
	Three(3), 
	Four(4), 
	Five(5), 
	Six(6), 
	Seven(7), 
	Eight(8), 
	Nine(9), 
	Ten(10), 
	Jack(11), 
	King(12), 
	Queen(13), 
	Ace(14);
	int number; //number makes comparisons easier between non-numerical values
	

	/**
	 * Used to assign each value with a number
	 * @param num the number associated with a value (card value)
	 */
	Value(int num) {
		this.number = num;
		
	}
	



}
