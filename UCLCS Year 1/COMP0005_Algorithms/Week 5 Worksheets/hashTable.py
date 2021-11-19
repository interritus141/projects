class HashTable():

    def __init__(self):
        INITIAL_CAPACITY = 50
        self.capacity = INITIAL_CAPACITY
        self.size = 0
        self.buckets = [None] * self.capacity

    class Node():

        def __init__(self, key, value):
            self.key = key
            self.value = value
            self.next = None

    def hashkey(self, key):
        hashsum = 0

        for index, c in enumerate(key):
            hashsum += (index + len(key)) ** ord(c)

            hashsum = hashsum % self.capacity
            # print(hashsum)
    
        return hashsum

    def insert(self, key, value):

        self.size += 1
        
        index = self.hashkey(key)
        # print(index)
        node = self.buckets[index]

        if node is None:
            self.buckets[index] = self.Node(key, value)
            return 

        prev = node 
        while node is not None:
            prev = node 
            node = node.next
        
        prev.next = Node(key, value)

    def find(self, key):

        index = self.hashkey(key)
        node = self.buckets[index]

        while node is not None and key != node.key:
            node = node.next

        if node is None:
            return None

        else:
            return node.value

    def remove(self, key):
        index = self.hashkey(key)
        node = self.buckets[index]

        prev = node 
        while node is not None and key != node.key:
            prev = node
            node = node.next

        if node is None:
            return None

        else:
            self -= 1
            value = node.value
            if prev is None:
                node = None
            else:
                prev.next = node.next
            return value
        

# Create a new HashTable

ht = HashTable()
# Create some data to be stored

phone_numbers = ["555-555-5555", "444-444-4444"]
# Insert our data under the key "phoneDirectory"

ht.insert("phoneDirectory", phone_numbers)
# Do whatever we need with the phone_numbers variable

phone_numbers = None
... # Later on...

# Retrieve the data we stored in the HashTable

phone_numbers = ht.find("phoneDirectory")
# find() retrieved our list object

print(phone_numbers)
# phone_numbers is now equal to ["555-555-5555", "444-444-4444"]