package com.androidbasicstructure.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.androidbasicstructure.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User... repos);

    @Insert
    void insert(User repo);

    @Insert
    void insert(List<User> repoList);

    @Update
    void update(User... repos);

    @Delete
    void delete(User... repos);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE id=:id")
    User getUser(int id);

    @Query("SELECT * FROM user")
    Cursor getUserCursor();

    @Query("SELECT * FROM user WHERE name=:name")
    List<User> getUsersByName(String name);

    @Query("SELECT * FROM user WHERE name=:name LIMIT :max")
    List<User> getUsersByName(int max, String... name);

}