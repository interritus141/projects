import point_generator as pg
import jarvis_march_worstcase as jmw
import jarvis_march_random as jmr
from jarvis_march_worstcase import points_on_circle
import timeit
import random
import math
from matplotlib import pyplot as plt 

time_jarvis_random = []
time_jarvis_worst = []
N = [100, 500, 1000, 5000, 10000, 15000, 20000]
elements = [x*1000 for x in range(1,20)]

# def points_on_circle():
#     xy = []
#     # for i in range (len(N)):
#     for i in range (0,N[0]):
#         angle = random.uniform(0,math.pi*2)
#         x_increase = 200 * math.sin(angle)
#         y_increase = 200 * math.cos(angle)
#         point = (x_increase ,y_increase)
#         xy.append(point)
#     return xy

for x in N:
    # points = pg.generate_positive_int_points(x, 32767, 32767)
    # start_time = timeit.default_timer()
    # jmr.jarvis_march(points)
    # time_jarvis_random.append(timeit.default_timer() - start_time)
    points = points_on_circle(x)
    start_time = timeit.default_timer()
    jmw.jarvis_march(points)
    time_jarvis_worst.append(timeit.default_timer() - start_time)

# plot1 = plt.figure()
# plt.plot(elements, time_jarvis_random, '-o', label = "My_jarvis_random")
plt.xlabel('Number of points')
plt.ylabel('Time taken (in s)')

plt.plot(N, time_jarvis_worst, '-o', label = "My_jarvis_worst")
plt.legend()
plt.show()


