package android.wgunn.collegeschedulelite.Database;

import android.content.Context;
import android.wgunn.collegeschedulelite.DAO.CourseAssessmentDAO;
import android.wgunn.collegeschedulelite.DAO.CourseDAO;
import android.wgunn.collegeschedulelite.DAO.CourseNoteDAO;
import android.wgunn.collegeschedulelite.DAO.TermDAO;
import android.wgunn.collegeschedulelite.Entity.Course;
import android.wgunn.collegeschedulelite.Entity.CourseAssessment;
import android.wgunn.collegeschedulelite.Entity.CourseNote;
import android.wgunn.collegeschedulelite.Entity.Term;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Term.class, Course.class, CourseAssessment.class, CourseNote.class}, version = 5, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_Name = "CSL.db";

    private static volatile AppDatabase instance;

    public abstract TermDAO termDAO();

    public abstract CourseDAO courseDAO();

    public abstract CourseAssessmentDAO courseAssessmentDAO();

    public abstract CourseNoteDAO courseNoteDAO();

    public static synchronized AppDatabase getInstance(final Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_Name)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
