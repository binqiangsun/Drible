/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sunbinqiang.drible.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.sunbinqiang.drible.db.DribleDatabase.DATABASE_NAME;

/**
 * Creates the {@link DribleDatabase} asynchronously, exposing a LiveData object to notify of creation.
 */
public class DatabaseCreator {

    private static DatabaseCreator sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private DribleDatabase mDb;

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    public synchronized static DatabaseCreator getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new DatabaseCreator();
                }
            }
        }
        return sInstance;
    }

    /** Used to observe when the database initialization is done */
    public LiveData<Boolean> isDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    @Nullable
    public DribleDatabase getDatabase() {
        return mDb;
    }

    /**
     * Creates or returns a previously-created database.
     * <p>
     * Although this uses an AsyncTask which currently uses a serial executor, it's thread-safe.
     */
    public void createDb(Context context) {

        if (mDb != null && mIsDatabaseCreated.getValue() != null && mIsDatabaseCreated.getValue()) {
            return; // Already initializing
        }

        mIsDatabaseCreated.setValue(false);// Trigger an update to show a loading screen.
        new AsyncTask<Context, Void, Void>() {

            @Override
            protected Void doInBackground(Context... params) {
                Log.d("DatabaseCreator",
                        "Starting bg job " + Thread.currentThread().getName());

                Context context = params[0].getApplicationContext();

                // Reset the database to have new data on every run.
                context.deleteDatabase(DATABASE_NAME);

                // Build the database!
                DribleDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                        DribleDatabase.class, DATABASE_NAME).build();

                //清理过期数据

                // Add some data to the database
                Log.d("DatabaseCreator",
                        "DB was populated in thread " + Thread.currentThread().getName());
                mDb = db;
                return null;
            }

            @Override
            protected void onPostExecute(Void ignored) {
                // Now on the main thread, notify observers that the db is created and ready.
                mIsDatabaseCreated.setValue(true);
            }
        }.execute(context.getApplicationContext());
    }

}
