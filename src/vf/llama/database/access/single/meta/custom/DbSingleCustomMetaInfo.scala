package vf.llama.database.access.single.meta.custom

import utopia.vault.nosql.access.single.model.distinct.SingleIntIdModelAccess
import vf.llama.model.stored.meta.CustomMetaInfo

/**
  * An access point to individual custom meta infos, based on their id
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
case class DbSingleCustomMetaInfo(id: Int) 
	extends UniqueCustomMetaInfoAccess with SingleIntIdModelAccess[CustomMetaInfo]

