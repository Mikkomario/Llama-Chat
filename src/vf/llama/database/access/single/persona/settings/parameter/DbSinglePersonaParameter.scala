package vf.llama.database.access.single.persona.settings.parameter

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.PersonaParameter

/**
  * An access point to individual persona parameters, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaParameter(id: Int) 
	extends UniquePersonaParameterAccess with SingleIntIdModelAccess[PersonaParameter]

