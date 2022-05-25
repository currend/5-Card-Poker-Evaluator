
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * The following class is responsible for determining the rank of a hand of cards, 
 * after creating said hand 
 * @author David Curren
 *
 */

public class Hand {
	
	/**
	 * The following fields are used to store information relating to a specific hand
	 * player: the player name
	 * hand: an Arraylist of all of a player's cards
	 * rank: the rank determined by each of these cards
	 * nbPerRank: count of all card values in hand
	 * maxCardVals: contains a list of max cards
	 */
	public String player; 
	public ArrayList<Card> hand = new ArrayList<>(); 
	public Rank rank; //rank of hand
	public HashMap<Value, Integer> nbPerRank = new HashMap<>(); 
	public ArrayList<Value> maxCardVals = new ArrayList<>(); 
	
	/**
	 * These constants are used as opposed to their magic number counterparts, specifically in
	 * searching the the nbPerRank arraylist and determine the rank
	 */
	static final int HAND_RANK_FOUR = 4;
	static final int HAND_RANK_THREE = 3;
	static final int HAND_RANK_TWO = 2;
	
	
	/**
	 * The following constructor will associate the player name to the hand and create
	 * the hand of cards
	 * @param playerName
	 * 			the player name associated with the hand
	 * @param playerCardInfo
	 * 			the string array of cards
	 */
	public Hand(String playerName, String [] playerCardInfo)
	{
		this.player = playerName;
		
		//Build the hand
		for(String individualCard : playerCardInfo)
		{
			hand.add(new Card(individualCard));
		}
		
		determineValueFrequency();
	}
	
	/**
	 * The following method is build the nbPerRank arraylist, which is used to determine the counts
	 * of each card value
	 * @requires
	 * 		hand != null
	 * 
	 * @ensures
	 * 		nbPerRank.size > 0
	 */
	public void determineValueFrequency() {
		
		//This will go ahead and get the counts of each card within the hand
		for(Card c: hand)
		{
			//If the hashmap does not contain the card currently, then add with a value of one
			if(!nbPerRank.containsKey(c.val))
			{
				nbPerRank.put(c.val, 1);
			}
			//Otherwise, increment the value of the currently existing card
			else
			{
				nbPerRank.put(c.val, nbPerRank.get(c.val) + 1);
			}
		}
		
	}

	/**
	 * This function will go ahead and determine the rank of the hand
	 * 
	 * @requires
	 * 	nbPerRank.size > 0 and hand.size > 0
	 * 
	 * @ensures
	 * 	[a rank is not determined]
	 * 		
	 */
	public void determineRank()
	{
		//The following two booleans are used to determine if a flush or straight exists
		boolean isFlush = flush();
		boolean isStraight = straight();
		
		if(isStraight && isFlush) //straight flush exists
		{
			rank = Rank.StraightFlush;
			sortCards(); //this will sort the cards based on highest to lowest value
			
			
		}
		
		/**
		 * Use the nbPerRank to determine card counts (for a value), which will determine rank
		 * for four of a kind, full house, three of a kind, etc.
		 */
		else if(nbPerRank.containsValue(HAND_RANK_FOUR))
		{
			rank = Rank.FourOfAKind;
			retrieveMaxCardVal(HAND_RANK_FOUR); //retrieve card value for the one that make a four of a kind
		}
		else if(nbPerRank.containsValue(HAND_RANK_THREE) && nbPerRank.containsValue(HAND_RANK_TWO))
		{
			rank = Rank.FullHouse;
			retrieveMaxCardVal(HAND_RANK_THREE); //retrieve card value for the one that make a full of house (three of a kind)
		}
		else if(isFlush)
		{
			rank = Rank.Flush;
			sortCards(); //use sort for high card
		}
		else if(isStraight)
		{
			rank = Rank.Straight;
			sortCards(); //use sort for high card
		}
		else if(nbPerRank.containsValue(HAND_RANK_THREE))
		{
			rank = Rank.ThreeOfAKind;
			retrieveMaxCardVal(HAND_RANK_THREE); //retrieve card value for the one that make a three of a kind
		}
		
		//Pairs
		else if(nbPerRank.containsValue(HAND_RANK_TWO))
		{
			int numPairs = pairCount(); //Looking for the number of pairs
			if(numPairs == 2) //Found 2 pairs
			{
				rank = Rank.TwoPairs;
				retrieveMaxCardsFromPair(); //Retrieve max cards, firstly for the two pairs and then for remaining cards
			}
			else {
				rank = Rank.Pair;
				retrieveMaxCardsFromPair(); //Retrieve max cards, firstly for the one pair and then for remaining cards
			}
		}
		else {
			rank = Rank.HighCard;
			sortCards(); //sort cards to determine highest card ordering
		}
	}
	
