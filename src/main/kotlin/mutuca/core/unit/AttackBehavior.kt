package mutuca.core.unit

import com.github.ocraft.s2client.bot.gateway.UnitInPool
import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.unit.DisplayType
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.game.GameInfo
import mutuca.core.info.unit.UnitInfo

class AttackBehavior : IUnitBehavior {

    override val priority: Int
        get() = 4

    /**
     * Scout behavior
     */
    override fun step(unit: Unit): Boolean {
        if (unit.engagedTargetTag.isPresent) {
            return true
        }
        val enemyUnits = UnitInfo.enemyUnitList.filter {
            it.unit().flying.isPresent && !it.unit().flying.get()
        }
        var smallestDistance = Double.MAX_VALUE
        var targetUnit: UnitInPool? = null
        for (enemyUnit in enemyUnits) {
            if (!enemyUnit.isAlive || enemyUnit.unit().displayType == DisplayType.SNAPSHOT) {
                continue
            }
            val distance = unit.position.distance(enemyUnit.unit().position)
            if (smallestDistance > distance) {
                smallestDistance = distance
                targetUnit = enemyUnit
            }
        }

        if (targetUnit == null) {
            return false
        }
        if (!unit.orders.isEmpty() &&
            unit.orders[0].ability == Abilities.ATTACK &&
            unit.orders[0].targetedUnitTag.isPresent &&
            unit.orders[0].targetedUnitTag.get() == targetUnit.tag
        ) {
            return true
        }

        GameInfo.actions.unitCommand(unit, Abilities.ATTACK, targetUnit.unit(), false)
        return true
    }
}