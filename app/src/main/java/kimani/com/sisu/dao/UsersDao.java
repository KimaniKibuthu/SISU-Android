package kimani.com.sisu.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import kimani.com.sisu.models.User;

@Dao
public interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM users_table")
    void deleteAll();

    @Query("SELECT * from users_table WHERE id=:id")
    LiveData<User> getUserById(int id);

}
