package mutuca.core.info.queen

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.bot.gateway.UnitInPool
import com.github.ocraft.s2client.protocol.data.Units
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.unit.UnitInfo
import mutuca.utils.UnitUtil

object QueenInfo {
    private val queenBase = mutableMapOf<Long, Long>()
    private val baseQueen = mutableMapOf<Long, Long>()

    fun setup(agent: S2Agent) {
    }

    /**
     * This method will take care of storing information about the enemy
     */
    fun step() {
        val units = UnitInfo.playerUnitList

        // Remove not found queens
        queenBase.keys
            .filter { queenTag -> units.find { it.unit().tag.value == queenTag } == null }
            .forEach {
                val baseTag = queenBase.remove(it)!!
                baseQueen.remove(baseTag)
            }

        // Remove not found bases
        baseQueen.keys
            .filter { baseTag -> units.find { it.unit().tag.value == baseTag } == null }
            .forEach {
                val queenTag = baseQueen.remove(it)!!
                queenBase.remove(queenTag)
            }
    }

    fun registerQueenBase(queen: Unit, base: Unit) {
        assert(queen.type == Units.ZERG_QUEEN)
        assert(UnitUtil.isBase(base))
        val baseTag = base.tag.value
        val queenTag = queen.tag.value
        queenBase[queenTag] = baseTag
        baseQueen[baseTag] = queenTag
    }

    fun getQueenBase(queen: Unit): UnitInPool? {
        if (!queenBase.containsKey(queen.tag.value)) {
            return null
        }
        return UnitInfo.playerUnitList.find { it.tag.value == queenBase[queen.tag.value] }
    }

    fun getFreeBases(): List<UnitInPool> {
        return UnitInfo.playerBaseList.filter { !baseQueen.containsKey(it.tag.value) }
    }
}