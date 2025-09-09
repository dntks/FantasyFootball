package com.dntks.groupstagesimulator.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dntks.groupstagesimulator.data.db.dao.GroupDao
import com.dntks.groupstagesimulator.data.db.dao.GroupStageMatchDao
import com.dntks.groupstagesimulator.data.db.dao.PlayerDao
import com.dntks.groupstagesimulator.data.db.dao.RoundDao
import com.dntks.groupstagesimulator.data.db.dao.TeamDao
import com.dntks.groupstagesimulator.data.db.entity.GroupEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseMatchEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseRoundEntity
import com.dntks.groupstagesimulator.data.db.entity.PlayerEntity
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity

@Database(
    entities = [
        GroupPhaseMatchEntity::class,
        GroupPhaseRoundEntity::class,
        TeamEntity::class,
        PlayerEntity::class,
        GroupEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GroupStageDatabase : RoomDatabase() {

    abstract fun groupStageMatchDao(): GroupStageMatchDao
    abstract fun groupDao(): GroupDao
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao
    abstract fun roundDao(): RoundDao
}