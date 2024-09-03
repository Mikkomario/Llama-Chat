package vf.llama.database.storable.`enum`

import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.vault.model.immutable.{DbPropertyDeclaration, Storable}
import utopia.vault.model.template.{FromIdFactory, HasId, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import utopia.vault.nosql.storable.deprecation.DeprecatableAfter
import vf.llama.database.LlamaChatTables
import vf.llama.model.factory.enum.EnumValueVersionFactory
import vf.llama.model.partial.enum.EnumValueVersionData
import vf.llama.model.stored.enum.EnumValueVersion

import java.time.Instant

/**
  * 
	Used for constructing EnumValueVersionDbModel instances and for inserting enum value versions to the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object EnumValueVersionDbModel 
	extends StorableFactory[EnumValueVersionDbModel, EnumValueVersion, EnumValueVersionData] 
		with FromIdFactory[Int, EnumValueVersionDbModel] with HasIdProperty 
		with EnumValueVersionFactory[EnumValueVersionDbModel] with DeprecatableAfter[EnumValueVersionDbModel]
{
	// ATTRIBUTES	--------------------
	
	override lazy val id = DbPropertyDeclaration("id", index)
	
	/**
	  * Database property used for interacting with enum value ids
	  */
	lazy val enumValueId = property("enumValueId")
	
	/**
	  * Database property used for interacting with names
	  */
	lazy val name = property("name")
	
	/**
	  * Database property used for interacting with color ids
	  */
	lazy val colorId = property("colorId")
	
	/**
	  * Database property used for interacting with creation times
	  */
	lazy val created = property("created")
	
	/**
	  * Database property used for interacting with deprecation times
	  */
	lazy val deprecatedAfter = property("deprecatedAfter")
	
	
	// IMPLEMENTED	--------------------
	
	override def table = LlamaChatTables.enumValueVersion
	
	override def apply(data: EnumValueVersionData) = 
		apply(None, Some(data.enumValueId), data.name, Some(data.colorId), Some(data.created), 
			data.deprecatedAfter)
	
	/**
	  * @param colorId Id of the color used for this enumeration value
	  * @return A model containing only the specified color id
	  */
	override def withColorId(colorId: Int) = apply(colorId = Some(colorId))
	
	/**
	  * @param created Time when this enum value version was added to the database
	  * @return A model containing only the specified created
	  */
	override def withCreated(created: Instant) = apply(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this version was replaced with a newer one
	  * @return A model containing only the specified deprecated after
	  */
	override
		 def withDeprecatedAfter(deprecatedAfter: Instant) = apply(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param enumValueId Id of the enumeration value which this version describes
	  * @return A model containing only the specified enum value id
	  */
	override def withEnumValueId(enumValueId: Int) = apply(enumValueId = Some(enumValueId))
	
	override def withId(id: Int) = apply(id = Some(id))
	
	/**
	  * @param name Name of this enumeration value
	  * @return A model containing only the specified name
	  */
	override def withName(name: String) = apply(name = name)
	
	override protected def complete(id: Value, data: EnumValueVersionData) = EnumValueVersion(id.getInt, data)
}

/**
  * Used for interacting with EnumValueVersions in the database
  * @param id enum value version database id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class EnumValueVersionDbModel(id: Option[Int] = None, enumValueId: Option[Int] = None, 
	name: String = "", colorId: Option[Int] = None, created: Option[Instant] = None, 
	deprecatedAfter: Option[Instant] = None) 
	extends Storable with HasId[Option[Int]] with FromIdFactory[Int, EnumValueVersionDbModel] 
		with EnumValueVersionFactory[EnumValueVersionDbModel]
{
	// IMPLEMENTED	--------------------
	
	override def table = EnumValueVersionDbModel.table
	
	override def valueProperties = 
		Vector(EnumValueVersionDbModel.id.name -> id, 
			EnumValueVersionDbModel.enumValueId.name -> enumValueId, 
			EnumValueVersionDbModel.name.name -> name, EnumValueVersionDbModel.colorId.name -> colorId, 
			EnumValueVersionDbModel.created.name -> created, 
			EnumValueVersionDbModel.deprecatedAfter.name -> deprecatedAfter)
	
	/**
	  * @param colorId Id of the color used for this enumeration value
	  * @return A new copy of this model with the specified color id
	  */
	override def withColorId(colorId: Int) = copy(colorId = Some(colorId))
	
	/**
	  * @param created Time when this enum value version was added to the database
	  * @return A new copy of this model with the specified created
	  */
	override def withCreated(created: Instant) = copy(created = Some(created))
	
	/**
	  * @param deprecatedAfter Time when this version was replaced with a newer one
	  * @return A new copy of this model with the specified deprecated after
	  */
	override def withDeprecatedAfter(deprecatedAfter: Instant) = copy(deprecatedAfter = Some(deprecatedAfter))
	
	/**
	  * @param enumValueId Id of the enumeration value which this version describes
	  * @return A new copy of this model with the specified enum value id
	  */
	override def withEnumValueId(enumValueId: Int) = copy(enumValueId = Some(enumValueId))
	
	override def withId(id: Int) = copy(id = Some(id))
	
	/**
	  * @param name Name of this enumeration value
	  * @return A new copy of this model with the specified name
	  */
	override def withName(name: String) = copy(name = name)
}

