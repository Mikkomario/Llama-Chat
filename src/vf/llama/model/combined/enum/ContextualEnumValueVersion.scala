package vf.llama.model.combined.`enum`

import vf.llama.model.stored.enum.{EnumValue, EnumValueVersion}

object ContextualEnumValueVersion
{
	// OTHER	--------------------
	
	/**
	  * @param version version to wrap
	  * @param value value to attach to this version
	  * @return Combination of the specified version and value
	  */
	def apply(version: EnumValueVersion, value: EnumValue): ContextualEnumValueVersion = 
		_ContextualEnumValueVersion(version, value)
	
	
	// NESTED	--------------------
	
	/**
	  * @param version version to wrap
	  * @param value value to attach to this version
	  */
	private case class _ContextualEnumValueVersion(version: EnumValueVersion, value: EnumValue) 
		extends ContextualEnumValueVersion
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: EnumValueVersion) = copy(version = factory)
	}
}

/**
  * Includes consistent enumeration value information with this versioned info
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ContextualEnumValueVersion extends CombinedEnumValueVersion[ContextualEnumValueVersion]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped enum value version
	  */
	def version: EnumValueVersion
	
	/**
	  * The value that is attached to this version
	  */
	def value: EnumValue
	
	
	// IMPLEMENTED	--------------------
	
	override def enumValueVersion = version
}

