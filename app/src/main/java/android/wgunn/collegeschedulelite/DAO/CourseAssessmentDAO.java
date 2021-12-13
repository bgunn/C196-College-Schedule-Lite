package android.wgunn.collegeschedulelite.DAO;

import android.wgunn.collegeschedulelite.Entity.CourseAssessmentEntity;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;

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
 * performed on the CourseAssessmentEntity entity
 * </pre>
 */
@Dao
public interface CourseAssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CourseAssessmentEntity courseAssessmentEntity);

    @Update
    public void update(CourseAssessmentEntity courseAssessmentEntity);

    @Delete
    void delete(CourseAssessmentEntity courseAssessmentEntity);

    @Query("SELECT * FROM course_assessments WHERE id = :id")
    CourseAssessmentEntity load(long id);

    @Query("SELECT * FROM course_assessments")
    List<CourseAssessmentEntity> loadAll();
}
