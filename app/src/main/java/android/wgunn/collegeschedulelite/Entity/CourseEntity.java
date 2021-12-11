package android.wgunn.collegeschedulelite.Entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The CourseEntity entity models a row in the courses table
 */
@Entity(
        tableName = "courses",
        foreignKeys = @ForeignKey(
                entity = TermEntity.class,
                parentColumns = "id",
                childColumns = "term_id",
                onDelete = CASCADE
        ),
        indices = {@Index(value = {"term_id"})}
)
public class CourseEntity {

    /**
     * The autogenerated primary key
     */
    @PrimaryKey(autoGenerate = true)
    private Long id;

    /**
     * The parent term ID
     */
    @ColumnInfo(name = "term_id")
    private Long termId;

    /**
     * The course title
     */
    @ColumnInfo(name = "title")
    private String title;

    /**
     * The course start date
     */
    @ColumnInfo(name = "start_date")
    private Date startDate;

    /**
     * The course end date
     */
    @ColumnInfo(name = "end_date")
    private Date endDate;

    /**
     * The course status
     */
    @ColumnInfo(name = "status")
    private String status;

    /**
     * The course instructor's name
     */
    @ColumnInfo(name = "instructor_name")
    private String instructorName;

    /**
     * The course instructor's phone number
     */
    @ColumnInfo(name = "instructor_phone")
    private String instructorPhone;

    /**
     * The course instructor's email
     */
    @ColumnInfo(name = "instructor_email")
    private String instructorEmail;

    public CourseEntity(long termId, String title, Date startDate, Date endDate, String status, String instructorName, String instructorPhone, String instructorEmail) {
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
    }

    /**
     * The course ID getter
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * The course ID setter
     * @param id The auto generated course ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * The term ID getter
     * @return term_id
     */
    public Long getTermId() {
        return termId;
    }

    /**
     * The term ID setter
     * @param termId The related term ID
     */
    public void setTermId(Long termId) {
        this.termId = termId;
    }

    /**
     * The course title getter
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The course title setter
     * @param title The course title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The start date getter
     * @return startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * The start date setter
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * The end date getter
     * @return endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * The end date setter
     * @param endDate The course end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * The status getter
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * The status setter
     * @param status The course status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * The instructor name getter
     * @return instructor_name
     */
    public String getInstructorName() {
        return instructorName;
    }

    /**
     * The instructor name setter
     * @param instructorName The course instructor's name
     */
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    /**
     * The instructor's phone getter
     * @return instructor_phone
     */
    public String getInstructorPhone() {
        return instructorPhone;
    }

    /**
     * The instructor's phone setter
     * @param instructorPhone The course instructor's phone number
     */
    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    /**
     * The instructor's email getter
     * @return instructor_email
     */
    public String getInstructorEmail() {
        return instructorEmail;
    }

    /**
     * The instructor's email setter
     * @param instructorEmail The course instructor's email
     */
    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = status.substring(0, 1).toUpperCase() + status.substring(1);
        // sdf.format(startDate)
        return String.format("%-35s %30s", title, s);
    }
}
