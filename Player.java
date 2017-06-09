/**
 * @File:   Player.java
 *
 * @Desc:   Class Player is designed to represent each player abstractly with
 *          5 cards in hand. This class also implements interface Comparable
 *          because it is necessary to decide the ranks of players while more
 *          than one player in a game by using method compareTo(). In order
 *          to decide the classification of each hand of five cards, we sort
 *          these cards depending on the card ranks in ascending order at
 *          first, after which we calculate the hand pattern by method 
 *          getHandPattern(). According to this pattern, we decide the hand
 *          type and save the index of cards that can also represent the rank
 *          of a hand by calling method handClassify().
 *          
 *          (Note: Different hand type may have different numbers of cards 
 *          that can represent the rank of a hand. For example, a full house
 *          is presented by two cards including one for three of a kind and
 *          another for a pair, while a straight only need the highest-rank
 *          card in hand to represent its rank.) 
 */

import java.util.Arrays;

public class Player implements Comparable<Player>{
    
    private final static int numOfCards = 5;//indicate number of cards 
                                            //in player's hand
    private int indexOfPlayer;
    private PokerCard[] Hand;       //used to store the cards of one player
    private typeOfHand classification;
    private int[] representative;   //indicate the indexes of cards 
                                    //that represent the value of a hand
    private boolean isStraight;     //boolean value for straight pattern
    private boolean isSameSuit;     //boolean value for flush pattern
    
    public Player(int index, String[] a){
        this.indexOfPlayer = index;
        this.Hand = new PokerCard[numOfCards];
        for(int i = 0; i < numOfCards; i ++){
            this.Hand[i] = new  PokerCard(a[i].charAt(0),a[i].charAt(1));
        }
        //sort 5 cards according to their rank.
        Arrays.sort(Hand);
        //decide the classification of a hand.
        handClassify();
        //print hand message.
        System.out.print("Player "+this.indexOfPlayer+": ");
        System.out.println(this);
    }
    
    private void handClassify(){//get hand classification.
        int[] patternOfHand = getHandPattern();    
        switch(Array_of_Int_to_String(patternOfHand)){
        //According to the pattern string, five cards have five different
        //ranks, so the type of this hand may be straight_flush, straight,
        //flush or high_card
        //-----------------------------------------------------------------
        //The classification and the card with largest rank in this hand
        //represent the rank of hand, so save the index of the   
        //representative card in representative array.
            case "1111":  //pattern: abcde
                if (isStraight){      
                        //is a straight
                    setRep(Hand.length-1);
                    if (isSameSuit) 
                            //have the same suit, so it is a straight_flush.
                        classification = typeOfHand.STRAIGHT_FLUSH;
                    else     
                        //have different suits, so it is a straight.
                        classification = typeOfHand.STRAIGHT;  
                }else{    
                    //is not a straight
                    setRep();
                    if (isSameSuit) 
                        //have the same suit, so it is a flush.
                        classification = typeOfHand.FLUSH;
                    else             
                        //have different suits, so it is a high_card.
                        classification = typeOfHand.HIGH_CARD;    
                }
                break;                
        //There are four cards with the same rank, so it is a four_of_a_kind.
        //------------------------------------------------------------------
        //The classification as well as the first card and the last card
        //represent the rank of this hand together.
            case "1000":  //pattern: abbbb
                setRep(Hand.length-1, 0);
                classification = typeOfHand.FOUR_OF_A_KIND;
                break;
            case "0001":  //pattern: aaaab
                setRep(0, Hand.length-1);
                classification = typeOfHand.FOUR_OF_A_KIND;
                break;
                
        //There are three cards with the same rank, and other two cards
        //have another same rank, so it is a full_house.
        //------------------------------------------------------------------
        //The classification as well as the first card and the last card
        //represent the rank of this hand.
            case "0010":  //pattern: aaabb
                setRep(0, Hand.length-1);
                classification = typeOfHand.FULL_HOUSE;
                break;
            case "0100":  //pattern: aabbb
                setRep(Hand.length-1, 0);
                classification = typeOfHand.FULL_HOUSE;
                break;
                
        //There are three cards with the same rank while other two cards
        //have two other ranks, so it is a three_of_a_kind.    
        //-----------------------------------------------------------------
        //The indexes of representative cards should be stored in array
        //representative.
            case "1100":  //pattern: abccc
                setRep(Hand.length/2,1,0);
                classification = typeOfHand.THREE_OF_A_KIND;
                break;
            case "1001":  //pattern: abbbc
                setRep(Hand.length/2,Hand.length-1,0);
                classification = typeOfHand.THREE_OF_A_KIND;
                break;
            case "0011":  //pattern: aaabc
                setRep(Hand.length/2,Hand.length-1, Hand.length-2);
                classification = typeOfHand.THREE_OF_A_KIND;
                break;
                
        //There are two pairs of cards with the same rank while the 
        //remaining one has another rank, so it is a two_pair.
        //-----------------------------------------------------------------
        //The indexes of representative cards should be stored in array
        //representative.
            case "1010":  //pattern: abbcc
                setRep(Hand.length-2, 1, 0);
                classification = typeOfHand.TWO_PAIR;        
                break;
            case "0110":  //pattern: aabcc
                setRep(Hand.length-2, 1, Hand.length/2);
                classification = typeOfHand.TWO_PAIR;        
                break;
            case "0101":  //pattern: aabbc
                setRep(Hand.length-2, 1, Hand.length-1);
                classification = typeOfHand.TWO_PAIR;        
                break;
                
        //There are a pairs of cards with the same rank while the remaining
        //three have different ranks, so it is a one_pair.
            case "1110":  //abcdd
            case "1101":  //abccd
                setRep(3,2,1,0);
                classification = typeOfHand.ONE_PAIR;
                break;
            case "1011":  //abbcd
            case "0111":  //aabcd
                setRep(1,4,3,0);
                classification = typeOfHand.ONE_PAIR;
                break;
        }
        
    }
    
