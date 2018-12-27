package mutuca.core.unit

import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.unit.Alliance
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.GameInfo
import mutuca.utils.PositionUtil
import java.util.Optional

class MiningBehavior : IUnitBehavior {

    override val priority: Int
        get() = 1

    /**
     * Worker behavior
     */
    override fun step(unit: Unit): Boolean {
        if (!unit.orders.isEmpty()) {
            return false
        }
        println("Idle worker collecting minerals")
        findNearestMineralPatch(unit).ifPresent {
            GameInfo.actions.unitCommand(unit, Abilities.HARVEST_GATHER, it, false)
        }
        return true
    }

    private fun findNearestMineralPatch(unit: Unit): Optional<Unit> {
        return PositionUtil.findNearestUnit(Alliance.NEUTRAL, Units.NEUTRAL_MINERAL_FIELD, unit.position.toPoint2d())
    }

}