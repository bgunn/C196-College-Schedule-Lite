package android.wgunn.collegeschedulelite.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TermWithCourses {

    @Embedded
    public Term term;

    @Relation(
            parentColumn = "id",
            entityColumn = "term_id"
    )
    public List<Course> courses;
}
