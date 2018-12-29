package mutuca.core.strategy.priority.general

import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.info.unit.UnitInfo
import mutuca.core.strategy.priority.IUnitProductionController

class DefaultProductionController : IUnitProductionController {

    override fun step() {
        // Note: We need an overlord if we have enough larva to reach cap
        // considering that we gain 1 food per larva

        // Units need larva
        if (UnitInfo.getUnitCount(Units.ZERG_LARVA) == 0) {
            return
        }
        if (UnitInfo.inProductionFoodCap < 200 &&
            UnitInfo.inProductionFoodCap - UnitInfo.currentFoodUsed - UnitInfo.getUnitCount(Units.ZERG_LARVA) <= 0
        ) {
            UnitInfo.incrementWantedCount(Units.ZERG_OVERLORD, 1)
        }

        if (UnitInfo.getUnitCountIncludingProduction(Units.ZERG_ZERGLING) < 75) {
            UnitInfo.incrementWantedCount(Units.ZERG_ZERGLING, UnitInfo.getUnitCount(Units.ZERG_LARVA))
        }
    }

}