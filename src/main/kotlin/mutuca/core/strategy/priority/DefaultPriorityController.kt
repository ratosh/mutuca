package mutuca.core.strategy.priority

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.GameInfo
import mutuca.core.info.morph.MorphDetails
import mutuca.core.info.unit.UnitInfo

class DefaultPriorityController : IUnitPriorityController {

    override fun step() {
    }

    override fun getUnitPriority(unitType: UnitType): Double {
        return when (unitType) {
            Units.ZERG_DRONE -> {
                // TODO: Use a better function
                if (UnitInfo.unitCount[Units.ZERG_DRONE] ?: 0 < 70) {
                    0.7
                } else {
                    0.0
                }
            }
            Units.ZERG_OVERLORD -> {
                // TODO: Use a better function
                // TODO: No need to build overlords over the unit cap
                val cap = UnitInfo.unitCount[Units.ZERG_OVERLORD]!! * 8 + UnitInfo.unitCount[Units.ZERG_HATCHERY]!! * 6
                0.9 - (cap - GameInfo.observation.foodUsed).toDouble() / GameInfo.observation.foodCap
            }
            else -> {
                0.0
            }
        }
    }
}