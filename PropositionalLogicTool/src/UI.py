from PyQt5 import QtWidgets, QtCore, QtGui
from PyQt5.QtCore import Qt
from PyQt5.QtGui import QBrush, QColor, QPalette, QFont
from PyQt5.QtWidgets import *
from Generate_problem import userInput
from Generate_Answer import run,run2_3
from Check_Answer import checkAnswer


class StartWidget(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):

        lable = QLabel('choose your difficulty',self)
        lable.move(90,50)
        lable.setStyleSheet(
                           "QLabel{color:rgb(255,105,180,250);font-size:50px;font-weight:bold;font-family:Roman times;}"
                           "QLabel:hover{color:rgb(100,100,100,120);}")

        easyb = QPushButton('Easy', self)
        easyb.setCheckable(True)
        easyb.move(40, 445)
        easyb.setStyleSheet(
            "QPushButton{color:rgb(0,206,209,250);font-size:60px;font-weight:bold;font-family:Roman times;}"
            "QPushButton:hover{color:rgb(0,255,255,120);}")
        easyb.setFlat(True)
        easyb.clicked.connect(self.goeasy)

        Intermediateb = QPushButton('Intermediate', self)
        Intermediateb.setCheckable(True)
        Intermediateb.move(180, 305)
        Intermediateb.setStyleSheet(
            "QPushButton{color:rgb(34,139,34,250);font-size:60px;font-weight:bold;font-family:Roman times;}"
            "QPushButton:hover{color:rgb(0,255,0,120);}")
        Intermediateb.setFlat(True)
        Intermediateb.clicked.connect(self.gointermediate)

        Advancedb = QPushButton('Advanced', self)
        Advancedb.setCheckable(True)
        Advancedb.move(500, 155)
        Advancedb.setStyleSheet(
            "QPushButton{color:rgb(255,228,181,250);font-size:60px;font-weight:bold;font-family:Roman times;}"
            "QPushButton:hover{color:rgb(255,165,0,120);}")
        Advancedb.setFlat(True)
        Advancedb.clicked.connect(self.goadvanced)

        self.setGeometry(300, 300, 780, 570)
        self.setWindowTitle('Practices')

    def goeasy(self):
        self.easy_list = userInput(1)
        self.close()
        self.easywidget = EasyWidget(self.easy_list)
        self.easywidget.show()

    def gointermediate(self):
        self.intermediate_list = userInput(2)
        self.close()
        self.intermediatewidget = IntermediateWidget(self.intermediate_list)
        self.intermediatewidget.show()

    def goadvanced(self):
        self.advanced_list = userInput(3)
        self.close()
        self.advanced = AdvancedWidget(self.advanced_list)
        self.advanced.show()


