package tp1.logic;

public interface GameModel {
    public boolean isFinished();
    public void update();
    public boolean reset(int level);
    public void addAction(Action act);
    public void clearActions();
    public void exit();
    public int getLevel();
}