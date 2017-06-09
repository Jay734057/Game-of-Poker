/**
 * @File:   Poker.java
 *
 * @Desc:   Main Poker function for comparing the rank of different players'
 *          hands. In this file, we firstly calculate the number of cards
 *          entered. If this number is equal to 0 or not a multiply of 5, it
 *          just show the error message. If there are only five cards, we
 *          only need to show the type and rank of one player. Else we need
 *          to sort player array according to their hand types and cards
 *          representing the rank of corresponding types in ascending order.
 *          Then, we  pick the last player and assume as winner before using
 *          findDraw() method to find out any draw situation. Finally
 *          calling the showResult() to print out whether it is a draw or
 *          has a winner.
 */

import java.util.Arrays;

public class Poker {
    static Player winner;
    static Player[] players;    //save information of different players.
    static int[] playerIndexes; //save the index of players
    static int drawCount;       //count for players that have hands with
                                //the same largest rank.
    
    public static void main(String[] args) {
        if ((args.length % 5) == 0 && args.length > 0){
            //determine whether the number of the cards satisfies the
            //conditions.
            int numOfPlayers = args.length/5;
            if (numOfPlayers == 1){
                //Only 5 cards are entered, so we just need to show the
                //information of one hand.
                winner = new Player(1,args);
            }else {
                players = new Player[numOfPlayers];
                playerIndexes = new int[numOfPlayers];
                for (int i = 1; i <= numOfPlayers; i++){
                    //initialize the player array.
                    players[i-1] = new Player(i
                            ,Arrays.copyOfRange(args, (i-1)*5, i*5));
                }
                //sort player array according to their classification and
                //rank
                Arrays.sort(players);
                //save the indexes of players in sorted player array.
                for (int i = 0; i < numOfPlayers; i ++){
                    playerIndexes[i] = players[i].getIndex();
                }
                winner = players[numOfPlayers-1];
                //find if there is any other players with the same rank as
                //the winner.
                findDraw(numOfPlayers);
                //show the game result according to drawCount number.
                showResult(drawCount);
            }
        }else {
            //show message about wrong number of cards.
            System.out.println("Error: wrong number of arguments;"
                    + " must be a multiple of 5");
            System.exit(0);
        }
    }

    private static void findDraw(int numOfPlayers){
        //find if any other players have the same rank as winner.
        drawCount = 1;
        int i = numOfPlayers-2;
        while(i >= 0 && winner.equals(players[i])){
                drawCount ++;
            i--;
        }
    }
    
    private static void showResult(int drawCount){//show game result.
        if (drawCount > 1){//drawCount > 1 means there is a draw.
            System.out.print("Players ");
            for(int j = playerIndexes.length - drawCount; 
                    j < playerIndexes.length - 2; j++){
                System.out.print(playerIndexes[j]+", ");
            }
            System.out.println(playerIndexes[playerIndexes.length - 2]
                    +" and " + playerIndexes[playerIndexes.length - 1]
                    + " draw.");
        }else //no draws, show the winner information.
            System.out.println("Player "+winner.getIndex()+" wins.");
    }
}
