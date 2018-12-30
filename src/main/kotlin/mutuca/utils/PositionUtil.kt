package mutuca.utils

import com.github.ocraft.s2client.bot.gateway.UnitInPool
import com.github.ocraft.s2client.protocol.data.Ability
import com.github.ocraft.s2client.protocol.data.UnitType
import com.github.ocraft.s2client.protocol.spatial.Point2d
import com.github.ocraft.s2client.protocol.unit.Alliance
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.game.GameInfo
import java.util.*

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
     * TODO: Write functions for other buildings/building formations
     */

    fun findInCreepBuildingPosition(ability: Ability, builder: Unit): Point2d? {
        val squareRadius = 55
        for (y in -squareRadius..squareRadius step 10) {
            for (x in -squareRadius..squareRadius step 10) {
                if (y == -squareRadius || y == squareRadius || x == -squareRadius || x == squareRadius) {
                    val testPosition = GameInfo.observation.startLocation.toPoint2d().add(x.toFloat() / 10, y.toFloat() / 10)
                    if (GameInfo.query.placement(ability, testPosition)) {
                        return testPosition
                    }
                }
            }
        }
        return null
    }
}