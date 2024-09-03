package vf.llama.database.access.many.`enum`.value.version

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.enum.EnumValueVersion

/**
  * The root access point when targeting multiple enum value versions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbEnumValueVersions 
	extends ManyEnumValueVersionsAccess with NonDeprecatedView[EnumValueVersion] 
		with ViewManyByIntIds[ManyEnumValueVersionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) enum value versions
	  */
	def includingHistory = DbEnumValueVersionsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbEnumValueVersionsIncludingHistory extends ManyEnumValueVersionsAccess with UnconditionalView
}

