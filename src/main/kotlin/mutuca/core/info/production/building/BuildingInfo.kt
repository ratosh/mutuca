package mutuca.core.info.production.building

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.info.production.ProductionDetails

// TODO: Change into info and get details from observation
object BuildingInfo {
    private val buildingList = listOf(
        ProductionDetails(
            Units.ZERG_DRONE,
            Units.ZERG_HATCHERY,
            Abilities.BUILD_HATCHERY,
            300,
            0,
            0.0f,
            1,
            1
        ),
        ProductionDetails(
            Units.ZERG_DRONE,
            Units.ZERG_SPAWNING_POOL,
            Abilities.BUILD_SPAWNING_POOL,
            200,
            0,
            0.0f,
            1,
            1
        )
    )
    val buildingFromTypes = mutableMapOf<UnitType, MutableList<ProductionDetails>>()
    val buildingToTypes = mutableMapOf<UnitType, ProductionDetails>()

    // TODO: Get info from agent
    fun setup(agent: S2Agent) {
    }

    init {
        for (buildingDetails in buildingList) {
            buildingToTypes[buildingDetails.toUnitType] = buildingDetails
            if (!buildingFromTypes.containsKey(buildingDetails.fromUnitType)) {
                buildingFromTypes[buildingDetails.fromUnitType] = mutableListOf()
            }
            buildingFromTypes[buildingDetails.fromUnitType]!!.add(buildingDetails)
        }
    }
}