package mutuca.core.strategy.priority.book

open class OpeningBook(private val openingOrderList: List<OpeningOrder>) {

    fun completed(): Boolean {
        return openingOrderList.find { !it.successCondition() } == null
    }

    fun currentOrder(): OpeningOrder? {
        return openingOrderList.find {
                !it.successCondition()
        }
    }
}