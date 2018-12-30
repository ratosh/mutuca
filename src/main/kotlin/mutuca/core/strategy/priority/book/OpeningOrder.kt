package mutuca.core.strategy.priority.book

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.info.unit.UnitInfo

data class OpeningOrder(
    val drones: Int,
    val unitType: UnitType,
    val wantedCount: Int
) {
    fun beginCondition(): Boolean {
        return UnitInfo.getUnitCountIncludingPending(Units.ZERG_DRONE) >= drones
    }

    fun successCondition(): Boolean {
        return UnitInfo.getUnitCountIncludingPending(unitType) >= wantedCount
    }

}