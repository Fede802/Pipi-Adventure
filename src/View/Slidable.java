package View;

public interface Slidable {
    int DEFAULT_WIDTH = 500;
    int DEFAULT_HEIGHT = 500;
    void slide();
    boolean isOnScreen();
    boolean isSliding();
    int getSlidingStep();
}
