package thigk.levannhatquan;

public class Language {
    private String imageName;
    private String name;
    private String description;

    public Language(String imageName, String name, String description) {
        this.imageName = imageName;
        this.name = name;
        this.description = description;
    }

    // Getter methods
    public String getImageName() { return imageName; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}