package vf.llama.database.access.many.meta.field

import utopia.vault.nosql.view.{NonDeprecatedView, UnconditionalView, ViewManyByIntIds}
import vf.llama.model.combined.meta.DetailedMetaInfoField

/**
  * The root access point when targeting multiple detailed meta info fields at a time
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object DbDetailedMetaInfoFields 
	extends ManyDetailedMetaInfoFieldsAccess with NonDeprecatedView[DetailedMetaInfoField] 
		with ViewManyByIntIds[ManyDetailedMetaInfoFieldsAccess]
{
	// COMPUTED	--------------------
	
	/**
	  * A copy of this access point that includes historical (i.e. deprecated) detailed meta info fields
	  */
	def includingHistory = DbDetailedMetaInfoFieldsIncludingHistory
	
	
	// NESTED	--------------------
	
	object DbDetailedMetaInfoFieldsIncludingHistory 
		extends ManyDetailedMetaInfoFieldsAccess with UnconditionalView
}

