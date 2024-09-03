-- 
-- Database structure for llama chat models
-- Version: v0.1
-- Last generated: 2024-09-01
--

CREATE DATABASE IF NOT EXISTS `llama_db` 
	DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE `llama_db`;

--	Llm	----------

-- Represents a large language model
-- name:          Name of this LLM in Ollama
-- alias:         Alias given to this LLM
-- created:       Time when this llm was added to the database
-- removed_after: Time when this LLM was removed from this application
CREATE TABLE `llm`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`name` VARCHAR(32) NOT NULL, 
	`alias` VARCHAR(32), 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`removed_after` DATETIME, 
	INDEX l_removed_after_idx (`removed_after`)
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a specific version (size) of a large language model
-- llm_id:        Id of the LLM this is a variant of
-- suffix:        Suffix added to the end of the LLM's name when targeting this specific variant
-- size:          Size of this LLM variant in billions of parameters. None if unknown.
-- created:       Time when this llm size variant was added to the database
-- removed_after: Time when this specific variant was removed from this application
CREATE TABLE `llm_size_variant`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`llm_id` INT NOT NULL, 
	`suffix` VARCHAR(32), 
	`size` INT, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`removed_after` DATETIME, 
	INDEX lsv_removed_after_idx (`removed_after`), 
	CONSTRAINT lsv_l_llm_ref_fk FOREIGN KEY lsv_l_llm_ref_idx (llm_id) REFERENCES `llm`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Persona	----------

