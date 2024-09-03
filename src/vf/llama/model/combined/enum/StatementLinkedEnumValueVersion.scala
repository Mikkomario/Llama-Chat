package vf.llama.model.combined.`enum`

import vf.llama.model.stored.enum.{EnumValueStatementLink, EnumValueVersion}

object StatementLinkedEnumValueVersion
{
	// OTHER	--------------------
	
	/**
	  * @param valueVersion value version to wrap
	  * @param link link to attach to this value version
	  * @return Combination of the specified value version and link
	  */
	def apply(valueVersion: EnumValueVersion, 
		link: Seq[EnumValueStatementLink]): StatementLinkedEnumValueVersion = 
		_StatementLinkedEnumValueVersion(valueVersion, link)
	
	
	// NESTED	--------------------
	
	/**
	  * @param valueVersion value version to wrap
	  * @param link link to attach to this value version
	  */
	private case class _StatementLinkedEnumValueVersion(valueVersion: EnumValueVersion, 
		link: Seq[EnumValueStatementLink]) 
		extends StatementLinkedEnumValueVersion
	{
		// IMPLEMENTED	--------------------
		
		override protected def wrap(factory: EnumValueVersion) = copy(valueVersion = factory)
	}
}

/**
  * Attaches statement links to an enumeration value version where they are made
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait StatementLinkedEnumValueVersion extends CombinedEnumValueVersion[StatementLinkedEnumValueVersion]
{
	// ABSTRACT	--------------------
	
	/**
	  * Wrapped enum value version
	  */
	def valueVersion: EnumValueVersion
	
	/**
	  * Link that are attached to this value version
	  */
	def link: Seq[EnumValueStatementLink]
	
	
	// IMPLEMENTED	--------------------
	
	override def enumValueVersion = valueVersion
}

