from matplotlib import pyplot as plt
from math import radians, sin, cos, atan2, sqrt, inf
import random
import timeit
import numpy

class Station():
    def __init__(self, index, X, Y):
        self.index = index
        self.X = X
        self.Y = Y

    def show(self):
        return self.index, self.X, self.Y


def dist(x1, y1, x2, y2):
    return sqrt((y2 - y1) ** 2 + (x2 - x1) ** 2)

def Stations(n, width= 900, height= 600, seed= 69):
    seed = random.randint(0,100)
    random.seed(seed* n)
    return frozenset(Station(index, random.randrange(width), random.randrange(height)) for index in range(n))

#generate all possible permutations of stations route and selects the shortest path
def bruteForce_tsp(stations):
    return shortest_tour(alltours(stations))

#selects the tour with the minimum tour length
def shortest_tour(tours):
    return min(tours, key= tour_length)

#calculates the length of the tour
def tour_length(tour):
    # print(tour)

    #place all stations and edges onto the graph for each tour 
    distSum = 0
    for i in range(1, len(tour)):

        station1 = tour[i-1]
        station2 = tour[i]
        distTo = dist(station1.X, station1.Y, station2.X, station2.Y)
        distSum += distTo

    #add dummy point whose distance to every point is 0 
    # for station in tour:
    #     graph.addWeightedEdge(Edge(station.index, -1, 0))

    return distSum

#returns a list of stations, each a permutation of the routes, but each starting with the same station
def alltours(stations):
    tours = []
    for station in stations:
        for rest in all_perms(list(stations - {station})):
            tours.append([station] + rest)
    return tours

def all_perms(elements):
    if len(elements) <=1:
        yield elements
    else:
        for perm in all_perms(elements[1:]):
            for i in range(len(elements)):
                # nb elements[0:1] works in both string and list contexts
                yield perm[:i] + elements[0:1] + perm[i:]
        
#returns the first station in the set
def first(stations):
    return next(iter(stations))


#nearest neighbour tsp algorithm
def nn_tsp(stations, start=None):
    if start is None:
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
    return min(stations, key=lambda c: dist(c.X, c.Y, station.X, station.Y))


""" repeated nn tsp algorithm """
def repeated_nn_tsp(stations, repetitions= 100):
    return shortest_tour(nn_tsp(stations, start) for start in sample(stations, repetitions))

def sample(population, k, seed= 42):
    if k is None or k > len(population):
        return population
    random.seed(len(population) * seed * k)
    return random.sample(population, k)

def repeat_10_nn_tsp(stations): return repeated_nn_tsp(stations, 10)
def repeat_100_nn_tsp(stations): return repeated_nn_tsp(stations, 100)
""" repeated nn tsp algorithm """

""" ALTERED NN TSP ALGORTIHM """
def reverse_if_better(tour, i, j):
    #if reversing the tour shortens it, then execute.
    A, B, C, D = tour[i-1], tour[i], tour[j-1], tour[j % len(tour)]
    distAB = dist(A.X, A.Y, B.X, B.Y)
    distCD = dist(C.X, C.Y, D.X, D.Y)
    distAC = dist(A.X, A.Y, C.X, C.Y)
    distBD = dist(B.X, B.Y, D.X, D.Y)

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
def improved_nn_tsp(stations):
    return alter_tour(nn_tsp(stations))


""" ALTERED N REPEATED NN TSP """
def repeated_improved_nn_tsp(stations, repetitions= 30):
    return shortest_tour(alter_tour(nn_tsp(stations, start)) 
                        for start in sample(stations, repetitions))


def plot_tour(tour):
    #plot the stations as nodes and the tour as lines between them
    # print(list(tour) + [tour[0]])
    start = tour[0]
    plot_lines(list(tour))
    plot_lines([start], 'rs')

def plot_lines(points, style= 'bo-'):
    #plot lines to connect a series of points
    x_coords = [point.X for point in points]
    y_coords = [point.Y for point in points]
    plt.plot(x_coords, y_coords, style)
    plt.axis('scaled')
    plt.axis('off')

#plot the graph for the corresponding algorithm and outputs the distance and time taken
def plot_tsp(algorithm, stations):
    time0 = timeit.default_timer()
    tour = algorithm(stations)
    time1 = timeit.default_timer()
    assert valid_tour(tour, stations)
    plot_tour(tour); plt.show()
    print("{} city tour with length {:.1f} in {:.3f} secs for {}".format(len(tour), tour_length(tour), time1 - time0, algorithm.__name__))

