package vf.llama.database.access.single.meta.field

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.combined.meta.DetailedMetaInfoField

/**
  * An access point to individual detailed meta info fields, based on their field id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleDetailedMetaInfoField(id: Int) 
	extends UniqueDetailedMetaInfoFieldAccess with SingleIntIdModelAccess[DetailedMetaInfoField]

