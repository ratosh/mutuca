package mutuca.core.strategy.priority

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.morph.MorphDetails

interface IUnitPriorityController {

    fun step()

    fun getUnitPriority(unitType: UnitType): Double
}
