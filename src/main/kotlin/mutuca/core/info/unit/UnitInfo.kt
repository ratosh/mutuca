package mutuca.core.info.unit

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.spatial.Point2d
import com.github.ocraft.s2client.protocol.unit.Alliance
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.game.GameInfo
import mutuca.core.info.production.ProductionDetails
import mutuca.core.info.production.morph.MorphInfo

object UnitInfo {
    private val aliveUnits = mutableSetOf<Long>()

    /**
     * Produced unitType count
     */
    private val producedUnitCountMap = mutableMapOf<UnitType, Int>()

    /**
     * Producing unitType information
     */
    private val producingUnitCountMap = mutableMapOf<UnitType, Int>()
    private val producingUnitMap = mutableMapOf<Long, UnitType>()

    /**
     * About to start producing unitType information
     */
    private val pendingUnitMap = mutableMapOf<Long, UnitType>()
    private val pendingUnitCountMap = mutableMapOf<UnitType, Int>()

    /**
     * Wanted to be produced unitType information
     */
    private val wantedUnitCountMap = mutableMapOf<UnitType, Int>()

    val currentFoodCap: Int
        get() = GameInfo.observation.foodCap

    val currentFoodUsed: Int
        get() = GameInfo.observation.foodUsed

    // TODO: Consider other unitType types also
    val inProductionFoodCap: Int
        get() = getUnitCountIncludingProduction(Units.ZERG_OVERLORD) * 8 +
            getUnitCountIncludingProduction(Units.ZERG_HATCHERY) * 6

    fun setup(agent: S2Agent) {

    }

    /**
     * Here we can count units and check if we need one type or another
     */
    fun step() {
        val myUnits = GameInfo.observation.units.filter {
            it.isAlive &&
                it.unit.isPresent &&
                it.unit.get().alliance == Alliance.SELF
        }

        producedUnitCountMap.clear()
        producingUnitCountMap.clear()
        pendingUnitCountMap.clear()
        val missingUnits = mutableSetOf<Long>()
        missingUnits.addAll(aliveUnits)

        for (unit in myUnits) {
            val tagValue = unit.tag.value
            incrementProducedCount(unit.unit.get().type)
            if (!aliveUnits.contains(tagValue)) {
                aliveUnits.add(tagValue)
            }
            if (unit.unit().type == Units.ZERG_EGG) {
                val ability = unit.unit().orders.first().ability
                val productionDetails = MorphInfo.morphingAbilities[ability]!!
                incrementProducingCount(productionDetails.toUnitType, productionDetails.produces)
            }
            missingUnits.remove(tagValue)
        }

        // Remove missing units
        for (missingUnit in missingUnits) {
            producingUnitMap.remove(missingUnit)
            pendingUnitMap.remove(missingUnit)
        }

        for (unitType in producingUnitMap.values) {
            incrementProducingCount(unitType, 1)
        }

        for (unitType in pendingUnitMap.values) {
            incrementPendingUnitCount(unitType, 1)
        }
    }

    private fun incrementProducingCount(unitType: UnitType, amount: Int) {
        producingUnitCountMap[unitType] = producingUnitCountMap.getOrDefault(unitType, 0) + amount
    }

    private fun incrementProducedCount(unitType: UnitType) {
        producedUnitCountMap[unitType] = producedUnitCountMap.getOrDefault(unitType, 0) + 1
    }

    /**
     * Unit does not move to start producing
     */
    fun startProduction(unit: Unit, productionDetails: ProductionDetails): Boolean {
        val tag = unit.tag.value
        val unitType = productionDetails.toUnitType
        println("Producing ${unit.type} from ${productionDetails.fromUnitType} into $unitType")
        producingUnitMap[tag] = unitType
        incrementProducingCount(unitType, productionDetails.produces)
        GameInfo.actions.unitCommand(unit, productionDetails.ability, false)
        return true
    }

    /**
     * Unit needs to move to start producing
     */
    fun reserveProduction(unit: Unit, productionDetails: ProductionDetails, location: Point2d?): Boolean {
        val tag = unit.tag.value
        val unitType = productionDetails.toUnitType
        pendingUnitMap[tag] = unitType
        println("Producing ${unit.type} from ${productionDetails.fromUnitType} into $unitType")

        incrementPendingUnitCount(unitType, productionDetails.produces)
        GameInfo.actions.unitCommand(unit, productionDetails.ability, location, false)
        return true
    }

    fun reservedProduction(unit: Unit): Boolean {
        return pendingUnitMap.containsKey(unit.tag.value)
    }

    private fun incrementPendingUnitCount(unitType: UnitType, amount: Int) {
        pendingUnitCountMap[unitType] = pendingUnitCountMap.getOrDefault(unitType, 0) + amount
    }

    fun getUnitCount(unitType: UnitType) =
        producedUnitCountMap.getOrDefault(unitType, 0)

    fun getUnitCountIncludingProduction(unitType: UnitType) =
        producedUnitCountMap.getOrDefault(unitType, 0) +
            producingUnitCountMap.getOrDefault(unitType, 0)

    fun getUnitCountIncludingPending(unitType: UnitType) =
        producedUnitCountMap.getOrDefault(unitType, 0) +
            producingUnitCountMap.getOrDefault(unitType, 0) +
            pendingUnitCountMap.getOrDefault(unitType, 0)

    fun getUnitCountWanted(unitType: UnitType) =
        wantedUnitCountMap.getOrDefault(unitType, 0)

    fun needProduction(unitType: UnitType) =
        getUnitCountWanted(unitType) > getUnitCountIncludingPending(unitType)

    fun setWantedCount(unitType: UnitType, amount: Int) {
        wantedUnitCountMap[unitType] = amount
    }

    fun incrementWantedCount(unitType: UnitType, amount: Int) {
        wantedUnitCountMap[unitType] = getUnitCountWanted(unitType) + amount
    }

    fun canProduce(unit: Unit, productionDetails: ProductionDetails): Boolean {
        return GameInfo.query.getAbilitiesForUnit(unit, false).abilities.find {
            it.ability.abilityId == productionDetails.ability.abilityId
        } != null
    }
}