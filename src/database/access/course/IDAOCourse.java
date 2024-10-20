package database.access.course;

import school.Course;
import school.Type;

import java.util.ArrayList;

public interface IDAOCourse {
    Course getCourseById(int courseId);
    Course addCourses(String name, Type section);
    boolean updateCourses(Course course, String name, Type section);
    boolean deleteCourses(Course course);
    ArrayList<Course> getCourses();
    int getNextId();
}
