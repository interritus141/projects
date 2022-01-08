#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h> 

#define SEARCH 1
#define ADD 2
#define UPDATE 3
#define DELETE 4
#define LIST 5
#define SAVE 6
#define LOAD 7
#define QUIT 8
#define MAXLINELENGTH 100
#define InventorySIZE 1000

struct item
{
    char *name;
    char *barcode;
    char *price;
    char *quantity;
}; 

struct item *Inventory[InventorySIZE];
void optionMenu(void);
int getMenuOption(void);
void executeOption(int option);
void start(void);
int searchITEM(void);
int executeSearchOption(int option);
int searchByName(void);
int searchByCode(void);
void addITEM(void);
struct item* addNewInfo(void);
void updateITEM(void);
void deleteITEM(void);
void listALLITEMS(void);
void saveInventory(void);
void loadInventory(void);
void removeNewLine(char *buffer);
char* getString(char*);
struct item *createITEM(char* name, char* barcode, char* price, char* quantity);

void start(void)
{
    while (true)
    {    
        optionMenu();
        int option = getMenuOption();
        if (option == QUIT)
        {
            break; 
        }
        else
        {
            executeOption(option);
        }
    }
}

void optionMenu(void)
{
    int option;
    printf("\nInventory Storage\n");
    printf("%d. Search for an item\n", SEARCH);
    printf("%d. Add a new item\n", ADD);
    printf("%d. Update an existing item\n", UPDATE);
    printf("%d. Delete an item from inventory\n", DELETE);
    printf("%d. List all items in inventory\n", LIST);
    printf("%d. Save the inventory to a file\n", SAVE);
    printf("%d. Load an inventory from a file\n", LOAD);
    printf("%d. Quit\n", QUIT); 
}

int getMenuOption(void)
{
    int option = 0;
    char *input = getString("Enter a selection:");
    sscanf(input, "%d", &option);
    free(input);
    return option;
}

void executeOption(int option)
{
    switch (option)
    {
    case SEARCH: searchITEM(); break;
    case ADD: addITEM(); break;
    case UPDATE: updateITEM(); break;
    case DELETE: deleteITEM(); break;
    case LIST: listALLITEMS(); break;
    case SAVE: saveInventory(); break;
    case LOAD: loadInventory(); break;
    default: printf("Invalid option. Please enter a valid option.\n"); break;
    }
}

void searchDisplayMenu(void)
{
    printf("\nDo you wish to search by...\n");
    printf("%d. Name\n", 1);
    printf("%d. Barcode\n", 2);
    printf("%d. Quit\n", 3);
}

int searchITEM(void)
{
    printf("\nSearch for an item in inventory\n");
    int input = 0;
    // setting index to max size if user finds no item which causes function to end
    int index = InventorySIZE;
    searchDisplayMenu();
    char *search = getString("Enter a selection:");
    sscanf(search, "%d", &input);
    while (true)
    {
        if (input == 3)
        {
            break;
        }
        else
        {
            index = executeSearchOption(input); 
            break;  
        }
    }
    if (Inventory[index] != NULL)
    {
        printf("\nName: %s\nBarcode: %s\nPrice: %s\nQuantity: %s\n", 
                Inventory[index]->name, 
                Inventory[index]->barcode, 
                Inventory[index]->price, 
                Inventory[index]->quantity);
        return index; 
    }
    free(search);
    return index; 
}

int executeSearchOption(int option)
{
    switch(option)
    {
        case 1: return searchByName(); break;
        case 2: return searchByCode(); break;
        default: printf("Invalid option. Please enter a valid option.\n"); break;
    }
}

int searchByName(void)
{
    int index = 0;
    char *name = getString("Enter the name of the item:");
    while (Inventory[index] != NULL)
    {
        if (!strcmp(Inventory[index]->name, name))
        {
            free(name);
            return index;
        }
        index++;
    }
    printf("Sorry. The item is not in the inventory.\n");
    free(name);
    return index;
}

int searchByCode(void)
{
    int index = 0;
    char *code = getString("Enter the barcode of the item:");
    while (Inventory[index] != NULL)
    {
        if (!strcmp(Inventory[index]->barcode, code))
        {
            free(code);
            return index;
        }
        index++;
    }
    printf("Sorry. The item is not in the inventory.\n");
    free(code);
    return index;
}

void addITEM(void)
{
    printf("\nAdd an item to inventory\n");
    int index = -1;
    while (Inventory[++index] != NULL);
    if (index > InventorySIZE - 2)
    {
        printf("The inventory is full and no more items can be added.\n");
        return;
    }
    Inventory[index] = addNewInfo();
    Inventory[index+1] = NULL;
}

