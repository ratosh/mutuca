package mutuca.core.info.player

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.protocol.game.PlayerInfo
import com.github.ocraft.s2client.protocol.game.Race

object PlayerInfo {
    private lateinit var playerInfo: PlayerInfo

    val race: Race
        get() = playerInfo.actualRace.orElse(Race.RANDOM)

    fun setup(agent: S2Agent) {
        playerInfo =
            agent.observation().gameInfo.playersInfo.first { it.playerId == agent.observation().playerId }
    }

    fun step() {
    }
}