package vf.llama.database.access.many.persona.image.animation

import utopia.vault.nosql.view.{UnconditionalView, ViewManyByIntIds}

/**
  * The root access point when targeting multiple persona animations at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbPersonaAnimations 
	extends ManyPersonaAnimationsAccess with UnconditionalView 
		with ViewManyByIntIds[ManyPersonaAnimationsAccess]

