package vf.llama.database.access.many.`enum`.value

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.enum.EnumValue

/**
  * The root access point when targeting multiple enum values at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbEnumValues 
	extends ManyEnumValuesAccess with NonDeprecatedView[EnumValue] with ViewManyByIntIds[ManyEnumValuesAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) enum values
	  */
	def includingHistory = DbEnumValuesIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbEnumValuesIncludingHistory extends ManyEnumValuesAccess with UnconditionalView
}

