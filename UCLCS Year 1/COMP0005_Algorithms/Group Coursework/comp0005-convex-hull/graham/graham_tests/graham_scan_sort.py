import point_plotter as pp 
import point_generator as pg
import math
import time


def find_start(points):
    start_point = points[0]
    for point in points:
        if point[1] < start_point[1]:
            start_point = point
        elif point[1] == start_point[1] and point[0] < start_point[0]:
            start_point = point
    return start_point

def calculate_cos_dist(start_point, point):
    hyp = calculate_dist(start_point, point)
    return (-((point[0] - start_point[0])/hyp), hyp)

def calculate_dist(start_point, point):
    return ((point[0] - start_point[0]) ** 2 + (point[1] - start_point[1]) ** 2) ** (1/2)

# def calculate_dist2(start_point, point):
#     return (point[0] - start_point[0]) ** 2 + (point[1] - start_point[1]) ** 2

# def calculate_cot_dist(start_point, point):
#     dist = calculate_dist2(start_point, point)
#     if point[1] - start_point[1] == 0:
#         if point[0] > 0:
#             return (float("inf"), dist)
#         else:
#             return (-1 * float("inf"), dist)

#     cot = (point[0] - start_point[0])/(point[1] - start_point[1])
#     return (cot, dist)


def cross_product(p1, p2, p3):
    return (p2[0] - p1[0])*(p3[1] - p1[1]) - (p2[1] - p1[1])*(p3[0] - p1[0])

def graham(start_point, points):
    stack = []
    stack.append(start_point)
    for point in points:
        while len(stack) > 1 and cross_product(stack[-2], stack[-1], point) <= 0:
            stack.pop()
        stack.append(point)
    return stack



def convex_hull(points, show = False):
    if show:
        root, canvas = pp.init_canvas()
        pp.draw_points(canvas, points)
        pp.show(root)
    
    start_point = find_start(points)
    points.remove(start_point)
    
    if show:
        pp.draw_point(canvas, start_point, "#00FF00")
        pp.show(root)

    # for point in points:
    #     print(calculate_cot_dist(start_point, point))
      
    points.sort(key = lambda p: calculate_cos_dist(start_point, p))
    convex_hull = graham(start_point, points)


    if show:    
        for i in range(len(convex_hull)):
            pp.draw_point(canvas, convex_hull[i], "#0000FF")
            pp.draw_line(canvas, convex_hull[i], convex_hull[(i + 1) % len(convex_hull)])
        pp.show(root)

# def convex_hull_test_cot(points, show = False):  
#     points.sort(key = lambda x:[x[1],x[0]])
#     start_point = points[0]
#     points.pop(0)
#     points.sort(key = lambda p: calculate_cot_dist(start_point, p), reverse = True)
#     convex_hull = graham(start_point, points)

def convex_hull_test_cos(points, show = False):
    start_point = find_start(points)
    points.remove(start_point)
    points.sort(key = lambda p: calculate_cos_dist(start_point, p))
    convex_hull = graham(start_point, points)

# convex_hull(pg.generate_int_points(10, 200, 200), True)

