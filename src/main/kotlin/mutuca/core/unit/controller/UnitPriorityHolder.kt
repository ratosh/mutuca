package mutuca.core.unit.controller

/**
 * Able to change controller to have different controllers during a game.
 */
object UnitPriorityHolder {

    private lateinit var unitPriorityController: IUnitPriorityController

    var controller
        get() = unitPriorityController
        set(value) {
            unitPriorityController = value
        }

}