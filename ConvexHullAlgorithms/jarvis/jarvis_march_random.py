import point_plotter as pp
import point_generator as pg
import timeit

# invoke the Jarvis March algorithm, input the points, and output the subset of points 
# that lie on the convex hull

# Use python timeit library to measure the execution of the implementation. 

def crossProduct(a, b, c):
    x1 = a[0] - b[0]
    x2 = a[0] - c[0]
    y1 = a[1] - b[1]
    y2 = a[1] - c[1]
    if (y2*x1 - y1*x2 == 0):
        return 0
    elif (y2*x1 - y1*x2 < 0):
        # point c is clockwise to point b
        return 2
    else:
        # point c is anticlockwise to point b
        return 1 

def findLeftMost(listofPoints): 
    leftmost = 0
    for i in range(1,len(listofPoints)):
        if listofPoints[i][0] < listofPoints[leftmost][0]:
            leftmost = i
        elif listofPoints[i][0] == listofPoints[leftmost][0]:
            if listofPoints[i][1] > listofPoints[leftmost][1]:
                leftmost = i
    return listofPoints[leftmost]

def jarvis_march(S):
    pointOnHull = findLeftMost(S)
    hull = []
    i = 0
    while True:
        hull.append(pointOnHull)
        endpoint = S[0]
        for j in range (len(S)):
            if (endpoint == pointOnHull) or (crossProduct(hull[i], endpoint, S[j]) == 2):
                endpoint = S[j]
        i += 1
        pointOnHull = endpoint
        if endpoint == hull[0]:
            break
    return hull

# N = [100, 500, 1000, 5000, 10000, 15000, 20000]
# for i in N:
#     generated_points = pg.generate_positive_int_points(i, 32767, 32767)
#     start_time = timeit.default_timer()
#     hull = jarvis_march(generated_points)
#     timetaken = timeit.default_timer() - start_time
#     print("RANDOM")
#     print("Number of points =", i)
#     print("Convex hull points =", hull)
#     print("Time taken =", timetaken, "\n")
#     start_time = timeit.default_timer()
#     hull = jarvis_march(generated_points)
#     timetaken = timeit.default_timer() - start_time
#     print("RANDOM")
#     print("Number of points =", i)
#     print("Convex hull points =", hull)
#     print("Time taken =", timetaken, "\n")
 

# root, canvas = pp.init_canvas()
# generated_points = pp.generate_int_points(50, 200, 200)
# pp.draw_points(canvas, generated_points)
# hull = jarvis_march(generated_points)
# for i in range(len(hull)):
#     pp.draw_point(canvas, hull[i], color='green')
# pp.show(root)