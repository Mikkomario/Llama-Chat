package vf.llama.database.access.single.persona.image.animation

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.persona.PersonaAnimationWithFrames

/**
  * An access point to individual persona animations with image frames, based on their animation id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaAnimationWithFrames(id: Int) 
	extends UniquePersonaAnimationWithFramesAccess with SingleIntIdModelAccess[PersonaAnimationWithFrames]

