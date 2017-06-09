/**
 * @File:   PokerCard.java
 *
 * @Desc:   This class is used to abstracted a poker card including its rank
 *          and suit. Also this class implements interface Comparable, since
 *          method CompareTo() can be used in the constructor of class Player
 *          to sort a hand of five cards. In the constructor of PokerCard, it
 *          decides whether a string represents a valid card by setRank() and
 *          setSuit() methods and show error message if an invalid card is 
 *          entered.
 */

public class PokerCard implements Comparable<PokerCard>{

    private typeOfRank rank;
    private typeOfSuit suit;
    
    public PokerCard(char rank, char suit){
        //If the rank or suit is invalid, show error message and end the
        //program.
        if (!setRank(rank) || !setSuit(suit))
            invalidCard(rank, suit);
    }
    
    private boolean setRank(char rank){
        //set the rank of a card according to the character entered.
        switch (rank){
        case '2': this.rank = typeOfRank.TWO;break;
        case '3': this.rank = typeOfRank.THREE;break;
        case '4': this.rank = typeOfRank.FOUR;break;
        case '5': this.rank = typeOfRank.FIVE;break;
        case '6': this.rank = typeOfRank.SIX;break;
        case '7': this.rank = typeOfRank.SEVEN;break;
        case '8': this.rank = typeOfRank.EIGHT;break;
        case '9': this.rank = typeOfRank.NIGHT;break;
        //set the rank regardless of lower case characters or 
        //upper case characters
        case 'T': 
        case 't': this.rank = typeOfRank.TEN;break;
        case 'J': 
        case 'j': this.rank = typeOfRank.JACK;break;
        case 'Q': 
        case 'q': this.rank = typeOfRank.QUEEN;break;
        case 'K':
        case 'k': this.rank = typeOfRank.KING;break;
        case 'A': 
        case 'a': this.rank = typeOfRank.ACE;break;
        default : 
            return false;//not a valid rank.
        }
        return true;
    }
    
    private boolean setSuit(char suit){
        //set the suit of a card according to the character entered.
        //No matter it is lower or upper case character.
        switch (suit){
        case 'D':
        case 'd': this.suit = typeOfSuit.DIAMOND;break;
        case 'C':
        case 'c': this.suit = typeOfSuit.CLUB;break;
        case 'H':
        case 'h': this.suit = typeOfSuit.HEART;break;
        case 'S':
        case 's': this.suit = typeOfSuit.SPADE;break;
        default:
            return false;//not a valid suit.
        }
        return true;
    }
    
    private void invalidCard(char rank, char suit){
        //show invalid card message.
        //The parameters passed to constructor are not valid, 
        //so it shows error message and ends the program. 
        System.out.println("Error: invalid card name '"+rank+suit+"'");
        System.exit(0);
    }
    
    
    typeOfSuit getSuit(){
        return this.suit;
    }
    
    int getRank(){//return the rank of a card according to its ordinal value.
        return this.rank.getRank();
    }
    
    public String getName(){//return the card name according to its rank.
        return this.rank.getName();
    }

    @Override //implement the abstract method compareTo
    public int compareTo(PokerCard c) {
        if (this.getRank() < c.getRank()){
            return -1;
        }else if(this.getRank() > c.getRank()){
            return 1;
        }else return 0;
    }

}
