package android.wgunn.collegeschedulelite.Entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Entity(
        tableName = "courses",
        foreignKeys = @ForeignKey(
                entity = Term.class,
                parentColumns = "id",
                childColumns = "term_id",
                onDelete = CASCADE
        ),
        indices = {@Index(value = {"term_id"})}
)
public class Course {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "term_id")
    private Long termId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    @ColumnInfo(name = "end_date")
    private Date endDate;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "instructor_name")
    private String instructorName;

    @ColumnInfo(name = "instructor_phone")
    private String instructorPhone;

    @ColumnInfo(name = "instructor_email")
    private String instructorEmail;

    public Course(long termId, String title, Date startDate, Date endDate, String status, String instructorName, String instructorPhone, String instructorEmail) {

        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", instructorName='" + instructorName + '\'' +
                '}';
    }
}
