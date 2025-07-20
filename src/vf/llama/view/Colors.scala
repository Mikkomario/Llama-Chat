package vf.llama.view

import utopia.paradigm.color.ColorRole.{Gray, Info, Primary, Secondary, Tertiary}
import utopia.paradigm.color.{ColorRole, ColorScheme, ColorSet}

/**
 * An interface for the commonly used color-scheme
 *
 * @author Mikko Hilpinen
 * @since 19.07.2025, v0.1
 */
object Colors
{
	// ATTRIBUTES   ------------------------
	
	val orange = ColorSet.fromHexes("#ee9700", "#f0c027", "#ec6800").get
	val cyan = ColorSet.fromHexes("#00ee97", "#b7f8d3", "#00ce5b").get
	val blue = ColorSet.fromHexes("#0057ee", "#718df5", "#0023b9").get
	val red = ColorSet.fromHexes("#ee2000", "#ff603a", "#d60000").get
	val gray = ColorSet.defaultDarkGray
	
	val primary = orange
	val secondary = cyan
	val tertiary = red
	
	val scheme = ColorScheme(primary, Map(
		Primary -> primary, Secondary -> secondary, Tertiary -> tertiary,
		ColorRole.Failure -> red, ColorRole.Warning -> red, Info -> blue,
		ColorRole.Success -> cyan, Gray -> gray))
}
