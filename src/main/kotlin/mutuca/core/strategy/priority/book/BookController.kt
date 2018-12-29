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
        if (openingBook.completed()) {
            println("Book completed")
            UnitPriorityHolder.controller = nextController
            return
        }
        val openingOrder = openingBook.currentOrder() ?: return
        UnitInfo.setWantedCount(Units.ZERG_DRONE, openingOrder.drones)
        if (openingOrder.beginCondition() &&
            UnitInfo.getUnitCountWanted(openingOrder.unitType) != openingOrder.wantedCount
        ) {
            println("Setting to produce ${openingOrder.wantedCount} ${openingOrder.unitType}")
            UnitInfo.setWantedCount(openingOrder.unitType, openingOrder.wantedCount)
        }
    }
}