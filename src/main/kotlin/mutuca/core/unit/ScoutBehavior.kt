package mutuca.core.unit

import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.spatial.Point2d
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.game.GameInfo

class ScoutBehavior : IUnitBehavior {

    override val priority: Int
        get() = 3

    /**
     * Scout behavior
     */
    override fun step(unit: Unit): Boolean {
        if (!unit.orders.isEmpty()) {
            return false
        }
        var bestPosition: Point2d? = null
        for (tries in 0 until 100) {
            val randomPosition = GameInfo.observation.gameInfo.findRandomLocation()
            if (unit.flying.isPresent || GameInfo.observation.isPathable(randomPosition)) {
                bestPosition = randomPosition
                break
            }
        }

        if (bestPosition == null) {
            return false
        }

        // TODO: Find a good location to scout, possibly use influence maps
        GameInfo.actions.unitCommand(unit, Abilities.ATTACK, bestPosition, false)
        return true
    }
}