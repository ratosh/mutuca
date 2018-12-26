package mutuca.core.unit.controller

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.unit.Alliance
import mutuca.core.GameInfo
import mutuca.core.info.morph.MorphDetails

class DefaultPriorityController : IUnitPriorityController {

    private val unitCount = mutableMapOf<UnitType, Int>()

    /**
     * Here we can count units and check if we need one type or another
     *
     */
    override fun step() {
        val myUnits = GameInfo.observation.units.filter {
            it.isAlive &&
                it.unit.isPresent &&
                it.unit.get().alliance == Alliance.SELF
        }
        unitCount.clear()
        for (unit in myUnits) {
            unitCount[unit.unit.get().type] = unitCount[unit.unit.get().type] ?: 0 + 1
        }
    }

    override fun getUnitPriority(unitType: UnitType): Double {
        return when (unitType) {
            Units.ZERG_DRONE -> {
                // TODO: Use a better function
                if (unitCount[Units.ZERG_DRONE] ?: 0 < 70) {
                    0.7
                } else {
                    0.0
                }
            }
            Units.ZERG_OVERLORD -> {
                // TODO: Use a better function
                // TODO: check if we are already morphing overlords
                0.9 - (GameInfo.observation.foodCap - GameInfo.observation.foodUsed).toDouble() / GameInfo.observation.foodCap
            }
            else -> {
                0.0
            }
        }
    }

    override fun registerMorph(morphDetails: MorphDetails) {
        // TODO: Implement some priority change so we don't build too many units from one type
    }
}