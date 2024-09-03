package vf.llama.model.enumeration

import utopia.flow.collection.immutable.Pair
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ValueConvertible
import utopia.flow.operator.equality.EqualsExtensions._

/**
  * Lists different roles statements can possibly have in summaries
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
sealed trait SummaryStatementRole extends ValueConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * id used to represent this summary statement role in database and json
	  */
	def id: Int
	
	
	// IMPLEMENTED	--------------------
	
	override def toValue = id
}

object SummaryStatementRole
{
	// ATTRIBUTES	--------------------
	
	/**
	  * All available summary statement role values
	  */
	val values: Pair[SummaryStatementRole] = Pair(Body, Header)
	
	
	// COMPUTED	--------------------
	
	/**
	  * The default summary statement role (i.e. body)
	  */
	def default = Body
	
	
	// OTHER	--------------------
	
	/**
	  * @param id id representing a summary statement role
	  * @return summary statement role matching the specified id. None if the id didn't match any
	  *  summary statement role
	  */
	def findForId(id: Int) = values.find { _.id == id }
	
	/**
	  * @param value A value representing an summary statement role id
	  * @return summary statement role matching the specified value. None if the value didn't match any
	  *  summary statement role
	  */
	def findForValue(value: Value) = 
		value.castTo(IntType, 
			StringType) match { case Left(idVal) => findForId(idVal.getInt); case Right(stringVal) => val str = stringVal.getString; values.find { _.toString ~== str } }
	
	/**
	  * @param id id matching a summary statement role
	  * @return summary statement role matching that id, or the default summary statement role (body)
	  */
	def forId(id: Int) = findForId(id).getOrElse(default)
	
	/**
	  * @param value A value representing an summary statement role id
	  * @return summary statement role matching the specified value, 
	  * when the value is interpreted as an summary statement role id, 
	  * or the default summary statement role (body)
	  */
	def fromValue(value: Value) = findForValue(value).getOrElse(default)
	
	
	// NESTED	--------------------
	
	/**
	  * Indicates that a statement appears within the summary body text
	  * @since 01.09.2024
	  */
	case object Body extends SummaryStatementRole
	{
		// ATTRIBUTES	--------------------
		
		override val id = 1
	}
	
	/**
	  * Indicates that a statement appears within the summary header / synopsis
	  * @since 01.09.2024
	  */
	case object Header extends SummaryStatementRole
	{
		// ATTRIBUTES	--------------------
		
		override val id = 2
	}
}

