package android.wgunn.collegeschedulelite.DAO;

import android.wgunn.collegeschedulelite.Entity.CourseNote;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseNoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CourseNote courseNote);

    @Update
    public void update(CourseNote courseNote);

    @Delete
    void delete(CourseNote courseNote);

    @Query("SELECT * FROM course_notes")
    List<CourseNote> getAll();
}
