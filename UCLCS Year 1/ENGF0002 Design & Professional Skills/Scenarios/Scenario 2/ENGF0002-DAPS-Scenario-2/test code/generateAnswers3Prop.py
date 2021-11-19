import createTable
import generateAnswers2Prop


#reads table headers and obtains equations
def read3PropQuestions(qnList):
    equations = []
    for i in range(3, len(qnList)):
        equations.append(qnList[i].split(" "))
    # print(equations)
    return equations


#separates equation based on brackets
#e.g. '¬B ↔ ( ¬A → C )' becomes [['¬B', '↔'], ['¬A', '→', 'C']]
def separate3Prop(equation):
    separated = []
    firstHalf = []
    secondHalf = []
    
    if equation[0] == '(':
        for i in range(1,4):
            firstHalf.append(equation[i])
        secondHalf.append(equation[5])
        secondHalf.append(equation[6])
    else:
        firstHalf.append(equation[0])
        firstHalf.append(equation[1])
        for i in range(3,6):
            secondHalf.append(equation[i])
            
    separated.append(firstHalf)
    separated.append(secondHalf)
    
    return separated

#solves the equation in brackets
def solve2Prop(separated, values):
    
    if len(separated[0]) == 3:
        converted = generateAnswers2Prop.convert2PropQuestions([separated[0]],
                                                                values)
    else:
        converted = generateAnswers2Prop.convert2PropQuestions([separated[1]],
                                                                values)                                    

    return generateAnswers2Prop.solve2PropQuestions(converted, values)

#uses the result from the brackets to solve the whole equation
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

    return generateAnswers2Prop.solve2PropQuestions([converted], values)
        

#loops through all 3 prop. equations and returns the list answers
def solveEquations(equations, values):
    answers = []
    for equation in equations:
        separated = separate3Prop(equation)
        ans = solve3Prop(separated, values)
        answers.append(ans[0])
    return answers

#sets answers in empty table spaces
def inputAnswers(answers, row):
    for i in range(3, len(table[0])):
        table[row][i] = answers[i - 3]

def run():
    equations = read3PropQuestions(table[0])
    for i in range(1, len(table)):
        answers = solveEquations(equations, table[i])
        inputAnswers(answers, i)

    print(table)
    

    
table = createTable.create3PropTable(2)
print(table)
run()
            
