import timeit
import math
import random
import point_plotter as pp

def points_on_circle(num):
    xy = []
    for i in range (0,num):
        angle = random.uniform(0,math.pi*2)
        x_increase = 32767 * math.sin(angle)
        y_increase = 32767 * math.cos(angle)
        point = (x_increase ,y_increase)
        xy.append(point)
    return xy

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
#     generated_points = points_on_circle(i)
#     start_time = timeit.default_timer()
#     hull = jarvis_march(generated_points)
#     timetaken = timeit.default_timer() - start_time
#     print("Number of points =", i)
#     print("Convex hull points =", hull)
#     print("Time taken =", timetaken, "\n")

# generated_points = points_on_circle()
# root, canvas = pp.init_canvas()
# # generated_points = pp.generate_int_points(50, 200, 200)
# pp.draw_points(canvas, generated_points)
# hull = jarvis_march(generated_points)
# for i in range(len(hull)):
#     pp.draw_point(canvas, hull[i], color='green')
# pp.show(root)