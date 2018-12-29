package mutuca.core.info.production

import com.github.ocraft.s2client.protocol.data.Ability
import com.github.ocraft.s2client.protocol.data.UnitType

data class ProductionDetails(
    val fromUnitType: UnitType,
    val toUnitType: UnitType,
    val ability: Ability,
    val minerals: Int,
    val gas: Int,
    val foodRequired: Float,
    val produces: Int,
    val priority: Int
)