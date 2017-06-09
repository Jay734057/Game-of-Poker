/**
 * @File:   typeOfRank.java
 *
 * @Desc:   This type is used to describe the 13 different rank of
 *          cards in ascending value. Each enum type has its own name
 *          mainly for message showing. Also, method getRank() returns
 *          the ordinal number of an enum type as its rank.
 */

public enum typeOfRank {
    TWO("2"),THREE("3"),FOUR("4"),FIVE("5"),SIX("6"),SEVEN("7"),
    EIGHT("8"),NIGHT("9"),TEN("10"),JACK("Jack"),QUEEN("Queen"),
    KING("King"),ACE("Ace");
        
    String name;//represent the printing name of each rank.
        
    typeOfRank(String name){
        this.name = name;
    }
        
    public String getName(){
        return this.name;
    }
        
    public int getRank(){
        return this.ordinal();
    }
}