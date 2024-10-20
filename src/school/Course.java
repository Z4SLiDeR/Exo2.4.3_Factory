package school;

public class Course implements ConfigurableName{
    private final int id;
    private Type type;
    private String name;

    public Course(int id, Type type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = validateName(name);
    }
}
