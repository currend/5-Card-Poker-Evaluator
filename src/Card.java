import java.util.HashMap;

/**
 * The following class can associate the string information from the input file with specific enum 
 * values for the value and suit
 * @author David Curren
 *
 */
public class Card {
	
	/**
	 * Each card contains a value and a suit
	 */
	public Value val;
	public Suit suit;
	
	/**
	 * The following hashmaps are created to help map a specific string (the value in the
	 * textfile) with an enum value
	 */
	private HashMap<Character, Value> cardVals = new HashMap<Character, Value>() 
	{{
		put('2', Value.Two);
		put('3', Value.Three);
		put('4', Value.Four);
		put('5', Value.Five);
		put('6', Value.Six);
		put('7', Value.Seven);
		put('8', Value.Eight);
		put('9', Value.Nine);
		put('T', Value.Ten);
		put('J', Value.Jack);
		put('Q', Value.Queen);
		put('K', Value.King);
		put('A', Value.Ace);
	}};
	
	private HashMap<Character, Suit> cardSuits = new HashMap<Character, Suit>() 
	{{
		put('H', Suit.Hearts);
		put('C', Suit.Clubs);
		put('D', Suit.Diamonds);
		put('S', Suit.Spades);
	}};
	
	
    /**
	 * Constructor that will retrieve the value and suit from the input file
	 * @param card
	 * 			string associated with a single card
	*/
	public Card(String card) {
		/**
		 * These functions will take in the file input value or suit and associate it with a key, 
		 * so that string can be associated with an enumeration value
		 */
		this.val = determineVal((card.charAt(0)));
		this.suit = determineSuit((card.charAt(1)));
		
	}
	
	/**
	 * This function will determine the enum for the value, using the arraylist
	 * @param val
	 * 			the card value (number)
	 * @requires
	 * 		val is in the key set of the hashmap of Values
	 * 
	 * @ensures
	 * 		[A value (string) is mapped to a specific enum value]
	 * 
	 * @return
	 * 		the associated enumeration value
	*/
	public Value determineVal(char val)
	{
		return cardVals.get(val);
		
	}
	
	/**
	 * This function will determine the enum for the suit, using the arraylist
	 * @param suit
	 * 			the card suit 
	 * @requires
	 * 		suit is in the key set of the hashmap of Suits
	 * 
	 * @ensures
	 * 		[A suit (string) is mapped to a specific enum value]
	 * 
	 * @return
	 * 		the associated enumeration value
	 */
	public Suit determineSuit(char suit)
	{
		return cardSuits.get(suit);
	}
	
	/**
	 * Simple getter for the value
	 * @return
	 * 		the value associated with the card
	 */
	public Value getValue()
	{
		return val;
		
	}
	
	/**
	 * Simple getter for the suit
	 * @return
	 * 		the suit associated with the card
	 */
	public Suit getSuit()
	{
		return suit;
	}
	

}
	
	



