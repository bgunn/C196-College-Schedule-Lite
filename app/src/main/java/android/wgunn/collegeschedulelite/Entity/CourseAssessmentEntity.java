package android.wgunn.collegeschedulelite.Entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The CourseAssessmentEntity entity models a row in the course_assessments table. The CourseAssessmentEntity
 * represents a one-to-many relationship with a CourseEntity.
 */
@Entity(
    tableName = "course_assessments",
    foreignKeys = @ForeignKey(
            entity = CourseEntity.class,
            parentColumns = "id",
            childColumns = "course_id",
            onDelete = CASCADE
    ),
    indices = {@Index(value = {"course_id"})}
)
public class CourseAssessmentEntity {

    /**
     * The autogenerated primary key
     */
    @PrimaryKey(autoGenerate = true)
    private Long id;

    /**
     * The parent course ID
     */
    @ColumnInfo(name = "course_id")
    private Long courseId;

    /**
     * The assessment title
     */
    @ColumnInfo(name = "title")
    private String title;

    /**
     * The assessment type (performance or objective)
     */
    @ColumnInfo(name = "type")
    private String type;

    /**
     * The assessment start date
     */
    @ColumnInfo(name = "start_date")
    private Date startDate;

    /**
     * The assessment end date
     */
    @ColumnInfo(name = "end_date")
    private Date endDate;

    public CourseAssessmentEntity(Long courseId, String title, String type, Date startDate, Date endDate) {
        this.courseId = courseId;
        this.title = title;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * The course asessment ID getter
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * The course assessment ID setter
     * @param id The autogenerated ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * The parent course ID getter
     * @return courseId
     */
    public Long getCourseId() {
        return courseId;
    }

    /**
     * The parent course ID setter
     * @param courseId The parent course ID
     */
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    /**
     * The assessment title getter
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The assessment title setter
     * @param title The course title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The assessment type getter
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * The assessment type setter
     * @param type The assessment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The date getter
     * @return startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * The date setter
     * @param startDate The assessment date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * The date / time getter
     * @return endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * The date setter
     * @param endDate The assessment date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return title + " - " + type + " - " + formatter.format(startDate);
    }
}
