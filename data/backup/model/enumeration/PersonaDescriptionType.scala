package vf.llama.model.enumeration

import utopia.flow.collection.immutable.Pair
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ValueConvertible
import utopia.flow.operator.equality.EqualsExtensions._

/**
  * Lists different types of descriptions given to a persona
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
sealed trait PersonaDescriptionType extends ValueConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * id used to represent this persona description type in database and json
	  */
	def id: Int
	
	
	// IMPLEMENTED	--------------------
	
	override def toValue = id
}

object PersonaDescriptionType
{
	// ATTRIBUTES	--------------------
	
	/**
	  * All available persona description type values
	  */
	val values: Pair[PersonaDescriptionType] = Pair(Description, IdentityDescription)
	
	
	// COMPUTED	--------------------
	
	/**
	  * The default persona description type (i.e. description)
	  */
	def default = Description
	
	
	// OTHER	--------------------
	
	/**
	  * @param id id representing a persona description type
	  * @return persona description type matching the specified id. None if the id didn't match any
	  *  persona description type
	  */
	def findForId(id: Int) = values.find { _.id == id }
	
	/**
	  * @param value A value representing an persona description type id
	  * @return persona description type matching the specified value. None if the value didn't match any
	  *  persona description type
	  */
	def findForValue(value: Value) = 
		value.castTo(IntType, 
			StringType) match { case Left(idVal) => findForId(idVal.getInt); case Right(stringVal) => val str = stringVal.getString; values.find { _.toString ~== str } }
	
	/**
	  * @param id id matching a persona description type
	  * @return persona description type matching that id, 
		or the default persona description type (description)
	  */
	def forId(id: Int) = findForId(id).getOrElse(default)
	
	/**
	  * @param value A value representing an persona description type id
	  * @return persona description type matching the specified value, 
	  * when the value is interpreted as an persona description type id, 
	  * or the default persona description type (description)
	  */
	def fromValue(value: Value) = findForValue(value).getOrElse(default)
	
	
	// NESTED	--------------------
	
	/**
	  * General persona description. Used mainly towards the user.
	  * @since 01.09.2024
	  */
	case object Description extends PersonaDescriptionType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 1
	}
	
	/**
	  * A description of a persona's identity. Used as an instruction to said persona on how to behave.
	  * @since 01.09.2024
	  */
	case object IdentityDescription extends PersonaDescriptionType
	{
		// ATTRIBUTES	--------------------
		
		override val id = 2
	}
}

