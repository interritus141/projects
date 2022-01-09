from math import sin, cos, sqrt, atan2, radians, inf
from matplotlib import pyplot as plt
import timeit
import random
import numpy

class Edge():
    def __init__(self, v, w, weight):
        self.v = v
        self.w = w
        self.weight = weight

    def endPoint(self):
        return self.v
    
    def otherEndPoint(self, vertex):
        if vertex == self.v: return self.w
        else: return self.v
    
    #use to debug
    def showValues(self):
        return self.v, self.w, self.weight

class EdgeWeightedGraph():
    def __init__(self, V):
        self.V = V
        self.adj = []
        for _ in range(0, V):
            self.adj.append([])

    def addWeightedEdge(self, e):
        v = e.endPoint()
        w = e.otherEndPoint(v)
        self.adj[v].append(e)
        self.adj[w].append(e)

    def adja(self, v):
        return self.adj[v]

    def calculateEdgeWeight(self, lat1, lon1, lat2, lon2):
        earthRadius = 3958.76 # in miles

        # converting from deg to rad
        lat1, lon1, lat2, lon2 = radians(lat1), radians(lon1), radians(lat2), radians(lon2)
        dlon = lon2 - lon1
        dlat = lat2 - lat1

        # Haversine formula for calculating distance between 2 points on a sphere
        a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
        c = 2 * atan2(sqrt(a), sqrt(1 - a))

        distance = earthRadius * c
        
        return distance

class Node():
    def __init__(self, vertex, distTo):
        self.vertex = vertex
        self.distTo = distTo

    def show(self):
        return self.vertex, self.distTo

