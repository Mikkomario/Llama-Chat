package vf.llama.database.access.many.persona.settings.parameter

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple persona parameters at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaParameters 
	extends ManyPersonaParametersAccess with UnconditionalView 
		with ViewManyByIntIds[ManyPersonaParametersAccess]

