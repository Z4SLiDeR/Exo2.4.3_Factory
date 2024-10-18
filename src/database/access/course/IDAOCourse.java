package database.access.course;

import school.Course;
import java.util.ArrayList;

public interface IDAOCourse {
    int getCoursesId(String nom);
    boolean addCourses(String nom, int idSection);
    boolean updateCourses(int id, String nom, int idSection);
    boolean deleteCourses(int id);
    ArrayList<Course> getCourses();
}
