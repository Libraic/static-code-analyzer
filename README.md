# Criteria to consider when building the static code analyzer
* ***
## Declaring a variable
A variable declaration instruction consists of the following parts:
* type of the variable;
* name of the variable;
* equal sign;
* the expression which is assigned.

Each of these individual components should be analyzed individually.

### 1. Type of the variable
The type of the variable should be from the existing language data type ecosystem (which,
in our case, is ***Java***) or should be from the types declared by us or declared in the
libraries used inside the program. These things are usually done by the compiler, so our tool
will assume everything is syntactically correct. In an instruction, the variable data type is the
first thing one writes, so it is always on the first position. This helps a lot when parsing the
source code, since, if we have an assignment operator somewhere in that particular instruction,
we know there must be a variable (or it may be missing, if we are to talk about reassignment).

### 2. Name of the variable
If we have the previous situation in place, namely if we have an assignment operator, then there
must be a variable name. If the data type is optional (in case of reassignment), then the variable
name is mandatory. Therefore, it can hold the 0th or the 1st spot in that instruction. Again, we
do not have to check if the variable respects the naming convention to make the instruction 
syntactically correct, since the compiler will assure it (a variable name must not start with a 
number).

### 3. Equal Sign
The equal sign, technically called the ***assignment operator***, represents a binary operator which
consists of left hand side operator and right hand side operator. We have already talked about
left hand side operator (which consists of ***type of the variable*** and 
***name of the variable***).


### 4. The expression which is assigned