from random import randint

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def __eq__(self, other):
        return self.x == other.x and self.y and other.y


def generate_int_points(num_of_points, x_max, y_max):
    point_list = []
    while len(point_list) != num_of_points:
        point = [randint(-x_max,x_max), randint(-y_max,y_max)]
        if point not in point_list:
            point_list.append(point)
    return point_list

def generate_positive_int_points(num_of_points, x_max, y_max):
    point_set = set()
    while len(point_set) != num_of_points:
        point = (randint(0,x_max), randint(0,y_max))
        point_set.add(point)
    
    point_list = []
    for x,y in point_set:
        point_list.append([x,y])

    return list(point_list)

# a = generate_positive_int_points(100000, 32767, 32767)
# print(len(a))

# def generate_positive_int_class_points(num_of_points, x_max, y_max):
#     point_list = []
#     while len(point_list) != num_of_points:
#         point = [randint(0,x_max), randint(0,y_max)]
#         if point not in point_list:
#             point_list.append(point)

#     point_class_list = []
#     for x, y in point_list:
#         point_class_list.append(Point(x, y))

#     return point_class_list

