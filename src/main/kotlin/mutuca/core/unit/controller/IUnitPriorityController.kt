package mutuca.core.unit.controller

import com.github.ocraft.s2client.protocol.data.UnitType
import mutuca.core.info.morph.MorphDetails

interface IUnitPriorityController {

    fun step()

    fun getUnitPriority(unitType: UnitType): Double

    fun registerMorph(morphDetails: MorphDetails)
}
