def get(key):
    index = binSearch(keys, key, 0 ,len(keys)-1)
    return values[index]

def put(key, value):
    index = binSearch(keys, key, 0, len(keys)-1)
    if index is not None:
        value[index] = value

    else:
        insert(key, value)

def binSearch(arr, key, left, right):
    if left <= right:
        mid = left + (right - left)//2
        # mid = (left + right) //2
        if key == arr[mid]:
            return mid
        if key < arr[mid]:
            return binSearch(arr, key, left, mid-1)
        else:
            return binSearch(arr, key, mid+1, right)

def insert(key, value):
    keys.append(key)
    values.append(value)
    index = len(keys)-2
    print(key, keys[index])
    while key < keys[index] and index >= 0:
        keys[index+1] = keys[index]
        values[index+1] = values[index]
        index -= 1
    keys[index+1] = key
    values[index+1] = value

# arr = [1,4,6,7,9,12,17,29,100, 25, 60, 70]
keys = ['A', 'C', 'D', 'F', 'H', 'M', 'X']
values = [100, 50, 44, 60, 69, 123,90]
put('V', 69)
put('B', -9)
print(keys)
print(values)
# print(len(arr)//2)
# print(binSearch(arr, 0, 0, len(arr)-1))
