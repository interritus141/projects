#import Generate_problem


# uncomment the print statements to get a rough idea of how the code runs
import Generate_problem


def read2PropQuestions(qnlist):
    equations = []

    # splitting the array to only include the propositions and the operators
    for i in range(2, len(qnlist)):
        equations.append(qnlist[i].split(" "))

    #print(equations)
    return equations


def convert2PropQuestions(equations, values):
    def A():
        return values[0]

    def B():
        return values[1]

    def notA():
        if values[0] == '0':
            return '1'
        else:
            return '0'

    def notB():
        if values[1] == '0':
            return '1'
        else:
            return '0'

    # use of switch statement to determine the values of the propositions
    switcher = {
        'A': A,
        'B': B,
        '¬A': notA,
        '¬B': notB,
    }

    # runs if there is a third column
    if values[2] != '':
        def C():
            return values[2]

        def notC():
            if values[2] == '0':
                return '1'
            else:
                return '0'

        switcher = {
            'A': A,
            'B': B,
            'C': C,
            '¬A': notA,
            '¬B': notB,
            '¬C': notC
        }

    new = []

    # converting propositions to values and appending to new list e.g. ['B', '↔', '¬B'] becomes ['0', '↔', '1']
    for equation in equations:
        numEqn = []
        func = switcher.get(equation[0], "nothing")
        numEqn.append(func())
        numEqn.append(equation[1])
        func = switcher.get(equation[2], "nothing")
        numEqn.append(func())
        new.append(numEqn)

    #print(new)
    return new


def solve2PropQuestions(convertedEqns, values):
    def orAns(A, B):
        if A == B and A == '0':
            return '0'
        else:
            return '1'

    def andAns(A, B):
        if A == '1' and B == '1':
            return '1'
        else:
            return '0'

    def implAns(A, B):
        if A == '1' and B == '0':
            return '0'
        else:
            return '1'

    def equivAns(A, B):
        if A == B:
            return '1'
        else:
            return '0'

    # switch statements to solve the equations with the respective operators
    switcher = {
        '∨': orAns,
        '∧': andAns,
        '→': implAns,
        '↔': equivAns
    }

    # adding answers to a new list e.g. [['1', '↔', '0'], ['1', '∧', '0'], ['1', '↔', '0'], ['0', '→', '1']]
    # becomes ['0', '0', '0', '1']
    ans = []
    for eqn in convertedEqns:
        func = switcher.get(eqn[1], "nothing")
        ans.append(func(eqn[0], eqn[2]))

    return ans

    # print(ans)
    # inputAnswers(ans, row)


def inputAnswers2Prop(answers, row,table):
    # assigning the answers to the table
    for i in range(2, 6):
        table[row][i] = answers[i - 2]


def run(table):
    equations = read2PropQuestions(table[0])

    # iterating through the rows in the table
    for i in range(1, len(table)):
        newEqns = convert2PropQuestions(equations, table[i])
        ans = solve2PropQuestions(newEqns, table[i])
        inputAnswers2Prop(ans, i,table)

    return table




# reads table headers and obtains equations
def read3PropQuestions(qnList):
    equations = []
    for i in range(3, len(qnList)):
        equations.append(qnList[i].split(" "))

    return equations


# separates equation based on brackets
# e.g. '¬B ↔ ( ¬A → C )' becomes [['¬B', '↔'], ['¬A', '→', 'C']]
def separate3Prop(equation):
    separated = []
    firstHalf = []
    secondHalf = []

    if equation[0] == '(':
        for i in range(1, 4):
            firstHalf.append(equation[i])
        secondHalf.append(equation[5])
        secondHalf.append(equation[6])
    else:
        firstHalf.append(equation[0])
        firstHalf.append(equation[1])
        for i in range(3, 6):
            secondHalf.append(equation[i])

    separated.append(firstHalf)
    separated.append(secondHalf)

    return separated


# solves the equation in brackets
def solve2Prop(separated, values):
    if len(separated[0]) == 3:
        converted = convert2PropQuestions([separated[0]],
                                                               values)
    else:
        converted = convert2PropQuestions([separated[1]],
                                                               values)

    return solve2PropQuestions(converted, values)


# uses the result from the brackets to solve the whole equation
def solve3Prop(separated, values):
    solved = solve2Prop(separated, values)

    def A():
        return values[0]

    def B():
        return values[1]

    def C():
        return values[2]

    def notA():
        if values[0] == '0':
            return '1'
        else:
            return '0'

    def notB():
        if values[1] == '0':
            return '1'
        else:
            return '0'

    def notC():
        if values[2] == '0':
            return '1'
        else:
            return '0'

    switcher = {
        'A': A,
        'B': B,
        'C': C,
        '¬A': notA,
        '¬B': notB,
        '¬C': notC
    }

    converted = []
    if len(separated[0]) == 2:
        func = switcher.get(separated[0][0], "nothing")
        converted.append(func())
        converted.append(separated[0][1])
        converted.append(solved[0])
    else:
        converted.append(solved[0])
        converted.append(separated[1][0])
        func = switcher.get(separated[1][1], "nothing")
        converted.append(func())

    return solve2PropQuestions([converted], values)


# loops through all 3 prop. equations and returns the list answers
def solveEquations(equations, values):
    answers = []
    for equation in equations:
        separated = separate3Prop(equation)
        ans = solve3Prop(separated, values)
        answers.append(ans[0])
    return answers


# sets answers in empty table spaces
def inputAnswers3Prop(answers, row,table):
    for i in range(3, len(table[0])):
        table[row][i] = answers[i - 3]


def run2_3(table):
    equations = read3PropQuestions(table[0])
    for i in range(1, len(table)):
        answers = solveEquations(equations, table[i])
        inputAnswers3Prop(answers, i,table)
    return table
    #print(table)


# table = Generate_problem.create3PropTable(3)
# print(run(table))
# run()