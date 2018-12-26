package mutuca.core.strategy

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.unit.Alliance
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.GameInfo
import mutuca.core.strategy.resource.DefaultGatherBehavior
import mutuca.core.unit.MiningBehavior
import mutuca.core.unit.MorphingBehavior
import mutuca.core.unit.UnitBehaviour
import mutuca.core.unit.controller.DefaultPriorityController
import mutuca.core.unit.controller.UnitPriorityHolder

/**
 * Default game strategy.
 */
class DefaultGameStrategy : IGameStrategy {

    private val gatherBehavior = DefaultGatherBehavior()

    private val unitInfo = HashMap<UnitType, UnitBehaviour>()

    init {
        UnitPriorityHolder.controller = DefaultPriorityController()
    }

    override fun step() {
        gatherBehavior.step()
        UnitPriorityHolder.controller.step()
        for (unitInPool in GameInfo.observation.units) {
            if (!unitInPool.isAlive) {
                continue
            }
            val unit = unitInPool.unit.get()
            if (unit.alliance != Alliance.SELF) {
                continue
            }
            if (!unitInfo.containsKey(unit.type)) {
                println("New unit type unit ${unit.type.unitTypeId}")
                // Found a unitInPool without info
                // Creating unitInPool behaviors and information
                unitInfo[unit.type] = getUnitInfo(unit)
            }
            unitInfo[unit.type]!!.step(unit)
        }
    }

    // TODO: Each strategy can have a different factory
    private fun getUnitInfo(unit: Unit): UnitBehaviour {
        val result = UnitBehaviour()
        when (unit.type) {
            Units.ZERG_DRONE -> {
                result.unitBehaviorList.add(MiningBehavior())
                result.unitBehaviorList.add(MorphingBehavior())
            }
            Units.ZERG_LARVA -> {
                result.unitBehaviorList.add(MorphingBehavior())
            }
        }
        return result
    }
}