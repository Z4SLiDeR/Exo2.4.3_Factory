package school;

/**
 *  Role assigné (Etudiant/Professeur/...)
 */
public class Role implements ConfigurableName{
    private final int id;
    private String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateName(name);
    }
}
