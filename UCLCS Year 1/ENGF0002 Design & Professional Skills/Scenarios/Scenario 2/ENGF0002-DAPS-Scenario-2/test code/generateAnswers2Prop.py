import createTable


# uncomment the print statements to get a rough idea of how the code runs

def read2PropQuestions(qnlist):
    equations = []

    # splitting the array to only include the propositions and the operators
    for i in range(2, len(qnlist)):
        equations.append(qnlist[i].split(" "))

    print(equations)
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

    # print(new)
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
    #inputAnswers(ans, row)


# def inputAnswers(answers, row):
#     # assigning the answers to the table
#     for i in range(2, 6):
#         table[row][i] = answers[i - 2]

#     print(table)


# def run():
#     equations = read2PropQuestions(table[0])

#     # iterating through the rows in the table
#     for i in range(1, len(table)):
#         newEqns = convert2PropQuestions(equations, table[i])
#         ans = solve2PropQuestions(newEqns, table[i])
#         inputAnswers(ans, i)


# table = createTable.create2PropTable()
# print(table)
# run()
