package vf.llama.database.access.single.meta.availability.thread

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.meta.ThreadInfoAvailability

/**
  * An access point to individual thread info availabilities, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleThreadInfoAvailability(id: Int) 
	extends UniqueThreadInfoAvailabilityAccess with SingleIntIdModelAccess[ThreadInfoAvailability]

