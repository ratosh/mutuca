package mutuca.core.strategy.factory

import mutuca.Mutuca
import mutuca.core.strategy.DefaultGameStrategy
import mutuca.core.strategy.IGameStrategy

/**
 * Strategy factory.
 */
object GameStrategyFactory {

    /**
     * Select the best strategy based on game information.
     */
    fun getStrategy(gameInfo: Mutuca): IGameStrategy {
        return DefaultGameStrategy(gameInfo)
    }
}