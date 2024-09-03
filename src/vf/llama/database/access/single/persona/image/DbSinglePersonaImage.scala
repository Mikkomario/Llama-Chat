package vf.llama.database.access.single.persona.image

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.PersonaImage

/**
  * An access point to individual persona images, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaImage(id: Int) 
	extends UniquePersonaImageAccess with SingleIntIdModelAccess[PersonaImage]

