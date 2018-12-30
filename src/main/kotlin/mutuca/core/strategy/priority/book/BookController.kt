package mutuca.core.strategy.priority.book

import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.info.unit.UnitInfo
import mutuca.core.strategy.priority.IUnitProductionController
import mutuca.core.strategy.priority.UnitPriorityHolder

class BookController(
    private val openingBook: OpeningBook,
    private val nextController: IUnitProductionController
) : IUnitProductionController {
    override fun step() {
        val currentOrder = openingBook.currentOrder()
        if (currentOrder == null) {
            println("Book completed")
            UnitPriorityHolder.controller = nextController
            return
        }
        UnitInfo.setWantedCount(Units.ZERG_DRONE, currentOrder.drones)
        if (currentOrder.beginCondition() &&
            UnitInfo.getUnitCountWanted(currentOrder.unitType) != currentOrder.wantedCount
        ) {
            println("Setting to produce ${currentOrder.wantedCount} ${currentOrder.unitType}")
            UnitInfo.setWantedCount(currentOrder.unitType, currentOrder.wantedCount)
        }
    }
}