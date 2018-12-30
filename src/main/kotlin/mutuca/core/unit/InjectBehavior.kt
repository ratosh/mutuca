package mutuca.core.unit

import com.github.ocraft.s2client.bot.gateway.UnitInPool
import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.game.GameInfo
import mutuca.core.info.queen.QueenInfo

class InjectBehavior : IUnitBehavior {

    override val priority: Int
        get() = 1

    /**
     * Inject behavior
     */
    override fun step(unit: Unit): Boolean {
        if (!unit.orders.isEmpty() ||
            GameInfo.query.getAbilitiesForUnit(unit, false).abilities.find {
                it.ability == Abilities.EFFECT_INJECT_LARVA
            } == null
        ) {
            return false
        }

        var injectBase: UnitInPool? = QueenInfo.getQueenBase(unit)
        if (injectBase == null) {
            val baseList = QueenInfo.getFreeBases()
            var smallestDistance = Float.MAX_VALUE
            for (base in baseList) {
                val distance = GameInfo.query.pathingDistance(unit, base.unit().position.toPoint2d())
                if (distance < smallestDistance) {
                    smallestDistance = distance
                    injectBase = base
                }
            }
            if (injectBase != null) {
                QueenInfo.registerQueenBase(unit, injectBase.unit())
            }
        }

        if (injectBase == null) {
            return false
        }

        GameInfo.actions.unitCommand(unit, Abilities.EFFECT_INJECT_LARVA, injectBase.unit(), false)
        return true
    }
}