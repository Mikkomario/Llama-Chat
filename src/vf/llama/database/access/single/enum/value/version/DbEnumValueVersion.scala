package vf.llama.database.access.single.`enum`.value.version

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.enum.EnumValueVersionDbFactory
import vf.llama.database.storable.enum.EnumValueVersionDbModel
import vf.llama.model.stored.enum.EnumValueVersion

/**
  * Used for accessing individual enum value versions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbEnumValueVersion 
	extends SingleRowModelAccess[EnumValueVersion] with NonDeprecatedView[EnumValueVersion] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * Model which contains the primary database properties interacted with in this access point
	  */
	private def model = EnumValueVersionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = EnumValueVersionDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted enum value version
	  * @return An access point to that enum value version
	  */
	def apply(id: Int) = DbSingleEnumValueVersion(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique enum value versions.
	  * @return An access point to the enum value version that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueEnumValueVersionAccess(mergeCondition(additionalCondition))
}

