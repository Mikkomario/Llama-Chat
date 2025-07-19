package vf.llama.database.access.single.persona.image.link

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.persona.PersonaImageSetLink

/**
  * An access point to individual persona image set links, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSinglePersonaImageSetLink(id: Int) 
	extends UniquePersonaImageSetLinkAccess with SingleIntIdModelAccess[PersonaImageSetLink]

