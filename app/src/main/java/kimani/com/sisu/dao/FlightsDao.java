package kimani.com.sisu.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import kimani.com.sisu.models.ComposedFlight;
import kimani.com.sisu.models.Flight;


@Dao
public interface FlightsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Flight user);

    @Query("DELETE FROM flights_table")
    void deleteAll();

    @Query("SELECT * from flights_table")
    LiveData<List<ComposedFlight>> getAllFlights();
}