class BinaryHeapMinPQ():
    def __init__(self):
        self.size = [Node(-1, 0)]
        self.index = -1

    def length(self):
        return len(self.size) - 1

    def isEmpty(self):
        return len(self.size) == 1

    def contains(self, key):
        i = 0
        for node in self.size:
            if key == node.vertex:
                self.index = i 
                return True
            i += 1
        return False

    def insert(self, node):
        self.size.append(node)
        self.shiftUp(self.length())

    def shiftUp(self, index):

        while index > 0:
            # print(self.size[index//2])
            if self.size[index].distTo < self.size[index//2].distTo:
                self.size[index], self.size[index//2] = self.size[index//2], self.size[index]
            index = index // 2
            
    def extractMin(self):
        minValue = self.size[1]

        # debug code
        # for node in self.size:
            # print("before pop", node.vertex, node.distTo)
        # print(str(self.size[1].vertex) + " swap with " + str(self.size[self.length()].vertex))
       
        self.size[1], self.size[self.length()] = self.size[self.length()], self.size[1]
        e = self.size.pop()

        # debug code
        # for node in self.size:
            # print("after pop", node.vertex, node.distTo)

        # print(len(self.size))
        # print('\n')
        self.shiftDown()
        # print("popped: " + str(e.vertex))
        return e

    def shiftDown(self):
        index = 1
        while index * 2 <= self.length():

            i = self.minChild(index)
            if self.size[index].distTo > self.size[i].distTo:
                self.size[index], self.size[i] = self.size[i], self.size[index]
            
            # print(self.size)
            index = i

    def minChild(self, index):
        if index*2 == self.length():
            return index*2
        if self.size[index*2].distTo > self.size[index*2 + 1].distTo:
            return index*2 + 1
        else:
            return index*2

    def decreaseKey(self, key, value):
        self.size[self.index].distTo = value
        self.shiftUp(self.index)

class Array():
 
    def __init__(self):
        self.queue = []

    def insert(self, item):
        self.queue.append(item)

    def isEmpty(self):
        return len(self.queue) == 0

    def extractMin(self):
        i = 0
        minvalue = Node(-1, inf)
        for item in self.queue:
            if item.distTo < minvalue.distTo:
               minvalue = item
               index = i
            i += 1

        minNode = self.queue.pop(index)
        return minNode

    def contains(self, V):
        return True

    def decreaseKey(self, V, w):
        return None

# array = Array()
# array.insert(Node(0, 10))
# array.insert(Node(2, 8))
# array.insert(Node(3, 48))
# array.insert(Node(6, 69))
# array.insert(Node(7, 3))
# print(array.extractMin().show())


class DijkstraShortestPath():
    def __init__(self, G, s, queue):
        self.distTo = [inf for _ in range(0, G.V)]
        self.edgeTo = [False for _ in range(0, G.V)]
        self.distTo[s] = 0

        if queue == 0:
            self.pq = BinaryHeapMinPQ()
        else:
            self.pq = Array()

        self.pq.insert(Node(s, 0))

        while self.pq.isEmpty() is False:

            v = self.pq.extractMin().vertex
            
            for e in G.adj[v]:
                self.relax(e)

    def relax(self, e):
        v = e.endPoint()
        w = e.otherEndPoint(v)

        if self.distTo[w] > self.distTo[v] + e.weight:
            self.distTo[w] = self.distTo[v] + e.weight
            self.edgeTo[w] = e

            if self.pq.contains(w):
                self.pq.decreaseKey(w, self.distTo[w])
            else:
                self.pq.insert(Node(w, self.distTo[w]))

        if self.distTo[v] > self.distTo[w] + e.weight:
            self.distTo[v] = self.distTo[w] + e.weight
            self.edgeTo[v] = e

            if self.pq.contains(v):
                self.pq.decreaseKey(v, self.distTo[v])
            else:
                self.pq.insert(Node(v, self.distTo[v]))

def dijkstraBinHeapTest(numVertices, edges, time_taken=[]):

    for v in numVertices:
        g = EdgeWeightedGraph(v)
        for i in range(edges):

            weight = random.uniform(0, 100)
            a, b = random.randint(0, v-1), random.randint(0, v-1)
            g.addWeightedEdge(Edge(a, b, weight))

        timeSum = 0
        for n in range(100):
            n = random.randint(0, v-1)
            time0 = timeit.default_timer()
            search = DijkstraShortestPath(g, n, 0)
            time1 = timeit.default_timer()
            time_diff = time1 - time0
            timeSum += time_diff

        time_avg = timeSum / 10

        time_taken.append(time_avg)    
        print("[Dijkstra's Algorithm] --BINARY HEAP-- {} number of vertices took {:.4f} to complete".format(v, time_avg))
    x = numpy.array(numVertices)
    y = numpy.array(time_taken)
    m, b = numpy.polyfit(x, y, 1)

    plt.plot(x, y, 'o', color= 'blue')
    plt.plot(x, m * x + b, color= 'blue', label= "Binary Heap implementation")

def dijkstraArrayTest(numVertices, edges, time_taken=[]):
    for v in numVertices:   
        g = EdgeWeightedGraph(v)
        for i in range(edges):

            weight = random.uniform(0, 100)
            a, b = random.randint(0, v-1), random.randint(0, v-1)
            g.addWeightedEdge(Edge(a, b, weight))

        timeSum = 0
        for n in range(100):
            n = random.randint(0, v-1)
            time0 = timeit.default_timer()
            search = DijkstraShortestPath(g, n, 1)
            time1 = timeit.default_timer()
            time_diff = time1 - time0
            timeSum += time_diff

        time_avg = timeSum / 10

        time_taken.append(time_avg)
        print("[Dijkstra's Algorithm] --ARRAY-- {} number of vertices took {:.4f} to complete".format(v, time_avg))

    x = numpy.array(numVertices)
    y = numpy.array(time_taken)
    m, b = numpy.polyfit(x, y, 1)

    plt.plot(x, y, 'o', color= 'red')
    plt.plot(x, m * x + b, color= 'red', label= "Array implementation")

def dijkstraShowTestPlots(edges):
    
    numVertices = [y for y in range(1000000, 10000001, 1000000)]
    dijkstraArrayTest(numVertices, edges)
    dijkstraBinHeapTest(numVertices, edges)
    plt.xlabel("Number of vertices", loc= 'center')
    plt.ylabel("Time taken (s)", loc= 'center')
    plt.legend(loc= 'best')
    plt.show()


dijkstraShowTestPlots(1000)
