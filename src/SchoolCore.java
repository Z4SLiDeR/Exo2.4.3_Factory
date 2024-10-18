import database.DatabaseServices;

import java.util.Arrays;

public class SchoolCore {

    public static void main(String[] args) {
        DatabaseServices databaseService = new DatabaseServices();
        databaseService.connect();

        Course ezaezaCourse = databaseService.createIDAOCourses().addCourses("ezaeza", 2);
        User gillesPoulet = databaseService.createIDAOUser().addUser("Gilles", "Poulet", Arrays.asList(ezaezaCourse));

        databaseService.close();
    }
}