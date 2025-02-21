package src.entity;

public class Gem {
    private int value; // Giá trị của viên gem (1 cho gem dân, 5 cho gem quan)

    public Gem(int value) {
        this.value = value;
    }

    public int getValue() {

        return value;
    }
}
