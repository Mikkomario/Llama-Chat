package vf.llama.database.access.single.persona.image.set

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.persona.PersonaLinkedImageSet

/**
  * An access point to individual persona linked image sets, based on their image set id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaLinkedImageSet(id: Int) 
	extends UniquePersonaLinkedImageSetAccess with SingleIntIdModelAccess[PersonaLinkedImageSet]

