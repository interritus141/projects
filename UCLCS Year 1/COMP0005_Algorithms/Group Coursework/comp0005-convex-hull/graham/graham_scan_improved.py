
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

def find_corners(points):
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

    return corners

def calculate_lines(corners):
    corners_len = len(corners)
    eq_list = []
    for i in range(corners_len):
        if corners[i] == corners[(i+1) % corners_len]:
            eq_list.append(None)
            continue
        if corners[i][0] - corners[(i+1) % corners_len][0] == 0:
            eq_list.append([corners[i][0]])
        else: 
            eq_list.append([(corners[i][1] - corners[(i+1) % corners_len][1])/(corners[i][0] - corners[(i+1) % corners_len][0]),\
                corners[i][1] - ((corners[i][1] - corners[(i+1) % corners_len][1])/(corners[i][0] - corners[(i+1) % corners_len][0])*corners[i][0])])

    return eq_list


def not_in_octagon(lines, point):
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

def cross_product(p1, p2, p3):
    return (p2[0] - p1[0]) * (p3[1] - p1[1]) - (p2[1] - p1[1]) * (p3[0] - p1[0])

def graham(start_point, points):
    stack = []
    stack.append(start_point)

    for point in points:
        while len(stack) > 1 and cross_product(stack[-2], stack[-1], point) <= 0:
            stack.pop()
        stack.append(point)
    return stack

def convex_hull_test_cos(points, show = False):  
    # Preprocessing
    corners = find_corners(points)
    lines = calculate_lines(corners)
    points = list(filter(lambda x: not_in_octagon(lines, x), points))

    # Standard Graham Scan
    start_point = find_start(points)
    points.remove(start_point)
    points.sort(key = lambda p: calculate_cos_dist(start_point, p))
    convex_hull = graham(start_point, points)

    return convex_hull


