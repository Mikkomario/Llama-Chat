package vf.llama.database.storable.instruction

import utopia.vault.model.immutable.Storable
import utopia.vault.model.template.{FromIdFactory, HasIdProperty}
import utopia.vault.nosql.storable.StorableFactory
import vf.llama.model.factory.instruction.InstructionTargetingLinkFactory

/**
  * Common trait for factories used for constructing instruction targeting link database models
  * @tparam DbModel Type of database interaction models constructed
  * @tparam A Type of read instances
  * @tparam Data Supported data-part type
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait InstructionTargetingLinkDbModelFactoryLike[+DbModel <: Storable, +A, -Data] 
	extends StorableFactory[DbModel, A, Data] with FromIdFactory[Int, DbModel] with HasIdProperty 
		with InstructionTargetingLinkFactory[DbModel]

