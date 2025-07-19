package vf.llama.database.access.single.persona.image.set

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.PersonaImageSet

/**
  * An access point to individual persona image sets, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaImageSet(id: Int) 
	extends UniquePersonaImageSetAccess with SingleIntIdModelAccess[PersonaImageSet]

