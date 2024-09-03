package vf.llama.database.access.single.`enum`.value.version.link.statement

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.enum.EnumValueStatementLink

/**
  * An access point to individual enum value statement links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleEnumValueStatementLink(id: Int) 
	extends UniqueEnumValueStatementLinkAccess with SingleIntIdModelAccess[EnumValueStatementLink]