class EasyWidget(QWidget):
    def __init__(self, easy_list):
        super(EasyWidget, self).__init__()
        self.easylist = easy_list
        self.tableheader = self.easylist[0]
        self.tablecontent = self.easylist[1:]
        self.tablecol = len(self.tableheader)
        self.tablerow = len(self.tablecontent)
        self.uncor = False
        self.initUI()

    def initUI(self):
        self.setWindowTitle("Easy")
        layout = QVBoxLayout()
        tl_widget = QWidget()
        tl_widget_layout = QHBoxLayout()
        tl_widget.setLayout(tl_widget_layout)
        self.tablewidget = QTableWidget()
        self.tablewidget.verticalHeader().setVisible(False)
        lable_widget = QWidget()
        lable_widget_layout = QVBoxLayout()
        lable_widget.setLayout(lable_widget_layout)
        tl_widget_layout.addWidget(self.tablewidget)
        self.label1 = QLabel(self)
        self.label2 = QLabel(self)
        lable_widget_layout.addWidget(self.label1)
        lable_widget_layout.addWidget(self.label2)
        self.label1.setText('score')
        self.label1.setStyleSheet("font:20pt")
        self.label2.setStyleSheet("font:20pt")
        self.label1.setAlignment(Qt.AlignTop)
        self.label2.setAlignment(Qt.AlignTop)
        tl_widget_layout.addWidget(lable_widget)
        layout.addWidget(tl_widget)
        self.tablewidget.horizontalHeader().setStyleSheet(
            "QHeaderView::section{background-color:rgb(0, 230, 0);font:12pt '宋体';color: black;};")

        self.resize(151*self.tablecol, 71*len(self.easylist))
        self.tablewidget.setRowCount(self.tablerow)
        self.tablewidget.setColumnCount(self.tablecol)
        self.tablewidget.setHorizontalHeaderLabels(self.tableheader)
        #self.tablewidget.horizontalHeader().setFixedWidth()#
        self.tablewidget.setEditTriggers(QAbstractItemView.NoEditTriggers)
        for i in range(self.tablecol):
            if i < 2:
                self.tablewidget.horizontalHeader().resizeSection(i, 50)
            else:
                self.tablewidget.horizontalHeader().resizeSection(i, 150)
        #self.tablewidget.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
        #self.tablewidget.horizontalHeader().setSectionResizeMode(QHeaderView.ResizeToContents)
        #self.tablewidget.setSelectionBehavior(QAbstractItemView.QAbstractItemView.SelectItems0Selecting)
        for i in range(self.tablerow):
            for j in range(self.tablecol):
                if j<2:
                    self.tablewidget.setItem(i, j, QTableWidgetItem(str(self.tablecontent[i][j])))
                    self.tablewidget.item(i,j).setFont(QFont('Times', 13, QFont.Black))
                else:
                    comBox = QComboBox()
                    comBox.addItems(['0','1'])
                    comBox.setStyleSheet('QComboBox{margin:0px;font-size:26px;}')
                    self.tablewidget.setCellWidget(i, j, comBox)

        bt_widget = QWidget()
        bt_widget_layout = QHBoxLayout()
        bt_widget.setLayout(bt_widget_layout)

        backb = QPushButton('back', self)
        backb.setCheckable(True)
        backb.clicked.connect(self.gostart)
        bt_widget_layout.addWidget(backb)

        submitb = QPushButton('submit', self)
        submitb.setCheckable(True)
        submitb.clicked.connect(self.gosubmit)
        bt_widget_layout.addStretch(1)
        bt_widget_layout.addWidget(submitb)

        layout.addWidget(bt_widget)
        self.setLayout(layout)

    def gostart(self):
        self.close()
        self.startwidget = StartWidget()
        self.startwidget.show()

    def gosubmit(self):
        useranswer = []
        for i in range(self.tablerow):
            b = []
            for j in range(self.tablecol):
                if j<2:
                    b.append(self.tablewidget.item(i,j).text())
                else:
                    b.append(self.tablewidget.cellWidget(i, j).currentText())
            useranswer.append(b)

        rs = checkAnswer(useranswer,run(self.easylist)[1:])

        all = self.tablerow*(self.tablecol-2)
        cor = all - len(rs)
        self.label2.setText(str(round(cor/all*100.0,2)))

        for i in range(self.tablerow):
            for j in range(2, self.tablecol):
                if [i,j] not in rs:
                    self.tablewidget.cellWidget(i, j).setStyleSheet(
                        'QComboBox{margin:0px;font-size:26px;}')
                else:
                    self.tablewidget.cellWidget(i, j).setStyleSheet(
                        "background-color: #FF4500; selection-background-color: #B22222;font-size:26px;")


