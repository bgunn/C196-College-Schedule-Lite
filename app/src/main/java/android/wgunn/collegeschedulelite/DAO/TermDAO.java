package android.wgunn.collegeschedulelite.DAO;

import android.wgunn.collegeschedulelite.Entity.TermEntity;
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
 * performed on the TermEntity entity
 * </pre>
 */
@Dao
public interface TermDAO {

    /**
     * Insert a term entity into the database
     *
     * @param term  The TermEntity entity
     * @return The inserted record ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TermEntity term);

    /**
     * Update a term database reccord
     *
     * @param term  The TermEntity entity
     */
    @Update
    void update(TermEntity term);

    /**
     * Delete a term database record
     *
     * @param term  The TermEntity entity
     */
    @Delete
    void delete(TermEntity term);

    /**
     * Select a term record from the database by ID
     *
     * @param id  The term ID
     * @return The selected term entity
     */
    @Query("SELECT * FROM terms WHERE id = :id")
    TermEntity load(Long id);

    /**
     * Select a term record from the database by name
     *
     * @param name  The term name
     * @return The selected term entity
     */
    @Query("SELECT * FROM terms WHERE name = :name")
    TermEntity loadByName(String name);

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
    @Query("SELECT * FROM terms ORDER BY start_date ASC")
    List<TermEntity> loadAll();

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
    @Query("SELECT * FROM terms ORDER BY start_date ASC")
    List<TermWithCourses> loadAllWithCourses();
}
