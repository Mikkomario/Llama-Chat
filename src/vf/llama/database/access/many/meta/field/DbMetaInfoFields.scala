package vf.llama.database.access.many.meta.field

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.stored.meta.MetaInfoField

/**
  * The root access point when targeting multiple meta info fields at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbMetaInfoFields 
	extends ManyMetaInfoFieldsAccess with NonDeprecatedView[MetaInfoField] 
		with ViewManyByIntIds[ManyMetaInfoFieldsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) meta info fields
	  */
	def includingHistory = DbMetaInfoFieldsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbMetaInfoFieldsIncludingHistory extends ManyMetaInfoFieldsAccess with UnconditionalView
}

