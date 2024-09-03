package vf.llama.model.enumeration

import utopia.flow.collection.CollectionExtensions._
import utopia.flow.generic.casting.ValueConversions._
import utopia.flow.generic.model.immutable.Value
import utopia.flow.generic.model.mutable.DataType.IntType
import utopia.flow.generic.model.mutable.DataType.StringType
import utopia.flow.generic.model.template.ValueConvertible
import utopia.flow.operator.equality.EqualsExtensions._

import java.util.NoSuchElementException

/**
  * Represents a meta information which can be provided automatically by this application
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
sealed trait AutomatedMetaInfo extends ValueConvertible
{
	// ABSTRACT	--------------------
	
	/**
	  * id used to represent this automated meta info in database and json
	  */
	def id: Int
	
	
	// IMPLEMENTED	--------------------
	
	override def toValue = id
}

object AutomatedMetaInfo
{
	// ATTRIBUTES	--------------------
	
	/**
	  * All available automated meta info values
	  */
	val values: Vector[AutomatedMetaInfo] = Vector(CurrentDate, CurrentTime, CurrentWeekday, Timezone)
	
	
	// OTHER	--------------------
	
	/**
	  * @param id id representing a automated meta info
	  * 
		@return automated meta info matching the specified id. None if the id didn't match any automated meta info
	  */
	def findForId(id: Int) = values.find { _.id == id }
	
	/**
	  * @param value A value representing an automated meta info id
	  * @return automated meta info matching the specified value. None if the value didn't match any
	  *  automated meta info
	  */
	def findForValue(value: Value) = 
		value.castTo(IntType, 
			StringType) match { case Left(idVal) => findForId(idVal.getInt); case Right(stringVal) => val str = stringVal.getString; values.find { _.toString ~== str } }
	
	/**
	  * @param id id matching a automated meta info
	  * @return automated meta info matching that id. Failure if no matching value was found.
	  */
	def forId(id: Int) = 
		findForId(id).toTry { new NoSuchElementException(s"No value of AutomatedMetaInfo matches id '$id'") }
	
	/**
	  * @param value A value representing an automated meta info id
	  * @return automated meta info matching the specified value, 
	  * when the value is interpreted as an automated meta info id. Failure if no matching value was found.
	  */
	def fromValue(value: Value) = 
		findForValue(value).toTry { new NoSuchElementException(
			s"No value of AutomatedMetaInfo matches id '$value'") }
	
	
	// NESTED	--------------------
	
	/**
	  * Always contains the current date
	  * @since 01.09.2024
	  */
	case object CurrentDate extends AutomatedMetaInfo
	{
		// ATTRIBUTES	--------------------
		
		override val id = 1
	}
	
	/**
	  * Always contains the current local time
	  * @since 01.09.2024
	  */
	case object CurrentTime extends AutomatedMetaInfo
	{
		// ATTRIBUTES	--------------------
		
		override val id = 2
	}
	
	/**
	  * Always contains the current weekday
	  * @since 01.09.2024
	  */
	case object CurrentWeekday extends AutomatedMetaInfo
	{
		// ATTRIBUTES	--------------------
		
		override val id = 3
	}
	
	/**
	  * Contains the local time-zone, based on system information
	  * @since 01.09.2024
	  */
	case object Timezone extends AutomatedMetaInfo
	{
		// ATTRIBUTES	--------------------
		
		override val id = 4
	}
}

