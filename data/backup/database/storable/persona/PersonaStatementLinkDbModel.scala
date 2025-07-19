package vf.llama.database.storable.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.DbPropertyDeclaration
import vf.llama.database.LlamaChatTables
import vf.llama.database.props.statement.StatementLinkDbProps
import vf.llama.database.storable.statement.{StatementLinkDbModel, StatementLinkDbModelFactoryLike, StatementLinkDbModelLike}
import vf.llama.model.enumeration.PersonaDescriptionType
import vf.llama.model.factory.persona.PersonaStatementLinkFactory
import vf.llama.model.partial.persona.PersonaStatementLinkData
import vf.llama.model.stored.persona.PersonaStatementLink

/**
  * Used for constructing PersonaStatementLinkDbModel instances and for inserting persona statement links
  *  to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object PersonaStatementLinkDbModel 
	extends StatementLinkDbModelFactoryLike[PersonaStatementLinkDbModel, PersonaStatementLink, PersonaStatementLinkData] 
		with PersonaStatementLinkFactory[PersonaStatementLinkDbModel] with StatementLinkDbProps
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with persona settings ids
	  */
	lazy val personaSettingsId = property("personaSettingsId")
	
	/**
	  * Database property used for interacting with statement ids
	  */
	override lazy val statementId = property("statementId")
	
	/**
	  * Database property used for interacting with order indices
	  */
	override lazy val orderIndex = property("orderIndex")
	
	/**
	  * Database property used for interacting with description types
	  */
	lazy val descriptionType = property("descriptionTypeId")
	
	
	// IMPLEMENTED	--------------------
	
	override def parentId = personaSettingsId
	
	override def table = LlamaChatTables.personaStatementLink
	
	override def apply(data: PersonaStatementLinkData) = 
		apply(None, Some(data.personaSettingsId), Some(data.statementId), Some(data.orderIndex), 
			Some(data.descriptionType))
	
	/**
	  * @param descriptionType Context in which the linked statement appears
	  * @return A model containing only the specified description type
	  */
	override def withDescriptionType(descriptionType: PersonaDescriptionType) = 
		apply(descriptionType = Some(descriptionType))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param orderIndex 0-based index that indicates the specific location of the placed text
	  * @return A model containing only the specified order index
	  */
	override def withOrderIndex(orderIndex: Int) = apply(orderIndex = Some(orderIndex))
	
	/**
	  * @param personaSettingsId Id of the settings to which this link is tied to
	  * @return A model containing only the specified persona settings id
	  */
	override def withPersonaSettingsId(personaSettingsId: Int) = 
		apply(personaSettingsId = Some(personaSettingsId))
	
	/**
	  * @param statementId Id of the statement made within the linked text / entity
	  * @return A model containing only the specified statement id
	  */
	override def withStatementId(statementId: Int) = apply(statementId = Some(statementId))
	
	override protected def complete(id: Value, data: PersonaStatementLinkData) = 
		PersonaStatementLink(id.getInt, data)
}

/**
  * Used for interacting with PersonaStatementLinks in the database
  * @param id persona statement link database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaStatementLinkDbModel(id: Option[Int] = None, personaSettingsId: Option[Int] = None, 
	statementId: Option[Int] = None, orderIndex: Option[Int] = None, 
	descriptionType: Option[PersonaDescriptionType] = None) 
	extends StatementLinkDbModel with StatementLinkDbModelLike[PersonaStatementLinkDbModel] 
		with PersonaStatementLinkFactory[PersonaStatementLinkDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def dbProps = PersonaStatementLinkDbModel
	
	override def parentId = personaSettingsId
	
	override def table = PersonaStatementLinkDbModel.table
	
	override def valueProperties = 
		super[StatementLinkDbModelLike].valueProperties ++
			Single(PersonaStatementLinkDbModel.descriptionType.name -> descriptionType.map[Value] { e => e.id }.getOrElse(Value.empty))
	
	/**
	  * @param id Id to assign to the new model (default = currently assigned id)
	  * @param statementId statement id to assign to the new model (default = currently assigned value)
	  * @param parentId parent id to assign to the new model (default = currently assigned value)
	  * @param orderIndex order index to assign to the new model (default = currently assigned value)
	  */
	override def copyStatementLink(id: Option[Int] = id, statementId: Option[Int] = statementId, 
		parentId: Option[Int] = parentId, orderIndex: Option[Int] = orderIndex) = 
		copy(id = id, statementId = statementId, personaSettingsId = parentId, orderIndex = orderIndex)
	
	/**
	  * @param descriptionType Context in which the linked statement appears
	  * @return A new copy of this model with the specified description type
	  */
	override def withDescriptionType(descriptionType: PersonaDescriptionType) = 
		copy(descriptionType = Some(descriptionType))
	
	/**
	  * @param personaSettingsId Id of the settings to which this link is tied to
	  * @return A new copy of this model with the specified persona settings id
	  */
	override
		 def withPersonaSettingsId(personaSettingsId: Int) = copy(personaSettingsId = Some(personaSettingsId))
}

