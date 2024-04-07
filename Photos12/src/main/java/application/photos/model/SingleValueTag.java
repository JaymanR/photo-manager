package application.photos.model;

public class SingleValueTag extends Tag {
    private String value;

    public SingleValueTag(String name, String value) {
        super(name);
        this.value = value;
    }
}
