package android.wgunn.collegeschedulelite.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

public class CourseWithNotes {

    @Embedded
    public Course course;

    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    public List<CourseNote> notes;
}
