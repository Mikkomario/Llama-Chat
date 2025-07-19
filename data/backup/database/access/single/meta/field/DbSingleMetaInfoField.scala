package vf.llama.database.access.single.meta.field

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.meta.MetaInfoField

/**
  * An access point to individual meta info fields, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleMetaInfoField(id: Int) 
	extends UniqueMetaInfoFieldAccess with SingleIntIdModelAccess[MetaInfoField]

