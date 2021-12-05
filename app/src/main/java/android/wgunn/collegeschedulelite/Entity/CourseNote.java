package android.wgunn.collegeschedulelite.Entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "course_notes",
    foreignKeys = @ForeignKey(
            entity = Course.class,
            parentColumns = "id",
            childColumns = "course_id",
            onDelete = CASCADE
    ),
    indices = {@Index(value = {"course_id"})}
)
public class CourseNote {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "course_id")
    private Long courseId;

    @ColumnInfo(name = "note")
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return note;
    }
}
