package vf.llama.model.partial.persona

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration}
import utopia.flow.generic.model.mutable.DataType.IntType
import vf.llama.model.enumeration.PersonaDescriptionType
import vf.llama.model.enumeration.PersonaDescriptionType.Description
import vf.llama.model.factory.persona.PersonaStatementLinkFactory
import vf.llama.model.partial.statement.{StatementLinkData, StatementLinkDataLike}

object PersonaStatementLinkData extends FromModelFactoryWithSchema[PersonaStatementLinkData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("personaSettingsId", IntType, Vector("parentId", 
			"parent_id", "persona_settings_id")), PropertyDeclaration("statementId", IntType, 
			Single("statement_id")), PropertyDeclaration("orderIndex", IntType, Single("order_index"), 0), 
			PropertyDeclaration("descriptionType", IntType, Single("description_type"), Description.id)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		PersonaStatementLinkData(valid("personaSettingsId").getInt, valid("statementId").getInt, 
			valid("orderIndex").getInt, PersonaDescriptionType.fromValue(valid("descriptionType")))
}

/**
  * Links a statement to a Persona description
  * @param personaSettingsId Id of the settings to which this link is tied to
  * @param statementId Id of the statement made within the linked text / entity
  * @param orderIndex 0-based index that indicates the specific location of the placed text
  * @param descriptionType Context in which the linked statement appears
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class PersonaStatementLinkData(personaSettingsId: Int, statementId: Int, orderIndex: Int = 0, 
	descriptionType: PersonaDescriptionType = Description) 
	extends PersonaStatementLinkFactory[PersonaStatementLinkData] with StatementLinkData 
		with StatementLinkDataLike[PersonaStatementLinkData]
{
	// IMPLEMENTED	--------------------
	
	override def parentId = personaSettingsId
	
	override def toModel = 
		super[StatementLinkData].toModel ++ Model(Single("descriptionType" -> descriptionType.id))
	
	override def copyStatementLink(statementId: Int, parentId: Int, orderIndex: Int) = 
		copy(personaSettingsId = parentId, statementId = statementId, orderIndex = orderIndex)
	
	override def withDescriptionType(descriptionType: PersonaDescriptionType) = 
		copy(descriptionType = descriptionType)
	
	override def withPersonaSettingsId(personaSettingsId: Int) = copy(personaSettingsId = personaSettingsId)
}

