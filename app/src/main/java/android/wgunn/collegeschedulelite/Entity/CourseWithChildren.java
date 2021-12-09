package android.wgunn.collegeschedulelite.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * <pre>
 * This data class models the one to many relationship between courses assessments and notes
 *
 * A CourseWithChildren instance holds an instance of the course entity and a list of
 * all corresponding assessment and note entities
 * </pre>
 */
public class CourseWithChildren {

    @Embedded
    public CourseEntity course;

    @Relation(
            parentColumn = "id",
            entityColumn = "course_id"
    )
    public List<CourseAssessment> assessments;

    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    public List<CourseNoteEntity> notes;
}
