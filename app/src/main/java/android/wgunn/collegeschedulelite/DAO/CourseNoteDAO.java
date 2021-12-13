package android.wgunn.collegeschedulelite.DAO;

import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.CourseNoteEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * <pre>
 * courseNoteDAO interface
 *
 * This interface defines the standard CRUD operations to be
 * performed on the CourseNoteEntity entity
 * </pre>
 */
@Dao
public interface CourseNoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CourseNoteEntity courseNote);

    @Update
    public void update(CourseNoteEntity courseNote);

    @Delete
    void delete(CourseNoteEntity courseNote);

    @Query("SELECT * FROM course_notes WHERE id = :id")
    CourseNoteEntity load(long id);

    @Query("SELECT * FROM course_notes")
    List<CourseNoteEntity> getAll();
}
