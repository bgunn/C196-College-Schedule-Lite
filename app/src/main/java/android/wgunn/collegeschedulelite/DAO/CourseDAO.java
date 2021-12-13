package android.wgunn.collegeschedulelite.DAO;

import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.CourseWithChildren;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

/**
 * <pre>
 * courseDAO interface
 *
 * This interface defines the standard CRUD operations to be
 * performed on the CourseEntity entity
 * </pre>
 */
@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CourseEntity course);

    @Update
    public void update(CourseEntity course);

    @Delete
    void delete(CourseEntity course);

    @Query("SELECT * FROM courses WHERE id = :id")
    CourseEntity load(long id);

    @Transaction
    @Query("SELECT * FROM courses WHERE id = :id")
    CourseWithChildren loadWithChildren(Long id);

    @Query("SELECT * FROM courses ORDER BY start_date ASC")
    List<CourseEntity> loadAll();

    @Transaction
    @Query("SELECT * FROM courses ORDER BY start_date ASC")
    public List<CourseWithChildren> loadAllWithChildren();
}
