package mutuca.core.strategy.priority.book.preset

import com.github.ocraft.s2client.protocol.data.Units
import mutuca.core.strategy.priority.book.OpeningBook
import mutuca.core.strategy.priority.book.OpeningOrder

class LingRushBook : OpeningBook(
    listOf(
        OpeningOrder(14, Units.ZERG_OVERLORD, 2),
        OpeningOrder(16, Units.ZERG_SPAWNING_POOL, 1),
        OpeningOrder(16, Units.ZERG_OVERLORD, 3),
        OpeningOrder(16, Units.ZERG_QUEEN, 1),
        OpeningOrder(16, Units.ZERG_ZERGLING, 10)
    )
)