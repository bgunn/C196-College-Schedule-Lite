package android.wgunn.collegeschedulelite.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * <pre>
 * This data class models the one to many relationship between terms and courses
 *
 * A TermWithCourses instance holds an instance of the term entity and a list of
 * all corresponding course entity instances
 * </pre>
 */
public class TermWithCourses {

    @Embedded
    public TermEntity term;

    @Relation(
            parentColumn = "id",
            entityColumn = "term_id"
    )
    public List<CourseEntity> courses;
}