-- Represents a personalized large language model
-- created:       Time when this persona was added to the database
-- deleted_after: Time when this persona was deleted
CREATE TABLE `persona`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deleted_after` DATETIME, 
	INDEX p_deleted_after_idx (`deleted_after`)
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a set of images used for visualizing a Persona
-- relative_directory: Path to the directory that contains the images used in this image set. 
-- 		Relative to the main image directory.
-- created:            Time when this persona image set was added to the database
-- removed_after:      Time when this image set was removed or deleted
CREATE TABLE `persona_image_set`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`relative_directory` VARCHAR(255), 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`removed_after` DATETIME, 
	INDEX pis_relative_directory_idx (`relative_directory`), 
	INDEX pis_created_idx (`created`), 
	INDEX pis_removed_after_idx (`removed_after`)
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an individual animation representing some state of a Persona
-- set_id:                        Id of the set to which this animation belongs
-- relative_sub_directory:        Path to the directory that contains image files specific to this animation. 
-- 		Relative to the image set's directory.
-- default_frame_duration_millis: Duration how long each frame will be shown by default. 
-- 		None if the common default should be used instead.
CREATE TABLE `persona_animation`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`set_id` INT NOT NULL, 
	`relative_sub_directory` VARCHAR(255), 
	`default_frame_duration_millis` BIGINT, 
	CONSTRAINT pa_pis_set_ref_fk FOREIGN KEY pa_pis_set_ref_idx (set_id) REFERENCES `persona_image_set`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links a Persona with an image set used for that Persona
-- persona_id:    Id of the persona for which the linked images are used
-- image_set_id:  Id of the image set used for the linked persona
-- created:       Time when this persona image set link was added to the database
-- removed_after: Time when this link was removed
CREATE TABLE `persona_image_set_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`persona_id` INT NOT NULL, 
	`image_set_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`removed_after` DATETIME, 
	INDEX pisl_removed_after_idx (`removed_after`), 
	CONSTRAINT pisl_p_persona_ref_fk FOREIGN KEY pisl_p_persona_ref_idx (persona_id) REFERENCES `persona`(`id`) ON DELETE CASCADE, 
	CONSTRAINT pisl_pis_image_set_ref_fk FOREIGN KEY pisl_pis_image_set_ref_idx (image_set_id) REFERENCES `persona_image_set`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Records general settings (version) for a Persona
-- persona_id:       Id of the persona which these settings describe
-- llm_variant_id:   Id of the LLM variant this persona wraps
-- name:             Name assigned for this persona
-- created:          Time when this persona settings was added to the database
-- deprecated_after: Time when these settings were replaced with a new version
CREATE TABLE `persona_settings`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`persona_id` INT NOT NULL, 
	`llm_variant_id` INT NOT NULL, 
	`name` VARCHAR(32), 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX ps_name_idx (`name`), 
	INDEX ps_created_idx (`created`), 
	INDEX ps_deprecated_after_idx (`deprecated_after`), 
	CONSTRAINT ps_p_persona_ref_fk FOREIGN KEY ps_p_persona_ref_idx (persona_id) REFERENCES `persona`(`id`) ON DELETE CASCADE, 
	CONSTRAINT ps_lsv_llm_variant_ref_fk FOREIGN KEY ps_lsv_llm_variant_ref_idx (llm_variant_id) REFERENCES `llm_size_variant`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Contains an assessment of an animation's emotional expression
-- animation_id:     Id of the animation this assignment describes
-- emotion:          Name of the emotion assigned to this animation
-- origin_llm_id:    Id of the LLM (variant) which assigned this emotion. None if not assigned by a (known) LLM.
-- created:          Time when this animation emotion assignment was added to the database
-- deprecated_after: Time when this assignment was replaced or retracted
CREATE TABLE `animation_emotion_assignment`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`animation_id` INT NOT NULL, 
	`emotion` VARCHAR(12) NOT NULL, 
	`origin_llm_id` INT, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX aea_deprecated_after_idx (`deprecated_after`), 
	CONSTRAINT aea_pa_animation_ref_fk FOREIGN KEY aea_pa_animation_ref_idx (animation_id) REFERENCES `persona_animation`(`id`) ON DELETE CASCADE, 
	CONSTRAINT aea_lsv_origin_llm_ref_fk FOREIGN KEY aea_lsv_origin_llm_ref_idx (origin_llm_id) REFERENCES `llm_size_variant`(`id`) ON DELETE SET NULL
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an individual image / frame within a Persona animation
-- animation_id:           Id of the animation this image is part of
-- file_name:              Name of the wrapped image file
-- order_index:            0-based index of this image within its animation
-- custom_duration_millis: Duration how long this image / frame should be displayed at once. 
-- 		None if the default duration should be applied.
CREATE TABLE `persona_image`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`animation_id` INT NOT NULL, 
	`file_name` VARCHAR(32) NOT NULL, 
	`order_index` TINYINT NOT NULL DEFAULT 0, 
	`custom_duration_millis` BIGINT, 
	INDEX pi_combo_1_idx (animation_id, order_index), 
	CONSTRAINT pi_pa_animation_ref_fk FOREIGN KEY pi_pa_animation_ref_idx (animation_id) REFERENCES `persona_animation`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an individual parameter / option assignment which is tied to a persona (version)
-- settings_id: Id of the persona settings this parameter assignment is part of
-- key:         Name / key of the adjusted parameter
-- value:       Value assigned to this parameter
CREATE TABLE `persona_parameter`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`settings_id` INT NOT NULL, 
	`key` VARCHAR(6) NOT NULL, 
	`value` VARCHAR(32), 
	CONSTRAINT pp_ps_settings_ref_fk FOREIGN KEY pp_ps_settings_ref_idx (settings_id) REFERENCES `persona_settings`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links a statement to a Persona description
-- persona_settings_id: Id of the settings to which this link is tied to
-- statement_id:        Id of the statement made within the linked text / entity
-- order_index:         0-based index that indicates the specific location of the placed text
-- description_type_id: Context in which the linked statement appears
-- 		References enumeration PersonaDescriptionType
-- 		Possible values are: 1 = description, 2 = identity description
CREATE TABLE `persona_statement_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`persona_settings_id` INT NOT NULL, 
	`statement_id` INT NOT NULL, 
	`order_index` TINYINT NOT NULL DEFAULT 0, 
	`description_type_id` TINYINT NOT NULL, 
	CONSTRAINT psl_ps_persona_settings_ref_fk FOREIGN KEY psl_ps_persona_settings_ref_idx (persona_settings_id) REFERENCES `persona_settings`(`id`) ON DELETE CASCADE, 
	CONSTRAINT psl_st_statement_ref_fk FOREIGN KEY psl_st_statement_ref_idx (statement_id) REFERENCES `statement`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Conversation	----------

