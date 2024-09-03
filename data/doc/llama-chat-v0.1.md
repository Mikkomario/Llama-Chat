# Llama Chat
Version: **v0.1**  
Updated: 2024-09-01

## Table of Contents
- [Enumerations](#enumerations)
  - [Automated Meta Info](#automated-meta-info)
  - [Persona Description Type](#persona-description-type)
  - [Summary Statement Role](#summary-statement-role)
- [Packages & Classes](#packages-and-classes)
  - [Color](#color)
    - [Color](#color)
  - [Conversation](#conversation)
    - [Conversation](#conversation)
    - [Conversation Summary](#conversation-summary)
    - [Conversation Summary Statement Link](#conversation-summary-statement-link)
    - [Memory](#memory)
    - [Memory Statement Link](#memory-statement-link)
    - [Message](#message)
    - [Message Statement Link](#message-statement-link)
    - [Session](#session)
    - [Session Summary Statement Link](#session-summary-statement-link)
    - [Thread](#thread)
    - [Topic](#topic)
    - [Topic Settings](#topic-settings)
  - [Enum](#enum)
    - [Enum](#enum)
    - [Enum Value](#enum-value)
    - [Enum Value Statement Link](#enum-value-statement-link)
    - [Enum Value Version](#enum-value-version)
  - [Instruction](#instruction)
    - [Instruction](#instruction)
    - [Instruction Statement Link](#instruction-statement-link)
    - [Instruction Targeting Link](#instruction-targeting-link)
    - [Instruction Version](#instruction-version)
    - [Persona Instruction Link](#persona-instruction-link)
    - [Thread Instruction Link](#thread-instruction-link)
    - [Topic Instruction Link](#topic-instruction-link)
  - [Llm](#llm)
    - [Llm](#llm)
    - [Llm Size Variant](#llm-size-variant)
  - [Meta](#meta)
    - [Custom Meta Info](#custom-meta-info)
    - [Meta Info Availability](#meta-info-availability)
    - [Meta Info Field](#meta-info-field)
    - [Persona Info Availability](#persona-info-availability)
    - [Thread Info Availability](#thread-info-availability)
    - [Topic Info Availability](#topic-info-availability)
  - [Persona](#persona)
    - [Animation Emotion Assignment](#animation-emotion-assignment)
    - [Persona](#persona)
    - [Persona Animation](#persona-animation)
    - [Persona Image](#persona-image)
    - [Persona Image Set](#persona-image-set)
    - [Persona Image Set Link](#persona-image-set-link)
    - [Persona Parameter](#persona-parameter)
    - [Persona Settings](#persona-settings)
    - [Persona Statement Link](#persona-statement-link)
  - [Statement](#statement)
    - [Statement Link](#statement-link)
    - [Text Placement](#text-placement)

## Enumerations
Below are listed all enumerations introduced in Llama Chat, in alphabetical order  

### Automated Meta Info
Represents a meta information which can be provided automatically by this application

Key: `id: Int`  

**Values:**
- **Current Date** (1) - Always contains the current date
- **Current Time** (2) - Always contains the current local time
- **Current Weekday** (3) - Always contains the current weekday
- **Timezone** (4) - Contains the local time-zone, based on system information

### Persona Description Type
Lists different types of descriptions given to a persona

Key: `id: Int`  
Default Value: **Description**

**Values:**
- **Description** (1) - General persona description. Used mainly towards the user.
- **Identity Description** (2) - A description of a persona's identity. Used as an instruction to said persona on how to behave.

Utilized by the following 1 classes:
- [Persona Statement Link](#persona-statement-link)

### Summary Statement Role
Lists different roles statements can possibly have in summaries

Key: `id: Int`  
Default Value: **Body**

**Values:**
- **Body** (1) - Indicates that a statement appears within the summary body text
- **Header** (2) - Indicates that a statement appears within the summary header / synopsis

Utilized by the following 1 classes:
- [Conversation Summary Statement Link](#conversation-summary-statement-link)

## Packages and Classes
Below are listed all classes introduced in Llama Chat, grouped by package and in alphabetical order.  
There are a total number of 8 packages and 43 classes

### Color
This package contains the following 1 classes: [Color](#color)

#### Color
Represents a color

##### Details

##### Properties
Color contains the following 4 properties:
- **Hue** - `hue: Angle` - Hue of this color, as an angle
- **Saturation** - `saturation: Double`, `1.0` by default - Saturation (color intensity) of this color
- **Lightness** - `lightness: Double`, `0.5` by default - Lightness of this color. Between 0 (black) and 1 (white).
- **Predefined** - `predefined: Boolean` - Whether this is a predefined color. False if this is a user-defined color.

##### Referenced from
- [Enum Value Version](#enum-value-version).`colorId`

### Conversation
This package contains the following 12 classes: [Conversation](#conversation), [Conversation Summary](#conversation-summary), [Conversation Summary Statement Link](#conversation-summary-statement-link), [Memory](#memory), [Memory Statement Link](#memory-statement-link), [Message](#message), [Message Statement Link](#message-statement-link), [Session](#session), [Session Summary Statement Link](#session-summary-statement-link), [Thread](#thread), [Topic](#topic), [Topic Settings](#topic-settings)

#### Conversation
Represents an individual conversation with a persona

##### Details
- Combines with multiple [Sessions](#session), creating a **Conversation With Sessions**
- Fully **versioned**
- Uses 2 database **indices**: `created`, `closed_after`

##### Properties
Conversation contains the following 3 properties:
- **Thread Id** - `threadId: Int` - Id of the thread this conversation is part of
  - Refers to [Thread](#thread)
- **Created** - `created: Instant` - Time when this conversation was added to the database
- **Closed After** - `closedAfter: Option[Instant]` - Time when this conversation was closed the last time

##### Referenced from
- [Conversation Summary](#conversation-summary).`conversationId`
- [Session](#session).`conversationId`

#### Conversation Summary
Summarizes a conversation

##### Details
- Combines with multiple [Conversation Summary Statement Links](#conversation-summary-statement-link), creating a **Statement Linked Conversation Summary**
- Fully **versioned**
- Uses 2 database **indices**: `created`, `deprecated_after`

##### Properties
Conversation Summary contains the following 3 properties:
- **Conversation Id** - `conversationId: Int` - Id of the conversation this is a summary of
  - Refers to [Conversation](#conversation)
- **Created** - `created: Instant` - Time when this conversation summary was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when this summary was replaced with a new version

##### Referenced from
- [Conversation Summary Statement Link](#conversation-summary-statement-link).`summaryId`

#### Conversation Summary Statement Link
Links statements to conversation summaries in which they appear

##### Details
- Uses **index**: `role_id`

##### Properties
Conversation Summary Statement Link contains the following 4 properties:
- **Summary Id** - `summaryId: Int` - Id of the linked conversation summary
  - Refers to [Conversation Summary](#conversation-summary)
- **Statement Id** - `statementId: Int` - Id of the statement made within the linked text / entity
  - Refers to *statement* from another module
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text
- **Role** - `role: SummaryStatementRole` - Role of the linked statement in the linked summary

#### Memory
Represents a memory recorded during a conversation

##### Details
- Combines with multiple [Memory Statement Links](#memory-statement-link), creating a **Statement Linked Memory**
- Preserves **history**
- Uses **index**: `archived_after`

##### Properties
Memory contains the following 2 properties:
- **Message Id** - `messageId: Int` - Id of the message from which this memory was triggered
  - Refers to [Message](#message)
- **Archived After** - `archivedAfter: Option[Instant]` - Time when this memory was archived

##### Referenced from
- [Memory Statement Link](#memory-statement-link).`memoryId`

#### Memory Statement Link
Links statements to memories in which they appear

##### Details

##### Properties
Memory Statement Link contains the following 3 properties:
- **Memory Id** - `memoryId: Int` - Id of the memory where the linked statement appears
  - Refers to [Memory](#memory)
- **Statement Id** - `statementId: Int` - Id of the statement made within the linked text / entity
  - Refers to *statement* from another module
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text

#### Message
Represents a single message from the user or from a persona

##### Details
- Combines with multiple [Message Statement Links](#message-statement-link), creating a **Statement Linked Message**
- **Chronologically** indexed
- Uses 2 database **indices**: `sender_id`, `created`

##### Properties
Message contains the following 3 properties:
- **Session Id** - `sessionId: Int` - Id of the session during which this message was posted
  - Refers to [Session](#session)
- **Sender** - `sender: ChatRole` - Role of the entity that sent this message
- **Created** - `created: Instant` - Time when this message was posted

##### Referenced from
- [Memory](#memory).`messageId`
- [Message Statement Link](#message-statement-link).`messageId`

#### Message Statement Link
Links statements to messages where they appear

##### Details

##### Properties
Message Statement Link contains the following 3 properties:
- **Message Id** - `messageId: Int` - Id of the message where the linked statement appears
  - Refers to [Message](#message)
- **Statement Id** - `statementId: Int` - Id of the statement made within the linked text / entity
  - Refers to *statement* from another module
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text

#### Session
Represents an individual user session within a conversation

##### Details
- Combines with possibly multiple [Session Summary Statement Links](#session-summary-statement-link), creating a **Summary Statement Linked Session**
- Fully **versioned**
- Uses 2 database **indices**: `started`, `ended`

##### Properties
Session contains the following 3 properties:
- **Conversation Id** - `conversationId: Int` - Id of the conversation active during this session
  - Refers to [Conversation](#conversation)
- **Started** - `started: Instant` - Time when this session started
- **Ended** - `ended: Option[Instant]` - Time when this session ended. None while active.

##### Referenced from
- [Message](#message).`sessionId`
- [Session Summary Statement Link](#session-summary-statement-link).`sessionId`

#### Session Summary Statement Link
Links statements to session summaries in which they appear

##### Details

##### Properties
Session Summary Statement Link contains the following 3 properties:
- **Session Id** - `sessionId: Int` - Id of the session being summarized
  - Refers to [Session](#session)
- **Statement Id** - `statementId: Int` - Id of the statement made within the linked text / entity
  - Refers to *statement* from another module
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text

#### Thread
Represents a single named sequence of conversations within a specific topic

##### Details
- Fully **versioned**
- Uses 3 database **indices**: `name`, `created`, `archived_after`

##### Properties
Thread contains the following 4 properties:
- **Topic Id** - `topicId: Int` - Id of the general topic of this thread
  - Refers to [Topic](#topic)
- **Name** - `name: String` - The current name of this thread
- **Created** - `created: Instant` - Time when this thread was added to the database
- **Archived After** - `archivedAfter: Option[Instant]` - Time when this thread was archived

##### Referenced from
- [Conversation](#conversation).`threadId`
- [Thread Info Availability](#thread-info-availability).`threadId`
- [Thread Instruction Link](#thread-instruction-link).`threadId`

#### Topic
Represents a general conversation topic / theme, assigned to a single persona

##### Details
- Combines with [Topic Settings](#topic-settings), creating a **Topic With Settings**
- Fully **versioned**
- Uses 2 database **indices**: `created`, `deprecated_after`

##### Properties
Topic contains the following 2 properties:
- **Created** - `created: Instant` - Time when this topic was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when this topic was archived

##### Referenced from
- [Thread](#thread).`topicId`
- [Topic Info Availability](#topic-info-availability).`topicId`
- [Topic Instruction Link](#topic-instruction-link).`topicId`
- [Topic Settings](#topic-settings).`topicId`

#### Topic Settings
Specifies the name of a topic and the persona with whom that topic is discussed

##### Details
- Fully **versioned**
- Uses 3 database **indices**: `name`, `created`, `deprecated_after`

##### Properties
Topic Settings contains the following 5 properties:
- **Topic Id** - `topicId: Int` - Id of the topic these settings describe
  - Refers to [Topic](#topic)
- **Persona Id** - `personaId: Int` - Id of the persona with whom this topic is discussed
  - Refers to [Persona](#persona)
- **Name** - `name: String` - Name assigned to this topic
- **Created** - `created: Instant` - Time when this topic settings was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when these settings were replaced with another version

### Enum
This package contains the following 4 classes: [Enum](#enum), [Enum Value](#enum-value), [Enum Value Statement Link](#enum-value-statement-link), [Enum Value Version](#enum-value-version)

#### Enum
Represents an enumeration

##### Details
- Preserves **history**
- Uses 2 database **indices**: `name`, `archived_after`

##### Properties
Enum contains the following 3 properties:
- **Name** - `name: String` - Name of this enumeration (mutable)
- **Created** - `created: Instant` - Time when this enum was added to the database
- **Archived After** - `archivedAfter: Option[Instant]` - Time when this enumeration was archived

##### Referenced from
- [Enum Value](#enum-value).`enumId`

#### Enum Value
Represents an individual enumeration value

##### Details
- Preserves **history**
- Uses **index**: `archived_after`

##### Properties
Enum Value contains the following 3 properties:
- **Enum Id** - `enumId: Int` - Id of the enumeration where this value appears
  - Refers to [Enum](#enum)
- **Created** - `created: Instant` - Time when this enum value was added to the database
- **Archived After** - `archivedAfter: Option[Instant]` - Time when this enumeration value was archived

##### Referenced from
- [Enum Value Version](#enum-value-version).`enumValueId`
- [Instruction Version](#instruction-version).`enumValueId`

#### Enum Value Statement Link
Common trait for links between statements and the texts where they are made

##### Details

##### Properties
Enum Value Statement Link contains the following 3 properties:
- **Enum Value Version Id** - `enumValueVersionId: Int` - Id of the enumeration value version where the linked statement appears
  - Refers to [Enum Value Version](#enum-value-version)
- **Statement Id** - `statementId: Int` - Id of the statement made within the linked text / entity
  - Refers to *statement* from another module
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text

#### Enum Value Version
Represents a specific version of an enumeration value

##### Details
- Combines with [Enum Value](#enum-value), creating a **Contextual Enum Value Version**
- Combines with possibly multiple [Enum Value Statement Links](#enum-value-statement-link), creating a **Statement Linked Enum Value Version**
- Fully **versioned**
- Uses 2 database **indices**: `created`, `deprecated_after`

##### Properties
Enum Value Version contains the following 5 properties:
- **Enum Value Id** - `enumValueId: Int` - Id of the enumeration value which this version describes
  - Refers to [Enum Value](#enum-value)
- **Name** - `name: String` - Name of this enumeration value
- **Color Id** - `colorId: Int` - Id of the color used for this enumeration value
  - Refers to [Color](#color)
- **Created** - `created: Instant` - Time when this enum value version was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when this version was replaced with a newer one

##### Referenced from
- [Enum Value Statement Link](#enum-value-statement-link).`enumValueVersionId`

### Instruction
This package contains the following 7 classes: [Instruction](#instruction), [Instruction Statement Link](#instruction-statement-link), [Instruction Targeting Link](#instruction-targeting-link), [Instruction Version](#instruction-version), [Persona Instruction Link](#persona-instruction-link), [Thread Instruction Link](#thread-instruction-link), [Topic Instruction Link](#topic-instruction-link)

#### Instruction
Represents a behavioral instruction that may be given to an LLM

##### Details
- **Chronologically** indexed
- Uses **index**: `created`

##### Properties
Instruction contains the following 1 properties:
- **Created** - `created: Instant` - Time when this instruction was added to the database

##### Referenced from
- [Instruction Targeting Link](#instruction-targeting-link).`instructionId`
- [Persona Instruction Link](#persona-instruction-link).`instructionId`
- [Thread Instruction Link](#thread-instruction-link).`instructionId`
- [Topic Instruction Link](#topic-instruction-link).`instructionId`

#### Instruction Statement Link
Attaches a statement to an instruction where that statement appears

##### Details

##### Properties
Instruction Statement Link contains the following 3 properties:
- **Instruction Version Id** - `instructionVersionId: Int` - Id of the instruction version where the linked statement is made
  - Refers to [Instruction Version](#instruction-version)
- **Statement Id** - `statementId: Int` - Id of the statement made within the linked text / entity
  - Refers to *statement* from another module
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text

#### Instruction Targeting Link
Links an instruction to some entity to which / where it applies

##### Details
- Fully **versioned**
- Uses 2 database **indices**: `created`, `removed_after`

##### Properties
Instruction Targeting Link contains the following 4 properties:
- **Target Id** - `targetId: Int` - Id of the entity to which or where the linked instruction applies
- **Instruction Id** - `instructionId: Int` - Id of the instruction applied to the linked entity
  - Refers to [Instruction](#instruction)
- **Created** - `created: Instant` - Time when this instruction targeting link was added to the database
- **Removed After** - `removedAfter: Option[Instant]` - Time after which this link was removed

#### Instruction Version
Represents a specific instruction version. Different versions may have different configurations, syntax, etc.

##### Details
- Fully **versioned**
- Uses 3 database **indices**: `name`, `created`, `deprecated_after`

##### Properties
Instruction Version contains the following 4 properties:
- **Name** - `name: String` - Name of this instruction
- **Enum Value Id** - `enumValueId: Option[Int]` - Id of the enumeration value selected by applying this instruction. None if not enumeration-based.
  - Refers to [Enum Value](#enum-value)
- **Created** - `created: Instant` - Time when this instruction version was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when this version was replaced with a new one

##### Referenced from
- [Instruction Statement Link](#instruction-statement-link).`instructionVersionId`

#### Persona Instruction Link
Links an instruction to a persona to which it applies

##### Details
- Fully **versioned**
- Uses 2 database **indices**: `created`, `removed_after`

##### Properties
Persona Instruction Link contains the following 4 properties:
- **Persona Id** - `personaId: Int` - Id of the persona to which the linked instruction applies
  - Refers to [Persona](#persona)
- **Instruction Id** - `instructionId: Int` - Id of the instruction applied to the linked entity
  - Refers to [Instruction](#instruction)
- **Created** - `created: Instant` - Time when this instruction targeting link was added to the database
- **Removed After** - `removedAfter: Option[Instant]` - Time after which this link was removed

#### Thread Instruction Link
Links an instruction to a thread where it applies

##### Details
- Fully **versioned**
- Uses 2 database **indices**: `created`, `removed_after`

##### Properties
Thread Instruction Link contains the following 4 properties:
- **Thread Id** - `threadId: Int` - Id of the thread where the linked instruction applies
  - Refers to [Thread](#thread)
- **Instruction Id** - `instructionId: Int` - Id of the instruction applied to the linked entity
  - Refers to [Instruction](#instruction)
- **Created** - `created: Instant` - Time when this instruction targeting link was added to the database
- **Removed After** - `removedAfter: Option[Instant]` - Time after which this link was removed

#### Topic Instruction Link
Links an instruction to a topic where it applies

##### Details
- Fully **versioned**
- Uses 2 database **indices**: `created`, `removed_after`

##### Properties
Topic Instruction Link contains the following 4 properties:
- **Topic Id** - `topicId: Int` - Id of the topic where the linked instruction applies
  - Refers to [Topic](#topic)
- **Instruction Id** - `instructionId: Int` - Id of the instruction applied to the linked entity
  - Refers to [Instruction](#instruction)
- **Created** - `created: Instant` - Time when this instruction targeting link was added to the database
- **Removed After** - `removedAfter: Option[Instant]` - Time after which this link was removed

### Llm
This package contains the following 2 classes: [Llm](#llm), [Llm Size Variant](#llm-size-variant)

#### Llm
Represents a large language model

##### Details
- Combines with possibly multiple [Llm Size Variants](#llm-size-variant), creating a **Llm With Variants**
- Preserves **history**
- Uses **index**: `removed_after`

##### Properties
Llm contains the following 4 properties:
- **Name** - `name: String` - Name of this LLM in Ollama
- **Alias** - `alias: String` - Alias given to this LLM
- **Created** - `created: Instant` - Time when this llm was added to the database
- **Removed After** - `removedAfter: Option[Instant]` - Time when this LLM was removed from this application

##### Referenced from
- [Llm Size Variant](#llm-size-variant).`llmId`

#### Llm Size Variant
Represents a specific version (size) of a large language model

##### Details
- Combines with [Llm](#llm), creating a **Contextual Llm Size Variant**
- Preserves **history**
- Uses **index**: `removed_after`

##### Properties
Llm Size Variant contains the following 5 properties:
- **Llm Id** - `llmId: Int` - Id of the LLM this is a variant of
  - Refers to [Llm](#llm)
- **Suffix** - `suffix: String` - Suffix added to the end of the LLM's name when targeting this specific variant
- **Size** - `size: Option[Int]` - Size of this LLM variant in billions of parameters. None if unknown.
- **Created** - `created: Instant` - Time when this llm size variant was added to the database
- **Removed After** - `removedAfter: Option[Instant]` - Time when this specific variant was removed from this application

##### Referenced from
- [Animation Emotion Assignment](#animation-emotion-assignment).`originLlmId`
- [Persona Settings](#persona-settings).`llmVariantId`

### Meta
This package contains the following 6 classes: [Custom Meta Info](#custom-meta-info), [Meta Info Availability](#meta-info-availability), [Meta Info Field](#meta-info-field), [Persona Info Availability](#persona-info-availability), [Thread Info Availability](#thread-info-availability), [Topic Info Availability](#topic-info-availability)

#### Custom Meta Info
Represents a user-defined meta info field definition

##### Details
- Fully **versioned**
- Uses 3 database **indices**: `name`, `created`, `deprecated_after`

##### Properties
Custom Meta Info contains the following 4 properties:
- **Name** - `name: String` - Name of this field
- **Value** - `value: String` - Value assigned to this field
- **Created** - `created: Instant` - Time when this custom meta info was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when this version was replaced with another

##### Referenced from
- [Meta Info Field](#meta-info-field).`customInfoId`

#### Meta Info Availability
Links a meta info field to a context where it is made available to a persona

##### Details

##### Properties
Meta Info Availability contains the following 3 properties:
- **Field Id** - `fieldId: Int` - Id of the meta info -field made available
  - Refers to [Meta Info Field](#meta-info-field)
- **Context Id** - `contextId: Int` - Id of the context where the linked information is made available
- **Created** - `created: Instant` - Time when this information was made available

#### Meta Info Field
Represents an individual meta info value that can be made available or unavailable

##### Details
- May combine with [Custom Meta Info](#custom-meta-info), creating a **Detailed Meta Info Field**
- Preserves **history**
- Uses **index**: `archived_after`

##### Properties
Meta Info Field contains the following 4 properties:
- **Automated Content** - `automatedContent: Option[AutomatedMetaInfo]` - Automatically fillable value. None if this is not an automated field.
- **Custom Info Id** - `customInfoId: Option[Int]` - Id of the customized information in this field. None if this is an automated field.
  - Refers to [Custom Meta Info](#custom-meta-info)
- **Created** - `created: Instant` - Time when this meta info field was added to the database
- **Archived After** - `archivedAfter: Option[Instant]` - Time when this field was archived, if applicable

##### Referenced from
- [Meta Info Availability](#meta-info-availability).`fieldId`
- [Persona Info Availability](#persona-info-availability).`fieldId`
- [Thread Info Availability](#thread-info-availability).`fieldId`
- [Topic Info Availability](#topic-info-availability).`fieldId`

#### Persona Info Availability
A link that makes a meta info field available to a persona

##### Details

##### Properties
Persona Info Availability contains the following 3 properties:
- **Persona Id** - `personaId: Int` - Id of the persona to which the linked information is made available
  - Refers to [Persona](#persona)
- **Field Id** - `fieldId: Int` - Id of the meta info -field made available
  - Refers to [Meta Info Field](#meta-info-field)
- **Created** - `created: Instant` - Time when this information was made available

#### Thread Info Availability
A link that makes a meta info field available within a specific thread

##### Details

##### Properties
Thread Info Availability contains the following 3 properties:
- **Thread Id** - `threadId: Int` - Id of the thread where the linked information is made available
  - Refers to [Thread](#thread)
- **Field Id** - `fieldId: Int` - Id of the meta info -field made available
  - Refers to [Meta Info Field](#meta-info-field)
- **Created** - `created: Instant` - Time when this information was made available

#### Topic Info Availability
A link that makes a meta info field available within a specific topic

##### Details

##### Properties
Topic Info Availability contains the following 3 properties:
- **Topic Id** - `topicId: Int` - Id of the topic where the linked information is made available
  - Refers to [Topic](#topic)
- **Field Id** - `fieldId: Int` - Id of the meta info -field made available
  - Refers to [Meta Info Field](#meta-info-field)
- **Created** - `created: Instant` - Time when this information was made available

### Persona
This package contains the following 9 classes: [Animation Emotion Assignment](#animation-emotion-assignment), [Persona](#persona), [Persona Animation](#persona-animation), [Persona Image](#persona-image), [Persona Image Set](#persona-image-set), [Persona Image Set Link](#persona-image-set-link), [Persona Parameter](#persona-parameter), [Persona Settings](#persona-settings), [Persona Statement Link](#persona-statement-link)

#### Animation Emotion Assignment
Contains an assessment of an animation's emotional expression

##### Details
- Preserves **history**
- Uses **index**: `deprecated_after`

##### Properties
Animation Emotion Assignment contains the following 5 properties:
- **Animation Id** - `animationId: Int` - Id of the animation this assignment describes
  - Refers to [Persona Animation](#persona-animation)
- **Emotion** - `emotion: String` - Name of the emotion assigned to this animation
- **Origin Llm Id** - `originLlmId: Option[Int]` - Id of the LLM (variant) which assigned this emotion. None if not assigned by a (known) LLM.
  - Refers to [Llm Size Variant](#llm-size-variant)
- **Created** - `created: Instant` - Time when this animation emotion assignment was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when this assignment was replaced or retracted

#### Persona
Represents a personalized large language model

##### Details
- Combines with [Persona Settings](#persona-settings), creating a **Persona With Settings**
- Preserves **history**
- Uses **index**: `deleted_after`

##### Properties
Persona contains the following 2 properties:
- **Created** - `created: Instant` - Time when this persona was added to the database
- **Deleted After** - `deletedAfter: Option[Instant]` - Time when this persona was deleted

##### Referenced from
- [Persona Image Set Link](#persona-image-set-link).`personaId`
- [Persona Info Availability](#persona-info-availability).`personaId`
- [Persona Instruction Link](#persona-instruction-link).`personaId`
- [Persona Settings](#persona-settings).`personaId`
- [Topic Settings](#topic-settings).`personaId`

#### Persona Animation
Represents an individual animation representing some state of a Persona

##### Details
- Combines with multiple [Persona Images](#persona-image), creating a **Persona Animation With Frames**

##### Properties
Persona Animation contains the following 3 properties:
- **Set Id** - `setId: Int` - Id of the set to which this animation belongs
  - Refers to [Persona Image Set](#persona-image-set)
- **Relative Sub Directory** - `relativeSubDirectory: Path`, `""` by default - Path to the directory that contains image files specific to this animation. 
Relative to the image set's directory.
- **Default Frame Duration** - `defaultFrameDuration: Option[FiniteDuration]` - Duration how long each frame will be shown by default. 
None if the common default should be used instead.

##### Referenced from
- [Animation Emotion Assignment](#animation-emotion-assignment).`animationId`
- [Persona Image](#persona-image).`animationId`

#### Persona Image
Represents an individual image / frame within a Persona animation

##### Details
- Uses a **combo index**: `animation_id` => `order_index`

##### Properties
Persona Image contains the following 4 properties:
- **Animation Id** - `animationId: Int` - Id of the animation this image is part of
  - Refers to [Persona Animation](#persona-animation)
- **File Name** - `fileName: String` - Name of the wrapped image file
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index of this image within its animation
- **Custom Duration** - `customDuration: Option[FiniteDuration]` - Duration how long this image / frame should be displayed at once. 
None if the default duration should be applied.

#### Persona Image Set
Represents a set of images used for visualizing a Persona

##### Details
- Combines with [Persona Image Set Link](#persona-image-set-link), creating a **Persona Linked Image Set**
- Fully **versioned**
- Uses 3 database **indices**: `relative_directory`, `created`, `removed_after`

##### Properties
Persona Image Set contains the following 3 properties:
- **Relative Directory** - `relativeDirectory: Path` - Path to the directory that contains the images used in this image set. 
Relative to the main image directory.
- **Created** - `created: Instant` - Time when this persona image set was added to the database
- **Removed After** - `removedAfter: Option[Instant]` - Time when this image set was removed or deleted

##### Referenced from
- [Persona Animation](#persona-animation).`setId`
- [Persona Image Set Link](#persona-image-set-link).`imageSetId`

#### Persona Image Set Link
Links a Persona with an image set used for that Persona

##### Details
- Preserves **history**
- Uses **index**: `removed_after`

##### Properties
Persona Image Set Link contains the following 4 properties:
- **Persona Id** - `personaId: Int` - Id of the persona for which the linked images are used
  - Refers to [Persona](#persona)
- **Image Set Id** - `imageSetId: Int` - Id of the image set used for the linked persona
  - Refers to [Persona Image Set](#persona-image-set)
- **Created** - `created: Instant` - Time when this persona image set link was added to the database
- **Removed After** - `removedAfter: Option[Instant]` - Time when this link was removed

#### Persona Parameter
Represents an individual parameter / option assignment which is tied to a persona (version)

##### Details

##### Properties
Persona Parameter contains the following 3 properties:
- **Settings Id** - `settingsId: Int` - Id of the persona settings this parameter assignment is part of
  - Refers to [Persona Settings](#persona-settings)
- **Key** - `key: String` - Name / key of the adjusted parameter
- **Value** - `value: Value` - Value assigned to this parameter

#### Persona Settings
Records general settings (version) for a Persona

##### Details
- Combines with possibly multiple [Persona Parameters](#persona-parameter), creating a **Persona Settings With Parameters**
- Combines with possibly multiple [Persona Statement Links](#persona-statement-link), creating a **Statement Linked Persona Settings**
- Fully **versioned**
- Uses 3 database **indices**: `name`, `created`, `deprecated_after`

##### Properties
Persona Settings contains the following 5 properties:
- **Persona Id** - `personaId: Int` - Id of the persona which these settings describe
  - Refers to [Persona](#persona)
- **Llm Variant Id** - `llmVariantId: Int` - Id of the LLM variant this persona wraps
  - Refers to [Llm Size Variant](#llm-size-variant)
- **Name** - `name: String` - Name assigned for this persona
- **Created** - `created: Instant` - Time when this persona settings was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when these settings were replaced with a new version

##### Referenced from
- [Persona Parameter](#persona-parameter).`settingsId`
- [Persona Statement Link](#persona-statement-link).`personaSettingsId`

#### Persona Statement Link
Links a statement to a Persona description

##### Details

##### Properties
Persona Statement Link contains the following 4 properties:
- **Persona Settings Id** - `personaSettingsId: Int` - Id of the settings to which this link is tied to
  - Refers to [Persona Settings](#persona-settings)
- **Statement Id** - `statementId: Int` - Id of the statement made within the linked text / entity
  - Refers to *statement* from another module
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text
- **Description Type** - `descriptionType: PersonaDescriptionType` - Context in which the linked statement appears

### Statement
This package contains the following 2 classes: [Statement Link](#statement-link), [Text Placement](#text-placement)

#### Statement Link
Common trait for links between statements and the texts where they are made

##### Details

##### Properties
Statement Link contains the following 3 properties:
- **Statement Id** - `statementId: Int` - Id of the statement made within the linked text / entity
  - Refers to *statement* from another module
- **Parent Id** - `parentId: Int` - Id of the text where the placed text appears
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text

#### Text Placement
Places some type of text to some location within another text

##### Details

##### Properties
Text Placement contains the following 3 properties:
- **Parent Id** - `parentId: Int` - Id of the text where the placed text appears
- **Placed Id** - `placedId: Int` - Id of the text that is placed within the parent text
- **Order Index** - `orderIndex: Int`, `0` by default - 0-based index that indicates the specific location of the placed text
