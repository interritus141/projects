class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.left = None
        self.right = None

    def get(self, key):

        if self.key == key:
            return self.value
        elif self.key < key and self.right:
            return self.right.get(key)
        elif self.key > key and self.left:
            return self.left.get(key)
        else: 
            return None
    
    def put(self, key, value):
        
        if self.key == key:
            self.value = value

        elif self.key < key:
            if self.right is None:
                self.right = Node(key, value)
            else:
                self.right.put(key, value)

        elif key < self.key:
            if self.left is None:
                self.left = Node(key, value)
            else:  
                self.left.put(key, value)

BSTroot = Node('A', 100)
BSTroot.put('B', 50)
BSTroot.put('C', 69)
BSTroot.put('D', 42)
value = BSTroot.get('B')
print(value)