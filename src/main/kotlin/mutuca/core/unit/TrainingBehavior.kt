package mutuca.core.unit

import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.production.ProductionDetails
import mutuca.core.info.production.training.TrainingInfo
import mutuca.core.info.unit.UnitInfo

class TrainingBehavior : IUnitBehavior {

    override val priority: Int
        get() = 1

    /**
     * Training behavior
     */
    override fun step(unit: Unit): Boolean {
        var bestTraining: ProductionDetails? = null
        var priority = 0
        for (buildingDetails in TrainingInfo.trainingFromTypes[unit.type]!!) {
            if (UnitInfo.needProduction(buildingDetails.toUnitType) &&
                buildingDetails.priority > priority &&
                UnitInfo.canProduce(unit, buildingDetails)
            ) {
                bestTraining = buildingDetails
                priority = buildingDetails.priority
            }
        }
        if (bestTraining == null ||
            unit.orders.find { it.ability == bestTraining.ability } != null
        ) {
            return false
        }
        return UnitInfo.reserveProduction(unit, bestTraining, null)
    }
}