package android.wgunn.collegeschedulelite.Database;

import android.app.Application;
import android.wgunn.collegeschedulelite.DAO.CourseDAO;
import android.wgunn.collegeschedulelite.DAO.CourseNoteDAO;
import android.wgunn.collegeschedulelite.Entity.Course;
import android.wgunn.collegeschedulelite.Entity.CourseNote;
import android.wgunn.collegeschedulelite.Entity.CourseWithNotes;

import java.util.List;

public class CourseRepository {

    private final CourseDAO courseDAO;
    private final CourseNoteDAO courseNoteDAO;

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        courseDAO = db.courseDAO();
        courseNoteDAO = db.courseNoteDAO();
    }

    public Course get(Long id) {
        return courseDAO.load(id);
    }

    public CourseWithNotes getWithNotes(Long id) {
        return courseDAO.loadWithNotes(id);
    }

    public List<Course> getAll() {
        return courseDAO.loadAll();
    }

    public List<CourseWithNotes> getAllWithNotes() {
        return courseDAO.loadAllWithNotes();
    }

    public void save(Course course) {
        if (course.getId() == null) {
            course.setId(courseDAO.insert(course));
        } else {
            courseDAO.update(course);
        }
    }

    public void delete(Course course) {
        courseDAO.delete(course);
    }

    public void addNote(Course course, CourseNote note) {
        note.setCourseId(course.getId());
        note.setId(courseNoteDAO.insert(note));
    }

    public void deleteNote(CourseNote note) {
        courseNoteDAO.delete(note);
    }
}
