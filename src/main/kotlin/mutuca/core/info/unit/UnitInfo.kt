package mutuca.core.info.unit

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.unit.Alliance
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.GameInfo
import mutuca.core.info.morph.MorphDetails

object UnitInfo {

    // TODO: Have a counter only with morphed units?
    val unitCount = mutableMapOf<UnitType, Int>()

    private val morphingUnits = mutableMapOf<Long, UnitType>()

    /**
     * Here we can count units and check if we need one type or another
     */
    fun step() {
        val myUnits = GameInfo.observation.units.filter {
            it.isAlive &&
                it.unit.isPresent &&
                it.unit.get().alliance == Alliance.SELF
        }

        val morphedUnits = mutableSetOf<Long>()
        morphedUnits.addAll(morphingUnits.keys)
        unitCount.clear()

        for (unit in myUnits) {
            val tag = unit.unit.get().tag.value!!
            if (morphingUnits.containsKey(tag)) {
                morphedUnits.remove(tag)
            }

            incrementCount(unit.unit.get().type)
        }
        morphedUnits.forEach {
            val morphedType = morphingUnits.remove(it)
            println("Morphed unit $morphedType")
        }

        for (unitType in morphingUnits.values) {
            incrementCount(unitType)
        }
    }

    private fun incrementCount(unitType: UnitType) {
        if (!unitCount.containsKey(unitType)) {
            unitCount[unitType] = 1
        } else {
            unitCount[unitType] = unitCount[unitType]!! + 1
        }
    }

    fun registerMorph(unit: Unit, morphDetails: MorphDetails): Boolean {
        val tag = unit.tag.value
        if (!morphingUnits.containsKey(tag) &&
            (!morphDetails.needFood || GameInfo.observation.foodCap > GameInfo.observation.foodUsed)
        ) {
            println("Morphing ${unit.type} from ${morphDetails.fromUnitType} into ${morphDetails.toUnitType}")
            println("${GameInfo.observation.foodCap} | ${GameInfo.observation.foodUsed}")
            morphingUnits[tag] = morphDetails.toUnitType
            incrementCount(morphDetails.toUnitType)
            GameInfo.resourceChanged = true
            return true
        }
        return false
    }
}