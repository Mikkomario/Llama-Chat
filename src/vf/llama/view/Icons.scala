package vf.llama.view

import utopia.flow.parse.file.FileExtensions._
import utopia.firmament.image.{ImageCache, SingleColorIcon}
import utopia.genesis.util.Screen.ppi
import utopia.paradigm.measurement.DistanceExtensions._
import utopia.paradigm.shape.shape2d.vector.size.Size
import vf.llama.util.Common._

/**
 * Provides access to icons used in this project
 *
 * @author Mikko Hilpinen
 * @since 18.07.2025, v0.1
 */
object Icons
{
	// ATTRIBUTES   --------------------------
	
	private val cache = ImageCache.icons("data/images/icons", Some(Size.square(1.cm.toPixels)))
	
	
	// COMPUTED ------------------------------
	
	def close = apply("close-48.png")
	def send = apply("send-48.png")
	
	
	// OTHER    ------------------------------
	
	private def apply(name: String) = cache(name)
}
