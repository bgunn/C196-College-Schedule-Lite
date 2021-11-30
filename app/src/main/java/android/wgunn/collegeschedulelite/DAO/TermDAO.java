package android.wgunn.collegeschedulelite.DAO;

import android.wgunn.collegeschedulelite.Entity.Term;
import android.wgunn.collegeschedulelite.Entity.TermWithCourses;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM terms WHERE id = :id")
    Term load(long id);

    @Transaction
    @Query("SELECT * FROM terms WHERE id = :id")
    TermWithCourses loadWithCourses(long id);

    @Query("SELECT * FROM terms")
    List<Term> loadAll();

    @Transaction
    @Query("SELECT * FROM terms")
    List<TermWithCourses> loadAllWithCourses();
}
