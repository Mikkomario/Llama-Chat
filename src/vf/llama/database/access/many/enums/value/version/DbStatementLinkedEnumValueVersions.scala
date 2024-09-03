package vf.llama.database.access.many.enums.value.version

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.enums.StatementLinkedEnumValueVersion

/**
  * The root access point when targeting multiple statement linked enum value versions at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbStatementLinkedEnumValueVersions 
	extends ManyStatementLinkedEnumValueVersionsAccess 
		with NonDeprecatedView[StatementLinkedEnumValueVersion] 
		with ViewManyByIntIds[ManyStatementLinkedEnumValueVersionsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * 
		A copy of this access point that includes historical (i.e. deprecated) statement linked enum value versions
	  */
	def includingHistory = DbStatementLinkedEnumValueVersionsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbStatementLinkedEnumValueVersionsIncludingHistory 
		extends ManyStatementLinkedEnumValueVersionsAccess with UnconditionalView
}

