package vf.llama.database.access.single.color

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.color.Color

/**
  * An access point to individual colors, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleColor(id: Int) extends UniqueColorAccess with SingleIntIdModelAccess[Color]

