def checkAnswer(userAnswer, correctAnswer):
    incorrectAnswers = []
    for i in range(len(correctAnswer)):
        for j in range(len(correctAnswer[i])):
            if userAnswer[i][j] != correctAnswer[i][j]:
                incorrectAnswers.append([i, j])
    return incorrectAnswers


# def test():
#     user = [[0, 1], [1, 0], [1, 1]]
#     correct = [[0, 1], [1, 1], [0, 1]]
#
#     print(checkAnswer(user, correct))
#
#
# test()
