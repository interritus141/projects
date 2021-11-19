import csv
from math import sin, cos, sqrt, atan2, radians, inf
from matplotlib import pyplot as plt
import timeit
import random
import itertools

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
                #retrieving index from station objects
                if w.endPoint() == x:
                    w = w.otherEndPoint(x)

                else:
                    w = w.endPoint()

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

class Station():
    def __init__(self, name, index, lat, lon):
        self.name = name
        self.index = index
        self.lat = lat
        self.lon = lon

    def show(self):
        return self.name, self.index, self.lat, self.lon

class Node():
    def __init__(self, vertex, distTo):
        self.vertex = vertex
        self.distTo = distTo

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

class DijkstraShortestPath():
    def __init__(self, G, s):
        self.distTo = [inf for _ in range(0, G.V)]
        self.edgeTo = [False for _ in range(0, G.V)]
        self.distTo[s] = 0

        self.pq = BinaryHeapMinPQ()
        self.pq.insert(Node(s, 0))

        while self.pq.isEmpty() is False:

            v = self.pq.extractMin().vertex
            # print(v)
            # for node in self.pq.size:
            #     print(node.vertex, node.distTo)

            # debug code
            # for edge in G.adj[v]:
            #     print(edge.showValues())
            
            for e in G.adj[v]:
                # print(e.showValues())
                self.relax(e)
            # print('\n')

    def relax(self, e):
        v = e.endPoint()
        w = e.otherEndPoint(v)
        # print("vertex " + str(w) + " distance: " + str(self.distTo[w]))
        # print("vertex " + str(v) + " + weight distance = " + str(self.distTo[v] + e.weight))
        if self.distTo[w] > self.distTo[v] + e.weight:
            self.distTo[w] = self.distTo[v] + e.weight
            self.edgeTo[w] = e
            # print(self.pq.contains(w))
            if self.pq.contains(w):
                self.pq.decreaseKey(w, self.distTo[w])
            else:
                # print("inserting vertex " + str(w))
                self.pq.insert(Node(w, self.distTo[w]))
                # for node in self.pq.size:
                #     print(node.vertex, node.distTo)
                # print('\n')

        if self.distTo[v] > self.distTo[w] + e.weight:
            self.distTo[v] = self.distTo[w] + e.weight
            self.edgeTo[v] = e
            # print(self.pq.contains(w))
            if self.pq.contains(v):
                self.pq.decreaseKey(v, self.distTo[v])
            else:
                # print("inserting vertex " + str(w))
                self.pq.insert(Node(v, self.distTo[v]))

def dist(lat1, lon1, lat2, lon2):
        earthRadius = 3958.76 # in miles

        lat1, lon1, lat2, lon2 = float(lat1), float(lon1), float(lat2), float(lon2)
        # converting from deg to rad
        lat1, lon1, lat2, lon2 = radians(lat1), radians(lon1), radians(lat2), radians(lon2)
        dlon = lon2 - lon1
        dlat = lat2 - lat1

        # Haversine formula for calculating distance between 2 points on a sphere
        a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
        c = 2 * atan2(sqrt(a), sqrt(1 - a))

        distance = earthRadius * c
        
        return distance

""" BRUTE FORCE ALGORITHM """

#generate all possible permutations of stations route and selects the shortest path
def bruteForce_tsp(stations):
    return shortest_tour(alltours(stations))

#returns a list of stations, each a permutation of the routes, but each starting with the same station
def alltours(stations):
    return [list(rest) for rest in itertools.permutations(stations)]

""" BASE NN TSP ALGORITHM """
#returns the first station in the set
def first(stations):
    return next(iter(stations))

def shortest_tour(tours):
    return min(tours, key= tour_length)

def tour_length(tour):
    # print(tour)

    #place all stations and edges onto the graph for each tour 
    # graph = EdgeWeightedGraph(len(tour))
    distSum = 0
    for i in range(1, len(tour)):

        station1 = tour[i-1]
        station2 = tour[i]
        distTo = dist(station1.lat, station1.lon, station2.lat, station2.lon)
        distSum += distTo
        # graph.addWeightedEdge(Edge(station1.index, station2.index, distTo))

    #add dummy point whose distance to every point is 0 
    # for station in tour:
    #     graph.addWeightedEdge(Edge(station.index, -1, 0))

    return distSum

#nearest neighbour tsp algorithm
def nn_tsp(stations, start=None):
    if start is None:
        start = first(stations)
    start = first(stations)
    tour = [start]
    unvisited = set(stations - {start})
    while unvisited:
        s = nearest_neighbour(tour[-1], unvisited)
        tour.append(s)
        unvisited.remove(s)

    return tour

#finds the station that is the closest to the previous city by distance 
def nearest_neighbour(station, stations):
    minValue1 = inf
    for stat in stations:
        distance = dist(stat.lat, stat.lon, station.lat, station.lon)
        if distance < minValue1:
            minValue1 = distance
            closest = stat
    # mini = min(stations, key=lambda c: dist(c.lat, c.lon, station.lat, station.lon))
    return closest

