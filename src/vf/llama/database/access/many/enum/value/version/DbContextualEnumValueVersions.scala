package vf.llama.database.access.many.`enum`.value.version

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.enum.ContextualEnumValueVersion

/**
  * The root access point when targeting multiple contextual enum value versions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbContextualEnumValueVersions 
	extends ManyContextualEnumValueVersionsAccess with NonDeprecatedView[ContextualEnumValueVersion] 
		with ViewManyByIntIds[ManyContextualEnumValueVersionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) contextual enum value versions
	  */
	def includingHistory = DbContextualEnumValueVersionsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbContextualEnumValueVersionsIncludingHistory 
		extends ManyContextualEnumValueVersionsAccess with UnconditionalView
}

