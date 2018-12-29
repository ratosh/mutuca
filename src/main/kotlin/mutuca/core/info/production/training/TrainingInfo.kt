package mutuca.core.info.production.training

import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.info.production.ProductionDetails

object TrainingInfo {
    private val trainingList = listOf(
        ProductionDetails(
            Units.ZERG_HATCHERY,
            Units.ZERG_QUEEN,
            Abilities.TRAIN_QUEEN,
            200,
            0,
            1.0f,
            1,
            1
        )
    )


    val trainingFromTypes = mutableMapOf<UnitType, MutableList<ProductionDetails>>()

    init {
        for (productionDetails in trainingList) {
            if (!trainingFromTypes.containsKey(productionDetails.fromUnitType)) {
                trainingFromTypes[productionDetails.fromUnitType] = mutableListOf()
            }
            trainingFromTypes[productionDetails.fromUnitType]!!.add(productionDetails)
        }
    }
}