""" REPEATED NN TSP ALGORITHM """
def repeated_nn_tsp(stations, repetitions= 100):
    return shortest_tour(nn_tsp(stations, start) for start in sample(stations, repetitions))

def sample(population, k, seed= 42):
    if k is None or k > len(population):
        return population
    random.seed(len(population) * seed * k)
    return random.sample(population, k)

def repeat_10_nn_tsp(stations): return repeated_nn_tsp(stations, 10)
def repeat_100_nn_tsp(stations): return repeated_nn_tsp(stations, 100)

""" ALTERED NN TSP ALGORTIHM """

#if reversing the tour shortens it, then execute.
def reverse_if_better(tour, i, j):
    A, B, C, D = tour[i-1], tour[i], tour[j-1], tour[j % len(tour)]
    distAB = dist(A.lat, A.lon, B.lat, B.lon)
    distCD = dist(C.lat, C.lon, D.lat, D.lon)
    distAC = dist(A.lat, A.lon, C.lat, C.lon)
    distBD = dist(B.lat, B.lon, D.lat, D.lon)

    if distAB + distCD > distAC + distBD:
        tour[i:j] = reversed(tour[i:j])

#alter tour by reversing segments
def alter_tour(tour):
    original_length = tour_length(tour)
    for (start, end) in all_segments(len(tour)):
        reverse_if_better(tour, start, end)
    
    if tour_length(tour) < original_length:
        return alter_tour(tour)
    return tour

#return pairs of indexes that form the tour segments
def all_segments(N):
    return [(start, start + length) 
            for length in range(N, 1, -1)
            for start in range(N - length + 1)]

#run altered nearest neightbour TSP algorithm, and altering the results
def altered_nn_tsp(stations):
    return alter_tour(nn_tsp(stations))


""" ALTERED N REPEATED NN TSP """
def repeated_altered_nn_tsp(stations, repetitions= 30):
    return shortest_tour(alter_tour(nn_tsp(stations, start)) 
                        for start in sample(stations, repetitions))

""" PLOTTING FUNCTIONS """
def plot_tour(tour):
    #plot the stations as nodes and the tour as lines between them
    # print(list(tour) + [tour[0]])
    plot_lines(list(tour))

def plot_lines(points, style= 'bo-'):
    #plot lines to connect a series of points
    x_coords, y_coords = [], []
    for point in points:
    
        x_coords.append(float(point.lat)) 
        y_coords.append(float(point.lon))
        
    # print(x_coords)
    # print(y_coords)
    plt.plot(x_coords, y_coords, style)
    plt.axis('scaled')
    plt.axis('off')

#plot the graph for the corresponding algorithm and outputs the distance and time taken
def plot_tsp(algorithm, stations):
    time0 = timeit.timeit()
    tour = algorithm(stations)
    time1 = timeit.timeit()
    assert valid_tour(tour, stations)
    plot_tour(tour); plt.show()
    print("{} city tour with length {:.1f} in {:.3f} secs for {}".format(len(tour), tour_length(tour), time1 - time0, algorithm.__name__))

#checks that the tour is valid 
def valid_tour(tour, stations):
    return set(tour) == set(stations) and len(tour) == len(stations)


