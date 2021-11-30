package android.wgunn.collegeschedulelite.DAO;

import android.wgunn.collegeschedulelite.Entity.Course;
import android.wgunn.collegeschedulelite.Entity.CourseWithNotes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Course course);

    @Update
    public void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses WHERE id = :id")
    Course load(long id);

    @Transaction
    @Query("SELECT * FROM courses WHERE id = :id")
    CourseWithNotes loadWithNotes(Long id);

    @Query("SELECT * FROM courses")
    List<Course> loadAll();

    @Transaction
    @Query("SELECT * FROM courses")
    public List<CourseWithNotes> loadAllWithNotes();
}
