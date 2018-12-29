package mutuca.core.unittype

import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.unit.IUnitBehavior

class UnitTypeBehaviour {

    val unitBehaviorList = mutableListOf<IUnitBehavior>()

    fun step(unit: Unit) {
        val sortedList = unitBehaviorList.sortedBy { it.priority }
        for (unitBehaivor in sortedList) {
            if (unitBehaivor.step(unit)) {
                break
            }
        }
    }
}