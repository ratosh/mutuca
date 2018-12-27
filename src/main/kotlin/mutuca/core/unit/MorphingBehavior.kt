package mutuca.core.unit

import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.GameInfo
import mutuca.core.info.morph.MorphDetails
import mutuca.core.info.morph.MorphData
import mutuca.core.info.unit.UnitInfo
import mutuca.core.strategy.priority.UnitPriorityHolder

class MorphingBehavior : IUnitBehavior {

    override val priority: Int
        get() = 2

    /**
     * Morph behavior
     */
    override fun step(unit: Unit): Boolean {
        if (!unit.orders.isEmpty() || GameInfo.resourceChanged) {
            return false
        }
        var bestPriority = 0.0
        var bestMorph: MorphDetails? = null
        for (morphDetails in MorphData.morphingFromTypes[unit.type]!!) {
            val unitPriority = UnitPriorityHolder.controller.getUnitPriority(morphDetails.toUnitType)
            if (unitPriority > bestPriority &&
                GameInfo.observation.minerals >= morphDetails.minerals &&
                GameInfo.observation.vespene >= morphDetails.gas
            ) {
                bestMorph = morphDetails
                bestPriority = unitPriority
            }
        }
        if (bestMorph == null) {
            return false
        }
        if (UnitInfo.registerMorph(unit, bestMorph)) {
            GameInfo.actions.unitCommand(unit, bestMorph.ability, false)
            return true
        }
        return false
    }

}