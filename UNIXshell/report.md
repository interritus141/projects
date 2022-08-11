# COMP0010 Individual Report

## Design & Refactoring

### Design Principles & Refactoring

#### Separation of Concerns
Initially, we found the development of this project should be divided into 3 separate layers: Lexer and
Parser, Application, and Command. This separation of the project requirements allows easier
management of tasks and debugging as changing one layer will only affect the others minimally.
The downside to this approach is that when we are working on different parts of the shell, merging
every change into one poses problem as each team member is more focused on their segment, rather
than developing the shell together.

#### Parser Architecture
We used ANTLR4 to generate Abstract Syntax Trees (ASTs), then built objects to represent them. This is
used to parse shell features such as I/O redirection, pipe, and command substitution. This parser
architecture is preferred as it generates recursive descent parsers, which is more efficient, and the
grammar is not too complex to follow.

#### Shell
The shell applications are designed with the factory pattern described below, with each of the
applications implementing the same interface. We then expanded the shell to include new applications,
including the unsafe versions of them and using the decorator pattern.
The command features are designed with the visitor pattern, analyzed below. We wrote a class,
CommandConverter that visits the AST, and returns an object that implements the Command interface.
We then built concrete classes to visit command objects and to evaluate the commands.

### Design Patterns
1. Factory Pattern
As there are quite a few applications that we must implement, it makes sense for us to create a factory
pattern that allows us to hide the common methods behind the Application class and allow the code to
be cleaner and easier to understand.

2. Visitor Pattern
The command features (Call, Pipe, Seq) can be all grouped together as they deal with the command line
arguments. By assigning the command features as elements, we created an interface, and created
concrete classes that implement this interface which has methods that execute their required function,
such as I/O redirection and globbing. Similarly for visitors, we created a visitor interface, and created
concrete visitors that implements this interface, such as Call and Pipe.
We figured that a visitor pattern is the most fitting and the most efficient in terms of designing the
structure of the Command interface, as the multitude of features can be hidden behind the layers and
the code will be easier to understand.

3. Decorator Pattern
The unsafe applications change the base applications such that they print the error messages to
standard output. By wrapping the applications with an unsafe decorator, we can keep the semantics of
the original applications while just changing how the output is dealt. This will then keep the code clean
and prevent duplication of code.

## Testing

### System Testing
We passed all the system tests provided to us in the original shell. However, as they are not sufficient to
cover the entire functionalities of each application and command feature, we wrote unit tests to
complement the testing and increase branch coverage.

### Unit Testing
For every application, we wrote and passed unit tests that cover all the cases in which an application
may fail in. For example, for the find application, there are several ways that the command can fail to
execute (specifying a directory or a file that does not exist, not using the -name argument, etc). We then
write unit tests that correctly identify the cases that will fail and assert that the tests will encounter a
runtime exception. The purpose of unit tests is that we cover every branch (including edge cases) of
possible instructions that can take place when a command is executed and ensure that they are the
same as the expected results.

### Branch Coverage
We aimed for >= 95% branch coverage, meaning that most edge cases in our shell have been tested and
work as intended. Exceptions show up when they are supposed to, and the output of the applications
matches the expected results.

### Test-Driven Development (TDD)
Following the approach of test-driven development, an additional point to review here is that we should
have written and passed unit tests first, before dealing with the system tests. This will be more useful in
the development of the shell, as the unit tests uncovers bugs earlier, and will detect bugs even in the
parser and when executing command features. We can then ensure all the tests pass for a certain
functionality before moving on to the next, which allows easier debugging during the development.

### Continuous Integration (CI)
We followed the approach of continuous integration, by committing and pushing to a git repository daily
and after every change that is made, even minor changes. By keeping the changes small, we can
pinpoint the bugs that arise after every push and fix them before continuing development. This is
essential in our development as we have many different applications that requires extensive testing;
hence this development approach was greatly beneficial to us.

## Code Quality
By sticking to what we have learnt over assignments and projects, methods are kept relatively short, and
use helper methods to keep code clean and easier to read. This also applies to code that are reused,
which are moved to helper methods to prevent duplicate code.

### Code Smells & Anti-Patterns
Code Smells and Anti-Patterns are practices that have bad fundamental designs, and risks being highly
counterproductive.

One such example of code smell is the use of duplicate code, where the same chunk of code appears in
multiple methods, which decreases the readability of the code. Our solution is to introduce helper
methods that can then be called by other methods and makes the code cleaner.

### Documentation & JavaDoc Format
Documentation is the text that accompanies computer software or is embedded in the source code.
As we are developing a whole UNIX shell, documentation is essential to allow both users and developers
understand our source code easily. We thus chose to follow the standard JavaDoc format, by including a
description of the application in every file, and the block tags that represent the parameters and the
return or thrown objects.

### Code Style
This is the set of rules that we follow when writing the source code for the shell. These include using
Camel Case for method names, use of proper indentation, and use of blank lines between logical parts in
a method. We also follow standard practices of not having methods that are too long and extract these
parts out into helper methods which breaks the code down into multiple short methods instead of a
single long one.

## Project Management

### Agile Methodology
We had weekly meetings to decide the tasks for the week and set the direction for the project. To
prevent debugging too many changes during refactoring, we worked on small sections one at a time to
ensure previous functionality was preserved.

The adoption of agile methodology proved to be effective. We set short weekly goals each time and
were able to achieve those goals in a short amount of time, completing small parts of the shell one at a
time. This gave us the motivation to continue building the shell, as we see the completion of every small
part as progress towards the bigger project.

## Abstraction
We separated work into non-overlapping sections using abstraction. To avoid having merge conflicts, we
each worked on our own distinct sections. For example, changes to the parser did not affect the shell
applications, and each application did not affect one another. Another plus is that we did not have to
understand one another’s work and can focus on our own parts, while being able to integrate our own
work into the main shell without problems.