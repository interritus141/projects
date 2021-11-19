from PyQt5 import QtWidgets, QtCore
from PyQt5.QtWidgets import *
import sys
from UI import StartWidget, EasyWidget, IntermediateWidget, AdvancedWidget


def main():
    app = QtWidgets.QApplication(sys.argv)
    start_show = StartWidget()
    start_show.show()
    sys.exit(app.exec_())


if __name__ == '__main__':
    main()