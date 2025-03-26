package com.grv.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grv.vo.Vizinho;

import java.util.List;

@Dao
public interface VizinhoDAO {

    @Insert
    long insert(Vizinho vizinho);

    @Delete
    int delete(Vizinho vizinho);

    @Update
    int update(Vizinho vizinho);

    @Query("SELECT * FROM vizinho WHERE id=:id")
    Vizinho queryForId(long id);

    @Query("SELECT * FROM vizinho")
    List<Vizinho> queryAll();
}
