package school;

//Type de cours
public class Type {
    private final int id;
    private String name;

    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
