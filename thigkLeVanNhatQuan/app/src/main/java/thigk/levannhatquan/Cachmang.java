package thigk.levannhatquan;

public class Cachmang {
    private String imageName;
    private String name;
    private String description;

    public Cachmang(String imageName, String name, String description) {
        this.imageName = imageName;
        this.name = name;
        this.description = description;
    }

    // Getter methods
    public String getImageName() { return imageName; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
