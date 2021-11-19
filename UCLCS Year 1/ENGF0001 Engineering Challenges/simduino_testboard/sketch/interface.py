from tkinter import *

#port = serial.Serial('/tmp/simavr-uart0', 9600)
#while(port.isOpen()):
#    print(f'waiting input from {port.name}')
#    signal = (port.readline())
#    print(signal)
    
mygui = Tk()
mygui.title('Bioreactor')
mygui.geometry('1000x500')
label1 = Label(mygui,bg = 'white',width=50,height=20)
label1.pack(side=RIGHT)
button1 = Button(mygui, text='simulate', bd=1, width=10)
button1.place(x=300,y=300)
label2=Label(mygui, text="input temperature")
label2.place(x=200,y=100)
label3=Label(mygui, text="input stirring")
label3.place(x=200,y=150)
label4=Label(mygui, text="input pH")
label4.place(x=200,y=200)
entry1=Entry(mygui)
entry1.place(x=325,y=100)
entry2=Entry(mygui)
entry2.place(x=325,y=150)
entry3=Entry(mygui)
entry3.place(x=325,y=200)
mainloop()



