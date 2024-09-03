package vf.llama.database.access.single.`enum`.value.version

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.enum.ContextualEnumValueVersion

/**
  * An access point to individual contextual enum value versions, based on their version id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleContextualEnumValueVersion(id: Int) 
	extends UniqueContextualEnumValueVersionAccess with SingleIntIdModelAccess[ContextualEnumValueVersion]

