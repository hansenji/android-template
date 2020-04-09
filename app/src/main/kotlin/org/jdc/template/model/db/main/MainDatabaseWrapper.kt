package org.jdc.template.model.db.main

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.dbtools.android.room.CloseableDatabaseWrapper
import org.dbtools.android.room.sqliteorg.SqliteOrgSQLiteOpenHelperFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MainDatabaseWrapper
@Inject constructor(
        application: Application
) : CloseableDatabaseWrapper<MainDatabase>(application) {

    override fun createDatabase(): MainDatabase {
        return Room.databaseBuilder(application, MainDatabase::class.java, MainDatabase.DATABASE_FILENAME)
            .openHelperFactory(SqliteOrgSQLiteOpenHelperFactory())
                .addMigrations(object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // ONLY views are changed

                        // drop and recreate views
                        recreateAllViews(database, MainDatabase.DATABASE_VIEW_QUERIES)
                    }
                })
                .addMigrations(object : Migration(2, 3) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // BOTH views and tables are changed

                        // drop views
                        dropAllViews(database, MainDatabase.DATABASE_VIEW_QUERIES)

                        // do other database migrations here

                        // recreate views
                        createAllViews(database, MainDatabase.DATABASE_VIEW_QUERIES)
                    }
                })
                .build()
    }
}