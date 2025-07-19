package vf.llama.database.storable.meta

import utopia.vault.model.immutable.Storable
import utopia.vault.model.template.{FromIdFactory, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import vf.llama.model.factory.meta.MetaInfoAvailabilityFactory

/**
  * Common trait for factories used for constructing meta info availability database models
  * @tparam DbModel Type of database interaction models constructed
  * @tparam A Type of read instances
  * @tparam Data Supported data-part type
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait MetaInfoAvailabilityDbModelFactoryLike[+DbModel <: Storable, +A, -Data] 
	extends StorableFactory[DbModel, A, Data] with FromIdFactory[Int, DbModel] with HasIdProperty 
		with MetaInfoAvailabilityFactory[DbModel]

