package mutuca.core.unit

import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.GameInfo
import mutuca.core.info.MorphInfo
import mutuca.core.info.morph.MorphDetails
import mutuca.core.unit.controller.UnitPriorityHolder

class MorphingBehavior : IUnitBehavior {

    override val priority: Int
        get() = 2

    /**
     * Morph behavior
     */
    override fun step(unit: Unit): Boolean {
        if (!unit.orders.isEmpty()) {
            return false
        }
        var bestPriority = 0.0
        var bestMorph: MorphDetails? = null
        for (morphDetails in MorphInfo.morphingFromTypes[unit.type]!!) {
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
        UnitPriorityHolder.controller.registerMorph(bestMorph)
        println("Morphing ${unit.type} from ${bestMorph.toUnitType} into ${bestMorph.toUnitType}")
        GameInfo.actions.unitCommand(unit, bestMorph.ability, false)
        return true
    }

}