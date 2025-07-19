package vf.llama.database.access.many.persona.settings

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.persona.StatementLinkedPersonaSettings

/**
  * The root access point when targeting multiple statement linked persona settings at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbManyStatementLinkedPersonaSettings 
	extends ManyStatementLinkedPersonaSettingsAccess with NonDeprecatedView[StatementLinkedPersonaSettings] 
		with ViewManyByIntIds[ManyStatementLinkedPersonaSettingsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * 
		A copy of this access point that includes historical (i.e. deprecated) statement linked persona settings
	  */
	def includingHistory = DbStatementLinkedPersonaSettingsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbStatementLinkedPersonaSettingsIncludingHistory 
		extends ManyStatementLinkedPersonaSettingsAccess with UnconditionalView
}

