package vf.llama.database.access.single.persona

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.persona.PersonaWithSettings

/**
  * An access point to individual personas with settings, based on their persona id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaWithSettings(id: Int) 
	extends UniquePersonaWithSettingsAccess with SingleIntIdModelAccess[PersonaWithSettings]

