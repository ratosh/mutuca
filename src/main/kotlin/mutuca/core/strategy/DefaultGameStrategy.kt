package mutuca.core.strategy

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.unit.Alliance
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.Mutuca
import mutuca.core.info.game.GameInfo
import mutuca.core.strategy.priority.UnitPriorityHolder
import mutuca.core.strategy.priority.book.BookController
import mutuca.core.strategy.priority.book.preset.LingRushBook
import mutuca.core.strategy.priority.general.DefaultProductionController
import mutuca.core.strategy.resource.DefaultGatherBehavior
import mutuca.core.unit.BuilderBehavior
import mutuca.core.unit.MiningBehavior
import mutuca.core.unit.MorphingBehavior
import mutuca.core.unittype.UnitTypeBehaviour

/**
 * Default game strategy.
 */
class DefaultGameStrategy(gameInfo: Mutuca) : IGameStrategy {

    private val gatherBehavior = DefaultGatherBehavior()

    private val unitInfo = HashMap<UnitType, UnitTypeBehaviour>()

    init {
        UnitPriorityHolder.controller = BookController(LingRushBook(), DefaultProductionController())
    }

    override fun step() {
        gatherBehavior.step()
        UnitPriorityHolder.controller.step()
        for (unitInPool in GameInfo.observation.units) {
            if (!unitInPool.isAlive) {
                continue
            }
            val unit = unitInPool.unit.get()
            if (unit.alliance != Alliance.SELF) {
                continue
            }
            if (!unitInfo.containsKey(unit.type)) {
                println("New unitType type unitType ${unit.type.unitTypeId}")
                // Found a unitInPool without info
                // Creating unitInPool behaviors and information
                unitInfo[unit.type] = getUnitInfo(unit)
            }
            unitInfo[unit.type]!!.step(unit)
        }
    }

    // TODO: Each strategy can have a different factory
    private fun getUnitInfo(unit: Unit): UnitTypeBehaviour {
        val result = UnitTypeBehaviour()
        when (unit.type) {
            Units.ZERG_DRONE -> {
                result.unitBehaviorList.add(MiningBehavior())
                result.unitBehaviorList.add(BuilderBehavior())
            }
            Units.ZERG_LARVA -> {
                result.unitBehaviorList.add(MorphingBehavior())
            }
        }
        return result
    }
}