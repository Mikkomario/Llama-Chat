package vf.llama.database.factory.`enum`

import utopia.vault.nosql.factory.multi.MultiCombiningFactory
import utopia.vault.nosql.template.Deprecatable
import vf.llama.model.combined.enum.StatementLinkedEnumValueVersion
import vf.llama.model.stored.enum.{EnumValueStatementLink, EnumValueVersion}

/**
  * Used for reading statement linked enum value versions from the database
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object StatementLinkedEnumValueVersionDbFactory 
	extends MultiCombiningFactory[StatementLinkedEnumValueVersion, EnumValueVersion, EnumValueStatementLink] 
		with Deprecatable
{
	// IMPLEMENTED	--------------------
	
	override def childFactory = EnumValueStatementLinkDbFactory
	
	override def isAlwaysLinked = false
	
	override def nonDeprecatedCondition = parentFactory.nonDeprecatedCondition
	
	override def parentFactory = EnumValueVersionDbFactory
	
	/**
	  * @param valueVersion value version to wrap
	  * @param link link to attach to this value version
	  */
	override def apply(valueVersion: EnumValueVersion, link: Seq[EnumValueStatementLink]) = 
		StatementLinkedEnumValueVersion(valueVersion, link)
}

