package mutuca.core.info.morph

import com.github.ocraft.s2client.protocol.data.Ability
import com.github.ocraft.s2client.protocol.data.UnitType

data class MorphDetails(
    val fromUnitType: UnitType,
    val toUnitType: UnitType,
    val ability: Ability,
    val minerals: Int,
    val gas: Int
)