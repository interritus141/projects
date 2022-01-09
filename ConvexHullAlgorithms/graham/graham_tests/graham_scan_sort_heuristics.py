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

# def calculate_angle_dist(start_point, point):
#     return (math.atan2(start_point[1] - point[1], start_point[0] - point[0]), calculate_dist(start_point, point))

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

def find_corners(points):
    # left = points[0]
    # right = points[0]
    # top = points[0]
    # bottom = points[0]
    # smallsum = points[0]
    # bigsum = points[0]
    # smalldiff = points[0]
    # bigdiff = points[0]
    corners = [points[0] for x in range(8)]

    for x, y in points:
        if x < corners[0][0]:
            corners[0] = [x, y]
        if y > corners[2][1]:
            corners[2] = [x, y]
        if x > corners[4][0]:
            corners[4] = [x, y]
        if y < corners[6][1]:
            corners[6] = [x, y]

        if x + y < corners[7][0] + corners[7][1]:
            corners[7] = [x, y]
        if x + y > corners[3][0] + corners[3][1]:
            corners[3] = [x, y]
        if -x + y < -corners[5][0] + corners[5][1]:
            corners[5] = [x, y]
        if -x + y > -corners[1][0] + corners[1][1]:
            corners[1] = [x, y]
        
    # for x, y in corners:
    #     if x < corners[0][0]:
    #         corners[0] = [x, y]
    #     if y > corners[2][1]:
    #         corners[2] = [x, y]
    #     if x > corners[4][0]:
    #         corners[4] = [x, y]
    #     if y < corners[6][1]:
    #         corners[6] = [x, y]

    #     if x + y < corners[7][0] + corners[7][1]:
    #         corners[7] = [x, y]
    #     if x + y > corners[3][0] + corners[3][1]:
    #         corners[3] = [x, y]
    #     if -x + y < -corners[5][0] + corners[5][1]:
    #         corners[5] = [x, y]
    #     if -x + y > -corners[1][0] + corners[1][1]:
    #         corners[1] = [x, y]

    
    # return (left, top, right, bottom)
    return corners

def calculate_lines(corners):
    corners_len = len(corners)
    eq_list = []
    for i in range(len(corners)):
        if corners[i] == corners[(i+1) % corners_len]:
            eq_list.append(None)
            continue
        if corners[i][0] - corners[(i+1) % corners_len][0] == 0:
            eq_list.append([corners[i][0]])
        else: 
            eq_list.append([(corners[i][1] - corners[(i+1) % corners_len][1])/(corners[i][0] - corners[(i+1) % corners_len][0]),\
                corners[i][1] - ((corners[i][1] - corners[(i+1) % corners_len][1])/(corners[i][0] - corners[(i+1) % corners_len][0])*corners[i][0])])

    return eq_list


def not_in_quad_lines(lines, point):
    for line in lines[:2]:
        if line == None:
            continue

        if len(line) == 1:
            if point[0] <= line[0]:
                return True
        else:
            if line[0] * point[0] + line[1] <= point[1]:
                return True

    for line in lines[2:4]:
        if line == None:
            continue

        if len(line) == 1:
            if point[0] >= line[0]:
                return True
        else:
            if line[0] * point[0] + line[1] <= point[1]:
                return True
                        
        
    for line in lines[4:6]:
        if line == None:
            continue

        if len(line) == 1:
            if point[0] >= line[0]:
                return True
        else:
            if line[0] * point[0] + line[1] >= point[1]:
                return True

    for line in lines[6:]:
        if line == None:
            continue

        if len(line) == 1:
            if point[0] <= line[0]:
                return True
        else:
            if line[0] * point[0] + line[1] >= point[1]:
                return True

    return False


def small_rectangle(corners):
    x_list = [x[0] for x in corners]
    y_list = [y[1] for y in corners]
    x_list.sort()
    y_list.sort()
    return (x_list[1], y_list[2], x_list[2], y_list[1])

def not_in_rectangle(small_rec, point):
    if point[0] >= small_rec[0] and point[0] <= small_rec[2] and point[1] >= small_rec[3] and point[1] <= small_rec[1]:
        return False
    else:
        return True

def not_in_quad(corners, point):
    corlen = len(corners)
    
    for i in range(corlen):
        # if (corners[(i + 1) % len(corners)][0] - corners[i][0])*(point[1] - corners[i][1]) - (corners[(i + 1) % len(corners)][1] - corners[i][1])*(point[0] - corners[i][0]) >= 0:
        if corners[i] == corners[(i + 1) % corlen]:
            continue
        if cross_product(corners[i], corners[(i + 1) % corlen], point) >= 0:
            return True   

    return False

def cross_product(p1, p2, p3):
    return (p2[0] - p1[0]) * (p3[1] - p1[1]) - (p2[1] - p1[1]) * (p3[0] - p1[0])

