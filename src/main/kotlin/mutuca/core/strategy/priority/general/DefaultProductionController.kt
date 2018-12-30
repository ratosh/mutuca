package mutuca.core.strategy.priority.general

import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.info.unit.UnitInfo
import mutuca.core.strategy.priority.IUnitProductionController

class DefaultProductionController : IUnitProductionController {

    override fun step() {
        // Produce a queen if we have more hatchery than queens
        if (UnitInfo.getUnitCount(Units.ZERG_HATCHERY) >= UnitInfo.getUnitCountWanted(Units.ZERG_QUEEN)) {
            UnitInfo.setWantedCount(Units.ZERG_QUEEN, UnitInfo.getUnitCount(Units.ZERG_HATCHERY) + 1)
        }

        // Units need larva
        if (UnitInfo.getUnitCount(Units.ZERG_LARVA) == 0) {
            return
        }
        // Note: We need an overlord if we have enough larva to reach cap
        // considering that we gain 1 food per larva
        if (UnitInfo.inProductionFoodCap < 200 &&
            UnitInfo.inProductionFoodCap - UnitInfo.currentFoodUsed - UnitInfo.getUnitCount(Units.ZERG_LARVA) <= 4
        ) {
            UnitInfo.incrementWantedCount(Units.ZERG_OVERLORD, 1)
        }
        if (UnitInfo.getUnitCountIncludingProduction(Units.ZERG_ZERGLING) >=
            UnitInfo.getUnitCountWanted(Units.ZERG_ZERGLING)
        ) {
            UnitInfo.incrementWantedCount(Units.ZERG_ZERGLING, UnitInfo.getUnitCount(Units.ZERG_LARVA))
        }
    }

}