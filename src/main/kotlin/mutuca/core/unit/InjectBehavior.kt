package mutuca.core.unit

import com.github.ocraft.s2client.protocol.data.Abilities
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.game.GameInfo

class InjectBehavior : IUnitBehavior {

    /**
     * QueenId, base
     */
    private val queenBases = mutableMapOf<Long, Long>()

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

        val queenTag = unit.tag.value
        var injectBase: Unit? = null
        if (queenBases.containsKey(queenTag)) {
            injectBase = GameInfo.observation.units.find { it.tag.value == queenBases[queenTag] }?.unit()
            if (injectBase == null) {
                queenBases.remove(queenTag)
            }
        } else {
            val baseList = GameInfo.observation.units.filter {
                it.unit().type == Units.ZERG_HATCHERY
            }
            for (base in baseList) {
                val baseTag = base.unit().tag.value
                if (!queenBases.containsValue(baseTag)) {
                    injectBase = base.unit()
                    queenBases[queenTag] = baseTag
                }
            }
        }

        if (injectBase == null) {
            return false
        }

        GameInfo.actions.unitCommand(unit, Abilities.EFFECT_INJECT_LARVA, injectBase, false)
        return true
    }
}