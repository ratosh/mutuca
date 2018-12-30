package mutuca.core.unit

import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.production.ProductionDetails
import mutuca.core.info.production.morph.MorphInfo
import mutuca.core.info.unit.UnitInfo

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
        var bestMorph: ProductionDetails? = null
        var priority = 0

        for (morphDetails in MorphInfo.morphingFromTypes[unit.type]!!) {
            if (UnitInfo.needProduction(morphDetails.toUnitType) &&
                UnitInfo.canProduce(unit, morphDetails) &&
                morphDetails.priority > priority
            ) {
                bestMorph = morphDetails
                priority = morphDetails.priority
            }
        }
        if (bestMorph == null) {
            return false
        }
        return UnitInfo.startProduction(unit, bestMorph)
    }

}