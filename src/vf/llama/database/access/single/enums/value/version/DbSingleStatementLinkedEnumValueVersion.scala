package vf.llama.database.access.single.enums.value.version

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.enums.StatementLinkedEnumValueVersion

/**
  * An access point to individual statement linked enum value versions, based on their value version id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleStatementLinkedEnumValueVersion(id: Int) 
	extends UniqueStatementLinkedEnumValueVersionAccess 
		with SingleIntIdModelAccess[StatementLinkedEnumValueVersion]

