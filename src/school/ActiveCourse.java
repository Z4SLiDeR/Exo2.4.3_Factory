package school;

public class ActiveCourse {
    private final User user;
    private final Course course;
    private int years;

    public ActiveCourse(User user, Course course, int years) {
        this.user = user;
        this.course = course;
        this.years = years;
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        if (years < 1900 || years > java.time.Year.now().getValue() + 5) {
            throw new IllegalArgumentException("L'année doit être comprise entre 1900 et l'année actuelle + 5 ans.");
        }
        this.years = years;
    }
}