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

/**
 * <pre>
 * termDAO interface
 *
 * This interface defines the standard CRUD operations to be
 * performed on the Term entity
 * </pre>
 */
@Dao
public interface TermDAO {

    /**
     * Insert a term entity into the database
     *
     * @param term  The Term entity
     * @return The inserted record ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Term term);

    /**
     * Update a term database reccord
     *
     * @param term  The Term entity
     */
    @Update
    void update(Term term);

    /**
     * Delete a term database record
     *
     * @param term  The Term entity
     */
    @Delete
    void delete(Term term);

    /**
     * Select a term record from the database by ID
     *
     * @param id  The term ID
     * @return The selected term entity
     */
    @Query("SELECT * FROM terms WHERE id = :id")
    Term load(Long id);

    /**
     * <pre>
     * Select a term record from the database by ID and additionally select
     * and include all related course records.
     *
     * This method requires Room to run two queries, so we add the @Transaction annotation to this
     * method to ensure that the whole operation is performed atomically.
     * </pre>
     *
     * @param id  The term ID
     * @return The TermWithCourses object
     */
    @Transaction
    @Query("SELECT * FROM terms WHERE id = :id")
    TermWithCourses loadWithCourses(Long id);

    /**
     * Select all term records from the database
     *
     * @return List of term entities
     */
    @Query("SELECT * FROM terms")
    List<Term> loadAll();

    /**
     * <pre>
     * Select all term records from the database and additionally select
     * and include all related course records.
     *
     * This method requires Room to run two queries, so we add the @Transaction annotation to this
     * method to ensure that the whole operation is performed atomically.
     * </pre>
     *
     * @return List of TermWithCourses objects
     */
    @Transaction
    @Query("SELECT * FROM terms")
    List<TermWithCourses> loadAllWithCourses();
}
