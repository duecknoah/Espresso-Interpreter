# Espresso Interpreter
A Simple interpreter made as my assignment.

## Context
This was done for an assignment in University. I had to make a simple interpreter that interpreted a made up programming language called "Espresso Language". In the end, it became a very basic scripting language interpreter. It had to follow the following:

- The compiler takes a file (program file) and executes it line by line, so it is more like an interpreter, not a real compiler

## Statements
​Each line of a program in Espresso language contains a single statement. There are
three types of statements in Espresso language:
1. ​The​ ​assignment​ ​statement:​ the syntax of an assignment statement is `variable = infix-expr`. When an assignment statement is executed the 'infix-expr' is evaluate and its value is
assigned to the variable.
2. ​The​ ​input​ ​statement:​ ​the syntax of an input statement is `read variable`
When an input statement is executed the program asks the user to enter an integer and assigns the entered value to the specified variable.
3. ​The​ ​output​ ​statement:​ ​the syntax of an output statement is
`print infix-expr`
When an output statement is executed the infix-expr is evaluate and its value is
printed on the screen.

#### I decided to add a couple extra statements for more functionality:
- `goto 'linenumber'` jumps to the given line number
- `if infix-exp <comparison operator> infix-exp` checks if comparison between the two infix expressions is true, if so, it executes the given code block within the if statement.

Examples programs are included.