class LondonRailwayMapper():
    
    def __init__(self):
        self.lines = {}
        self.stations = {}
        self.graph = None
    
    def loadStationsAndLines(self):
        try:
            with open('londonstations.csv', newline='') as stationsFile, open('londonrailwaylines.csv', newline='') as railwayLinesFile:

                stationsReader = csv.reader(stationsFile, delimiter= ',')
                linesReader = csv.reader(railwayLinesFile, delimiter= ',')

                # storing stations and coordinates in a hash table
                next(stationsReader)
                index = 0
                for station in stationsReader:
                    self.stations[station[0]] = Station(station[0], index, station[1], station[2])
                    index += 1
                    # print(station[0], station[1], station[2])
                
                # coord = stations.get("Warren Street")
                # print(coord.show())
                vertices = len(self.stations)
                self.graph = EdgeWeightedGraph(vertices)

                # storing lines and stations in a hash table
                next(linesReader)
                for row in linesReader:
                    
                    station1 = self.stations.get(row[1])
                    station2 = self.stations.get(row[2])
                    lat1, lon1, lat2, lon2 = float(station1.lat), float(station1.lon), float(station2.lat), float(station2.lon)
                    
                    # print(lat2, lon2)
                    dist = self.graph.calculateEdgeWeight(lat1, lon1, lat2, lon2) #in miles
                    # print(dist)

                    # storing the vertices using indexes of the stations
                    # edge = Edge(station1.index, station2.index, dist)
                    self.graph.addWeightedEdge(Edge(station1.index, station2.index, dist))

                    #check if line is in table. If not, append it.
                    if row[0] in self.lines:
                        if row[1] not in self.lines[row[0]]:
                            self.lines[row[0]].append(station1)

                        if row[2] not in self.lines[row[0]]:
                            self.lines[row[0]].append(station2)

                    else:
                        newline = []
                        newline.append(station1)
                        newline.append(station2)
                        self.lines[row[0]] = newline
                
            # for value in lines.values():
            #     for v in value:
            #         print(v.show())

            # adjlist = graph.adja(595)
            # for adj in adjlist:
            #     print(adj.showValues())
        except IOError as e:
            print(e)
        pass
    
    
    #shortest path without weights
    def minStops(self, fromS, toS):     
        numStops = -1

        #obtain index of station object from hashTable
        fromStation = self.stations.get(fromS).index
        toStation = self.stations.get(toS).index

        #create BFS object for shortest route without weights
        search = BFS(self.graph)
        search.bfs(self.graph, fromStation)
        # path = search.pathTo(toStation, fromStation)
        # path.reverse()
        # print(path)

        numStops = search.pathLengthTo(toStation)
        return numStops    
    
    
    #shortest path with weights
    def minDistance(self, fromS, toS):
        minDistance = -1.0

        #obtain index of station object from hashTable
        fromStation = self.stations.get(fromS).index
        toStation = self.stations.get(toS).index
        # print(fromStation)

        #initialise Dijkstra's algorithm
        shortestPath = DijkstraShortestPath(self.graph, fromStation)
        # print(shortestPath.distTo[fromStation])
        minDistance = shortestPath.distTo[toStation]
        
        return minDistance
    
    #travelling salesman problem
    def newRailwayLine(self, inputList):
        outputList = []
        inputStations = set()
        for station in inputList:
            station = self.stations.get(station)
            inputStations.add(station)
        
        if len(inputList) < 8:
            line = bruteForce_tsp(inputStations)
            plot_tsp(bruteForce_tsp, inputStations)

        else:
            line = repeated_altered_nn_tsp(inputStations)
            # line = repeated_altered_nn_tsp(inputStations)
            # plot_tsp(repeated_altered_nn_tsp, inputStations)

        #add edges between stations of new line
        # for station in line:
        #     print(station.show())
        for i in range(len(line) - 1):
            edgeDist = dist(line[i].lat, line[i].lon, line[i+1].lat, line[i+1].lon)
            self.graph.addWeightedEdge(Edge(line[i].index, line[i+1].index, edgeDist))
            outputList.append(line[i].name)

        outputList.append(line[-1].name)
        return outputList

g = LondonRailwayMapper()
g.loadStationsAndLines()
# print(g.minDistance("Enfield Town", "Farringdon"))

# test code for minStops

# fromList = ["Baker Street", "Epping", "Canonbury", "Vauxhall"]
# toList = ["North Wembley", "Belsize Park", "Balham", "Leytonstone"]

# for i in range(len(fromList)):
#     starttime = timeit.default_timer()
#     stops = g.minStops(fromList[i], toList[i])
#     endtime = timeit.default_timer()
#     print("\nExecution time minStops:", round(endtime-starttime,3))

#     starttime = timeit.default_timer()
#     dist = g.minStops(fromList[i], toList[i])
#     endtime = timeit.default_timer()
#     print("Execution time minDistance:", round(endtime-starttime,3))

#     print("From", fromList[i], "to", toList[i], "in", stops, "stops and", dist, "miles")  


stationsList = ["Queens Park", "Chigwell", "Moorgate", "Swiss Cottage", "Liverpool Street", "Highgate"]

starttime = timeit.default_timer()
newLine = g.newRailwayLine(stationsList)
endtime = timeit.default_timer()

# print("\n\nStation list", stationsList)
# print("New station line", newLine)
print("Total track length from", newLine[0], "to", newLine[len(newLine)-1], ":", g.minDistance(newLine[0], newLine[len(newLine)-1]), "miles")
print("Execution time newLine:", round(endtime-starttime,3))

#
# testing the newRailwayLine() API on a big list of stations  
#
stationsList = ["Abbey Road", "Barbican", "Bethnal Green", "Cambridge Heath", "Covent Garden", "Dollis Hill", "East Finchley", "Finchley Road and Frognal", "Great Portland Street", "Hackney Wick", "Isleworth", "Kentish Town West", "Leyton", "Marble Arch", "North Wembley", "Old Street", "Pimlico", "Queens Park", "Richmond", "Shepherds Bush", "Tottenham Hale", "Uxbridge", "Vauxhall", "Wapping"]

starttime = timeit.default_timer()
newLine = g.newRailwayLine(stationsList)
endtime = timeit.default_timer()

# print("\n\nStation list", stationsList)
# print("New station line", newLine)
print("Total track length from", newLine[0], "to", newLine[len(newLine)-1], ":", g.minDistance(newLine[0], newLine[len(newLine)-1]), "miles")
print("Execution time newLine:", round(endtime-starttime,3))