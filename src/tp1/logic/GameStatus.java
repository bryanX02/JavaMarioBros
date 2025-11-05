package tp1.logic;

public interface GameStatus {
    public int remainingTime();
    public int points();
    public int numLives();
    public boolean playerWins();
    public boolean playerLoses();
    public boolean isExit();
    public String positionToString(int row, int col);
}