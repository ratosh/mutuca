package mutuca.utils

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units

object UnitTypeUtil {

    fun isBase(unitType: UnitType) =
        unitType == Units.ZERG_HATCHERY ||
            unitType == Units.ZERG_HIVE ||
            unitType == Units.ZERG_LAIR ||
            unitType == Units.TERRAN_COMMAND_CENTER ||
            unitType == Units.PROTOSS_NEXUS
}