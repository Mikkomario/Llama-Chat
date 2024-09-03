package vf.llama.model.factory.persona

import vf.llama.model.enumeration.PersonaDescriptionType
import vf.llama.model.factory.statement.StatementLinkFactoryWrapper

/**
  * Common trait for classes that implement PersonaStatementLinkFactory by wrapping
  *  a PersonaStatementLinkFactory instance
  * @tparam A Type of constructed instances
  * @tparam Repr Implementing type of this factory
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait PersonaStatementLinkFactoryWrapper[A <: PersonaStatementLinkFactory[A], +Repr] 
	extends PersonaStatementLinkFactory[Repr] with StatementLinkFactoryWrapper[A, Repr]
{
	// IMPLEMENTED	--------------------
	
	override def withDescriptionType(descriptionType: PersonaDescriptionType) = 
		mapWrapped { _.withDescriptionType(descriptionType) }
	
	override def withPersonaSettingsId(personaSettingsId: Int) = withParentId(personaSettingsId)
}

