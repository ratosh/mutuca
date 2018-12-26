package mutuca.core.unit

import com.github.ocraft.s2client.protocol.unit.Unit

/**
 * Interface to control units.
 */
interface IUnitBehavior {

    val priority: Int

    fun step(unit: Unit): Boolean
}