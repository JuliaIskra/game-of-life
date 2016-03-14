package julia.nemtseva.game.of.life;

/**
 * @author Julia Nemtseva
 */
public class Cell {
    private final CellState state;

    public Cell() {
        this(CellState.DEAD);
    }

    public Cell(CellState state) {
        this.state = state;
    }

    public CellState getState() {
        return state;
    }
}
