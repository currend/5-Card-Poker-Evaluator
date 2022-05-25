import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * The following class is responsible for facilitating the creation of the players and determing
 * the winner.
 * 
 * @author David Curren
 *
 */
public class Game {
	
	/**
	 * NUM_PER_HAND specifies the total amount of cards found in each hand
	 */
	
	static final int NUM_PER_HAND = 5;
	
	 /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
	public static void main(String[] args) throws IOException {
		BufferedReader in = null; //created reader to read file
		
		/**Attempt to read file**/
		try {
			
				FileReader fr = new FileReader(args[0]);
				in = new BufferedReader(fr);
				
		} catch (FileNotFoundException e) {
			
				System.out.println("ERROR: File could not be opened.");
	            System.exit(0);
		}
					
			/** Read line by line within the file, extracting player information**/
			String line = null; 
			while((line  = in.readLine()) != null) { 
				line = line.replace(":", "");
				String [] split = line.split("\\s+"); //remove large spaces in file
				HashMap<String, String []> players = new HashMap<String, String[]>(); 
				
				/**Within the string array, extract information related to the player and cards*/
				for(int i = 0; i< split.length; i++)
				{
					//Files are specific and contain these specific player names
					if(split[i].equals("Black") || split[i].equals("White"))
					{
						String player = split[i];
						String [] cards = new String[NUM_PER_HAND];
						
						//Loop through the split array to retrieve card information
						for(int arrayIndx = 0; arrayIndx < cards.length; arrayIndx++, i++)
						{
							cards[arrayIndx] = split[i+1];
						}
						players.put(player, cards);
					}
				}
				
				/** Create player hands**/
				Hand playerOneHand = new Hand("Black", players.get("Black"));
				Hand playerTwoHand = new Hand("White", players.get("White"));
				
				/**
				 * Will take each hand and determine the rank and 
				 * set of high cards (based on rank)
				 */
				playerOneHand.determineRank();		
				playerTwoHand.determineRank();
				
				/**
				 * With ranks determined, now we can determine the winner
				 */
				determineWinner(playerOneHand, playerTwoHand);
				
			}	
			
	}

	
	/**
	 * The following function will be used to determine the winner, based on the rank or the highest cards.		
	 * @param playerOne 
	 * 			the information relating to player one's hand
	 * @param playerTwo
	 * 			the information relating to player two's hand
	 * 
	 * @requires 
	 * 			playerOne != null and playerTwo != null
	 * 
	 * @ensures
	 * 			[One of the player hands is a winning hand or that they two players tie]
	 */
	public static void determineWinner(Hand playerOne, Hand playerTwo)
	{
		/**
		 * If they have the same rank, we will look at the highest card values. This does
		 * differ based on the rank, but the list was created in a different class
		*/
		if(playerOne.rank.number == playerTwo.rank.number)
		{
			//The amount of cards are the same, so we can simply use high cards.
			highCardWinnerDetermination(playerOne, playerTwo);
		}
		//Based on the enum, if the player's rank number is lower, then the rank is higher.
		else if(playerOne.rank.number < playerTwo.rank.number)
		{
			System.out.println(playerOne.player + " wins with a " +  getRankInfo(playerOne));
		}
		else 
		{
			System.out.println(playerTwo.player + " wins with a " +  getRankInfo(playerTwo));
		}
		
		System.out.println("");
	}
	
	/**
	 * This function will search through the list of cards (with higher cards first) 
	 * to determine who has the best hand
	 * @param playerOne 
	 * 			the information relating to player one's hand
	 * @param playerTwo
	 * 			the information relating to player two's hand
	 * 
	 * @requires 
	 * 			playerOne != null and playerTwo != null
	 * 
	 * @ensures
	 * 		[A higher card was found in one of the hands or the two players have a similar 
	 * 		hand (tie)]
	 * 
	 */
	
	
	public static void highCardWinnerDetermination(Hand playerOne, Hand playerTwo)
	{
		boolean sameCheck = true; //this will be used to determine if a tie exists.
		
		//Both maxCardVals sizes are the same
		for(int i = 0; i < playerOne.maxCardVals.size(); i++)
		{
			/**
			 * Since the cards are ordered with no duplicates, if the current card is larger, 
			 * then that player wins.
			 */
			
			if(playerOne.maxCardVals.get(i).number > playerTwo.maxCardVals.get(i).number)
			{
				System.out.println(playerOne.player + " wins with a higher card of " + playerOne.maxCardVals.get(i));
				sameCheck = false; //no tie
				break;
			}
			
			else if(playerOne.maxCardVals.get(i).number < playerTwo.maxCardVals.get(i).number)
			{
				System.out.println(playerTwo.player + " wins with a higher card of " + playerTwo.maxCardVals.get(i));
				sameCheck = false; // no tie
				break;
			}
		}
		
		//If the cards are the same, then we will conclude that there is a tie
		if(sameCheck) {
			System.out.println("Both players had a " + getRankInfo(playerOne) + " and a similar hand (Tie!)");
		}
		
		//Otherwise, they simply only have the same rank.
		else {
		
		System.out.println("Both players had the same rank: " + getRankInfo(playerOne));
		}
		
		
	}
	
	
	/**
	 * This function is simply used to assist in the format of the ranks being printed out
	 * (as opposed to the enum)
	 * @param playerInfo
	 * 			Individual player's hand
	 * @requires
	 * 		playerInfo != null
	 * 
	 * @ensures
	 * 		[The hand that has a rank is associated with better formatted string for output]
	 * 
	 * @return
	 * 		The formatted string associated with the rank
	 */
	public static String getRankInfo(Hand playerInfo)
	{
		String rank;
		switch(playerInfo.rank) {
		case StraightFlush:
			rank = "Straight Flush";
			break;
		
		case FourOfAKind:
			rank = "Four of A Kind";
			break;
		
		case FullHouse:
			rank = "Full House";
			break;
			
		case Flush:
			rank = "Flush";
			break;
			
		case Straight:
			rank = "Straight";
			break;
			
		case ThreeOfAKind:
			rank = "Three of A Kind";
			break;
		
		case TwoPairs:
			rank = "Two Pairs";
			break;
			
		case Pair:
			rank = "Pair";
			break;
			
		default:
			rank = "High Card";
			break;
		}
		
		return rank;
	}
	
}
		
		
		
			

	


