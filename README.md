# 5-Card-Poker-Card-Evaluator
The following program was created based on the assignment on CodingDojo (http://codingdojo.org/kata/PokerHands/).
The program will read in a textfile containing information about two players, each with 5 cards in a hand. The java files included in src are:

Main Class Files:

Card: Includes information needed to associate the value and suit string information with the Value and Suit enumeration
Hand: The class used to both build a arraylist of cards, but to use said cards and evaluate them to determine the rank for said hand. Based on the rank, the maximum card(s) can be determined.
Game: This class will be used to read in the file information and determine the winner based on the rank of each hand and the maximum cards.

Enumerations:
Value: This enumeration file contains all of the possible card values (with respective numbers that allow for sorting and determing the higher card)
Suit: This enumeration file contains all of the possible suits within a deck
Rank: This enumeration file contains all of the possible ranks, including a high card. A number is associated with each rank, allowing for an easy way to compare them (determine the winner)

Comparators:
ValueSort: This comparator was used in relation with the specific numbers are associated with each card value, with the goal of sorting the cards based on the value.

The remaining files in src were used for testing. Sampleinput.txt was used as a way to determine the winner in the game class (later in development) whereas the other files were used to test that the correct rank could be determined.
