package vf.llama.database.access.many.persona.image.set

import utopia.vault.nosql.access.many.model.ManyRowModelAccess
import utopia.vault.nosql.view.{ChronoRowFactoryView, ViewFactory}
import utopia.vault.sql.Condition
import vf.llama.database.factory.persona.PersonaImageSetDbFactory
import vf.llama.model.stored.persona.PersonaImageSet

object ManyPersonaImageSetsAccess extends ViewFactory[ManyPersonaImageSetsAccess]
{
	// IMPLEMENTED	--------------------
	
	/**
	  * @param condition Condition to apply to all requests
	  * @return An access point that applies the specified filter condition (only)
	  */
	override def apply(condition: Condition): ManyPersonaImageSetsAccess = 
		_ManyPersonaImageSetsAccess(Some(condition))
	
	
	// NESTED	--------------------
	
	private case class _ManyPersonaImageSetsAccess(override val accessCondition: Option[Condition]) 
		extends ManyPersonaImageSetsAccess
}

/**
  * A common trait for access points which target multiple persona image sets at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ManyPersonaImageSetsAccess 
	extends ManyPersonaImageSetsAccessLike[PersonaImageSet, ManyPersonaImageSetsAccess] 
		with ManyRowModelAccess[PersonaImageSet] 
		with ChronoRowFactoryView[PersonaImageSet, ManyPersonaImageSetsAccess]
{
	// IMPLEMENTED	--------------------
	
	override def factory = PersonaImageSetDbFactory
	
	override protected def self = this
	
	override
		 def apply(condition: Condition): ManyPersonaImageSetsAccess = ManyPersonaImageSetsAccess(condition)
}

