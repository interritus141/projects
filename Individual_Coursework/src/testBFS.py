from matplotlib import pyplot as plt
from math import radians, sin, cos, atan2, sqrt, inf
import random
import timeit
import numpy


class Graph():
    def __init__(self, V):
        # def Bag():
        #     return random.sample(range(V), V)
        self.V = V
        self.adj = []
        for _ in range(V):
            self.adj.append([])

    def addAdj(self, v, w):
        self.adj[v].append(w)
        self.adj[w].append(v)

    def adja(self, v):
        return self.adj[v]

class BFS():
    def __init__(self, G):
        self.distToSource = [-1 for _ in range(0, G.V)]
        self.edgeTo = [-1 for _ in range(0, G.V)]

    def bfs(self, G, s):
        queue = []
        queue.append(s)
        self.distToSource[s] = 0
        while queue != []:
            x = queue.pop(0)
            for w in G.adj[x]:
                if self.distToSource[w] == -1:
                    queue.append(w)
                    self.distToSource[w] = self.distToSource[x] + 1
                    self.edgeTo[w] = x

    def hasPathTo(self, v):
        return self.distToSource[v] != -1

    def pathLengthTo(self, v):
        return self.distToSource[v]

    def pathTo(self, v, s):
        if self.hasPathTo(v) == False:
            return None
        
        stack = []
        x = v
        while x != s:
            stack.append(x)
            x = self.edgeTo[x]
        stack.append(s)
        return stack


""" code for testing BFS """

def test_BFS_constant_vertices(vertices, numEdges, time_taken):

    g = Graph(vertices)

    #ensure all vertices are connected
    for i in range(vertices - 1):
        g.addAdj(i, i+1) 

    for num in numEdges:
        for n in range(num):
            x = random.randint(0, vertices-1)
            g.addAdj(n, x)

        search = BFS(g)
        
        timeSum = 0
        for n in range(100):
            n = random.randint(0, vertices-1)
            time0 = timeit.default_timer()
            search.bfs(g, n)
            time1 = timeit.default_timer()
            time_diff = time1 - time0
            timeSum += time_diff

        time_avg = timeSum / 10

        time_taken.append(time_avg)
        print("{} number of edges took {:.4f} to complete".format(num, time_avg))

    x = numpy.array(numEdges)
    y = numpy.array(time_taken)
    m, b = numpy.polyfit(x, y, 1)

    plt.plot(x, y, 'o', color= 'blue')
    plt.plot(x, m * x + b, color= 'blue', label= "constant Vertices")
    # plt.show()

def test_BFS_constant_edges(edges, numVertices, time_taken=[]):

    for v in numVertices:
        g = Graph(v)
        
        for i in range(edges):
            a, b = random.randint(0, v-1), random.randint(0, v-1)
            g.addAdj(a, b)

        search = BFS(g)
        
        timeSum = 0
        for n in range(100):
            n = random.randint(0, v-1)
            time0 = timeit.default_timer()
            search.bfs(g, n)
            time1 = timeit.default_timer()
            time_diff = time1 - time0
            timeSum += time_diff

        time_avg = timeSum / 10

        time_taken.append(time_avg)
        print("{} number of vertices took {:.4f} to complete".format(v, time_avg))

    x = numpy.array(numVertices)
    y = numpy.array(time_taken)
    m, b = numpy.polyfit(x, y, 1)

    plt.plot(x, y, 'o', color= 'green')
    plt.plot(x, m * x + b, color= 'green', label= "constant Edges")
    # plt.show()

def test_BFS():
    numEdges = [x for x in range(1000, 10001, 1000)]
    numVertices = [y for y in range(1000, 10001, 1000)]
    test_BFS_constant_vertices(max(numVertices), numEdges)
    test_BFS_constant_edges(max(numEdges), numVertices)

    plt.xlabel("Number of edges/vertices", loc= 'center')
    plt.ylabel("Time taken (s)", loc= 'center')
    plt.legend(loc= 'best')
    plt.show()

test_BFS()