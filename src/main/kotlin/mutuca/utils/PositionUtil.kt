package mutuca.utils

import com.github.ocraft.s2client.bot.gateway.UnitInPool
import com.github.ocraft.s2client.protocol.data.Ability
import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.spatial.Point2d
import com.github.ocraft.s2client.protocol.unit.Alliance
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.game.GameInfo
import java.lang.Math.abs
import java.util.Optional

object PositionUtil {

    private val MIN_DISTANCE = 3
    private val MAX_DISTANCE = 10

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

    /**
     * TODO: Implement a function that returns the best position to place a building in creep
     */
    fun findInCreepBuildingPosition(ability: Ability, builder: Unit): Point2d? {
        for (distance in 1 until MAX_DISTANCE) {
            for (xoffset in -distance until distance) {
                for (yoffset in -distance until distance) {
                    // only check border area
                    if (abs(xoffset) < distance && abs(yoffset) < distance) {
                        continue
                    }
                    val testPosition = builder.position.toPoint2d().add(xoffset.toFloat(), yoffset.toFloat())
                    if (GameInfo.query.placement(ability, testPosition)) {
                        return testPosition
                    }
                }
            }
        }
        return null
    }
}