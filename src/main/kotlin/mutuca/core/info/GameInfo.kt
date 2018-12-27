package mutuca.core.info

import com.github.ocraft.s2client.bot.gateway.ActionInterface
import com.github.ocraft.s2client.bot.gateway.DebugInterface
import com.github.ocraft.s2client.bot.gateway.ObservationInterface
import mutuca.core.info.unit.UnitInfo

object GameInfo {
    lateinit var observation: ObservationInterface

    lateinit var actions: ActionInterface

    lateinit var debug: DebugInterface

    var resourceChanged = false

    fun reset() {
        resourceChanged = false
    }

    fun step() {
        UnitInfo.step()
    }
}