class IntermediateWidget(QWidget):
        def __init__(self, intermediate_list):
            super(IntermediateWidget, self).__init__()
            self.intermediatelist = intermediate_list
            self.tableheader = self.intermediatelist[0]
            self.tablecontent = self.intermediatelist[1:]
            self.tablecol = len(self.tableheader)
            self.tablerow = len(self.tablecontent)
            self.initUI()

        def initUI(self):
            self.setWindowTitle("Intermediate")
            layout = QVBoxLayout()
            tl_widget = QWidget()
            tl_widget_layout = QHBoxLayout()
            tl_widget.setLayout(tl_widget_layout)
            self.tablewidget = QTableWidget()
            self.tablewidget.verticalHeader().setVisible(False)
            lable_widget = QWidget()
            lable_widget_layout = QVBoxLayout()
            lable_widget.setLayout(lable_widget_layout)
            tl_widget_layout.addWidget(self.tablewidget)
            self.label1 = QLabel(self)
            self.label2 = QLabel(self)
            lable_widget_layout.addWidget(self.label1)
            lable_widget_layout.addWidget(self.label2)
            self.label1.setText('score')
            self.label1.setStyleSheet("font:20pt")
            self.label2.setStyleSheet("font:20pt")
            self.label1.setAlignment(Qt.AlignTop)
            self.label2.setAlignment(Qt.AlignTop)
            tl_widget_layout.addWidget(lable_widget)
            layout.addWidget(tl_widget)
            self.tablewidget.horizontalHeader().setStyleSheet(
                "QHeaderView::section{background-color:rgb(0, 230, 0);font:12pt '宋体';color: black;};")

            self.resize(172 * self.tablecol, 60 * len(self.intermediatelist))
            self.tablewidget.setRowCount(self.tablerow)
            self.tablewidget.setColumnCount(self.tablecol)
            self.tablewidget.setHorizontalHeaderLabels(self.tableheader)
            # self.tablewidget.horizontalHeader().setFixedWidth()#
            self.tablewidget.setEditTriggers(QAbstractItemView.NoEditTriggers)
            for i in range(self.tablecol):
                if i < 3:
                    self.tablewidget.horizontalHeader().resizeSection(i, 50)
                else:
                    self.tablewidget.horizontalHeader().resizeSection(i, 250)
            # self.tablewidget.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)
            # self.tablewidget.horizontalHeader().setSectionResizeMode(QHeaderView.ResizeToContents)
            # self.tablewidget.setSelectionBehavior(QAbstractItemView.QAbstractItemView.SelectItems0Selecting)
            for i in range(self.tablerow):
                for j in range(self.tablecol):
                    if j < 3:
                        self.tablewidget.setItem(i, j, QTableWidgetItem(str(self.tablecontent[i][j])))
                        self.tablewidget.item(i, j).setFont(QFont('Times', 13, QFont.Black))
                        #self.tablewidget.item(i, j).setBackground(QBrush(QColor(0, 230, 0)))
                    else:
                        comBox = QComboBox()
                        comBox.addItems(['0', '1'])
                        comBox.setStyleSheet('QComboBox{margin:0px;font-size:26px;}')
                        self.tablewidget.setCellWidget(i, j, comBox)

            bt_widget = QWidget()
            bt_widget_layout = QHBoxLayout()
            bt_widget.setLayout(bt_widget_layout)

            backb = QPushButton('back', self)
            backb.setCheckable(True)
            backb.clicked.connect(self.gostart)
            bt_widget_layout.addWidget(backb)

            submitb = QPushButton('submit', self)
            submitb.setCheckable(True)
            submitb.clicked.connect(self.gosubmit)
            bt_widget_layout.addStretch(1)
            bt_widget_layout.addWidget(submitb)

            layout.addWidget(bt_widget)
            self.setLayout(layout)

        def gostart(self):
            self.close()
            self.startwidget = StartWidget()
            self.startwidget.show()

        def gosubmit(self):
            useranswer = []
            for i in range(self.tablerow):
                b = []
                for j in range(self.tablecol):
                    if j < 3:
                        b.append(self.tablewidget.item(i, j).text())
                    else:
                        b.append(self.tablewidget.cellWidget(i, j).currentText())
                useranswer.append(b)
            rs = checkAnswer(useranswer, run2_3(self.intermediatelist)[1:])
            all = self.tablerow * (self.tablecol - 3)
            cor = all - len(rs)
            self.label2.setText(str(round(cor / all * 100.0,2)))

            for i in range(self.tablerow):
                for j in range(3, self.tablecol):
                    if [i, j] not in rs:
                        self.tablewidget.cellWidget(i, j).setStyleSheet(
                            'QComboBox{margin:1px;font-size:26px;}')
                    else:
                        self.tablewidget.cellWidget(i, j).setStyleSheet(
                            "background-color: #FF4500; selection-background-color: #B22222;font-size:26px;")


