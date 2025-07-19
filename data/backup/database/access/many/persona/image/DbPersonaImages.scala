package vf.llama.database.access.many.persona.image

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple persona images at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaImages 
	extends ManyPersonaImagesAccess with UnconditionalView with ViewManyByIntIds[ManyPersonaImagesAccess]

