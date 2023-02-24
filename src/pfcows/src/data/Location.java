package pfcows.src.data;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public enum Location {
    WEST(new Area(new Tile(3210, 3309, 0),
            new Tile(3197, 3335, 0),
            new Tile(3156, 3347, 0),
            new Tile(3155, 3315, 0),
            new Tile(3169, 3319, 0))),
    EAST(new Area( new Tile(3253, 3255, 0),
            new Tile(3265, 3255, 0),
            new Tile(3265, 3296, 0),
            new Tile(3242, 3298, 0),
            new Tile(3244, 3280, 0),
            new Tile(3253, 3277, 0)));

    private Area area;

    Location(Area area) {
        this.area = area;
    }

    Location(String name, Tile x1, Tile y1, Tile x2, Tile y2) {
    }

    public Area getArea() {
        return area;
    }
}
