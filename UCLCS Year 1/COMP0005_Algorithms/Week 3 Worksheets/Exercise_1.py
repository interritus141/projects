import random

def insertionSort(list1):
    for i in range(0, len(list1)):
        key = list1[i]
        j = i - 1
        while j >= 0 and key < list1[j]:
            list1[j+1] = list1[j]
            j -= 1
        list1[j+1] = key

def mergeSort(arr, l, r):
    if l < r:
        mid = (l + r) // 2

        mergeSort(arr, l, mid)
        mergeSort(arr, mid+1, r)

        merge(arr, l, mid, r)

def merge(arr, l, mid, r):
    i = j = 0
    arrLeft = arr[l:mid+1]
    arrRight = arr[mid+1:r+1]
    k = l
    while i < len(arrLeft) and j < len(arrRight):
        if arrLeft[i] <= arrRight[j]:
            arr[k] = arrLeft[i]
            i += 1

        else:
            arr[k] = arrRight[j]
            j += 1
        k += 1

    while i < len(arrLeft):
        arr[k] = arrLeft[i]
        i += 1 
        k += 1   
        
    while j < len(arrRight):
        arr[k] = arrRight[j]
        j += 1
        k += 1


def quickSort(arr, start, end):
    if len(arr) == 1:
        return arr
    if start < end:
        j = partition(arr, start, end) 

        quickSort(arr, start, j-1)
        quickSort(arr, j+1, end)

def partition(arr, start, end):
    i = start - 1
    pivot = arr[end]

    for j in range(start, end):
        if arr[j] <= pivot:
            i += 1    
            arr[i], arr[j] = arr[j], arr[i]

    arr[i+1], arr[end] = arr[end], arr[i+1]
    return i+1

arr = [124,55,-5,0,4,1,13,10,9,-9,-100]
# mergeSort(list1, 0, len(list1))
# random.shuffle(arr)
print(arr)
quickSort(arr, 0, len(arr)-1)
print(arr)