package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.PersonaSettings

/**
  * An access point to individual persona settings, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaSettings(id: Int) 
	extends UniquePersonaSettingsAccess with SingleIntIdModelAccess[PersonaSettings]

