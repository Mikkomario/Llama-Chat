package vf.llama.database.access.many.meta.availability.topic

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple topic info availabilities at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbTopicInfoAvailabilities 
	extends ManyTopicInfoAvailabilitiesAccess with UnconditionalView 
		with ViewManyByIntIds[ManyTopicInfoAvailabilitiesAccess]