#checks that the tour is valid 
def valid_tour(tour, stations):
    return set(tour) == set(stations) and len(tour) == len(stations)


# stationSet = Stations(9)
# print(stationSet)
# for station in stationSet:
#     print(station.show())

# for station in alltours_tsp(stationSet):
#     print(station.show())

# print("tour length:", tour_length(alltours_tsp(stationSet)))

#test brute force algorithm
# for station in alltours_tsp(stationSet):
#     print(station.show())
# plot_tsp(alltours_tsp, stationSet)

#test nearest neighbour algorithm
# for station in nn_tsp(stationSet):
#     print(station.show())
# plot_tsp(nn_tsp, stationSet)


def test_tsp_algorithms(stations, algorithm, time_taken):
    timeSum = 0
    for n in range(10):
        time0 = timeit.default_timer()
        tour = algorithm(stations)
        time1 = timeit.default_timer()
        timet = time1 - time0
        timeSum += timet
    time_avg = timeSum / 10
    assert valid_tour(tour, stations)
    time_taken.append(time_avg)
    print("{} station tour with length {:.1f} in {:.3f} secs for {}".format(len(tour), tour_length(tour), time1 - time0, algorithm.__name__))

def plot_tsp_test_graphs(x_axis, y_axis, label, color):
    x = numpy.array(x_axis)
    y = numpy.array(y_axis)
    m, b = numpy.polyfit(x, y, 1)

    plt.plot(x, y, 'o', color= color, label= label)
    # plt.plot(x, m * x + b, color= color, label= label)

def run_tsp_test(numOfStations, algorithm, label, color, start=3):
    numOfStations_xaxis = [x for x in range(start, numOfStations)]
    time_taken = []
    for n in range(start, numOfStations):
        stations = Stations(n)
        test_tsp_algorithms(stations, algorithm, time_taken)

    plot_tsp_test_graphs(numOfStations_xaxis, time_taken, label, color)

def optimal_ratio(numOfStations, algorithm1, algorithm2):
    numOfStations_xaxis = [x for x in range(3, numOfStations)]
    ratios = []
    for n in range(3, numOfStations):
        tour1Sum, tour2Sum = 0, 0
        for i in range(50):
            stations = Stations(n)
            tour1 = algorithm1(stations)
            length1 = tour_length(tour1)
            tour1Sum += length1 
            tour2 = algorithm2(stations)
            length2 = tour_length(tour2)
            tour2Sum += length2

        length1 = tour1Sum / 50
        length2 = tour2Sum / 50
        ratio = tour2Sum / tour1Sum
        ratios.append(ratio)
        print("{} station tour with length {:.1f} for {} and length {:.1f} for {}".format(n, length1, algorithm1.__name__, length2, algorithm2.__name__))
    
    x = numpy.array(numOfStations_xaxis)
    y = numpy.array(ratios)
    m, b = numpy.polyfit(x, y, 1)

    plt.plot(x, y, 'o')
    plt.plot(x, m * x + b)

def run_tsp_final(numStations):
    if numStations <= 8:
        run_tsp_test(numStations, bruteForce_tsp, "Brute Force TSP", 'red')
    
    else:
        run_tsp_test(8, bruteForce_tsp, "brute_force_TSP", 'red')
        run_tsp_test(numStations, repeated_improved_nn_tsp, "repeated_improved_nn_tsp", 'green', 8)



# plot_tsp(bruteForce_tsp, Stations(8))
# plot_tsp(nn_tsp, Stations(8))
# plot_tsp(improved_nn_tsp, Stations(8))
# plot_tsp(repeated_improved_nn_tsp, Stations(200))
    
# run_tsp_test(11, bruteForce_tsp, "Brute Force TSP", 'red')
# run_tsp_test(31, nn_tsp, "nn_tsp", 'green')
# run_tsp_test(31, repeated_improved_nn_tsp, "repeated_improved_nn_tsp", 'blue')

run_tsp_final(61)

# optimal_ratio(30, nn_tsp, repeated_improved_nn_tsp)
# optimal_ratio(9, bruteForce_tsp, repeated_improved_nn_tsp)
plt.xlabel("Number of stations", loc= 'center')
plt.ylabel("Time taken (s)", loc= 'center')
# plt.ylabel("ratio of improved_nn_tsp length to nn_tsp length", loc= 'center')
# plt.ylabel("ratio of bruteForce_tsp length to repeated_altered_nn_tsp length", loc= 'center')
plt.legend(loc= 'best')
plt.show()

# stations = Stations(8)
# tour = bruteForce_tsp(stations)
# print(tour_length(tour))
