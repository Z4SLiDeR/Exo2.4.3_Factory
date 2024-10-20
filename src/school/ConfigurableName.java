package school;

public interface ConfigurableName {

    default String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être null ou vide.");
        }
        return name;
    }
}
