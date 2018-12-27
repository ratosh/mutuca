package mutuca.core.strategy.priority

/**
 * Able to change priority to have different controllers during a game.
 */
object UnitPriorityHolder {

    private lateinit var unitPriorityController: IUnitPriorityController

    var controller
        get() = unitPriorityController
        set(value) {
            unitPriorityController = value
        }

}