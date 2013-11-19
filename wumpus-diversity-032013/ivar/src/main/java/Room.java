/**
 * Created with IntelliJ IDEA.
 * User: ivar
 * Date: 3/14/13
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Room {
    int x;
    int y;

    boolean cellHasArrow;

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public boolean isCellHasArrow() {
        return cellHasArrow;
    }

    public void setCellHasArrow(boolean cellHasArrow) {
        this.cellHasArrow = cellHasArrow;
    }
}
