package com.grv.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.grv.vo.Vizinho;

@Database(entities = {Vizinho.class}, version = 1)
public abstract class VizinhosDatabase extends RoomDatabase {

    private static VizinhosDatabase INSTANCE;

    public abstract VizinhoDAO getVizinhoDAO();

    public static VizinhosDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (VizinhosDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            VizinhosDatabase.class,
                            "pessoas.db"
                    ).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
