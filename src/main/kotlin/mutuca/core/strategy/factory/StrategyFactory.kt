package mutuca.core.strategy.factory

import mutuca.core.strategy.DefaultGameStrategy
import mutuca.core.strategy.IGameStrategy

/**
 * Strategy factory.
 */
object StrategyFactory {

    /**
     * Select the best strategy based on game information.
     */
    fun getStrategy(): IGameStrategy {
        return DefaultGameStrategy()
    }
}