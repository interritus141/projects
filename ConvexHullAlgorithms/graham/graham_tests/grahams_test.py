import point_generator as pg
import graham_scan_sort as gss
import graham_scan_sort_heuristics as gssh
import timeit
from matplotlib import pyplot as plt

from tqdm import tqdm
# time_graham = []
time_graham_sort_cos = []
# time_graham_sort_heur2 = []
time_graham_sort_heur = []

time_graham_web = []
elements = [100, 500, 1000, 5000, 10000, 15000, 20000, 50000]
# elements = [10]
# elements = [50000]
# elements = [100, 500, 1000, 5000, 10000, 15000, 20000, 30000, 50000, 100000, 200000, 500000]
# elements = [x*1000 for x in range(1, 20)]

for x in elements:
    print(x)
    time_internal1 = []
    time_internal2 = []
    # time_internal3 = []

    for _ in tqdm(range(100)):
        # start = time.time()
        points = pg.generate_positive_int_points(x, 32767, 32767)
        points_copy = points.copy()
        points_copy2 = points.copy()

        # points_copy2 = points.copy()
        # print(time.time() - start)

        # start_time = timeit.default_timer()
        # gs.convex_hull(points)
        # time_graham.append(timeit.default_timer() - start_time)
        start_time = timeit.default_timer()
        gss.convex_hull_test_cos(points)
        end_time = timeit.default_timer()
        # time_graham_sort_cos.append(end_time - start_time)
        time_internal1.append(end_time - start_time)

        # start_time = timeit.default_timer()
        # gssh.convex_hull_test_cos2(points_copy2)
        # end_time = timeit.default_timer()
        # time_internal3.append(end_time - start_time)


    # print(len(points))

    # time_internal = []
    # for i in range(10):
        start_time = timeit.default_timer()
        gssh.convex_hull_test_cos(points_copy)
        end_time = timeit.default_timer()
        # time_graham_sort_cos.append(end_time - start_time)
        time_internal2.append(end_time - start_time)


        # start_time = timeit.default_timer()
        # gssh2.convex_hull_test_cos2(points_copy2)
        # end_time = timeit.default_timer()
        # # time_graham_sort_cos.append(end_time - start_time)
        # time_internal3.append(end_time - start_time)
    # points = points_copy.copy()
    # time_graham_sort_heur.append(sum(time_internal)/len(time_internal))

    # print(points)
    # print(points2)
    time_graham_sort_cos.append(sum(time_internal1)/len(time_internal1))
    time_graham_sort_heur.append(sum(time_internal2)/len(time_internal2))
    # time_graham_sort_heur2.append(sum(time_internal3)/len(time_internal3))

    # start_time = timeit.default_timer()
    # gssh.convex_hull_test_cos(points_copy)
    # end_time = timeit.default_timer()
    # time_graham_sort_heur.append(end_time - start_time)


    # print(points)
    # print(points2)
    # start_time = timeit.default_timer()
    # gss.convex_hull_test_cot(points)
    # time_graham_sort_cot.append(timeit.default_timer() - start_time)
    # start_time = timeit.default_timer()
    # gsw.compute_convex_hull(points)
    # time_graham_web.append(timeit.default_timer() - start_time)

# plt.plot(elements, time_graham, '-o', label = "My_graham")
print(elements)
print(time_graham_sort_cos)
print(time_graham_sort_heur)
# print(time_graham_sort_heur2)


# plt.plot(elements, time_graham_sort_heur2, '-o', label = "Graham cross prod")
plt.plot(elements, time_graham_sort_cos, '-o', label = "Graham cos")
plt.plot(elements, time_graham_sort_heur, '-o', label = "Graham heur lines")
plt.legend()
plt.grid()
plt.show()


