Here is the updated summary of the Java Style Guidelines, integrating your custom naming conventions for visibility, interfaces, abstract classes, and enums alongside the original principles.

## Purpose of the Guidelines

Using consistent coding style makes programs more readable, helps prevent bugs, and prepares you for professional programming environments. These guidelines combine standard practices (like those from Sun Microsystems) with your specific custom prefixes.

## Updated Naming Conventions

The most significant updates to the style guide involve using specific prefixes to denote the type or visibility of a member.

### 1. Variables and Members (Fields/Methods)

Names should reflect the data they store or the action they perform, using whole words (no ambiguous abbreviations). **Visibility now strictly dictates the prefix:**

- **Private Members:** Must start with an underscore (`_`) followed by camelCase (e.g., `_hoursWorked`, `_calculateGrossPay`).
    
- **Protected Members:** Must start with a dollar sign (`$`) followed by camelCase (e.g., `$payRate`, `$getPayData`).
    
- **Public Members:** Start normally with standard lower camelCase and no special prefix (e.g., `grossPay`, `displayLabel`).
    

### 2. Classes, Interfaces, Abstract Classes, and Enums

All of these structures should use descriptive, whole words. The capitalization and prefix rules are as follows:

- **Classes:** Use standard UpperCamelCase (e.g., `RetirementAccount`).
    
- **Interfaces:** Must start with a capital **I** followed by UpperCamelCase (e.g., `IRunnable`).
    
- **Abstract Classes:** Must start with a capital **A** followed by UpperCamelCase (e.g., `AEmployee`).
    
- **Enums:** Must start with **En** followed by UpperCamelCase (e.g., `EnDaysOfWeek`).
    

### 3. Constants

- Declared using the `final` keyword.
    
- Must be written in ALL_CAPS.
    
- Separate words with a single underscore (e.g., `DISCOUNT_RATE`).
    

## Formatting and Control Statements

- **Indentation:** Always indent the body of control statements (`if`, `while`, `for`). Nested blocks must be indented further than their enclosing blocks.
    
- **Curly Braces `{}`:** Always enclose the bodies of control statements in curly braces, even if the block contains only a single line of code.
    
- **Brace Placement:** The opening brace can go on the same line as the statement or on a new line, but you must be consistent. The closing brace is always on its own line, aligned with the start of the block.
    

## Scope, Visibility, and Best Practices

- **Scope & Access:** Declare variables with the smallest possible scope (e.g., local to a method or block if possible) and the most restrictive visibility.
    
- **Import Statements:** Do not use wildcard imports (e.g., `import java.awt.*;`). Explicitly import the exact classes you need (e.g., `import java.awt.TextBox;`) so it is clear where packages come from.
    
- **Complex Expressions:** Avoid complex, multi-operation expressions that have side effects (e.g., `$y = --_x - _x++ * (_x /= 2)`). Break them into multiple, simpler statements using temporary local variables to make debugging and maintenance easier.