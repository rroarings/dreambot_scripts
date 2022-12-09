package pfminer.src.data;

public enum Rock {

    CLAY("Clay", 1, 11161),
    TIN("Tin", 1, 11360),
    IRON("Iron", 15, 11364),
    COAL("Coal", 30, 11366),
    GOLD("Gold", 40, 11371),
    MITHRIL("Mithril", 55, 11372);

    Rock(String name, int levelRequired, int modelColor) {
        this.name = name;
        this.levelRequired = levelRequired;
        this.modelColor = modelColor;
    }

    public String getName() {
        return name;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public int getModelColor() {
        return modelColor;
    }

    private String name;
    private final int levelRequired;
    private final int modelColor;
}
