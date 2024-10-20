import database.DatabaseServices;
import database.access.active_course.IDAOActiveCourse;
import database.access.course.IDAOCourse;
import database.access.role.IDAORole;
import database.access.type.IDAOType;
import database.access.user.IDAOUser;
import school.Course;
import school.Type;
import school.User;
import school.Role;


public class SchoolCore {

    public static void main(String[] args) {
        DatabaseServices databaseService = new DatabaseServices();
        databaseService.connect();

        // Remplir les sections (types)
        IDAOType idaoType = databaseService.createIDAOType();
        Type informatiqueDeGestion = idaoType.addType("Informatique de gestion");
        Type droit = idaoType.addType("Droit");

        // Remplir les cours
        IDAOCourse idaoCourses = databaseService.createIDAOCourses();
        Course baseDesReseaux = idaoCourses.addCourses("Base des réseaux", informatiqueDeGestion);
        Course systemesExploitation = idaoCourses.addCourses("Systèmes d'exploitation", informatiqueDeGestion);
        Course poo = idaoCourses.addCourses("Programmation orienté objet", informatiqueDeGestion);
        Course droitCivil = idaoCourses.addCourses("Droit civil", droit);
        Course droitCommercial = idaoCourses.addCourses("Droit commercial", droit);

        // Remplir les statuts (rôles)
        IDAORole idaoRole = databaseService.createIDAORole();
        Role etudiant = idaoRole.addRole("Etudiant");
        Role chargeDeCours = idaoRole.addRole("Charge de cours");
        Role employeAdministratif = idaoRole.addRole("Employe administratif");

        // Remplir les personnes (utilisateurs)
        IDAOUser idaoUser = databaseService.createIDAOUser();
        User gillesPoulet = idaoUser.addUser("Poulet", "Gilles", chargeDeCours);
        User emmanuelGodissart = idaoUser.addUser("Godissart", "Emmanuel", chargeDeCours);
        User valeriaLai = idaoUser.addUser("Lai", "Valeria", employeAdministratif);
        User davidMairesse = idaoUser.addUser("Mairesse", "David", employeAdministratif);
        User richardDurant = idaoUser.addUser("Durant", "Richard", etudiant);
        User valerieOrtiz = idaoUser.addUser("Ortiz", "Valerie", etudiant);

        // Remplir la table intermédiaire (ActiveCourses)
        IDAOActiveCourse idaoActiveCourse = databaseService.createIDAOActiveCourse();
        idaoActiveCourse.addActiveCourse(gillesPoulet, systemesExploitation, 2022);
        idaoActiveCourse.addActiveCourse(emmanuelGodissart, baseDesReseaux, 2022);
        idaoActiveCourse.addActiveCourse(richardDurant, systemesExploitation, 2023);
        idaoActiveCourse.addActiveCourse(richardDurant, baseDesReseaux, 2024);

        databaseService.close();

        System.out.println("La base de données a été remplie avec succès et la connexion a bien été fermée.");
    }
}
