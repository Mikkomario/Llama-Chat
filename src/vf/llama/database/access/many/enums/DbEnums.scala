package vf.llama.database.access.many.enums

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.enums.Enum

/**
  * The root access point when targeting multiple enums at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbEnums extends ManyEnumsAccess with NonDeprecatedView[Enum] with ViewManyByIntIds[ManyEnumsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) enums
	  */
	def includingHistory = DbEnumsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbEnumsIncludingHistory extends ManyEnumsAccess with UnconditionalView
}

