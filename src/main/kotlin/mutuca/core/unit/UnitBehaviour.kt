package mutuca.core.unit

import com.github.ocraft.s2client.protocol.unit.Unit

class UnitBehaviour {

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