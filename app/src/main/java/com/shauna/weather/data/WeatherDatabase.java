package com.shauna.weather.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.shauna.weather.data.converter.ListWeatherConverter;
import com.shauna.weather.data.dao.WeatherDao;
import com.shauna.weather.data.entity.DBWeather;

import static com.shauna.weather.data.DatabaseConstantsKt.DATABASE_NAME;
import static com.shauna.weather.data.DatabaseConstantsKt.DB_WEATHER_CITY_ID;
import static com.shauna.weather.data.DatabaseConstantsKt.DB_WEATHER_CITY_NAME;
import static com.shauna.weather.data.DatabaseConstantsKt.DB_WEATHER_CONDITION;
import static com.shauna.weather.data.DatabaseConstantsKt.DB_WEATHER_DETAILS;
import static com.shauna.weather.data.DatabaseConstantsKt.DB_WEATHER_TABLE_NAME;
import static com.shauna.weather.data.DatabaseConstantsKt.DB_WEATHER_UNIQUE_ID;
import static com.shauna.weather.data.DatabaseConstantsKt.DB_WEATHER_WIND;

@Database(entities = {DBWeather.class}, version = 1, exportSchema = false)

@TypeConverters(ListWeatherConverter.class)
public abstract class WeatherDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static WeatherDatabase INSTANCE;

    public static WeatherDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, DATABASE_NAME).addMigrations(MIGRATION_1_2).build();
            }
        }

        return INSTANCE;
    }

    public abstract WeatherDao weatherDao();

    private static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE " + DB_WEATHER_TABLE_NAME +
                    "(" + DB_WEATHER_UNIQUE_ID + " INTEGER NOT NULL, " +
                    DB_WEATHER_CITY_ID + " INTEGER NOT NULL, " +
                    DB_WEATHER_CITY_NAME + " TEXT NOT NULL, " +
                    DB_WEATHER_DETAILS + " TEXT NOT NULL, " +
                    DB_WEATHER_WIND + " TEXT NOT NULL, " +
                    DB_WEATHER_CONDITION + " TEXT NOT NULL, " +
                    "PRIMARY KEY(" + DB_WEATHER_UNIQUE_ID + ")) "
            );
        }
    };

}
