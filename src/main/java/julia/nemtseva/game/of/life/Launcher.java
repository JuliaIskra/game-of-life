package julia.nemtseva.game.of.life;

/**
 * @author Julia Nemtseva
 */
public class Launcher {
    public static void main(String[] args) {
        Grid grid = new Grid(10);
        grid.markAsAlive(
                new Coordinates(4, 5),
                new Coordinates(5, 5),
                new Coordinates(6, 5));
        System.out.println(grid);
        for (int i = 0; i < 3; i++) {
            Grid newGrid = grid.tick();
            System.out.println(newGrid);
            grid = newGrid;
        }
    }
}
