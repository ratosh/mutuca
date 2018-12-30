package mutuca.utils

import com.github.ocraft.s2client.protocol.unit.Unit

object UnitUtil {

    fun isBase(unit: Unit) =
        UnitTypeUtil.isBase(unit.type)
}