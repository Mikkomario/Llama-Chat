package vf.llama.database.factory.persona

import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.linked.CombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.persona.PersonaLinkedImageSet
import vf.llama.model.stored.persona.{PersonaImageSet, PersonaImageSetLink}

/**
  * Used for reading persona linked image sets from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaLinkedImageSetDbFactory 
	extends CombiningFactory[PersonaLinkedImageSet, PersonaImageSet, PersonaImageSetLink] 
		with FromTimelineRowFactory[PersonaLinkedImageSet] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = PersonaImageSetLinkDbFactory
	
	override def nonDeprecatedCondition = 
		parentFactory.nonDeprecatedCondition && childFactory.nonDeprecatedCondition
	
	override def parentFactory = PersonaImageSetDbFactory
	
	override def timestamp = parentFactory.timestamp
	
	/**
	  * @param imageSet image set to wrap
	  * @param link link to attach to this image set
	  */
	override def apply(imageSet: PersonaImageSet, link: PersonaImageSetLink) = 
		PersonaLinkedImageSet(imageSet, link)
}

