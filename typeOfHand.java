/**
 * @File:   typeOfHand.java
 *
 * @Desc:   This type is used to describe the nine classifications of hands
 *          in ascending order. It is beneficial to decide the rank of each
 *          classification. Method getRank() returns the ordinal of a
 *          classification as its rank.
 */

public enum typeOfHand {
    
    HIGH_CARD,ONE_PAIR,TWO_PAIR,THREE_OF_A_KIND,
    STRAIGHT,FLUSH,FULL_HOUSE,FOUR_OF_A_KIND,STRAIGHT_FLUSH;
    
    public int getRank(){//return the ordinal value of each classification
        return this.ordinal();
    }
}
