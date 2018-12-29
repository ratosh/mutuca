package mutuca.core.info.production.morph

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.data.Ability
import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.info.production.ProductionDetails

object MorphInfo {
    private val morphingList = listOf(
        ProductionDetails(
            Units.ZERG_LARVA,
            Units.ZERG_DRONE,
            Abilities.TRAIN_DRONE,
            50,
            0,
            1.0f,
            1,
            1
        ),
        ProductionDetails(
            Units.ZERG_LARVA,
            Units.ZERG_OVERLORD,
            Abilities.TRAIN_OVERLORD,
            100,
            0,
            0.0f,
            1,
            99
        ),
        ProductionDetails(
            Units.ZERG_LARVA,
            Units.ZERG_ZERGLING,
            Abilities.TRAIN_ZERGLING,
            50,
            0,
            1.0f,
            2,
            2
        )
    )
    val morphingFromTypes = mutableMapOf<UnitType, MutableList<ProductionDetails>>()
    val morphingToTypes = mutableMapOf<UnitType, ProductionDetails>()
    val morphingAbilities = mutableMapOf<Ability, ProductionDetails>()

    // TODO: Get info from agent
    fun setup(agent: S2Agent) {
    }

    init {
        for (morphingDetails in morphingList) {
            morphingToTypes[morphingDetails.toUnitType] = morphingDetails
            if (!morphingFromTypes.containsKey(morphingDetails.fromUnitType)) {
                morphingFromTypes[morphingDetails.fromUnitType] = mutableListOf()
            }
            morphingFromTypes[morphingDetails.fromUnitType]!!.add(morphingDetails)
            morphingAbilities[morphingDetails.ability] = morphingDetails
        }
    }
}