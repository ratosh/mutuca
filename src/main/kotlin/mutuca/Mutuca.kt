package mutuca

import com.github.ocraft.s2client.bot.S2Agent
import mutuca.core.info.GameInfo
import mutuca.core.strategy.IGameStrategy
import mutuca.core.strategy.factory.StrategyFactory

/**
 * Main AI Class
 */
class Mutuca : S2Agent() {

    private lateinit var gameStrategy: IGameStrategy

    /**
     * Starting AI
     */
    override fun onGameStart() {
        GameInfo.observation = observation()
        GameInfo.actions = actions()
        GameInfo.debug = debug()

        gameStrategy = StrategyFactory.getStrategy()
    }

    /**
     * Main loop
     */
    override fun onStep() {
        gameStrategy.step()
    }
}