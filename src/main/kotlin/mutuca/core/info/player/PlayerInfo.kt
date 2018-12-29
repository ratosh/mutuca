package mutuca.core.info.player

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.protocol.game.PlayerInfo
import com.github.ocraft.s2client.protocol.game.Race
import mutuca.core.info.game.GameInfo

object PlayerInfo {
    private lateinit var playerInfo: PlayerInfo

    val race: Race
        get() = playerInfo.actualRace.orElse(Race.RANDOM)

    var mineralSpending = 0
    var gasSpending = 0

    val availableMineral: Int
        get () = GameInfo.observation.minerals - mineralSpending

    val availableGas: Int
        get() = GameInfo.observation.vespene - gasSpending

    fun setup(agent: S2Agent) {
        playerInfo =
            agent.observation().gameInfo.playersInfo.first { it.playerId == agent.observation().playerId }
    }

    fun step() {
        mineralSpending = 0
        gasSpending = 0
    }

    fun registerSpending(mineralSpending: Int, gasSpending: Int) {
        this.mineralSpending += mineralSpending
        this.gasSpending += gasSpending
    }
}