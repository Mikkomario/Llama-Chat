package vf.llama.database.access.many.meta.availability.thread

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple thread info availabilities at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbThreadInfoAvailabilities 
	extends ManyThreadInfoAvailabilitiesAccess with UnconditionalView 
		with ViewManyByIntIds[ManyThreadInfoAvailabilitiesAccess]

