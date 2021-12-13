package android.wgunn.collegeschedulelite.Database;

import android.app.Application;
import android.wgunn.collegeschedulelite.DAO.TermDAO;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.Entity.TermWithCourses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The TermRepository handles all data operations for the term entity
 */
public class TermRepository {

    /**
     * The TermDAO object
     */
    private final TermDAO termDAO;

    /**
     * Receives the current application context and initializes the termDAO.
     * @param application
     */
    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        termDAO = db.termDAO();;
    }

    /**
     * Get a term entity by ID
     *
     * @param id The term ID
     * @return The selected term or null
     */
    public TermEntity get(Long id) {
        return termDAO.load(id);
    }

    /**
     * Get a term entity by name
     *
     * @param name The term name
     * @return The selected term or null
     */
    public TermEntity getByName(String name) {
        return termDAO.loadByName(name);
    }

    /**
     * A convenience method to find and return the current term
     *
     * @return The current term or null
     */
    public TermEntity getCurrentTerm() {

        Date now = new Date();

        for (TermEntity term : termDAO.loadAll()) {
            if (now.after(term.getStartDate()) && now.before(term.getEndDate())) {
                return term;
            }
        }

        return null;
    }

    /**
     * A convenience method to find and return all remaining / future terms
     *
     * @return The list of remaining terms
     */
    public List<TermEntity> getRemainingTerms() {

        List<TermEntity> terms = new ArrayList<>();

        Date now = new Date();

        for (TermEntity term : termDAO.loadAll()) {
            if (term.getStartDate().after(now)) {
                terms.add(term);
            }
        }

        return terms;
    }

    /**
     * A convenience method to find and return all completed / terms
     *
     * @return The list of completed terms
     */
    public List<TermEntity> getCompletedTerms() {

        List<TermEntity> terms = new ArrayList<>();

        Date now = new Date();

        for (TermEntity term : termDAO.loadAll()) {
            if (term.getEndDate().before(now)) {
                terms.add(term);
            }
        }

        return terms;
    }

    /**
     * Fetches a term by ID and all related courses. The entities are packaged and returned in
     * a TermWithCourses data object.
     *
     * @param id
     * @return The selected term with related courses
     */
    public TermWithCourses getWithCourses(long id) {
        return termDAO.loadWithCourses(id);
    }

    /**
     * Fetch and return all terms
     *
     * @return List of terms
     */
    public List<TermEntity> getAll() {
        return termDAO.loadAll();
    }

    /**
     * Fetch and return all terms and related courses
     *
     * @return List of TermWithCourses data objects
     */
    public List<TermWithCourses> getAllWithCourses() {
        return termDAO.loadAllWithCourses();
    }

    /**
     * <pre></pre>
     * Saves the provided term doing an insert or update as needed.
     *
     * If an insert is done, the entity ID is set from the return value.
     * </pre>
     *
     * @param term
     */
    public void save(TermEntity term) {
        if (term.getId() == null) {
            term.setId(termDAO.insert(term));
        } else {
            termDAO.update(term);
        }
    }

    /**
     * Delete the provided term
     * @param term
     */
    public void delete(TermEntity term) {
        termDAO.delete(term);
    }
}
