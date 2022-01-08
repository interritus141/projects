## Parsers Program Report 

### Section 1: Summary 

<ins>In both programs:</ins> The program is able to confirm that each tag is a valid HTML tag, each tag has a corresponding closing tag if needed, tags are nested properly, there are &lt;html> and &lt;/html> tags around the entire document, and there is a single &lt;head> section followed by a single &lt;body> section. 

<ins>n C:</ins> If an error is found, an error message is displayed and the program stops, so only the first error found is reported. If no errors are found the program displays a 
message that the HTML was correct. 

<ins>In Haskell:</ins> If an error is found, the program will only display the tag that is erroneous, and will not be specific in what the error is. 

### Section 2: Design and Programming Process 

<ins>In C:</ins> Being an imperative programming language, the structure of the program is from top to bottom. The functions and their parameters are thought out first in a skeletal 
structure and then extended by adding more and more functions that targets the requirements of the coursework. The error messages can then be added into their respective functions and 
to display the correct information every time it is called. The debugging process is simply using print statements to identify any mistakes in the logic and rectifying it one by one. 

<ins>In Haskell:</ins> Being a functional programming language, the program does not follow a rigid structure, and instead relies on individual functions to execute the program. As 
such, the design process behind is to fully write out the required functions one by one and test them before integrating all of them into the program. Without the ability to freely use 
print statements like in C, the debugging process has to be done for every function and ensure that they are working properly first. 

The design process behind writing in C is more straightforward and intuitive. By following the steps in a methodical manner, the program is able to churn out reliable and specific 
error messages. Haskell, on the other hand, requires one to think recursively, which is less intuitive to most people, and needs one to pay more attention to how the data is being 
manipulated in the midst of a recursive function.

### Section 3: Evaluation 

<ins>Evaluation Criteria 1: Error Messages</ins>

C is the better language for producing error messages. In C, I am able to single out the specific cases where there are no closing tags, incorrectly nested tags, etc. and generate the
error message for each case in the corresponding functions without much issues. However, in Haskell, the concept of each function having to follow its type signatures, and output onto 
the terminal being an IO function, prevents the printing of error messages within the same function, and new functions have to be created to handle the respective messages. This can 
lower the readability of the code and overall quality. In general, error messages in a parser program like this are important as they provide information as to which part of the file 
has an error, hence the messages have to be concise yet specific. C is the language that can provide error messages in such a way while not impeding the quality of the program. 

<ins>Evaluation Criteria 2: Debugging Code</ins>
It is easier to debug the program in Haskell. Being a functional programming language, its functions are independent of one another and due to 
the nature of type signatures, the return type of the functions will be consistent. This means that if a new function is tested and works as normal, it will work just as well when 
fitted into a larger program, provided that the parameters fit the type signatures. Hence, Haskell is better in this aspect. Evaluation Criteria 3: Code Quality By nature of the 
languages’ syntax, Haskell is much shorter in terms of the number of lines of code. However, this is because of the recursive nature of the language, and will not make the code easier 
to understand, and may actually make it more difficult. Hence, the program in C is more readable and easier to understand. 

<ins>Conclusion</ins>
Considering that the program is a parser for HTML files, providing quality error messages should be the top priority and one of the bigger concerns. As such, C will be 
the more suitable language for writing the program.