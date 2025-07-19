package vf.llama.database

import vf.llama.util.Common._
import utopia.vault.database.Tables
import utopia.vault.model.immutable.Table

/**
  * Used for accessing the database tables introduced in this project
  * @author Mikko Hilpinen
  * @since 01.09.2024, v0.1
  */
object LlamaChatTables extends Tables(cPool)
{
	// ATTRIBUTES   ----------------
	
	private val dbName = "llama_db"
	
	
	// COMPUTED	--------------------
	
	/**
	  * Table that contains animation emotion assignments (Contains an assessment of an animation's
	  *  emotional expression)
	  */
	def animationEmotionAssignment = apply("animation_emotion_assignment")
	
	/**
	  * Table that contains colors (Represents a color)
	  */
	def color = apply("color")
	
	/**
	  * Table that contains conversations (Represents an individual conversation with a persona)
	  */
	def conversation = apply("conversation")
	
	/**
	  * Table that contains conversation summaries (Summarizes a conversation)
	  */
	def conversationSummary = apply("conversation_summary")
	
	/**
	  * Table that contains conversation summary statement links (Links statements to conversation summaries in which
	  *  they appear)
	  */
	def conversationSummaryStatementLink = apply("conversation_summary_statement_link")
	
	/**
	  * Table that contains custom meta infos (Represents a user-defined meta info field definition)
	  */
	def customMetaInfo = apply("custom_meta_info")
	
	/**
	  * Table that contains enums (Represents an enumeration)
	  */
	def enums = apply("enum")
	
	/**
	  * Table that contains enum values (Represents an individual enumeration value)
	  */
	def enumValue = apply("enum_value")
	
	/**
	  * Table that contains enum value statement links (Common trait for links between statements and the texts
	  *  where they are made)
	  */
	def enumValueStatementLink = apply("enum_value_statement_link")
	
	/**
	  * Table that contains enum value versions (Represents a specific version of an enumeration value)
	  */
	def enumValueVersion = apply("enum_value_version")
	
	/**
	  * Table that contains instructions (Represents a behavioral instruction that may be given to an LLM)
	  */
	def instruction = apply("instruction")
	
	/**
	  * Table that contains instruction statement links (Attaches a statement to an instruction where
	  *  that statement appears)
	  */
	def instructionStatementLink = apply("instruction_statement_link")
	
	/**
	  * Table that contains instruction versions (Represents a specific instruction version.
	 * Different versions may have different configurations, syntax, etc.)
	  */
	def instructionVersion = apply("instruction_version")
	
	/**
	  * Table that contains llms (Represents a large language model)
	  */
	def llm = apply("llm")
	
	/**
	  * Table that contains llm size variants (Represents a specific version (size) of a large language model)
	  */
	def llmSizeVariant = apply("llm_size_variant")
	
	/**
	  * Table that contains memories (Represents a memory recorded during a conversation)
	  */
	def memory = apply("memory")
	
	/**
	  * Table that contains memory statement links (Links statements to memories in which they appear)
	  */
	def memoryStatementLink = apply("memory_statement_link")
	
	/**
	  * Table that contains messages (Represents a single message from the user or from a persona)
	  */
	def message = apply("message")
	
	/**
	  * Table that contains message statement links (Links statements to messages where they appear)
	  */
	def messageStatementLink = apply("message_statement_link")
	
	/**
	  * Table that contains meta info fields (Represents an individual meta info value that can be made available
	  *  or unavailable)
	  */
	def metaInfoField = apply("meta_info_field")
	
	/**
	  * Table that contains personas (Represents a personalized large language model)
	  */
	def persona = apply("persona")
	
	/**
	  * Table that contains persona animations (Represents an individual animation representing some state
	  *  of a Persona)
	  */
	def personaAnimation = apply("persona_animation")
	
	/**
	  * Table that contains persona images (Represents an individual image / frame within a Persona animation)
	  */
	def personaImage = apply("persona_image")
	
	/**
	  * Table that contains persona image sets (Represents a set of images used for visualizing a Persona)
	  */
	def personaImageSet = apply("persona_image_set")
	
	/**
	  * Table that contains persona image set links (Links a Persona with an image set used for that Persona)
	  */
	def personaImageSetLink = apply("persona_image_set_link")
	
	/**
	  * Table that contains persona info availabilities (A link that makes a meta info field available to a persona)
	  */
	def personaInfoAvailability = apply("persona_info_availability")
	
	/**
	  * Table that contains persona instruction links (Links an instruction to a persona to which it applies)
	  */
	def personaInstructionLink = apply("persona_instruction_link")
	
	/**
	  * Table that contains persona parameters (Represents an individual parameter / 
	  * option assignment which is tied to a persona (version))
	  */
	def personaParameter = apply("persona_parameter")
	
	/**
	  * Table that contains persona settings (Records general settings (version) for a Persona)
	  */
	def personaSettings = apply("persona_settings")
	
	/**
	  * Table that contains persona statement links (Links a statement to a Persona description)
	  */
	def personaStatementLink = apply("persona_statement_link")
	
	/**
	  * Table that contains sessions (Represents an individual user session within a conversation)
	  */
	def session = apply("session")
	
	/**
	  * Table that contains session summary statement links (Links statements to session summaries in which
	  *  they appear)
	  */
	def sessionSummaryStatementLink = apply("session_summary_statement_link")
	
	/**
	  * Table that contains threads (Represents a single named sequence of conversations within a specific topic)
	  */
	def thread = apply("thread")
	
	/**
	  * Table that contains thread info availabilities (A link that makes a meta info field available within a
	  *  specific thread)
	  */
	def threadInfoAvailability = apply("thread_info_availability")
	
	/**
	  * Table that contains thread instruction links (Links an instruction to a thread where it applies)
	  */
	def threadInstructionLink = apply("thread_instruction_link")
	
	/**
	  * Table that contains topics (Represents a general conversation topic / theme, 
		assigned to a single persona)
	  */
	def topic = apply("topic")
	
	/**
	  * Table that contains topic info availabilities (A link that makes a meta info field available within a
	  *  specific topic)
	  */
	def topicInfoAvailability = apply("topic_info_availability")
	
	/**
	  * Table that contains topic instruction links (Links an instruction to a topic where it applies)
	  */
	def topicInstructionLink = apply("topic_instruction_link")
	
	/**
	  * Table that contains topic settings (Specifies the name of a topic and the persona 
	  * with whom that topic is discussed)
	  */
	def topicSettings = apply("topic_settings")
	
	
	// OTHER	--------------------
	
	private def apply(tableName: String): Table = apply(dbName, tableName)
}

