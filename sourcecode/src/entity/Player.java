package src.entity;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int id;
    private final String name;
    private int score;
    private final List<Square> playerSquares; // Các ô của người chơi (Player 1: 0-4, Player 2: 5-9)
    private List<Gem> scoreGems;
    private int borrowGems ;
    public Player(int id,String name,List<Square> playerSquares) {
        this.id = id;
        this.name = name;
        this.score = 0;
        this.playerSquares = playerSquares;
        this.borrowGems = 0;
        this.scoreGems = new ArrayList<>();
    }
    //Getter and Setter

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
    public int getBorrowGems() {
        return borrowGems;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Square> getPlayerSquares() {
        return playerSquares;
    }
    // Phương thức khác

    public void increaseBorrowGem(int value) {
        this.borrowGems +=value;
    }

    //Đếm số small Gem trong số gem mà player ăn được
    public int checkScoreGems(){
        int count =0;
        if(scoreGems.isEmpty()){
            return count;
        }
        for(Gem gem : scoreGems){
            if(gem.getValue()==1) count++;
        }
        return count;
    }
    public void increaseScore(List<Gem> scoreGems) {
        for(Gem gem : scoreGems){
            this.score+=gem.getValue();
        }
        this.scoreGems.addAll(scoreGems);
    }
    public void handleScoreForRefill() {
        this.score -= 5;
        this.scoreGems.removeIf(gem -> gem.getValue()==1&& scoreGems.indexOf(gem)<5);
    }

    public void refillPlayerSquares(){
        for(Square square : playerSquares){
            square.addGem(new SmallGem());
        }
    }
    public int getAllGemInPlayerSquares() {
        int totalGems = 0;
        for (Square square : playerSquares) {
            totalGems += square.getGemsQuantity(); // Lấy số lượng gem trong mỗi ô và cộng dồn
        }
        return totalGems;
    }

}
