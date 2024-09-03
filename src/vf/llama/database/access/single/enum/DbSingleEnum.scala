package vf.llama.database.access.single.`enum`

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.enum.Enum

/**
  * An access point to individual enums, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleEnum(id: Int) extends UniqueEnumAccess with SingleIntIdModelAccess[Enum]