-- Represents a general conversation topic / theme, assigned to a single persona
-- created:          Time when this topic was added to the database
-- deprecated_after: Time when this topic was archived
CREATE TABLE `topic`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX to_created_idx (`created`), 
	INDEX to_deprecated_after_idx (`deprecated_after`)
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a single named sequence of conversations within a specific topic
-- topic_id:       Id of the general topic of this thread
-- name:           The current name of this thread
-- created:        Time when this thread was added to the database
-- archived_after: Time when this thread was archived
CREATE TABLE `thread`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`topic_id` INT NOT NULL, 
	`name` VARCHAR(16), 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`archived_after` DATETIME, 
	INDEX th_name_idx (`name`), 
	INDEX th_created_idx (`created`), 
	INDEX th_archived_after_idx (`archived_after`), 
	CONSTRAINT th_to_topic_ref_fk FOREIGN KEY th_to_topic_ref_idx (topic_id) REFERENCES `topic`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Specifies the name of a topic and the persona with whom that topic is discussed
-- topic_id:         Id of the topic these settings describe
-- persona_id:       Id of the persona with whom this topic is discussed
-- name:             Name assigned to this topic
-- created:          Time when this topic settings was added to the database
-- deprecated_after: Time when these settings were replaced with another version
CREATE TABLE `topic_settings`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`topic_id` INT NOT NULL, 
	`persona_id` INT NOT NULL, 
	`name` VARCHAR(24), 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX ts_name_idx (`name`), 
	INDEX ts_created_idx (`created`), 
	INDEX ts_deprecated_after_idx (`deprecated_after`), 
	CONSTRAINT ts_to_topic_ref_fk FOREIGN KEY ts_to_topic_ref_idx (topic_id) REFERENCES `topic`(`id`) ON DELETE CASCADE, 
	CONSTRAINT ts_p_persona_ref_fk FOREIGN KEY ts_p_persona_ref_idx (persona_id) REFERENCES `persona`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an individual conversation with a persona
-- thread_id:    Id of the thread this conversation is part of
-- created:      Time when this conversation was added to the database
-- closed_after: Time when this conversation was closed the last time
CREATE TABLE `conversation`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`thread_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`closed_after` DATETIME, 
	INDEX con_created_idx (`created`), 
	INDEX con_closed_after_idx (`closed_after`), 
	CONSTRAINT con_th_thread_ref_fk FOREIGN KEY con_th_thread_ref_idx (thread_id) REFERENCES `thread`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Summarizes a conversation
-- conversation_id:  Id of the conversation this is a summary of
-- created:          Time when this conversation summary was added to the database
-- deprecated_after: Time when this summary was replaced with a new version
CREATE TABLE `conversation_summary`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`conversation_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX cs_created_idx (`created`), 
	INDEX cs_deprecated_after_idx (`deprecated_after`), 
	CONSTRAINT cs_con_conversation_ref_fk FOREIGN KEY cs_con_conversation_ref_idx (conversation_id) REFERENCES `conversation`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an individual user session within a conversation
-- conversation_id: Id of the conversation active during this session
-- started:         Time when this session started
-- ended:           Time when this session ended. None while active.
CREATE TABLE `session`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`conversation_id` INT NOT NULL, 
	`started` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`ended` DATETIME, 
	INDEX se_started_idx (`started`), 
	INDEX se_ended_idx (`ended`), 
	CONSTRAINT se_con_conversation_ref_fk FOREIGN KEY se_con_conversation_ref_idx (conversation_id) REFERENCES `conversation`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links statements to conversation summaries in which they appear
