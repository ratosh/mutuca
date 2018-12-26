package mutuca.core.info

import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.info.morph.MorphDetails

object MorphInfo {
    private val morphingList = listOf(
        MorphDetails(Units.ZERG_LARVA, Units.ZERG_DRONE, Abilities.TRAIN_DRONE, 50, 0),
        MorphDetails(Units.ZERG_LARVA, Units.ZERG_OVERLORD, Abilities.TRAIN_OVERLORD, 100, 0)
    )
    val morphingFromTypes = mutableMapOf<UnitType, MutableList<MorphDetails>>()
    val morphingToTypes = mutableMapOf<UnitType, MorphDetails>()

    init {
        for (morphingDetails in morphingList) {
            morphingToTypes[morphingDetails.toUnitType] = morphingDetails
            if (!morphingFromTypes.containsKey(morphingDetails.fromUnitType)) {
                morphingFromTypes[morphingDetails.fromUnitType] = mutableListOf()
            }
            morphingFromTypes[morphingDetails.fromUnitType]!!.add(morphingDetails)
        }
    }
}