import random


def create2PropTable():
    table = create2PropHeaders()
    return create2PropValues(table)


def create2PropHeaders():
    # decided to include implication and biimplication for 2 propositions
    Operators = ['∧', '∨', '→', '↔']
    Propositions = ['A', 'B', '¬A', '¬B']
    table = []
    headers = []
    headers.append('A')
    headers.append('B')

    for i in range(len(Propositions)):
        # random sampling to prevent duplicates and generate distinct keys
        shuffleOps = random.sample(Operators, 4)
        shuffleProps = random.sample(Propositions, 4)

        # random equation generator
        equation = shuffleProps[0] + " " + shuffleOps[0] + " " + shuffleProps[1]
        headers.append(equation)

    table.append(headers)
    return table


def create2PropValues(table):
    values = [('0', '0'), ('0', '1'), ('1', '0'), ('1', '1')]

    for value in values:
        row = []
        row.append(value[0])
        row.append(value[1])

        for i in range(len(values)):
            row.append('')

        # print(row)
        table.append(row)
    #print(table)
    return table


def create3PropTable(diff):
    table = create3PropHeaders(diff)
    return create3PropValues(table, diff)


def create3PropHeaders(diff):
    Operators = ['∧', '∨', '→', '↔']
    Propositions = ['A', 'B', 'C', '¬A', '¬B', '¬C']
    Brackets = ['(', '']
    table = []
    headers = []
    headers.append('A')
    headers.append('B')
    headers.append('C')

    for i in range(diff):

        # random sampling to prevent duplicates and generate distinct keys
        shuffleOps = random.sample(Operators, 4)
        shuffleProps = random.sample(Propositions, 6)
        shuffleBrac = random.sample(Brackets, 2)

        # random equation generator
        equation = shuffleBrac[0] + " " + shuffleProps[0] + " " + shuffleOps[0] + " " + shuffleBrac[1] + " " + \
                   shuffleProps[1]

        # use brackets to handle 3 proposition equations
        if shuffleBrac[0] == '(':
            equation = shuffleBrac[0] + " " + shuffleProps[0] + " " + shuffleOps[0] + " " + \
                       shuffleProps[1] + ' )' + " " + shuffleOps[1] + " " + shuffleProps[2]

        elif shuffleBrac[0] == '':
            equation = shuffleProps[0] + " " + shuffleOps[0] + " " + shuffleBrac[1] + " " + \
                       shuffleProps[1] + " " + shuffleOps[1] + " " + shuffleProps[2] + ' )'

        headers.append(equation)

    table.append(headers)
    # print(table)
    return table


def create3PropValues(table, diff):
    values = [('0', '0', '0'), ('0', '0', '1'), ('0', '1', '0'),
              ('0', '1', '1'), ('1', '0', '0'), ('1', '0', '1'),
              ('1', '1', '0'), ('1', '1', '1')]

    for value in values:
        row = []

        for v in value:
            row.append(v)

        for i in range(diff):
            row.append('')

        table.append(row)

    #print(table)
    return table


def userInput(selection):
    if selection == 1:
        return create2PropTable()
    if selection == 2:
        return create3PropTable(2)
    if selection == 3:
        return create3PropTable(4)


# def run():
#     option = int(input("Select one: 1. Easy / 2. Medium / 3. Hard\n"))
#     print(userInput(option))
#
# run()