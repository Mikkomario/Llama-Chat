package vf.llama.database.storable.meta

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.NullDeprecatable
import vf.llama.database.LlamaChatTables
import vf.llama.model.enumeration.AutomatedMetaInfo
import vf.llama.model.factory.meta.MetaInfoFieldFactory
import vf.llama.model.partial.meta.MetaInfoFieldData
import vf.llama.model.stored.meta.MetaInfoField

import java.time.Instant

/**
  * Used for constructing MetaInfoFieldDbModel instances and for inserting meta info fields to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object MetaInfoFieldDbModel 
	extends StorableFactory[MetaInfoFieldDbModel, MetaInfoField, MetaInfoFieldData] 
		with FromIdFactory[Int, MetaInfoFieldDbModel] with HasIdProperty 
		with MetaInfoFieldFactory[MetaInfoFieldDbModel] with NullDeprecatable[MetaInfoFieldDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with automated contents
	  */
	lazy val automatedContent = property("automatedContentId")
	
	/**
	  * Database property used for interacting with custom info ids
	  */
	lazy val customInfoId = property("customInfoId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with archive times
	  */
	lazy val archivedAfter = property("archivedAfter")
	
	override val deprecationAttName = "archivedAfter"
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.metaInfoField
	
	override def apply(data: MetaInfoFieldData) = 
		apply(None, data.automatedContent, data.customInfoId, Some(data.created), data.archivedAfter)
	
	/**
	  * @param archivedAfter Time when this field was archived, if applicable
	  * @return A model containing only the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = apply(archivedAfter = Some(archivedAfter))
	
	/**
	  * @param automatedContent Automatically fillable value. None if this is not an automated field.
	  * @return A model containing only the specified automated content
	  */
	override def withAutomatedContent(automatedContent: AutomatedMetaInfo) = 
		apply(automatedContent = Some(automatedContent))
	
	/**
	  * @param created Time when this meta info field was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * 
		@param customInfoId Id of the customized information in this field. None if this is an automated field.
	  * @return A model containing only the specified custom info id
	  */
	override def withCustomInfoId(customInfoId: Int) = apply(customInfoId = Some(customInfoId))
	
	override def withDeprecatedAfter(deprecationTime: Instant) = withArchivedAfter(deprecationTime)
	
	override def withId(id: Int) = apply(id = Some(id))
	
	override protected def complete(id: Value, data: MetaInfoFieldData) = MetaInfoField(id.getInt, data)
}

/**
  * Used for interacting with MetaInfoFields in the database
  * @param id meta info field database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class MetaInfoFieldDbModel(id: Option[Int] = None, automatedContent: Option[AutomatedMetaInfo] = None, 
	customInfoId: Option[Int] = None, created: Option[Instant] = None, archivedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, MetaInfoFieldDbModel] 
		with MetaInfoFieldFactory[MetaInfoFieldDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = MetaInfoFieldDbModel.table
	
	override def valueProperties = 
		Vector(MetaInfoFieldDbModel.id.name -> id, 
			MetaInfoFieldDbModel.automatedContent.name -> automatedContent.map[Value] { e => e.id }.getOrElse(Value.empty), 
			MetaInfoFieldDbModel.customInfoId.name -> customInfoId, 
			MetaInfoFieldDbModel.created.name -> created, 
			MetaInfoFieldDbModel.archivedAfter.name -> archivedAfter)
	
	/**
	  * @param archivedAfter Time when this field was archived, if applicable
	  * @return A new copy of this model with the specified archived after
	  */
	override def withArchivedAfter(archivedAfter: Instant) = copy(archivedAfter = Some(archivedAfter))
	
	/**
	  * @param automatedContent Automatically fillable value. None if this is not an automated field.
	  * @return A new copy of this model with the specified automated content
	  */
	override def withAutomatedContent(automatedContent: AutomatedMetaInfo) = 
		copy(automatedContent = Some(automatedContent))
	
	/**
	  * @param created Time when this meta info field was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * 
		@param customInfoId Id of the customized information in this field. None if this is an automated field.
	  * @return A new copy of this model with the specified custom info id
	  */
	override def withCustomInfoId(customInfoId: Int) = copy(customInfoId = Some(customInfoId))
	
	override def withId(id: Int) = copy(id = Some(id))
}

