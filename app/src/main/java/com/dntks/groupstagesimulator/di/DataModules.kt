package com.dntks.groupstagesimulator.di

import android.content.Context
import androidx.room.Room
import com.dntks.groupstagesimulator.data.db.GroupStageDatabase
import com.dntks.groupstagesimulator.data.db.dao.GroupDao
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import com.dntks.groupstagesimulator.data.repository.GroupStageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): GroupStageDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GroupStageDatabase::class.java,
            "GroupStageDatabase.db"
        )
//            .createFromAsset("db/GroupStageDatabase_db.db")
            .build()
    }

    @Provides
    fun provideGroupStageDao(database: GroupStageDatabase): GroupDao = database.groupDao()
    @Provides
    fun provideGroupStageRepository(groupStageDao: GroupDao): GroupStageRepository =
        GroupStageRepositoryImpl(groupStageDao)

}
