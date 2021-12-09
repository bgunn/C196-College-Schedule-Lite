package android.wgunn.collegeschedulelite.DAO;

import android.wgunn.collegeschedulelite.Entity.CourseAssessment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * <pre>
 * courseAssessmentDAO interface
 *
 * This interface defines the standard CRUD operations to be
 * performed on the CourseAssessment entity
 * </pre>
 */
@Dao
public interface CourseAssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CourseAssessment courseAssessment);

    @Update
    public void update(CourseAssessment courseAssessment);

    @Delete
    void delete(CourseAssessment courseAssessment);

    @Query("SELECT * FROM course_assessments")
    List<CourseAssessment> getAll();
}
