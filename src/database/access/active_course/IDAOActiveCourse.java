package database.access.active_course;

import school.ActiveCourse;
import school.Course;
import school.User;

import java.util.ArrayList;

public interface IDAOActiveCourse {
    ActiveCourse addActiveCourse(User user, Course course, int years);
    boolean updateActiveCourse(ActiveCourse activeCourse);
    boolean deleteActiveCourse(ActiveCourse activeCourse);
    ActiveCourse getActiveCourseByUserIdAndCourseId(int userId, int courseId);
    ArrayList<ActiveCourse> getActiveCourses();
}
