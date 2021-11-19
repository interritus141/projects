def flip(pancake, index):
    new_pancake_top = []
    pancake_top = pancake[:index-1]
    pancake_bottom = pancake[index-1:]
    for i in range(len(pancake_top)-1, 0):
        new_pancake_top.append(pancake_top[i])
    pancake = new_pancake_top + pancake_bottom
    return pancake

def findMax(pancake):
    max = 0
    for i in range(0, len(pancake)):
        if pancake[i] > max:
            max = i
    return max

# def pancake_sort(pancake):
    
