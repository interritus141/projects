class Node:
    def __init__(self,data):
        self.next = None
        self.data = data

class CircularList:
    def __init__(self):
        self.head = Node(None)
        self.tail = Node(None)
        self.head.next = self.tail
        self.tail.next = self.head

    def isEmpty(self):
        if self.head.data is None:
            return True
        return False

    def length(self):
        count = 0
        for node in self:
            count += 1
        return count 

    def add(self, item):
        newNode = Node(item)
        if self.isEmpty() == True:
            self.head = newNode
            self.tail = newNode
            newNode.next = self.head
        else:
            self.tail.next = newNode
            self.tail = newNode
            self.tail.next = self.head

    def prepend(self, item):
        newNode = Node(item)
        if self.isEmpty() == True:
            self.head = newNode
            self.tail = newNode
            newNode.next = self.head
        else:
            newNode.next = self.head
            self.tail.next = newNode
            self.head = newNode
    
    def delete(self, pos):
        item = self.head
        count = 1  
        if item is not None:
            if pos == 1:
                self.head = item.next 
                item = None
                return
        while item is not None:
            if pos == count:
                break
            prev = item 
            item = item.next
            count += 1
        if item is None:
            return 
        prev.next = item.next
        item = None
        
    def access(self, pos):
        count = 1 
        item = self.head
        if self.head is not None:
            if count == pos:
                print(item)
                return
        while item is not None:
            if pos == count:
                break 
            item = item.next 
            count += 1
        print("The node to be accessed is :", item.data)

    def display(self):    
        current = self.head;    
        if self.head is None:    
            print("List is empty");    
            return;    
        else:    
            print("Nodes of the circular linked list: ")    
            #Prints each node by incrementing pointer.    
            print(current.data)    
            while(current.next != self.head):    
                current = current.next    
                print(current.data)  

llist = CircularList()
llist.add(4)
llist.add(8)
llist.add(157)
llist.add(0)

llist.display()  
llist.delete(2) 
llist.prepend(1000) 
llist.display()  
llist.access(4)
