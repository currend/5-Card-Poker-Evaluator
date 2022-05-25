
/**
 * Enum representing the ranks that are possible
 * @author David Curren
 *
 */
public enum Rank {
	StraightFlush(0), FourOfAKind(1), FullHouse(2), Flush(3), Straight(4), ThreeOfAKind(5), TwoPairs(6), Pair(7), HighCard(8);
	int number; //use of number makes comparisons easier
	
	/**
	 * This enumeration represents all of the ranks, including high card
	 * @param num used for determining which rank is better (lower ranks are better)
	 */
	Rank(int num) {
		this.number = num;
		
	}
	
	

}
