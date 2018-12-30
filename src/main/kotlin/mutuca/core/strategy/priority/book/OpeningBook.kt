package mutuca.core.strategy.priority.book

open class OpeningBook(private val openingOrderList: List<OpeningOrder>) {

    fun currentOrder(): OpeningOrder? {
        return openingOrderList.find {
            !it.successCondition()
        }
    }
}