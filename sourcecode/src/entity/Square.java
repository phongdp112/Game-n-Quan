package src.entity;

import java.util.ArrayList;
import java.util.List;

public class Square {
    private final int squareId; // ID của ô
    private final List<Gem> gems; // Danh sách gem trong ô

    public Square(int squareId) {
        this.squareId = squareId;
        this.gems = new ArrayList<>();
    }

    public int getSquareId() {
        return squareId;
    }
    public int getScoreInSquare(){
        int sum=0;
        for(Gem gem:gems){
            sum = sum + gem.getValue();
        }
        return sum;
    }
    public List<Gem> getGems() {
        return gems;
    }

    public void addGem(Gem gem) {
        gems.add(gem);
    }

    public int getGemsQuantity() {
        return gems.size();
    }

    public void removeAllGems()
    {
        gems.clear();
    }
}