class AdvancedWidget(QWidget):
    def __init__(self, advanced_list):
        super(AdvancedWidget,self).__init__()
        self.advanced_list = advanced_list

        self.tableheader = self.advanced_list[0]
        self.tablecontent = self.advanced_list[1:]
        #print(self.tablecontent )
        self.tablecol = len(self.tableheader)
        self.tablerow = len(self.tablecontent)
        self.initUI()

    def initUI(self):
        self.setWindowTitle("Advanced")
        layout = QVBoxLayout()
        tl_widget = QWidget()
        tl_widget_layout = QHBoxLayout()
        tl_widget.setLayout(tl_widget_layout)
        self.tablewidget = QTableWidget()
        self.tablewidget.verticalHeader().setVisible(False)
        lable_widget = QWidget()
        lable_widget_layout = QVBoxLayout()
        lable_widget.setLayout(lable_widget_layout)
        tl_widget_layout.addWidget(self.tablewidget)
        self.label1 = QLabel(self)
        self.label2 = QLabel(self)
        lable_widget_layout.addWidget(self.label1)
        lable_widget_layout.addWidget(self.label2)
        self.label1.setText('score')
        self.label1.setStyleSheet("font:20pt")
        self.label2.setStyleSheet("font:20pt")
        self.label1.setAlignment(Qt.AlignTop)
        self.label2.setAlignment(Qt.AlignTop)
        tl_widget_layout.addWidget(lable_widget)
        layout.addWidget(tl_widget)
        #layout.addWidget(self.tablewidget)
        self.tablewidget.horizontalHeader().setStyleSheet(
            "QHeaderView::section{background-color:rgb(0, 230, 0);font:11pt '黑体';color: black;};")

        self.resize(177*self.tablecol, 60*len(self.advanced_list))
        self.tablewidget.setRowCount(self.tablerow)
        self.tablewidget.setColumnCount(self.tablecol)
        self.tablewidget.setHorizontalHeaderLabels(self.tableheader)
        for i in range(self.tablecol):
            if i <3:
                self.tablewidget.horizontalHeader().resizeSection(i, 50)
            else:
                self.tablewidget.horizontalHeader().resizeSection(i, 220)
        #self.tablewidget.horizontalHeader().setSectionResizeMode(1,QHeaderView.Stretch)
        #self.tablewidget.horizontalHeader().setSectionResizeMode( QHeaderView.ResizeToContents)
        for i in range(self.tablerow):
            for j in range(self.tablecol):
                if j < 3:
                    self.tablewidget.setItem(i,j,QTableWidgetItem(str(self.tablecontent[i][j])))
                    self.tablewidget.item(i, j).setFont(QFont('Times', 13, QFont.Black))
                else:
                    comBox = QComboBox()
                    comBox.addItems(['0', '1'])
                    comBox.setStyleSheet('QComboBox{margin:2px;font-size:26px;}')
                    self.tablewidget.setCellWidget(i, j, comBox)

        bt_widget = QWidget()
        bt_widget_layout = QHBoxLayout()
        bt_widget.setLayout(bt_widget_layout)

        backb = QPushButton('back', self)
        backb.setCheckable(True)
        backb.clicked.connect(self.gostart)
        bt_widget_layout.addWidget(backb)

        submitb = QPushButton('submit', self)
        submitb.setCheckable(True)
        submitb.clicked.connect(self.gosubmit)
        bt_widget_layout.addStretch(1)
        bt_widget_layout.addWidget(submitb)

        layout.addWidget(bt_widget)
        self.setLayout(layout)

    def gostart(self):
        self.close()
        self.startwidget = StartWidget()
        self.startwidget.show()

    def gosubmit(self):
        useranswer = []
        for i in range(self.tablerow):
            b = []
            for j in range(self.tablecol):
                if j < 3:
                    b.append(self.tablewidget.item(i, j).text())
                else:
                    b.append(self.tablewidget.cellWidget(i, j).currentText())
            useranswer.append(b)
        # print(useranswer)
        # print(run3(self.advanced_list)[1:])
        # print(checkAnswer(useranswer, run3(self.advanced_list)[1:]))
        rs = checkAnswer(useranswer, run2_3(self.advanced_list)[1:])
        all = self.tablerow * (self.tablecol - 3)
        cor = all - len(rs)
        self.label2.setText(str(round(cor / all * 100.0,2)))
        for i in range(self.tablerow):
            for j in range(3, self.tablecol):
                if [i, j] not in rs:
                    self.tablewidget.cellWidget(i, j).setStyleSheet(
                        'QComboBox{margin:1px;font-size:26px;}')
                else:
                    self.tablewidget.cellWidget(i, j).setStyleSheet(
                        "background-color: #FF4500; selection-background-color: #B22222;font-size:26px;")