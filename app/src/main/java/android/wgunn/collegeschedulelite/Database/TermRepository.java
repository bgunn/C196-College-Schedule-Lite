package android.wgunn.collegeschedulelite.Database;

import android.app.Application;
import android.wgunn.collegeschedulelite.DAO.TermDAO;
import android.wgunn.collegeschedulelite.Entity.Term;
import android.wgunn.collegeschedulelite.Entity.TermWithCourses;

import java.util.List;

public class TermRepository {

    private final TermDAO termDAO;

    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        termDAO = db.termDAO();;
    }

    public Term get(Long id) {
        return termDAO.load(id);
    }

    public TermWithCourses getWithCourses(long id) {
        return termDAO.loadWithCourses(id);
    }

    public List<Term> getAll() {
        return termDAO.loadAll();
    }

    public List<TermWithCourses> getAllWithCourses() {
        return termDAO.loadAllWithCourses();
    }

    public void save(Term term) {
        if (term.getId() == null) {
            term.setId(termDAO.insert(term));
        } else {
            termDAO.update(term);
        }
    }

    public void delete(Term term) {
        termDAO.delete(term);
    }
}
