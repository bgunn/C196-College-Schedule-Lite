package android.wgunn.collegeschedulelite.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * This data class models the one to many relationship between courses and notes
 *
 * A CourseWithNotes instance holds an instance of the course entity and a list of
 * all corresponding course note entity instances
 * </pre>
 */
public class CourseWithNotes {

    @Embedded
    public Course course;

    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    public List<CourseNote> notes;
}
