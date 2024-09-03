package vf.llama.database.storable.meta

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.meta.CustomMetaInfoFactory
import vf.llama.model.partial.meta.CustomMetaInfoData
import vf.llama.model.stored.meta.CustomMetaInfo

import java.time.Instant

/**
  * Used for constructing CustomMetaInfoDbModel instances and for inserting custom meta infos to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object CustomMetaInfoDbModel 
	extends StorableFactory[CustomMetaInfoDbModel, CustomMetaInfo, CustomMetaInfoData] 
		with FromIdFactory[Int, CustomMetaInfoDbModel] with HasIdProperty 
		with CustomMetaInfoFactory[CustomMetaInfoDbModel] with DeprecatableAfter[CustomMetaInfoDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with names
	  */
	lazy val name = property("name")
	
	/**
	  * Database property used for interacting with values
	  */
	lazy val value = property("value")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deprecation times
	  */
	lazy val deprecatedAfter = property("deprecatedAfter")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.customMetaInfo
	
	override def apply(data: CustomMetaInfoData) = 
		apply(None, data.name, data.value, Some(data.created), data.deprecatedAfter)
	
	/**
	  * @param created Time when this custom meta info was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this version was replaced with another
	  * @return A model containing only the specified deprecated after
	  */
	override
		 def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param name Name of this field
	  * @return A model containing only the specified name
	  */
	override def withName(name: String) = apply(name = name)
	
	/**
	  * @param value Value assigned to this field
	  * @return A model containing only the specified value
	  */
	override def withValue(value: String) = apply(value = value)
	
	override protected def complete(id: Value, data: CustomMetaInfoData) = CustomMetaInfo(id.getInt, data)
}

/**
  * Used for interacting with CustomMetaInfos in the database
  * @param id custom meta info database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class CustomMetaInfoDbModel(id: Option[Int] = None, name: String = "", value: String = "", 
	created: Option[Instant] = None, deprecatedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, CustomMetaInfoDbModel] 
		with CustomMetaInfoFactory[CustomMetaInfoDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = CustomMetaInfoDbModel.table
	
	override def valueProperties = 
		Vector(CustomMetaInfoDbModel.id.name -> id, CustomMetaInfoDbModel.name.name -> name, 
			CustomMetaInfoDbModel.value.name -> value, CustomMetaInfoDbModel.created.name -> created, 
			CustomMetaInfoDbModel.deprecatedAfter.name -> deprecatedAfter)
	
	/**
	  * @param created Time when this custom meta info was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this version was replaced with another
	  * @return A new copy of this model with the specified deprecated after
	  */
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param name Name of this field
	  * @return A new copy of this model with the specified name
	  */
	override def withName(name: String) = copy(name = name)
	
	/**
	  * @param value Value assigned to this field
	  * @return A new copy of this model with the specified value
	  */
	override def withValue(value: String) = copy(value = value)
}

