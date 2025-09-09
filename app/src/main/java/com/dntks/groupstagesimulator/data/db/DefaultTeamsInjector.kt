package com.dntks.groupstagesimulator.data.db

import com.dntks.groupstagesimulator.data.db.dao.PlayerDao
import com.dntks.groupstagesimulator.data.db.dao.TeamDao
import com.dntks.groupstagesimulator.data.db.entity.PlayerEntity
import com.dntks.groupstagesimulator.data.db.entity.Position.DEFENDER
import com.dntks.groupstagesimulator.data.db.entity.Position.FORWARD
import com.dntks.groupstagesimulator.data.db.entity.Position.GOAL_KEEPER
import com.dntks.groupstagesimulator.data.db.entity.Position.MIDFIELDER
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTeamsInjector @Inject constructor(
    val teamDao: TeamDao,
    val playerDao: PlayerDao,
    @ApplicationScope
    val applicationScope: CoroutineScope
) {
    val defaultTeams = mapOf(
        TeamEntity(name = "Middle Earth") to listOf(
            PlayerEntity(name = "Tom Bombadil", position = GOAL_KEEPER),
            PlayerEntity(name = "Frodo Baggins", position = DEFENDER),
            PlayerEntity(name = "Bilbo Baggins", position = DEFENDER),
            PlayerEntity(name = "Peregrin Took", position = DEFENDER),
            PlayerEntity(name = "Samwise Gamgee", position = DEFENDER),
            PlayerEntity(name = "Gandalf", position = MIDFIELDER),
            PlayerEntity(name = "Gimli", position = MIDFIELDER),
            PlayerEntity(name = "Theoden", position = MIDFIELDER),
            PlayerEntity(name = "Merry Brandybuck", position = MIDFIELDER),
            PlayerEntity(name = "Legolas", position = FORWARD),
            PlayerEntity(name = "Aragorn", position = FORWARD),
        ),
        TeamEntity(name = "Asgard") to listOf(
            PlayerEntity(name = "Heimdall", position = GOAL_KEEPER),
            PlayerEntity(name = "Baldur", position = DEFENDER),
            PlayerEntity(name = "Forseti", position = DEFENDER),
            PlayerEntity(name = "Freya", position = DEFENDER),
            PlayerEntity(name = "Frigg", position = DEFENDER),
            PlayerEntity(name = "Magni", position = MIDFIELDER),
            PlayerEntity(name = "Njord", position = MIDFIELDER),
            PlayerEntity(name = "Odin", position = MIDFIELDER),
            PlayerEntity(name = "Loki", position = FORWARD),
            PlayerEntity(name = "Thor", position = FORWARD),
            PlayerEntity(name = "Tyr", position = FORWARD),
        ),
        TeamEntity(name = "Dune") to listOf(
            PlayerEntity(name = "Chani", position = GOAL_KEEPER),
            PlayerEntity(name = "Lady Jessica", position = DEFENDER),
            PlayerEntity(name = "Alia Atreides", position = DEFENDER),
            PlayerEntity(name = "Liet-Kynes", position = DEFENDER),
            PlayerEntity(name = "Princess Irulan", position = DEFENDER),
            PlayerEntity(name = "Vladimir Harkonnen", position = MIDFIELDER),
            PlayerEntity(name = "Wellington Yueh", position = MIDFIELDER),
            PlayerEntity(name = "Gurney Halleck", position = MIDFIELDER),
            PlayerEntity(name = "Duncan Idaho", position = FORWARD),
            PlayerEntity(name = "Stilgar", position = FORWARD),
            PlayerEntity(name = "Paul Atreides", position = FORWARD),
        ),
        TeamEntity(name = "The Office") to listOf(
            PlayerEntity(name = "Kevin Malone", position = GOAL_KEEPER),
            PlayerEntity(name = "Toby Flenderson", position = DEFENDER),
            PlayerEntity(name = "Ryan Howard", position = DEFENDER),
            PlayerEntity(name = "Kelly Kapoor", position = DEFENDER),
            PlayerEntity(name = "Darryl Philbin", position = DEFENDER),
            PlayerEntity(name = "Robert California", position = MIDFIELDER),
            PlayerEntity(name = "Oscar Martinez", position = MIDFIELDER),
            PlayerEntity(name = "Andrew Baines Bernard", position = MIDFIELDER),
            PlayerEntity(name = "Michael Gary Scott", position = FORWARD),
            PlayerEntity(name = "Jim Halpert", position = FORWARD),
            PlayerEntity(name = "Dwight Kurt Schrute", position = FORWARD),
        ),
        TeamEntity(name = "Hogwarts") to listOf(
            PlayerEntity(name = "Rubeus Hagrid", position = GOAL_KEEPER),
            PlayerEntity(name = "Remus John Lupin", position = DEFENDER),
            PlayerEntity(name = "Sirius Black", position = DEFENDER),
            PlayerEntity(name = "Arthur Weasley", position = DEFENDER),
            PlayerEntity(name = "Neville Longbottom", position = DEFENDER),
            PlayerEntity(name = "Dobby", position = MIDFIELDER),
            PlayerEntity(name = "Severus Snape", position = MIDFIELDER),
            PlayerEntity(name = "Albus Percival Wulfric Brian Dumbledore", position = MIDFIELDER),
            PlayerEntity(name = "Hermione Granger", position = FORWARD),
            PlayerEntity(name = "Ronald Bilius Weasley", position = FORWARD),
            PlayerEntity(name = "Harry James Potter", position = FORWARD),
        ),
        TeamEntity(name = "Westeros") to listOf(
            PlayerEntity(name = "Eddard \"Ned\" Stark", position = GOAL_KEEPER),
            PlayerEntity(name = "Tyrion Lannister", position = DEFENDER),
            PlayerEntity(name = "Stannis Baratheon", position = DEFENDER),
            PlayerEntity(name = "Samwell Tarly", position = DEFENDER),
            PlayerEntity(name = "Davos Seaworth", position = DEFENDER),
            PlayerEntity(name = "Theon Greyjoy", position = MIDFIELDER),
            PlayerEntity(name = "Daenerys Targaryen", position = MIDFIELDER),
            PlayerEntity(name = "Brienne of Tarth", position = MIDFIELDER),
            PlayerEntity(name = "Petyr Baelish ", position = FORWARD),
            PlayerEntity(name = "Jaime Lannister", position = FORWARD),
            PlayerEntity(name = "Jon Snow", position = FORWARD),
        ),
        TeamEntity(name = "Dragon Ball") to listOf(
            PlayerEntity(name = "Master Roshi", position = GOAL_KEEPER),
            PlayerEntity(name = "Son Gohan", position = DEFENDER),
            PlayerEntity(name = "Son Goten", position = DEFENDER),
            PlayerEntity(name = "Tien Shinhan", position = DEFENDER),
            PlayerEntity(name = "Yamcha", position = DEFENDER),
            PlayerEntity(name = "Frieza", position = MIDFIELDER),
            PlayerEntity(name = "Krillin", position = MIDFIELDER),
            PlayerEntity(name = "Trunks", position = MIDFIELDER),
            PlayerEntity(name = "Piccolo", position = FORWARD),
            PlayerEntity(name = "Vegeta", position = FORWARD),
            PlayerEntity(name = "Son Goku", position = FORWARD),
        ),
        TeamEntity(name = "Azeroth") to listOf(
            PlayerEntity(name = "Thrall", position = GOAL_KEEPER),
            PlayerEntity(name = "Malfurion Stormrage", position = DEFENDER),
            PlayerEntity(name = "Khadgar", position = DEFENDER),
            PlayerEntity(name = "Medivh", position = DEFENDER),
            PlayerEntity(name = "Uther the Lightbringer", position = DEFENDER),
            PlayerEntity(name = "Varian Wrynn", position = MIDFIELDER),
            PlayerEntity(name = "Vol’jin", position = MIDFIELDER),
            PlayerEntity(name = "Cairne Bloodhoof", position = MIDFIELDER),
            PlayerEntity(name = "Sylvanas Windrunner", position = FORWARD),
            PlayerEntity(name = "Illidan Stormrage", position = FORWARD),
            PlayerEntity(name = "Arthas Menethil", position = FORWARD),
        )
    )

    fun addTeams() {
        defaultTeams.forEach { team, players ->
            applicationScope.launch(Dispatchers.IO) {
                val savedTeamId = teamDao.upsertTeam(team = team)
                val updatedPlayers = players.map {
                    it.copy(
                        playerTeamId = savedTeamId
                    )
                }
                playerDao.upsertPlayers(updatedPlayers)
            }
        }
    }
}