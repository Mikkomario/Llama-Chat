package vf.llama.database.access.single.enums.value.version

import utopia.vault.nosql.access.single.model.SingleRowModelAccess
import utopia.vault.nosql.template.Indexed
import utopia.vault.nosql.view.NonDeprecatedView
import utopia.vault.sql.Condition
import vf.llama.database.factory.enums.ContextualEnumValueVersionDbFactory
import vf.llama.database.storable.enums.{EnumValueDbModel, EnumValueVersionDbModel}
import vf.llama.model.combined.enums.ContextualEnumValueVersion

/**
  * Used for accessing individual contextual enum value versions
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbContextualEnumValueVersion 
	extends SingleRowModelAccess[ContextualEnumValueVersion] 
		with NonDeprecatedView[ContextualEnumValueVersion] with Indexed
{
	// COMPUTED	--------------------
	
	/**
	  * A database model (factory) used for interacting with the linked value
	  */
	protected def valueModel = EnumValueDbModel
	
	/**
	  * A database model (factory) used for interacting with linked versions
	  */
	private def model = EnumValueVersionDbModel
	
	
	// IMPLEMENTED	--------------------
	
	override def factory = ContextualEnumValueVersionDbFactory
	
	
	// OTHER	--------------------
	
	/**
	  * @param id Database id of the targeted contextual enum value version
	  * @return An access point to that contextual enum value version
	  */
	def apply(id: Int) = DbSingleContextualEnumValueVersion(id)
	
	/**
	  * 
		@param additionalCondition Filter condition to apply in addition to this root view's condition. Should yield
	  *  unique contextual enum value versions.
	  * @return An access point to the contextual enum value version that satisfies the specified condition
	  */
	private def filterDistinct(additionalCondition: Condition) = 
		UniqueContextualEnumValueVersionAccess(mergeCondition(additionalCondition))
}

