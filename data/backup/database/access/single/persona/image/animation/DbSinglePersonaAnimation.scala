package vf.llama.database.access.single.persona.image.animation

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.PersonaAnimation

/**
  * An access point to individual persona animations, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaAnimation(id: Int) 
	extends UniquePersonaAnimationAccess with SingleIntIdModelAccess[PersonaAnimation]