    private int[] getHandPattern(){//get the hand pattern information.
        //The information includes the straight pattern, flush pattern
        //an dan array of integers showing the difference between
        //adjacent cards.
        //For the array of integers, called "pattern", if the rank of cardn
        //is different from the the rank of card n+1, pattern[n] is marked
        //as 1.
        //For the straight pattern(variable "isStraight") and flush pattern
        //(variable "isSameSuit") are also decided in this function
        isStraight = true;
        isSameSuit = true;
        int[] pattern = new int[numOfCards-1];
        for (int i = 1; i < Hand.length; i++){
            if (Hand[i].getRank() - Hand[i-1].getRank() == 0)
        //the two adjacent cards have the same rank, 
        //mark the straight pattern as false. 
                isStraight = false;
            else if (Hand[i].getRank() - Hand[i-1].getRank() == 1)
        //the two adjacent cards have the adjacent rank, 
        //mark the corresponding element in pattern array as 1. 
                pattern[i-1] = 1;
            else{
        //the difference of ranks of the two adjacent cards is larger
        //than 1, mark the straight pattern as false and the corresponding n
        //element i pattern array as 1.
                isStraight = false;
                pattern[i-1] = 1;
            }
            if (isSameSuit && Hand[i].getSuit() != Hand[i-1].getSuit()){
        //the two adjacent cards have different suits, mark flush pattern
        //as false.
                isSameSuit = false;
            }
        }
        return pattern; 
    }
    
    private String Array_of_Int_to_String(int[] array){
        //map array of integer to String.
        String builder = new String();
        for (int i : array){
            builder+=i;
        }
        return builder;
    }
    
    private void setRep(){
        //set five representative cards.(for the flush pattern and one high
        //pattern)
        representative = new int[5];
        for (int i = 0; i < representative.length; i++){
            representative[i] = representative.length - 1 - i;
        }
    }
    
    private void setRep(int index){
        //set one representative card.(for straight and flush straight)
        representative = new int[1];
        representative[0] = index;
    }
    
    private void setRep(int index0, int index1){
        //set two representative cards.(for four of a kind and full house)
        representative = new int[2];
        representative[0] = index0;
        representative[1] = index1;
    }
    
    private void setRep(int index0, int index1, int index2){
        //set three representative cards.(for three of a kind and two pair)
        representative = new int[3];
        representative[0] = index0;
        representative[1] = index1;
        representative[2] = index2;
    }
    
    private void setRep(int index0, int index1, int index2, int index3){
        //set four representative cards.(for one pair)
        representative = new int[4];
        representative[0] = index0;
        representative[1] = index1;
        representative[2] = index2;
        representative[2] = index3;
    }
    
    int getNumOfRep(){//return the number of representative cards.
        int l = representative.length;
        return l;
    }
    
    private String getCardName(int i){
        return Hand[i].getName();
    }
    
    typeOfHand getClassification(){
        return this.classification;
    }
    
    int getIndex(){
        int i = this.indexOfPlayer;
        return i;
    }
    
    int getRep(int i){//get the rank of specific representative card.
        int r = this.Hand[representative[i]].getRank();
        return r;
    }
    
    public String toString(){
        //show information of player's hand depending on the classification.
        switch(this.classification){
        case ONE_PAIR: 
            return "Pair of "+getCardName(representative[0])+"s";
        case TWO_PAIR: 
            return getCardName(representative[0])+"s over "
                    +getCardName(representative[1])+"s";
        case THREE_OF_A_KIND:
            return "Three "+getCardName(representative[0])+"s";
        case STRAIGHT:
            return getCardName(representative[0])+"-high straight";
        case FLUSH:
            return getCardName(representative[0])+"-high flush";
        case FULL_HOUSE:
            return getCardName(representative[0])+"s full of "
                    +getCardName(representative[1])+"s";
        case FOUR_OF_A_KIND:
            return "Four "+getCardName(representative[0])+"s";
        case STRAIGHT_FLUSH:
            return getCardName(representative[0])+"-high straight flush";
        default:    
            return getCardName(representative[0])+"-high";
        }
    }
    
    boolean equals(Player h){
        //determine whether two players have the hands of the same rank.
        if (this.getClassification() != h.getClassification()){
            //first compare the classification.
            return false;
        }else{//then compare the representative cards.
            for (int i = 0; i < this.getNumOfRep(); i++){
                if (this.getRep(i) != h.getRep(i))
                    return false;
            }
        }
        return true;
    }
    
    @Override
    public int compareTo(Player p) {
        //'-1' represents smaller; '1' represents larger; '0' represents
        //equal;
        if (this.getClassification().getRank() 
                < p.getClassification().getRank())
            //firstly compare classification
            return -1;
        else if(this.getClassification().getRank() 
                > p.getClassification().getRank())
            return 1;
        else {
            //for the same classification, then compare the presentative
            //cards.
            for (int i = 0; i < this.getNumOfRep(); i ++){
                if(this.getRep(i) < p.getRep(i))
                    return -1;
                else if (this.getRep(i) > p.getRep(i))
                    return 1;
            }
        }
        return 0;//for players have hands of the same rank.
    }
}
