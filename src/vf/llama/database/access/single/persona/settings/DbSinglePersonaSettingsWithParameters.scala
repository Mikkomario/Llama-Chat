package vf.llama.database.access.single.persona.settings

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.persona.PersonaSettingsWithParameters

/**
  * An access point to individual persona settings with parameters, based on their settings id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaSettingsWithParameters(id: Int) 
	extends UniquePersonaSettingsWithParametersAccess 
		with SingleIntIdModelAccess[PersonaSettingsWithParameters]

