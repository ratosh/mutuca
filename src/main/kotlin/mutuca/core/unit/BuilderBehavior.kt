package mutuca.core.unit

import com.github.ocraft.s2client.protocol.spatial.Point2d
import com.github.ocraft.s2client.protocol.unit.Unit
import mutuca.core.info.production.ProductionDetails
import mutuca.core.info.production.building.BuildingInfo
import mutuca.core.info.unit.UnitInfo
import mutuca.utils.PositionUtil

class BuilderBehavior : IUnitBehavior {

    override val priority: Int
        get() = 3

    /**
     * Morph behavior
     */
    override fun step(unit: Unit): Boolean {
        if (UnitInfo.reservedProduction(unit)) {
            return true
        }
        if (unit.cargoSpaceTaken.isPresent) {
            return false
        }
        var bestBuilding: ProductionDetails? = null
        var priority = 0
        var bestLocation: Point2d? = null

        // TODO: Check if we have a good place to build close to the unit
        for (buildingDetails in BuildingInfo.buildingFromTypes[unit.type]!!) {
            if (UnitInfo.needProduction(buildingDetails.toUnitType) &&
                UnitInfo.canProduce(unit, buildingDetails) &&
                buildingDetails.priority > priority
            ) {
                val buildingLocation = PositionUtil.findInCreepBuildingPosition(buildingDetails.ability, unit)
                if (buildingLocation != null) {
                    println("Found position")
                    bestBuilding = buildingDetails
                    priority = buildingDetails.priority
                    bestLocation = buildingLocation
                }
            }
        }
        if (bestBuilding == null) {
            return false
        }
        return UnitInfo.reserveProduction(unit, bestBuilding, bestLocation)
    }

}