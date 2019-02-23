package kimani.com.sisu.repositories;

import android.app.Application;
import android.os.AsyncTask;

import kimani.com.sisu.AuthActivity;
import kimani.com.sisu.MainActivity;
import kimani.com.sisu.dao.AssessmentDao;
import kimani.com.sisu.dao.FlightsDao;
import kimani.com.sisu.utils.SisuDatabase;

public class AuthRepository {

    private FlightsDao flightsDao;
    private AssessmentDao assessmentDao;


    public AuthRepository(Application application){
        SisuDatabase sisuDatabase = SisuDatabase.getDatabase(application);
        flightsDao = sisuDatabase.flightsDao();
        assessmentDao = sisuDatabase.assessmentDao();
    }

    public void logout(){
        new LogoutAsync(flightsDao,assessmentDao).execute();
    }

    public static class LogoutAsync extends AsyncTask<Void,Void,Void> {

        private FlightsDao flightsDao;
        private AssessmentDao assessmentDao;

        public LogoutAsync(FlightsDao flightsDao,AssessmentDao assessmentDao){

            this.flightsDao = flightsDao;
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            flightsDao.deleteAll();
            assessmentDao.deleteAllAssesmentQuestions();
            assessmentDao.deleteAllAssessment();

            MainActivity.logout.postValue(true);


            return null;
        }
    }
}
