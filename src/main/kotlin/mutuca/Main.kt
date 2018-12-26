package mutuca

import com.github.ocraft.s2client.bot.S2Coordinator
import com.github.ocraft.s2client.protocol.game.BattlenetMap
import com.github.ocraft.s2client.protocol.game.Difficulty
import com.github.ocraft.s2client.protocol.game.Race

fun main(args: Array<String>) {
    println("Starting bot")
    val bot = Mutuca()
    val s2Coordinator = S2Coordinator.setup()
        .loadSettings(args)
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