struct item* addNewInfo(void)
{
    char *name = getString("Enter the name of the item:");
    char *barcode = getString("Enter the barcode of the item:");
    char *price = getString("Enter the price of the item:");
    char *quantity = getString("Enter the quantity of the item:");
    struct item *entry = createITEM(name, barcode, price, quantity);
    free(name);
    free(barcode);
    free(price);
    free(quantity);
    return entry;
}

void updateITEM(void)
{
    printf("\nUpdate an item in inventory\n");
    int index = searchITEM();
    struct item *newentry = addNewInfo();
    memcpy(Inventory[index], newentry, sizeof(struct item));
}

void deleteITEM(void)
{
    printf("\nDelete an item from inventory\n");
    int index = searchITEM();
    while (Inventory[index] != NULL)
    {
        Inventory[index] = Inventory[index+1];
        index++;
    }
}

void listALLITEMS(void)
{
    printf("\nList all items in inventory\n");
    int index = 0;
    if (Inventory[0] == NULL)
    {
        printf("There are no entries in this inventory.\n");
        return;
    }
    while (Inventory[index] != NULL)
    {
        printf("Item %d. Name: %s Barcode: %s Price: %s Quantity: %s\n", 
                index + 1, 
                Inventory[index]->name, 
                Inventory[index]->barcode, 
                Inventory[index]->price, 
                Inventory[index]->quantity);
        index++;
    }
}

void saveInventory(void)
{
    printf("\nSave the inventory to a file\n");
    char *fileName = getString("Enter the file name: ");
    FILE *outputFile = fopen(fileName,"w");
    if (outputFile == NULL)
    {
        printf("\nUnable to write to file - inventory not saved.\n");
        free(fileName);
        return;
    }
    int index = 0;
    while (Inventory[index] != NULL)
    {
        fprintf(outputFile, "%s\n", Inventory[index]->name);  
        fprintf(outputFile, "%s\n", Inventory[index]->barcode);
        fprintf(outputFile, "%s\n", Inventory[index]->price);
        fprintf(outputFile, "%s\n", Inventory[index]->quantity);
        index++;
    }
    fclose(outputFile);
    free(fileName);  
}

void loadInventory(void)
{
    printf("\nLoad an inventory from a file\n");
    char *fileName = getString("Enter the file name: ");
    FILE *inputFile = fopen(fileName,"r");
    if (inputFile == NULL)
    {
        printf("\nUnable to read from file - inventory not loaded.\n");
        free(fileName);
        return;
    }  
  
    int index = 0;
    char name[MAXLINELENGTH];
    char barcode[MAXLINELENGTH];
    char price[MAXLINELENGTH];
    char quantity[MAXLINELENGTH];
    while (index < InventorySIZE-1)
    {
        fgets(name, MAXLINELENGTH-1, inputFile);
        fgets(barcode, MAXLINELENGTH-1, inputFile);
        fgets(price, MAXLINELENGTH-1, inputFile);
        char *input = fgets(quantity, MAXLINELENGTH-1, inputFile);
        if (input == NULL)
        {
            break;
        }
        removeNewLine(name);
        removeNewLine(barcode);
        removeNewLine(price);
        removeNewLine(quantity);
        Inventory[index] = createITEM(name, barcode, price, quantity);
        index++;
    }
    Inventory[index] = NULL;
    fclose(inputFile);
    free(fileName);
}

void removeNewLine(char *buffer)
{
    size_t length = strlen(buffer);
    if (length == 0)
    {
        return;
    }
    if (buffer[length-1] == '\n')
    {
        buffer[length-1] = '\0';
    }
}

//This function is referenced from the phonebook example.
struct item *createITEM(char* name, char* barcode, char* price, char* quantity)
{
    struct item *item = calloc(sizeof(struct item), 1);
    item->name = calloc(sizeof(char), strlen(name) + 1);
    strcpy(item->name, name);
    item->barcode = calloc(sizeof(char), strlen(barcode) + 1);
    strcpy(item->barcode, barcode);
    item->price = calloc(sizeof(char), strlen(price) + 1);
    strcpy(item->price, price);
    item->quantity = calloc(sizeof(char), strlen(quantity) + 1);
    strcpy(item->quantity, quantity);
    return item;
}

//This function is referenced from the phonebook example.
char* getString(char* prompt)
{
    char buffer[MAXLINELENGTH];
    printf("%s", prompt);
    fgets(buffer, MAXLINELENGTH, stdin);
    size_t inputLength = strlen(buffer);
    char *input = calloc(sizeof(char), inputLength);
    strncpy(input, buffer, inputLength-1);
    input[inputLength] = '\0';
    return input;
}

void createSampleData(void)
{
    Inventory[0] = createITEM("potatoes", "56784", "1.32", "50");
    Inventory[1] = createITEM("broccoli", "23241", "0.87", "4");
    Inventory[2] = NULL;
}

int main(void)
{
    Inventory[0] = NULL;
    createSampleData();
    start();
    return 0;
}
