package cloudflight.integra.backend.coffeemug.model;

public class CoffeeMug {
    private Long id;
    private String color;
    private int capacityMl;
    private boolean clean;

    public CoffeeMug(Long id, String color, int capacityMl, boolean clean) {
        this.id = id;
        this.color = color;
        this.capacityMl = capacityMl;
        this.clean = clean;
    }

    public CoffeeMug() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public int getCapacityMl() { return capacityMl; }
    public void setCapacityMl(int capacityMl) { this.capacityMl = capacityMl; }
    public boolean isClean() { return clean; }
    public void setClean(boolean clean) { this.clean = clean; }
}
