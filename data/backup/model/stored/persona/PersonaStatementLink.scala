package vf.llama.model.stored.persona

import utopia.flow.generic.model.template.ModelLike.AnyModel
import utopia.vault.model.template.StoredFromModelFactory
import vf.llama.database.access.single.persona.link.statement.DbSinglePersonaStatementLink
import vf.llama.model.factory.persona.PersonaStatementLinkFactoryWrapper
import vf.llama.model.partial.persona.PersonaStatementLinkData
import vf.llama.model.partial.statement.StatementLinkData
import vf.llama.model.stored.statement.StoredStatementLinkLike

object PersonaStatementLink extends StoredFromModelFactory[PersonaStatementLinkData, PersonaStatementLink]
{
	// IMPLEMENTED	--------------------
	
	override def dataFactory = PersonaStatementLinkData
	
	override protected def complete(model: AnyModel, data: PersonaStatementLinkData) = 
		model("id").tryInt.map { apply(_, data) }
}

/**
  * Represents a persona statement link that has already been stored in the database
  * @param id id of this persona statement link in the database
  * @param data Wrapped persona statement link data
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaStatementLink(id: Int, data: PersonaStatementLinkData) 
	extends PersonaStatementLinkFactoryWrapper[PersonaStatementLinkData, PersonaStatementLink] 
		with StatementLinkData with StoredStatementLinkLike[PersonaStatementLinkData, PersonaStatementLink]
{
	// COMPUTED	--------------------
	
	/**
	  * An access point to this persona statement link in the database
	  */
	def access = DbSinglePersonaStatementLink(id)
	
	
	// IMPLEMENTED	--------------------
	
	override def withId(id: Int) = copy(id = id)
	
	override protected def wrap(data: PersonaStatementLinkData) = copy(data = data)
}

