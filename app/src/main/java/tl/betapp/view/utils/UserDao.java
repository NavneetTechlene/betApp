package tl.betapp.view.utils;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tl.betapp.view.model.SportModelRoom;

@Dao
public interface UserDao {


    @Query("Select * from sportModel")
    List<SportModelRoom> getList();


    @Insert
    void insertSport(SportModelRoom sportModelRoom);


    @Update
    void updateSport(SportModelRoom sportModelRoom);

   @Delete
    void deleteSport(SportModelRoom sportModelRoom);

}
