package src.controller;

import src.entity.Board;
import src.entity.Player;
import src.entity.Square;
import src.entity.Gem;

import java.util.ArrayList;
import java.util.List;

public class GameController {


    private final Player player1;
    private final Player player2;
    private final Board board;
    private Player currentPlayer;
    public GameController(String player1Name, String player2Name) {
        Board board = new Board();
        List<Square> player1Square = board.getSquareList().subList(1,6);
        List<Square> player2Square = board.getSquareList().subList(7,12);
        Player player1 = new Player(1,player1Name,player1Square);
        Player player2 = new Player(2,player2Name,player2Square);
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.currentPlayer = player1;
        // player1 bắt đầu trước
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchTurn(){
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    public int handleRefillGems() {
        if(currentPlayer.getAllGemInPlayerSquares()==0) {
            if (currentPlayer.checkScoreGems() >= 5) {
                currentPlayer.handleScoreForRefill();
                currentPlayer.refillPlayerSquares();
                 return 1;
            } else {
                Player nextPlayer;
                if (currentPlayer == player1) nextPlayer = player2;
                else nextPlayer = player1;
                if (nextPlayer.getScore() < 5) {
                    endGame();
                    return 2;
                }
                else {
                    currentPlayer.increaseBorrowGem(5);
                    currentPlayer.refillPlayerSquares();
                    return 1;
                }

            }
        }
        else  return 0;
    }

    public List<Integer> spreadGems(int pickSquareId, boolean clockwise) {

        Square pickSquare = board.getSquareList().get(pickSquareId);

        List<Gem> gems = pickSquare.getGems();
        List<Integer> squareId = new ArrayList<>();
        int currentIndex = pickSquareId;
        while (!gems.isEmpty()) {
            currentIndex = getNextSquareIndex(currentIndex, clockwise);
            Square currentSquare = board.getSquareById(currentIndex);
            currentSquare.addGem(gems.remove(0));
            squareId.add(currentIndex);
            // Rải từng viên đá
        }
        return squareId;

    }
    public int getNextSquareIndex(int currentIndex, boolean clockwise) {
        if(clockwise) {
            return (currentIndex+1)%12;
        }else {
            return (currentIndex-1+12)%12;
        }
    }
    public int handleNextEndSquare(int nextEndIndex, boolean clockwise) {
        Square nextEndSquare = board.getSquareById(nextEndIndex);
        if(nextEndSquare.getGemsQuantity()!=0){
            if(nextEndIndex%6==0) return 2;
            else return 1;

        }else{
            int nextTwoEndSquareIndex = getNextSquareIndex(nextEndIndex, clockwise);
            Square nextTwoEndSquare = board.getSquareById(nextTwoEndSquareIndex);
            if(nextTwoEndSquare.getGemsQuantity()==0) return 2;
            else {
                currentPlayer.increaseScore(nextTwoEndSquare.getGems());
                nextTwoEndSquare.removeAllGems();
                return 0;
            }
        }
    }
    public boolean isGameOver() {
        // Kiểm tra xem game có kết thúc không
        return board.getSquareById(0).getGemsQuantity() + board.getSquareById(6).getGemsQuantity() == 0;
    }

    public void endGame() {
        int playerScore = player1.getScore();
        player1.setScore(playerScore+player1.getAllGemInPlayerSquares()-player1.getBorrowGems());
        playerScore = player2.getScore();
        player2.setScore(playerScore+player2.getAllGemInPlayerSquares()-player2.getBorrowGems());
        System.out.println("Game Over!");
        System.out.println("Player 1: " + player1.getScore());
        System.out.println("Player 2: " + player2.getScore());
    }
}