# def cross_product2(p1, p2, p3):
#     return (p2[0] - p1[0])*(p3[1] - p1[1]) - (p2[1] - p1[1])*(p3[0] - p1[0])

def graham(start_point, points):
    stack = []
    stack.append(start_point)

    for point in points:
        while len(stack) > 1 and cross_product(stack[-2], stack[-1], point) <= 0:
            stack.pop()
        stack.append(point)
    return stack


def convex_hull_test_cos(points, show = False):  
    # start1 = time.time()
    if show:
        root, canvas = pp.init_canvas()
    # pp.draw_points(canvas, points)
    # pp.show(root)
    if show:
        for point in points:
            pp.draw_point(canvas, point, "#FF0000")
        pp.show(root)

    # if len(points) > 3:
    corners = find_corners(points)

    lines = calculate_lines(corners)
    points = list(filter(lambda x: not_in_quad_lines(lines, x), points))



    if show:
        for i in range(len(corners)):
            pp.draw_point(canvas, corners[i], "#FFFF00")
            pp.draw_line(canvas, corners[i], corners[(i + 1) % len(corners)])
        pp.show(root)


    #     start = time.time()
    #     for point in points:
    #         not_in_quad(corners, point)
    #     print(time.time() - start)

    # newpoints = []
    # for point in points:
    #     if not not_in_quad(corners, point):
    #         newpoints.append(point)

    # small_rec = small_rectangle(corners)

    
    # print(len(points))
    # points = list(filter(lambda x: not_in_rectangle(small_rec, x), points))
    # print(len(points))
    # if show:
    #     for point in points:
    #         pp.draw_point(canvas, point, "#FF00FF")
    #     pp.show(root)

    # points = list(filter(lambda x: not_in_quad(corners, x), points))

    if show:
        for point in points:
            pp.draw_point(canvas, point, "#0000FF")
        pp.show(root)

    # print(len(points))
    # print(time.time() - start1)
    # print("---")
    # points = newpoints
    # points.sort(key = lambda x:[x[1],x[0]])
    start_point = find_start(points)
    points.remove(start_point)
    points.sort(key = lambda p: calculate_cos_dist(start_point, p))
    convex_hull = graham(start_point, points)

    if show:
        for i in range(len(convex_hull)):
            pp.draw_point(canvas, convex_hull[i], "#00FFFF")
            pp.draw_line(canvas, convex_hull[i], convex_hull[(i + 1) % len(convex_hull)])
        pp.show(root)

    return convex_hull


def convex_hull_test_cos2(points, show = False):  
    # start1 = time.time()
    if show:
        root, canvas = pp.init_canvas()
    # pp.draw_points(canvas, points)
    # pp.show(root)
    if show:
        for point in points:
            pp.draw_point(canvas, point, "#FF0000")
        pp.show(root)

    # if len(points) > 3:
    start_point = find_start(points)

    corners = find_corners(points)

    lines = calculate_lines(corners)
    points = list(filter(lambda x: not_in_quad_lines(lines, x), points))



    if show:
        for i in range(len(corners)):
            pp.draw_point(canvas, corners[i], "#FFFF00")
            pp.draw_line(canvas, corners[i], corners[(i + 1) % len(corners)])
        pp.show(root)


    #     start = time.time()
    #     for point in points:
    #         not_in_quad(corners, point)
    #     print(time.time() - start)

    # newpoints = []
    # for point in points:
    #     if not not_in_quad(corners, point):
    #         newpoints.append(point)

    # small_rec = small_rectangle(corners)

    
    # print(len(points))
    # points = list(filter(lambda x: not_in_rectangle(small_rec, x), points))
    # print(len(points))
    # if show:
    #     for point in points:
    #         pp.draw_point(canvas, point, "#FF00FF")
    #     pp.show(root)

    # points = list(filter(lambda x: not_in_quad(corners, x), points))

    if show:
        for point in points:
            pp.draw_point(canvas, point, "#0000FF")
        pp.show(root)

    # print(len(points))
    # print(time.time() - start1)
    # print("---")
    # points = newpoints
    # points.sort(key = lambda x:[x[1],x[0]])
    start_point = find_start(points)
    points.remove(start_point)
    points.sort(key = lambda p: calculate_cos_dist(start_point, p))
    convex_hull = graham(start_point, points)

    if show:
        for i in range(len(convex_hull)):
            pp.draw_point(canvas, convex_hull[i], "#00FFFF")
            pp.draw_line(canvas, convex_hull[i], convex_hull[(i + 1) % len(convex_hull)])
        pp.show(root)

    return convex_hull


# convex_hull_test_cos2(pg.generate_int_points(1000, 250, 250), True)
