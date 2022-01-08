## Java Spellcheck Program

### Purpose: Carries out spellcheck on input from user

This program takes a sequence of strings from the user as input, checks the strings against a set of file of words, and produces a list of strings within the sequence that do not exist in the file. In addition, the program is extended to include a WordCorrector that suggests to the user possible corrections to the erraneous word, which the user can then pick one to correct the word, and have it overwrite the erraneous word in the input.

<ins>StringArray Class</ins>

This program has a Java StringArray class that can store a collection of String object references (i.e a collection of Strings).

<ins>SpellCheck Class</ins>

The program includes a SpellCheck class. This class accepts input from the user via the terminal or uploading a file, and checks the text in the input against a dictionary of words. The 
words which found no match in the dictionary are then displayed in the terminal. 

<ins>WordCorrector Class</ins>

This program also includes a WordCorrector class. This class scans through the list of words that do not match, and then provides suggestions from the dictionary that are similar to the 
aforementioned words. The user can then select one suggestion from the options, which will then replace the unmatched word in the file or the text. If the user does not find the 
suggestions to be satisfactory, they can then leave the original word unchanged in the text. The final edited file will then replace the original file with all the corrected words.