-- summary_id:   Id of the linked conversation summary
-- statement_id: Id of the statement made within the linked text / entity
-- order_index:  0-based index that indicates the specific location of the placed text
-- role_id:      Role of the linked statement in the linked summary
-- 		References enumeration SummaryStatementRole
-- 		Possible values are: 1 = body, 2 = header
CREATE TABLE `conversation_summary_statement_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`summary_id` INT NOT NULL, 
	`statement_id` INT NOT NULL, 
	`order_index` TINYINT NOT NULL DEFAULT 0, 
	`role_id` TINYINT NOT NULL, 
	INDEX cssl_role_id_idx (`role_id`), 
	CONSTRAINT cssl_cs_summary_ref_fk FOREIGN KEY cssl_cs_summary_ref_idx (summary_id) REFERENCES `conversation_summary`(`id`) ON DELETE CASCADE, 
	CONSTRAINT cssl_st_statement_ref_fk FOREIGN KEY cssl_st_statement_ref_idx (statement_id) REFERENCES `statement`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a single message from the user or from a persona
-- session_id: Id of the session during which this message was posted
-- sender_id:  Role of the entity that sent this message
-- 		References enumeration ChatRole
-- 		Possible values are: 
-- created:    Time when this message was posted
CREATE TABLE `message`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`session_id` INT NOT NULL, 
	`sender_id` TINYINT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	INDEX mes_sender_id_idx (`sender_id`), 
	INDEX mes_created_idx (`created`), 
	CONSTRAINT mes_se_session_ref_fk FOREIGN KEY mes_se_session_ref_idx (session_id) REFERENCES `session`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links statements to session summaries in which they appear
-- session_id:   Id of the session being summarized
-- statement_id: Id of the statement made within the linked text / entity
-- order_index:  0-based index that indicates the specific location of the placed text
CREATE TABLE `session_summary_statement_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`session_id` INT NOT NULL, 
	`statement_id` INT NOT NULL, 
	`order_index` TINYINT NOT NULL DEFAULT 0, 
	CONSTRAINT sssl_se_session_ref_fk FOREIGN KEY sssl_se_session_ref_idx (session_id) REFERENCES `session`(`id`) ON DELETE CASCADE, 
	CONSTRAINT sssl_st_statement_ref_fk FOREIGN KEY sssl_st_statement_ref_idx (statement_id) REFERENCES `statement`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a memory recorded during a conversation
-- message_id:     Id of the message from which this memory was triggered
-- archived_after: Time when this memory was archived
CREATE TABLE `memory`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`message_id` INT NOT NULL, 
	`archived_after` DATETIME, 
	INDEX mem_archived_after_idx (`archived_after`), 
	CONSTRAINT mem_mes_message_ref_fk FOREIGN KEY mem_mes_message_ref_idx (message_id) REFERENCES `message`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links statements to messages where they appear
-- message_id:   Id of the message where the linked statement appears
-- statement_id: Id of the statement made within the linked text / entity
-- order_index:  0-based index that indicates the specific location of the placed text
CREATE TABLE `message_statement_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`message_id` INT NOT NULL, 
	`statement_id` INT NOT NULL, 
	`order_index` TINYINT NOT NULL DEFAULT 0, 
	CONSTRAINT mstl_mes_message_ref_fk FOREIGN KEY mstl_mes_message_ref_idx (message_id) REFERENCES `message`(`id`) ON DELETE CASCADE, 
	CONSTRAINT mstl_st_statement_ref_fk FOREIGN KEY mstl_st_statement_ref_idx (statement_id) REFERENCES `statement`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links statements to memories in which they appear
-- memory_id:    Id of the memory where the linked statement appears
-- statement_id: Id of the statement made within the linked text / entity
-- order_index:  0-based index that indicates the specific location of the placed text
CREATE TABLE `memory_statement_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`memory_id` INT NOT NULL, 
	`statement_id` INT NOT NULL, 
	`order_index` TINYINT NOT NULL DEFAULT 0, 
	CONSTRAINT mstl_mem_memory_ref_fk FOREIGN KEY mstl_mem_memory_ref_idx (memory_id) REFERENCES `memory`(`id`) ON DELETE CASCADE, 
	CONSTRAINT mstl_st_statement_ref_fk FOREIGN KEY mstl_st_statement_ref_idx (statement_id) REFERENCES `statement`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Meta	----------

