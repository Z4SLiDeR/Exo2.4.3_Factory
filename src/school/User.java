package school;

public class User implements ConfigurableName {
    private final int id;
    private String lastName;
    private String firstName;
    private Role role;

    public User(int id, String lastName, String firstName, Role role) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = validateName(lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = validateName(firstName);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
