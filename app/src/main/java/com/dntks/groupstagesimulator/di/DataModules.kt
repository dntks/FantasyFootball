package com.dntks.groupstagesimulator.di

import android.content.Context
import androidx.room.Room
import com.dntks.groupstagesimulator.data.db.GroupStageDatabase
import com.dntks.groupstagesimulator.data.db.dao.GroupDao
import com.dntks.groupstagesimulator.data.db.dao.GroupStageMatchDao
import com.dntks.groupstagesimulator.data.db.dao.PlayerDao
import com.dntks.groupstagesimulator.data.db.dao.RoundDao
import com.dntks.groupstagesimulator.data.db.dao.TeamDao
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import com.dntks.groupstagesimulator.data.repository.GroupStageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for providing Database and DAOs
 */
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
            .createFromAsset("db/GroupStageDatabase.db")
            .build()
    }

    @Provides
    fun provideGroupStageDao(database: GroupStageDatabase): GroupDao = database.groupDao()

    @Provides
    fun provideTeamDao(database: GroupStageDatabase): TeamDao = database.teamDao()

    @Provides
    fun provideGroupStageMatchDao(database: GroupStageDatabase): GroupStageMatchDao =
        database.groupStageMatchDao()

    @Provides
    fun providePlayerDao(database: GroupStageDatabase): PlayerDao = database.playerDao()

    @Provides
    fun provideRoundDao(database: GroupStageDatabase): RoundDao = database.roundDao()

    @Provides
    fun provideGroupStageRepository(
        groupDao: GroupDao,
        groupStageMatchDao: GroupStageMatchDao,
        roundDao: RoundDao,
        teamDao: TeamDao,
    ): GroupStageRepository =
        GroupStageRepositoryImpl(
            groupStageMatchDao,
            teamDao,
            groupDao,
            roundDao
        )
}
