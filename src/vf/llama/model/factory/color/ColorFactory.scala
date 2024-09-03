package vf.llama.model.factory.color

import utopia.paradigm.angular.Angle

/**
  * Common trait for color-related factories which allow construction with individual properties
  * @tparam A Type of constructed instances
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
trait ColorFactory[+A]
{
	// ABSTRACT	--------------------
	
	/**
	  * @param hue New hue to assign
	  * @return Copy of this item with the specified hue
	  */
	def withHue(hue: Angle): A
	
	/**
	  * @param lightness New lightness to assign
	  * @return Copy of this item with the specified lightness
	  */
	def withLightness(lightness: Double): A
	
	/**
	  * @param predefined New predefined to assign
	  * @return Copy of this item with the specified predefined
	  */
	def withPredefined(predefined: Boolean): A
	
	/**
	  * @param saturation New saturation to assign
	  * @return Copy of this item with the specified saturation
	  */
	def withSaturation(saturation: Double): A
}

