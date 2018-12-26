package mutuca.utils

import com.github.ocraft.s2client.bot.gateway.UnitInPool
import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.spatial.Point2d
import com.github.ocraft.s2client.protocol.unit.Alliance
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.GameInfo
import java.util.Optional

object PositionUtil {

    fun findNearestUnit(alliance: Alliance, unitType: UnitType, position: Point2d): Optional<Unit> {
        val units = GameInfo.observation.getUnits(alliance, UnitInPool.isUnit(unitType))
        return if (units.isEmpty()) {
            Optional.empty()
        } else {
            var target: Unit? = null
            var distance = java.lang.Double.MAX_VALUE
            for (unitInPool in units) {
                val unit = unitInPool.unit()
                val d = unit.position.toPoint2d().distance(position)
                if (d < distance) {
                    distance = d
                    target = unit
                }
            }
            Optional.ofNullable(target)
        }
    }
}