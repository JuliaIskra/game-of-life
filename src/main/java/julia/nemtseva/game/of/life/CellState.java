package julia.nemtseva.game.of.life;

/**
 * @author Julia Nemtseva
 */
public enum CellState {
    DEAD("\u25A1"),
    ALIVE("\u25A0");

    private final String view;

    CellState(String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return view;
    }
}
