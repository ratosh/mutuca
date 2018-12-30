package mutuca

import com.github.ocraft.s2client.bot.S2Coordinator
import com.github.ocraft.s2client.protocol.game.BattlenetMap
import com.github.ocraft.s2client.protocol.game.Difficulty
import com.github.ocraft.s2client.protocol.game.Race

/**
 * Main function
 */
fun main(args: Array<String>) {
    // TODO: Change coordinator setup to ladder if we receive args
    val bot = Mutuca()
    val s2Coordinator = S2Coordinator.setup()
        .loadSettings(args)
        .setRealtime(false)
        .setParticipants(
            S2Coordinator.createParticipant(Race.ZERG, bot),
            S2Coordinator.createComputer(Race.ZERG, Difficulty.EASY)
        )
        .launchStarcraft()
        .startGame(BattlenetMap.of("Lava Flow"))

    while (s2Coordinator.update()) {
    }

    s2Coordinator.quit()
}