-- Represents a user-defined meta info field definition
-- name:             Name of this field
-- value:            Value assigned to this field
-- created:          Time when this custom meta info was added to the database
-- deprecated_after: Time when this version was replaced with another
CREATE TABLE `custom_meta_info`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`name` VARCHAR(16) NOT NULL, 
	`value` VARCHAR(32) NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX cmi_name_idx (`name`), 
	INDEX cmi_created_idx (`created`), 
	INDEX cmi_deprecated_after_idx (`deprecated_after`)
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an individual meta info value that can be made available or unavailable
-- automated_content_id: Automatically fillable value. None if this is not an automated field.
-- custom_info_id:       Id of the customized information in this field. None if this is an automated field.
-- created:              Time when this meta info field was added to the database
-- archived_after:       Time when this field was archived, if applicable
CREATE TABLE `meta_info_field`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`automated_content_id` TINYINT, 
	`custom_info_id` INT, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`archived_after` DATETIME, 
	INDEX mif_archived_after_idx (`archived_after`), 
	CONSTRAINT mif_cmi_custom_info_ref_fk FOREIGN KEY mif_cmi_custom_info_ref_idx (custom_info_id) REFERENCES `custom_meta_info`(`id`) ON DELETE SET NULL
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- A link that makes a meta info field available to a persona
-- persona_id: Id of the persona to which the linked information is made available
-- field_id:   Id of the meta info -field made available
-- created:    Time when this information was made available
CREATE TABLE `persona_info_availability`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`persona_id` INT NOT NULL, 
	`field_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	CONSTRAINT pia_p_persona_ref_fk FOREIGN KEY pia_p_persona_ref_idx (persona_id) REFERENCES `persona`(`id`) ON DELETE CASCADE, 
	CONSTRAINT pia_mif_field_ref_fk FOREIGN KEY pia_mif_field_ref_idx (field_id) REFERENCES `meta_info_field`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- A link that makes a meta info field available within a specific thread
-- thread_id: Id of the thread where the linked information is made available
-- field_id:  Id of the meta info -field made available
-- created:   Time when this information was made available
CREATE TABLE `thread_info_availability`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`thread_id` INT NOT NULL, 
	`field_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	CONSTRAINT thia_th_thread_ref_fk FOREIGN KEY thia_th_thread_ref_idx (thread_id) REFERENCES `thread`(`id`) ON DELETE CASCADE, 
	CONSTRAINT thia_mif_field_ref_fk FOREIGN KEY thia_mif_field_ref_idx (field_id) REFERENCES `meta_info_field`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- A link that makes a meta info field available within a specific topic
