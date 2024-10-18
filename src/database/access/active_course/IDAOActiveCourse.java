package database.access.active_course;

import school.ActiveCourse;
import school.Course;
import school.User;

import java.util.ArrayList;

public interface IDAOActiveCourse {
    boolean addActiveCourse(User user, Course course, int years);
    boolean deleteActiveCourse(User user, Course course);
    boolean updateActiveCourse(User user, Course course);
    ArrayList<ActiveCourse> getAllActiveCourses();
}
