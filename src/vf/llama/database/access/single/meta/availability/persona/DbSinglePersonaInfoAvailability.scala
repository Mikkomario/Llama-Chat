package vf.llama.database.access.single.meta.availability.persona

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.meta.PersonaInfoAvailability

/**
  * An access point to individual persona info availabilities, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaInfoAvailability(id: Int) 
	extends UniquePersonaInfoAvailabilityAccess with SingleIntIdModelAccess[PersonaInfoAvailability]