-- topic_id: Id of the topic where the linked information is made available
-- field_id: Id of the meta info -field made available
-- created:  Time when this information was made available
CREATE TABLE `topic_info_availability`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`topic_id` INT NOT NULL, 
	`field_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	CONSTRAINT toia_to_topic_ref_fk FOREIGN KEY toia_to_topic_ref_idx (topic_id) REFERENCES `topic`(`id`) ON DELETE CASCADE, 
	CONSTRAINT toia_mif_field_ref_fk FOREIGN KEY toia_mif_field_ref_idx (field_id) REFERENCES `meta_info_field`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Color	----------

-- Represents a color
-- hue_degrees: Hue of this color, as an angle
-- saturation:  Saturation (color intensity) of this color
-- lightness:   Lightness of this color. Between 0 (black) and 1 (white).
-- predefined:  Whether this is a predefined color. False if this is a user-defined color.
CREATE TABLE `color`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`hue_degrees` DOUBLE NOT NULL, 
	`saturation` DOUBLE NOT NULL DEFAULT 1.0, 
	`lightness` DOUBLE NOT NULL DEFAULT 0.5, 
	`predefined` BOOLEAN NOT NULL DEFAULT FALSE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Enum	----------

-- Represents an enumeration
-- name:           Name of this enumeration (mutable)
-- created:        Time when this enum was added to the database
-- archived_after: Time when this enumeration was archived
CREATE TABLE `enum`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`name` VARCHAR(16) NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`archived_after` DATETIME, 
	INDEX e_name_idx (`name`), 
	INDEX e_archived_after_idx (`archived_after`)
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an individual enumeration value
-- enum_id:        Id of the enumeration where this value appears
-- created:        Time when this enum value was added to the database
-- archived_after: Time when this enumeration value was archived
CREATE TABLE `enum_value`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`enum_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`archived_after` DATETIME, 
	INDEX ev_archived_after_idx (`archived_after`), 
	CONSTRAINT ev_e_enum_ref_fk FOREIGN KEY ev_e_enum_ref_idx (enum_id) REFERENCES `enum`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a specific version of an enumeration value
-- enum_value_id:    Id of the enumeration value which this version describes
-- name:             Name of this enumeration value
-- color_id:         Id of the color used for this enumeration value
-- created:          Time when this enum value version was added to the database
-- deprecated_after: Time when this version was replaced with a newer one
CREATE TABLE `enum_value_version`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`enum_value_id` INT NOT NULL, 
	`name` VARCHAR(16) NOT NULL, 
	`color_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX evv_created_idx (`created`), 
	INDEX evv_deprecated_after_idx (`deprecated_after`), 
	CONSTRAINT evv_ev_enum_value_ref_fk FOREIGN KEY evv_ev_enum_value_ref_idx (enum_value_id) REFERENCES `enum_value`(`id`) ON DELETE CASCADE, 
	CONSTRAINT evv_col_color_ref_fk FOREIGN KEY evv_col_color_ref_idx (color_id) REFERENCES `color`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Common trait for links between statements and the texts where they are made
-- enum_value_version_id: Id of the enumeration value version where the linked statement appears
-- statement_id:          Id of the statement made within the linked text / entity
-- order_index:           0-based index that indicates the specific location of the placed text
CREATE TABLE `enum_value_statement_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`enum_value_version_id` INT NOT NULL, 
	`statement_id` INT NOT NULL, 
	`order_index` TINYINT NOT NULL DEFAULT 0, 
	CONSTRAINT evsl_evv_enum_value_version_ref_fk FOREIGN KEY evsl_evv_enum_value_version_ref_idx (enum_value_version_id) REFERENCES `enum_value_version`(`id`) ON DELETE CASCADE, 
	CONSTRAINT evsl_st_statement_ref_fk FOREIGN KEY evsl_st_statement_ref_idx (statement_id) REFERENCES `statement`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Instruction	----------

