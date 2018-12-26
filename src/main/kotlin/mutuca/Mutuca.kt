package mutuca

import com.github.ocraft.s2client.bot.S2Agent
import com.github.ocraft.s2client.protocol.debug.Color
import mutuca.core.GameInfo


/**
 * Main AI Class
 */
class Mutuca : S2Agent() {

    /**
     * Starting AI
     */
    override fun onGameStart() {
        debug().debugTextOut("Starting Mutuca!", Color.BLUE)
        GameInfo.observation = observation()

        debug().sendDebug()
    }

    /**
     * Main loop
     */
    override fun onStep() {

    }
}