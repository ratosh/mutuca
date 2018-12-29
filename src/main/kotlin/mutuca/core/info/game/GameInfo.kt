package mutuca.core.info.game

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.bot.gateway.ActionInterface
import com.github.ocraft.s2client.bot.gateway.DebugInterface
import com.github.ocraft.s2client.bot.gateway.ObservationInterface
import com.github.ocraft.s2client.bot.gateway.QueryInterface
import mutuca.core.info.player.EnemyInfo
import mutuca.core.info.player.PlayerInfo
import mutuca.core.info.production.building.BuildingInfo
import mutuca.core.info.production.morph.MorphInfo
import mutuca.core.info.unit.UnitInfo

/**
 * Game info singleton, responsible of updating all info singletons
 */
object GameInfo {

    // Agent information
    lateinit var observation: ObservationInterface
    lateinit var actions: ActionInterface
    lateinit var debug: DebugInterface
    lateinit var query: QueryInterface

    fun setup(agent: S2Agent) {
        observation = agent.observation()
        actions = agent.actions()
        debug = agent.debug()
        query= agent.query()

        PlayerInfo.setup(agent)
        EnemyInfo.setup(agent)
        UnitInfo.setup(agent)
        MorphInfo.setup(agent)
        BuildingInfo.setup(agent)
    }

    fun step() {
        PlayerInfo.step()
        EnemyInfo.step()
        UnitInfo.step()
    }
}