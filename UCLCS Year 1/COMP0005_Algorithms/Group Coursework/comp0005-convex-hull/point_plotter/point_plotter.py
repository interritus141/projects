# API for plotting points. You can use to visualize how does you algo work.

# In order to use it in a different file in the same directory, just import it by 'import point_plotter as pp'.
# Then you can refer to its functions e.g. pp.show(root)

# Example below, have fun!


from random import randint
import tkinter as tk

# this method return a list of tuples (x, y) of points coordinates. 
# You must precise the number of points and x_max and y_max (x_min = -x_max and y_min = -y_min) for their cords.
def generate_int_points(num_of_points, x_max, y_max):
    point_list = []
    while len(point_list) != num_of_points:
        point = (randint(-x_max,x_max), randint(-y_max,y_max))
        if point not in point_list:
            point_list.append(point)
    return point_list

def init_canvas():
    canvas_width = 596 # default window width
    canvas_height = 596 # default window height

    root = tk.Tk()
    canvas = tk.Canvas(root, 
            width = canvas_width, 
            height = canvas_height)

    canvas.pack()
    canvas.create_line(0, canvas_height/2, canvas_width, canvas_height/2)
    canvas.create_line(canvas_width/2, 0, canvas_width/2, canvas_height)
    root.update()

    return root, canvas

# draw all points from a list of point tuples (x,y)
def draw_points(canvas, point_list):
    for point in point_list:
        draw_point(canvas, point)
        
# You can use it to draw a custom, single point. point is a (x,y) tuple
def draw_point(canvas, point, color = "#FF0000"):
    x, y = rescale_points_cords(canvas, point)
    r = 10 # default point radius for drawing
    canvas.create_oval(x - r, y - r, x + r, y + r, fill = color) # Point with a radius
    canvas.create_oval(x, y, x, y) # Exact black point (dot)

# Rescaling point cords in order to show it properly
def rescale_points_cords(canvas, old_point): 
    x, y = old_point
    return (x + canvas.winfo_width()/2, canvas.winfo_height() - (y + canvas.winfo_height()/2))

def draw_line(canvas, point1, point2):
    x1, y1 = rescale_points_cords(canvas, point1)
    x2, y2 = rescale_points_cords(canvas, point2)
    canvas.create_line(x1,y1,x2,y2)


# In order to show you points just call this method
# In order to exit this window just press 'q' button
def show(root):

    def key(event):
        if event.char == 'q':
            root.quit()

    root.bind_all('<Key>', key)

    tk.mainloop()



'''
### EXAMPLE (just uncomment the whole block)
# Init
root, canvas = init_canvas()    

# you can generate some points and draw them
generated_points = generate_int_points(10, 200, 200)
draw_points(canvas, generated_points)

# You can draw a single point with a different color
draw_point(canvas, (100, 100), "#00FF00")

# You must call this method to show the canvas. You can quit it by pressing 'q' button, however, it won't be destroyed. You can still add something to it.
show(root)

# Lets add something to it.
draw_point(canvas, (-100, -100), "#0000FF")

# Call show again.
show(root)
'''