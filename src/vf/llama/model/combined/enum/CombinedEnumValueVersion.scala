package vf.llama.model.combined.`enum`

import utopia.flow.view.template.Extender
import utopia.vault.model.template.HasId
import vf.llama.model.factory.enum.EnumValueVersionFactoryWrapper
import vf.llama.model.partial.enum.EnumValueVersionData
import vf.llama.model.stored.enum.EnumValueVersion

/**
  * Common trait for combinations that add additional data to enum value versions
  * @tparam Repr Type of the implementing class
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait CombinedEnumValueVersion[+Repr] 
	extends Extender[EnumValueVersionData] with HasId[Int] 
		with EnumValueVersionFactoryWrapper[EnumValueVersion, Repr]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped enum value version
	  */
	def enumValueVersion: EnumValueVersion
	
	
	// IMPLEMENTED	--------------------
	
	/**
	  * Id of this enum value version in the database
	  */
	override def id = enumValueVersion.id
	
	override def wrapped = enumValueVersion.data
	
	override protected def wrappedFactory = enumValueVersion
}

