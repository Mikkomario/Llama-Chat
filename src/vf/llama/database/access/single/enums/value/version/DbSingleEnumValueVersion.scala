package vf.llama.database.access.single.enums.value.version

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.enums.EnumValueVersion

/**
  * An access point to individual enum value versions, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleEnumValueVersion(id: Int) 
	extends UniqueEnumValueVersionAccess with SingleIntIdModelAccess[EnumValueVersion]