	/**
	 * This function is used to determine if a flush exists within the current hand
	 * @requires
	 * 		hand.size > 0
	 * 
	 * @ensures
	 * 		[hand is not changed]
	 * 
	 * @return the boolean stating if a flush exists
	 */
	private boolean flush() {
		boolean flushPresent = true;
		Suit first = hand.get(0).suit; //get the suit of the first card
		
		//Run through the hand arraylist and determine if the suit is the same for all cards
		for(int i = 1; i < hand.size(); i++) {
			if(hand.get(i).suit != first) //If not, then there is no flush
			{
				flushPresent = false;
			}
		}
		return flushPresent;
		
	}
	
	/**
	 * This function is used to determine if a straight exists in the current hand
	 * @requires
	 * 		hand.size > 0
	 * 
	 * @ensures
	 * 		[hand is not changed]
	 * 
	 * @return the boolean stating if a straight exists
	 */
	private boolean straight()
	{
		boolean straightPresent = true;
		
		//Loop through the list and determine if the card value is the same for all cards
		for(int i = 1; i < hand.size(); i++) {

			if (!(hand.get(i-1).val.number == (hand.get(i).val.number + 1)))
			{
				straightPresent = false;
			}
		}
		return straightPresent;
	}
	
	/**
	 * This function is used to determine the number of pairs
	 * @requires
	 * 		nbPerRank.size > 0
	 * 
	 * @ensures
	 * 		[nbPerRank is not changed]
	 * 
	 * @return the number of pairs
	 */
	private int pairCount()
	{
		int numPairs = 0;
		
		//Loop through the nbPerRank hashmap, determining how many of the values are 2
		for(int value: nbPerRank.values()) {
			
			if(value == 2)
			{
				numPairs++;
			}
		}
		return numPairs;
	}
	
	/**
	 * This function is used to find the card value for a particular rank such as four of a kind
	 * @param valToSearch
	 * 			the value of which makes the particular rank true
	 * 
	 * @requires
	 * 		nbPerRank.size > 0 and valToSearch is in nbPerRank
	 * 
	 * @ensures
	 * 		[finds the max card]
	 */
	private void retrieveMaxCardVal(int valToSearch)
	{
		for(Entry<Value, Integer> entry: nbPerRank.entrySet())
		{
			/*Loop through the entry set and get the value from each entry. valToSearch is used
			 * to determine the number representing the rank (4 for a four of a kind)
			 */
			if(entry.getValue() == valToSearch)
			{
				maxCardVals.add(entry.getKey());
			}
		}
	}
	
	/**
	 * This function is used to sort through the cards, based on ordering of the value.
	 * @requires
	 * 		hand.size > 0
	 * @ensures
	 * 		[cards in order based on value]
	 */
	private void sortCards()
	{
		for(Card c: hand)
		{
			//If statement to remove duplicates
			if(!maxCardVals.contains(c.val))
			{
				maxCardVals.add(c.val);
			}
		}
		Collections.sort(maxCardVals, new ValueSort());
	}
			
	
	/**
	 * This function will retrieve max cards for the pair rankings. This means
	 * that we will extract the values of the pairs, sort those and then sort the
	 * remaining cards.
	 * 
	 * @requires
	 * 		nbPerRank.size > 0 and a pair has to exist
	 * 
	 * @ensures
	 * 		[retrieve max cards in correct order (based on looking at pair values first)]
	 */
	private void retrieveMaxCardsFromPair()
	{
		ArrayList<Value> remCards = new ArrayList<>(); //remaining cards that are not part of the pairs
		for(Entry<Value, Integer> entry: nbPerRank.entrySet())
		{
			if(entry.getValue() == 2) //Find card values that make up a pair
			{
				maxCardVals.add(entry.getKey());
			}
			else if(entry.getValue() == 1) //Find card values that are not a part of a pair
			{
				remCards.add(entry.getKey());
			}
		}
		
		Collections.sort(maxCardVals, new ValueSort()); //Sort pair card values
		Collections.sort(remCards, new ValueSort()); //sort remaining cards
		maxCardVals.addAll(remCards); //Append the two lists
		
		
	}
}
	
