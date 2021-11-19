#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h> 

#define NUMOFTAGS 26
#define CLOSETAGS 12
#define OPENTAGS 12
#define STACKSIZE 30

void getInputFromFile();
void printStack();
bool checkValidTag(char str[], int line);
bool checkAttribute(char (*stack)[30][50], char s[]);
bool checkTag(char str[]);
bool checkOpenTag(char (*stack)[30][50], char tag[]);
bool checkCloseTag(char tag[]);
bool checkSingleTag(char tag[]);
bool checkTitleTag(char (*stack)[30][50]);
bool checkHeadAndBodyTag(char (*stack)[30][50]);
bool checkDivAndPTag(char (*stack)[30][50]);
void push(char *element, char (*stack)[30][50], int *top, int stackSize);
void pop(char (*stack)[30][50], int *top, int stackSize);
bool contains(char (*stack)[30][50], char *str);
bool compareStackTop(char (*stack)[30][50], int *top, char *str);

char *openTags[] = {"html", "head", "body", "title", "h1", "h2", "h3", "p", "ul", "li", "a", "div"};
char *closeTags[] = {"/html", "/head", "/body", "/title", "/h1", "/h2", "/h3", "/p", "/ul", "/li", "/a", "/div"};
char *singleTags[] = {"br", "hr"};
char stack[1][30][50] = {};
int top = -1;

void getInputFromFile() {
    FILE *file;
    file = fopen("file.html", "r");
    char buff[255];
    int line = 0;

    if (file == NULL) {
        perror("Error. File does not exist or cannot be opened.");
        exit(EXIT_FAILURE);
    } 
    else {
        while(fgets(buff, 255, (FILE*)file) != NULL) {
            if (!checkValidTag(buff, line)) {
                printf("Error. Parsing error on line %d: %s\n", line+1, buff);
                printf("Exiting the program...");
                exit(0);
            }
            line++;
        }
        printf("parsing complete. No errors found.");
    }
    fclose(file);
}

void printStack() {
    char (*stackElem)[50];
    stackElem = malloc(sizeof(char) * 50);
    stackElem = *stack;
    for (int i = 0; i < STACKSIZE; i++) {
        printf("ELEM: %s\n", *stackElem);
        stackElem++;
    }
}

bool checkValidTag(char buff[], int line) {
    char str[255] = "";
    int start = -1; 
    int end = -1;
    for (int i = 0; i < strlen(buff); i++) {
        if (buff[i] == '>') {
            end = i;
            if (line == 0 && strcmp(str, "html") != 0) {
                printf("Parsing error. First tag is not <html>.\n");
                return false;
            }
            if (!checkTag(str)) return false;
            end = -1;
            start = -1;
            strcpy(str, "");
        }
        if (start != -1 || end != -1) {
            strncat(str, &buff[i], 1);
        }
        if (buff[i] == '<') {
            start = i;
        }
    }
    return true;
}

bool checkTag(char str[]) {
    char (*s)[STACKSIZE][50] = stack;
    if (checkOpenTag(s, str)) {
        push(str, s, &top, STACKSIZE);
        return true;
    }
    else if (checkCloseTag(str)) {
        return compareStackTop(s, &top, str);
    }
    else if (checkSingleTag(str)) return true;
    else {
        if (strncmp(str, "a", 1) == 0 || strncmp(str, "div", 3) == 0) {
            return checkAttribute(s, str);
        }
        else { 
            printf("Parsing error. <%s> is not a valid html tag.\n", str);
            return false;
        }
    }
    return true;
}

bool checkOpenTag(char (*stack)[30][50], char tag[]) {
    for (int i = 0; i < OPENTAGS; i++) {
        if (strcmp(tag, openTags[i]) == 0) {
            if (strcmp(tag, "title") == 0) return checkTitleTag(stack);
            if (strcmp(tag, "head") == 0 || strcmp(tag, "body") == 0) return checkHeadAndBodyTag(stack);
            if (strcmp(tag, "p") == 0 || strcmp(tag, "div") == 0) return checkDivAndPTag(stack);
            else return true;
        }
    }
    return false;
}

bool checkTitleTag(char (*stack)[30][50]) {
    if (contains(stack, "head")) {
        return true;
    }
    printf("Parsing error. <title> tags can only be nested within <head> tags and nothing else.\n");
    return false;
}

bool checkHeadAndBodyTag(char (*stack)[30][50]) {
    if (contains(stack, "html") && top == STACKSIZE-1) {
        return true;
    }
    printf("Parsing error. <head> or <body> tags cannot be nested in any other tags except <html>.\n");
    return false;
}

bool checkDivAndPTag(char (*stack)[30][50]) {
    if (contains(stack, "p")) {
        printf("Parsing error. <div> or <p> tags cannot be nested within <p> tags.\n");
        return false;
    }
    return true;
}

bool checkCloseTag(char tag[]) {
    for (int i = 0; i < CLOSETAGS; i++) {
        if (strcmp(tag, closeTags[i]) == 0) {
            return true;
        }
    }
    return false;
}

bool checkSingleTag(char tag[]) {
    for (int i = 0; i < 2; i++) {
        if (strcmp(tag, singleTags[i]) == 0) {
            return true;
        }
    }
    return false;
}

bool checkAttribute(char (*stack)[30][50], char s[]) {
    char subbuff[20];
    if (strncmp(s, "a", 1) == 0) {
        memcpy(subbuff, &s[2], 20);
        subbuff[19] = '\0';
        if (strncmp(subbuff, "href=\"", 6) == 0) { 
            push("a", stack, &top, STACKSIZE);
            return true;
        }
    }
    else {
        memcpy(subbuff, &s[4], 20);
        subbuff[19] = '\0';
        if (strncmp(subbuff, "class=\"", 7) == 0) { 
            push("div", stack, &top, STACKSIZE);
            return true;
        }
    }
    return false;
}

void push(char *element, char (*stack)[30][50], int *top, int stackSize){
    if(*top == -1) {
        strncpy((*stack)[stackSize - 1], element, sizeof(element));
        *top = stackSize - 1;
    }
    else if(*top == 0) {
        printf("The stack is already full. \n");
    }
    else {
        strncpy((*stack)[(*top) - 1], element, sizeof(element));
        (*top)--;
    }
}

void pop(char (*stack)[30][50], int *top, int stackSize){
    if(*top == -1) {
        printf("The stack is empty. \n");
    }
    else {
        strcpy((*stack)[(*top)], "");
        if((*top) == stackSize - 1){
            (*top) = -1;
        }
        else{
            
            (*top)++;
        }
    }
}

bool contains(char (*stack)[30][50], char *str) {
    char (*stackElem)[50];
    stackElem = malloc(sizeof(char) * 50);
    stackElem = *stack;
    for (int i = 0; i < STACKSIZE; i++) {
        if (strcmp(*stackElem, str) == 0) {
            return true;
        }
        stackElem++;
    }
    return false;
} 

bool compareStackTop(char (*stack)[30][50], int *top, char *str) {
    char *chTop = (char*) malloc(sizeof(char) * strlen((*stack)[*top]));
    strcpy(chTop, (*stack)[*top]);
    str++;
    if (strcmp(str, chTop) == 0) {
        free(chTop);
        pop(stack, top, STACKSIZE);
        return true;
    }
    free(chTop);
    printf("Parsing error. The tag is nested incorrectly or is an incorrect closing tag.\n");
    return false;
}

int main(void) {
    getInputFromFile();
}

