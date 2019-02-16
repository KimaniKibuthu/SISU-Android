package kimani.com.sisu.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import kimani.com.sisu.models.Category;
import kimani.com.sisu.models.User;

@Dao
public interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Query("DELETE FROM categories_table")
    void deleteAll();

    @Query("SELECT * from categories_table")
    LiveData<List<Category>> getAllCategories();
}
