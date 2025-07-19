package vf.llama.database.access.single.enums.value

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.enums.EnumValue

/**
  * An access point to individual enum values, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleEnumValue(id: Int) extends UniqueEnumValueAccess with SingleIntIdModelAccess[EnumValue]

