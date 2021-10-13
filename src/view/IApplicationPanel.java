package view;

public interface IApplicationPanel{

    int DEFAULT_WIDTH = 600;
    int DEFAULT_HEIGHT = 600;
    int TIMER_TICK = 16;
    int MUSIC_THEME = 0;
    int CONFIRM_THEME = 1;
    int SCROLL_THEME = 2;
    int ERROR_THEME = 3;

    void start();
    void stop();
}