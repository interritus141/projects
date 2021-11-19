import System.IO
import System.Exit
import Data.List

openTags = ["html", "head", "body", "title", "h1", "h2", "h3", "p", "ul", "li", "a", "div"]
closeTags = ["/html", "/head", "/body", "/title", "/h1", "/h2", "/h3", "/p", "/ul", "/li", "/a", "/div"]
singleTags = ["br", "hr"]

type Stack a = [String]

main = do
    withFile "file.html" ReadMode (\handle -> do
        contents <- hGetContents handle
        let linesOfFile = map convertNonTags (words (replC contents))
        let tags = map retrieveTagFirst (removeNonTags "" linesOfFile)
        checkhtml tags
        let stack = emptyStack
        let editedStack = mainloop stack tags
        evaluateStack editedStack)

mainloop :: [String] -> [String] -> [String]
mainloop [] [] = []
mainloop xs [] = xs
mainloop xs ys = mainloop (checkStack (checkTag xs ys) (head (removeElem (findLength xs (checkTag xs ys)) ys))) (tail (removeElem (findLength xs (checkTag xs ys)) ys))

checkhtml :: [String] -> IO ()
checkhtml [] = exitResourceMissing
checkhtml xs
    | (head xs == "html") && (last xs == "/html") = return ()
    | otherwise = exitResourceMissing
   
exitWithErrorMessage :: String -> ExitCode -> IO a
exitWithErrorMessage str e = hPutStrLn stderr str >> exitWith e

exitResourceMissing :: IO a
exitResourceMissing = exitWithErrorMessage "File does not start with <html> and end with </html>." (ExitFailure 2)

evaluateStack :: [String] -> IO ()
evaluateStack [] = print "parsing complete. No errors found."
evaluateStack (x:xs) = putStrLn ("Error found. <" ++ x ++ "> is an invalid tag, does not have a closing tag, or is nested incorrectly.")

findLength :: [String] -> [String] -> Int
findLength [] [] = 0
findLength xs ys = (length ys) - (length xs)

removeElem :: Int -> [String] -> [String]
removeElem _ [] = []
removeElem 0 ys = ys
removeElem i ys = removeElem (i-1) (tail ys)

checkStack :: [String] -> String -> [String]
checkStack [] [] = []
checkStack xs [] = xs
checkStack xs ys
    | (head xs) == (tail ys) = tail xs
    | otherwise = xs

replC :: String -> String
replC [] = []
replC (x:xs) 
    | x == '<' = ' ' : '<' : replSpace xs
    | otherwise = x : replC xs

replSpace :: String -> String
replSpace [] = []
replSpace (x:xs)
    | x == '>' = '>' : replC xs
    | x == ' ' = replC xs
    | otherwise = x : replSpace xs

convertNonTags :: String -> String
convertNonTags [] = []
convertNonTags (x:xs) 
    | x == '<' = '<' : replC xs
    | otherwise = ""

removeNonTags :: String -> [String] -> [String]
removeNonTags _ [] = []
removeNonTags y (x:xs)
    | x == y = removeNonTags y xs
    | otherwise = x : removeNonTags y xs

retrieveTagFirst :: String -> String
retrieveTagFirst [] = ""
retrieveTagFirst (x:xs)
    | x == '<' = retrieveTagNext xs
    | otherwise = retrieveTagFirst xs

retrieveTagNext :: String -> String
retrieveTagNext [] = ""
retrieveTagNext (x:xs)
    | x == '>' = retrieveTagFirst xs
    | otherwise = [x] ++ retrieveTagNext xs

checkTag :: Stack String -> [String] -> Stack String
checkTag [] [] = []
checkTag xs [] = xs
checkTag xs (y:ys)
    | y `elem` openTags = checkTag (y:xs) ys
    | y `elem` closeTags = xs
    | y `elem` singleTags = checkTag xs ys
    | otherwise = checkSpecialTag xs (y:ys)

checkSpecialTag :: [String] -> [String] -> [String]
checkSpecialTag [] (y:ys) = checkTag [y] ys
checkSpecialTag xs (y:ys) 
    | (take 3 y) == "div" = checkTag ("div":xs) ys
    | (take 6 y) == "ahref=" = checkTag ("a":xs) ys
    | otherwise = checkTag (y:xs) ys

emptyStack :: Stack String
emptyStack = []
