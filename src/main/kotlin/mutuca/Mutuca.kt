package mutuca

import com.github.ocraft.s2client.bot.S2Agent
import mutuca.core.info.game.GameInfo
import mutuca.core.strategy.IGameStrategy
import mutuca.core.strategy.factory.GameStrategyFactory

/**
 * Main AI Class
 */
class Mutuca : S2Agent() {

    lateinit var gameStrategy: IGameStrategy

    /**
     * Starting AI
     */
    override fun onGameStart() {
        GameInfo.setup(this)
        gameStrategy = GameStrategyFactory.getStrategy(this)
    }

    /**
     * Main loop
     */
    override fun onStep() {
        GameInfo.step()

        gameStrategy.step()
    }
}