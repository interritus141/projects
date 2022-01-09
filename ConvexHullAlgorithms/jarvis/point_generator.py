from random import randint

def generate_int_points(num_of_points, x_max, y_max):
    point_list = []
    while len(point_list) != num_of_points:
        point = (randint(-x_max,x_max), randint(-y_max,y_max))
        if point not in point_list:
            point_list.append(point)
    return point_list


def generate_positive_int_points(num_of_points, x_max, y_max):
    point_list = []
    while len(point_list) != num_of_points:
        point = (randint(0,x_max), randint(0,y_max))
        if point not in point_list:
            point_list.append(point)
    return point_list