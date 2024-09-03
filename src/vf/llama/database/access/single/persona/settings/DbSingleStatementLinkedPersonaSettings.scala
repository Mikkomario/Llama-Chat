package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.persona.StatementLinkedPersonaSettings

/**
  * An access point to individual statement linked persona settings, based on their settings id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleStatementLinkedPersonaSettings(id: Int) 
	extends UniqueStatementLinkedPersonaSettingsAccess 
		with SingleIntIdModelAccess[StatementLinkedPersonaSettings]

