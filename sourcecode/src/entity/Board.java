package src.entity;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Square> squareList;

    public Board() {
        squareList = new ArrayList<>(12);

        // Tạo các ô từ 0 đến 9
        for (int i = 0; i < 12; i++) {
            squareList.add(new Square(i));
        }

        // Khởi tạo gem cho các ô
        // 5 gem dân cho mỗi ô dân (1-5 và 7-11) còn 1 gem quan cho mỗi ô quan (0 và 6)
        for (int i = 0; i < 12; i++) {
            if(i%6==0){
                squareList.get(i).addGem(new BigGem());    // 1 gem quan cho mỗi ô quan (0, 6)
            }
            else{
           
               
                    for(int j=0;j<5;j++) {
                    squareList.get(i).addGem(new SmallGem());
      
            }
        }
        }
    }

    public Square getSquareById(int id) {
        return squareList.get(id);
    }

    public List<Square> getSquareList() {
        return squareList;
    }
}
