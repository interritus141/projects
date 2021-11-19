id = []
for i in range(10):
    id.append(i)

def union(u,v):
    uid = id[u]
    vid = id[v]
    for i in range(10):
        if id[i] == uid:
            id[i] == vid

def find(u,v):
    return id[u] == id[v]

union(5,3)
print(find(5,4))