-- Represents a behavioral instruction that may be given to an LLM
-- created: Time when this instruction was added to the database
CREATE TABLE `instruction`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	INDEX i_created_idx (`created`)
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a specific instruction version. Different versions may have different configurations, syntax, etc.
-- name:             Name of this instruction
-- enum_value_id:    Id of the enumeration value selected by applying this instruction. None if not enumeration-based.
-- created:          Time when this instruction version was added to the database
-- deprecated_after: Time when this version was replaced with a new one
CREATE TABLE `instruction_version`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`name` VARCHAR(16), 
	`enum_value_id` INT, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX iv_name_idx (`name`), 
	INDEX iv_created_idx (`created`), 
	INDEX iv_deprecated_after_idx (`deprecated_after`), 
	CONSTRAINT iv_ev_enum_value_ref_fk FOREIGN KEY iv_ev_enum_value_ref_idx (enum_value_id) REFERENCES `enum_value`(`id`) ON DELETE SET NULL
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Attaches a statement to an instruction where that statement appears
-- instruction_version_id: Id of the instruction version where the linked statement is made
-- statement_id:           Id of the statement made within the linked text / entity
-- order_index:            0-based index that indicates the specific location of the placed text
CREATE TABLE `instruction_statement_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`instruction_version_id` INT NOT NULL, 
	`statement_id` INT NOT NULL, 
	`order_index` TINYINT NOT NULL DEFAULT 0, 
	CONSTRAINT isl_iv_instruction_version_ref_fk FOREIGN KEY isl_iv_instruction_version_ref_idx (instruction_version_id) REFERENCES `instruction_version`(`id`) ON DELETE CASCADE, 
	CONSTRAINT isl_st_statement_ref_fk FOREIGN KEY isl_st_statement_ref_idx (statement_id) REFERENCES `statement`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links an instruction to a persona to which it applies
-- persona_id:     Id of the persona to which the linked instruction applies
-- instruction_id: Id of the instruction applied to the linked entity
-- created:        Time when this instruction targeting link was added to the database
-- removed_after:  Time after which this link was removed
CREATE TABLE `persona_instruction_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`persona_id` INT NOT NULL, 
	`instruction_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`removed_after` DATETIME, 
	INDEX pil_created_idx (`created`), 
	INDEX pil_removed_after_idx (`removed_after`), 
	CONSTRAINT pil_p_persona_ref_fk FOREIGN KEY pil_p_persona_ref_idx (persona_id) REFERENCES `persona`(`id`) ON DELETE CASCADE, 
	CONSTRAINT pil_i_instruction_ref_fk FOREIGN KEY pil_i_instruction_ref_idx (instruction_id) REFERENCES `instruction`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links an instruction to a thread where it applies
-- thread_id:      Id of the thread where the linked instruction applies
-- instruction_id: Id of the instruction applied to the linked entity
-- created:        Time when this instruction targeting link was added to the database
-- removed_after:  Time after which this link was removed
CREATE TABLE `thread_instruction_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`thread_id` INT NOT NULL, 
	`instruction_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`removed_after` DATETIME, 
	INDEX thil_created_idx (`created`), 
	INDEX thil_removed_after_idx (`removed_after`), 
	CONSTRAINT thil_th_thread_ref_fk FOREIGN KEY thil_th_thread_ref_idx (thread_id) REFERENCES `thread`(`id`) ON DELETE CASCADE, 
	CONSTRAINT thil_i_instruction_ref_fk FOREIGN KEY thil_i_instruction_ref_idx (instruction_id) REFERENCES `instruction`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Links an instruction to a topic where it applies
-- topic_id:       Id of the topic where the linked instruction applies
-- instruction_id: Id of the instruction applied to the linked entity
-- created:        Time when this instruction targeting link was added to the database
-- removed_after:  Time after which this link was removed
CREATE TABLE `topic_instruction_link`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`topic_id` INT NOT NULL, 
	`instruction_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`removed_after` DATETIME, 
	INDEX toil_created_idx (`created`), 
	INDEX toil_removed_after_idx (`removed_after`), 
	CONSTRAINT toil_to_topic_ref_fk FOREIGN KEY toil_to_topic_ref_idx (topic_id) REFERENCES `topic`(`id`) ON DELETE CASCADE, 
	CONSTRAINT toil_i_instruction_ref_fk FOREIGN KEY toil_i_instruction_ref_idx (instruction_id) REFERENCES `instruction`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

