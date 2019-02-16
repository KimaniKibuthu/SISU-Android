package kimani.com.sisu.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import kimani.com.sisu.dao.AssessmentDao;
import kimani.com.sisu.dao.CategoriesDao;
import kimani.com.sisu.dao.FlightsDao;
import kimani.com.sisu.dao.UsersDao;
import kimani.com.sisu.models.Assess.AssessmentQuestion;
import kimani.com.sisu.models.Assess.FlightAssessment;
import kimani.com.sisu.models.Assess.Question;
import kimani.com.sisu.models.Authorization;
import kimani.com.sisu.models.Category;
import kimani.com.sisu.models.Flight;
import kimani.com.sisu.models.User;


@Database(entities = {Authorization.class, User.class, Category.class, Flight.class, AssessmentQuestion.class, FlightAssessment.class, Question.class}, version = 1)
public abstract class SisuDatabase extends RoomDatabase {

    public abstract UsersDao usersDao();
    public abstract FlightsDao flightsDao();
    public abstract AssessmentDao assessmentDao();
    public abstract CategoriesDao categoriesDao();

    private static SisuDatabase INSTANCE;

    public static SisuDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SisuDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SisuDatabase.class, "sisu_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
