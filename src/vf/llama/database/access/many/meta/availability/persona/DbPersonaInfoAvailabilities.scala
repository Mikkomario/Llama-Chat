package vf.llama.database.access.many.meta.availability.persona

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple persona info availabilities at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaInfoAvailabilities 
	extends ManyPersonaInfoAvailabilitiesAccess with UnconditionalView 
		with ViewManyByIntIds[ManyPersonaInfoAvailabilitiesAccess]

