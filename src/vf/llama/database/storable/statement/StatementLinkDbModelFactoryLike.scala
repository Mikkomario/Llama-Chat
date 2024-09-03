package vf.llama.database.storable.statement

import utopia.logos.database.storable.text.TextPlacementDbModelFactoryLike
import utopia.vault.model.immutable.Storable
import vf.llama.model.factory.statement.StatementLinkFactory

/**
  * Common trait for factories used for constructing statement link database models
  * @tparam DbModel Type of database interaction models constructed
  * @tparam A Type of read instances
  * @tparam Data Supported data-part type
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkDbModelFactoryLike[+DbModel <: Storable, +A, -Data] 
	extends TextPlacementDbModelFactoryLike[DbModel, A, Data] with StatementLinkFactory[DbModel]

