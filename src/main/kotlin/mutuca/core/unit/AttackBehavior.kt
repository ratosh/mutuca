package mutuca.core.unit

import com.github.ocraft.s2client.bot.gateway.UnitInPool
import com.github.ocraft.s2client.protocol.data.Abilities
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
        if (!unit.orders.isEmpty()) {
            return false
        }

        val enemyUnits = UnitInfo.enemyUnits
        var smallestDistance = Double.MAX_VALUE
        var targetUnit: UnitInPool? = null
        for (enemyUnit in enemyUnits) {
            val distance = unit.position.distance(enemyUnit.unit().position)
            if (smallestDistance > distance) {
                smallestDistance = distance
                targetUnit = enemyUnit
            }
        }

        if (targetUnit == null) {
            return false
        }

        GameInfo.actions.unitCommand(unit, Abilities.ATTACK, targetUnit.unit(), false)
        return true
    }
}