package vf.llama.database.factory.enums

import utopia.vault.nosql.factory.row.FromTimelineRowFactory
import utopia.vault.nosql.factory.row.linked.CombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.enums.ContextualEnumValueVersion
import vf.llama.model.stored.enums.{EnumValue, EnumValueVersion}

/**
  * Used for reading contextual enum value versions from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object ContextualEnumValueVersionDbFactory 
	extends CombiningFactory[ContextualEnumValueVersion, EnumValueVersion, EnumValue] 
		with FromTimelineRowFactory[ContextualEnumValueVersion] with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = EnumValueDbFactory
	
	override def nonDeprecatedCondition = 
		parentFactory.nonDeprecatedCondition && childFactory.nonDeprecatedCondition
	
	override def parentFactory = EnumValueVersionDbFactory
	
	override def timestamp = parentFactory.timestamp
	
	/**
	  * @param version version to wrap
	  * @param value value to attach to this version
	  */
	override def apply(version: EnumValueVersion, value: EnumValue) = ContextualEnumValueVersion(version, 
		value)
}

