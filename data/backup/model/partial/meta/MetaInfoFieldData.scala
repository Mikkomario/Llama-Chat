package vf.llama.model.partial.meta

import utopia.flow.collection.immutable.Single
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.factory.FromModelFactoryWithSchema
import utopia.flow.generic.model.immutable.{Model, ModelDeclaration, PropertyDeclaration, Value}
import utopia.flow.generic.model.mutable.DataType.InstantType
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.template.ModelConvertible
import utopia.flow.time.Now
import vf.llama.model.enumeration.AutomatedMetaInfo
import vf.llama.model.factory.meta.MetaInfoFieldFactory

import java.time.Instant

object MetaInfoFieldData extends FromModelFactoryWithSchema[MetaInfoFieldData]
{
	// ATTRIBUTES	--------------------
	
	override lazy val schema = 
		ModelDeclaration(Vector(PropertyDeclaration("automatedContent", IntType, Single("automated_content"), 
			isOptional = true), PropertyDeclaration("customInfoId", IntType, Single("custom_info_id"), 
			isOptional = true), PropertyDeclaration("created", InstantType, isOptional = true), 
			PropertyDeclaration("archivedAfter", InstantType, Single("archived_after"), isOptional = true)))
	
	
	// IMPLEMENTED	--------------------
	
	override protected def fromValidatedModel(valid: Model) = 
		MetaInfoFieldData(valid("automatedContent").int.flatMap(AutomatedMetaInfo.findForId), 
			valid("customInfoId").int, valid("created").getInstant, valid("archivedAfter").instant)
}

/**
  * Represents an individual meta info value that can be made available or unavailable
  * @param automatedContent Automatically fillable value. None if this is not an automated field.
  * @param customInfoId Id of the customized information in this field. None if this is an automated field.
  * @param created Time when this meta info field was added to the database
  * @param archivedAfter Time when this field was archived, if applicable
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MetaInfoFieldData(automatedContent: Option[AutomatedMetaInfo] = None, 
	customInfoId: Option[Int] = None, created: Instant = Now, archivedAfter: Option[Instant] = None) 
	extends MetaInfoFieldFactory[MetaInfoFieldData] with ModelConvertible
{
	// COMPUTED	--------------------
	
	/**
	  * Whether this meta info field has already been deprecated
	  */
	def isDeprecated = archivedAfter.isDefined
	
	/**
	  * Whether this meta info field is still valid (not deprecated)
	  */
	def isValid = !isDeprecated
	
	
	// IMPLEMENTED	--------------------
	
	override def toModel = 
		Model(Vector("automatedContent" -> automatedContent.map[Value] { e => e.id }.getOrElse(Value.empty), 
			"customInfoId" -> customInfoId, "created" -> created, "archivedAfter" -> archivedAfter))
	
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	override def withAutomatedContent(automatedContent: AutomatedMetaInfo) = 
		copy(automatedContent = Some(automatedContent))
	
	override def withCreated(created: Instant) = copy(created = created)
	
	override def withCustomInfoId(customInfoId: Int) = copy(customInfoId = Some(customInfoId))
}

