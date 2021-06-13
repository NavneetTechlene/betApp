package tl.betapp.view.utils;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tl.betapp.view.model.SportModelRoom;


@Database(entities ={SportModelRoom.class},exportSchema=false,version =2)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME="user_db";
    private static UserDatabase userDatabase;


    public static synchronized  UserDatabase getInstance(Context context)
    {
       if (userDatabase==null)
       {
           userDatabase= Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,DB_NAME)
                   .fallbackToDestructiveMigration().build();
       }
       return userDatabase;

    }
    public abstract UserDao userDao();


}
