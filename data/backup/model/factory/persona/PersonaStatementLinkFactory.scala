package vf.llama.model.factory.persona

import vf.llama.model.enumeration.PersonaDescriptionType
import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for persona statement link-related factories which allow construction 
	with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaStatementLinkFactory[+A] extends StatementLinkFactory[A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param descriptionType New description type to assign
	  * @return Copy of this item with the specified description type
	  */
	def withDescriptionType(descriptionType: PersonaDescriptionType): A
	
	/**
	  * @param personaSettingsId New persona settings id to assign
	  * @return Copy of this item with the specified persona settings id
	  */
	def withPersonaSettingsId(personaSettingsId: Int): A
	
	
	// IMPLEMENTED	--------------------
	
	override def withParentId(parentId: Int) = withPersonaSettingsId(parentId)
}

