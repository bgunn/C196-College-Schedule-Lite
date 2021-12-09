package android.wgunn.collegeschedulelite.Database;

import android.app.Application;
import android.wgunn.collegeschedulelite.DAO.CourseAssessmentDAO;
import android.wgunn.collegeschedulelite.DAO.CourseDAO;
import android.wgunn.collegeschedulelite.DAO.CourseNoteDAO;
import android.wgunn.collegeschedulelite.Entity.Course;
import android.wgunn.collegeschedulelite.Entity.CourseAssessment;
import android.wgunn.collegeschedulelite.Entity.CourseNote;
import android.wgunn.collegeschedulelite.Entity.CourseWithChildren;

import java.util.ArrayList;
import java.util.List;

/**
 * The CourseRepository handles all data operations for the course entity
 */
public class CourseRepository {

    /**
     * The CourseDAO object
     */
    private final CourseDAO courseDAO;

    /**
     * The CourseNoteDAO object
     */
    private final CourseNoteDAO courseNoteDAO;

    /**
     * The CourseAssessmentDAO object
     */
    private final CourseAssessmentDAO courseAssessmentDAO;

    /**
     * Constructor gets the database instance and initialize the DAO's
     *
     * @param application The epplication context
     */
    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        courseDAO = db.courseDAO();
        courseNoteDAO = db.courseNoteDAO();
        courseAssessmentDAO = db.courseAssessmentDAO();
    }

    /**
     * Get a course entity by ID
     *
     * @param id The Course ID
     * @return The selected course or null
     */
    public Course get(Long id) {
        return courseDAO.load(id);
    }

    /**
     * Fetches a course by ID and all related notes. The entities are packaged and returned in
     * a CourseWithCourses data object.
     *
     * @param id
     * @return The selected course with related courses
     */
    public CourseWithChildren getWithNotes(Long id) {
        return courseDAO.loadWithNotes(id);
    }

    /**
     * Fetch and return all courses
     *
     * @return List of courses
     */
    public List<Course> getAll() {
        return courseDAO.loadAll();
    }

    /**
     * Fetch and return all courses and related notes
     *
     * @return List of CourseWithNotes data objects
     */
    public List<CourseWithChildren> getAllWithNotes() {
        return courseDAO.loadAllWithChildren();
    }

    /**
     * A convenience method to find and return all courses by status
     *
     * @return The list of courses
     */
    public List<Course> getCoursesByStatus(String status) {

        List<Course> courses = new ArrayList<>();

        for (Course course : courseDAO.loadAll()) {
            if (course.getStatus().equals(status)) {
                courses.add(course);
            }
        }

        return courses;
    }

    /**
     * <pre></pre>
     * Saves the provided course doing an insert or update as needed.
     *
     * If an insert is done, the entity ID is set from the return value.
     * </pre>
     *
     * @param course The Course entity
     */
    public void save(Course course) {
        if (course.getId() == null) {
            course.setId(courseDAO.insert(course));
        } else {
            courseDAO.update(course);
        }
    }

    /**
     * Delete the provided course
     *
     * @param course The Course entity
     */
    public void delete(Course course) {
        courseDAO.delete(course);
    }

    /**
     * Add a note and attach to a course
     *
     * @param course The Course entity
     * @param note The CourseNote entity
     */
    public void addNote(Course course, CourseNote note) {
        note.setCourseId(course.getId());
        note.setId(courseNoteDAO.insert(note));
    }

    /**
     * Delete the provided note
     *
     * @param note The CourseNote entity
     */
    public void deleteNote(CourseNote note) {
        courseNoteDAO.delete(note);
    }

    /**
     * Add a note and attach to a course
     *
     * @param course The Course entity
     * @param assessment The CourseAssessment entity
     */
    public void addAssessment(Course course, CourseAssessment assessment) {
        assessment.setCourseId(course.getId());
        assessment.setId(courseAssessmentDAO.insert(assessment));
    }

    /**
     * Delete the provided assessment
     *
     * @param assessment The CourseAssessment entity
     */
    public void deleteAssessment(CourseAssessment assessment) {
        courseAssessmentDAO.delete(assessment);
    }
}
