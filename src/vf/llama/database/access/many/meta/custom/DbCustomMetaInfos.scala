package vf.llama.database.access.many.meta.custom

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.meta.CustomMetaInfo

/**
  * The root access point when targeting multiple custom meta infos at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbCustomMetaInfos 
	extends ManyCustomMetaInfosAccess with NonDeprecatedView[CustomMetaInfo] 
		with ViewManyByIntIds[ManyCustomMetaInfosAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) custom meta infos
	  */
	def includingHistory = DbCustomMetaInfosIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbCustomMetaInfosIncludingHistory extends ManyCustomMetaInfosAccess with UnconditionalView
}

