package mutuca

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.protocol.debug.Color
import mutuca.core.GameInfo
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
        debug().debugTextOut("Starting Mutuca!", Color.BLUE)
        GameInfo.observation = observation()
        GameInfo.actions = actions()

        gameStrategy = StrategyFactory.getStrategy()

        debug().sendDebug()
    }

    /**
     * Main loop
     */
    override fun onStep() {
        gameStrategy.step()
    }
}