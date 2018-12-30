package mutuca.core.strategy

import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.Mutuca
import mutuca.core.info.unit.UnitInfo
import mutuca.core.strategy.priority.UnitPriorityHolder
import mutuca.core.strategy.priority.book.BookController
import mutuca.core.strategy.priority.book.preset.LingRushBook
import mutuca.core.strategy.priority.general.DefaultProductionController
import mutuca.core.unit.AttackBehavior
import mutuca.core.unit.BuilderBehavior
import mutuca.core.unit.InjectBehavior
import mutuca.core.unit.MiningBehavior
import mutuca.core.unit.MorphingBehavior
import mutuca.core.unit.ScoutBehavior
import mutuca.core.unit.TrainingBehavior
import mutuca.core.unittype.UnitTypeBehavior

/**
 * Default game strategy.
 */
class DefaultGameStrategy(gameInfo: Mutuca) : IGameStrategy {


    private val unitTypeBehaviorMap = HashMap<UnitType, UnitTypeBehavior>()

    init {
        UnitPriorityHolder.controller = BookController(LingRushBook(), DefaultProductionController())
    }

    override fun step() {
        UnitPriorityHolder.controller.step()
        for (unitInPool in UnitInfo.playerUnitList) {
            val unit = unitInPool.unit.get()
            if (!unitTypeBehaviorMap.containsKey(unit.type)) {
                println("New unitType type unitType ${unit.type.unitTypeId}")
                // Found a unitInPool without info
                // Creating unitInPool behaviors and information
                unitTypeBehaviorMap[unit.type] = getUnitInfo(unit)
            }
            unitTypeBehaviorMap[unit.type]!!.step(unit)
        }
    }

    // TODO: Each strategy can have a different factory
    private fun getUnitInfo(unit: Unit): UnitTypeBehavior {
        val result = UnitTypeBehavior()
        when (unit.type) {
            Units.ZERG_HATCHERY -> {
                result.unitBehaviorList.add(TrainingBehavior())
            }
            Units.ZERG_QUEEN -> {
                result.unitBehaviorList.add(InjectBehavior())
            }
            Units.ZERG_OVERLORD -> {
                result.unitBehaviorList.add(ScoutBehavior())
            }
            Units.ZERG_LARVA -> {
                result.unitBehaviorList.add(MorphingBehavior())
            }
            Units.ZERG_DRONE -> {
                result.unitBehaviorList.add(MiningBehavior())
                result.unitBehaviorList.add(BuilderBehavior())
            }
            Units.ZERG_ZERGLING -> {
                result.unitBehaviorList.add(ScoutBehavior())
                result.unitBehaviorList.add(AttackBehavior())
            }
        }
        return result
    }
}