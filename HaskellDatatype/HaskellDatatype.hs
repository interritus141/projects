module CourseworkRev where

import qualified Data.Set as HS (fromList, toList)
import Test.QuickCheck

data Set a = Empty | a :-: (Set a) deriving (Show, Read)  

toList :: Ord a => Set a -> [a]
toList Empty = [] 
toList (x :-: xs) = isort (x : toList xs)

isort :: (Ord a) => [a] -> [a]
isort [] = []
isort (x:xs) = insert' x (isort xs)

insert' :: (Ord a) => a -> [a] -> [a]
insert' a [] = [a]
insert' a (x:xs)
    | a >= x = x : insert' a xs
    | otherwise = a : (x:xs)

fromList :: Ord a => [a] -> Set a
fromList [] = Empty
fromList (x:xs) 
  | (lookUp x xs == False) = x :-: fromList xs
  | otherwise = fromList xs

lookUp :: (Ord a) => a -> [a] -> Bool
lookUp a [] = False
lookUp a (x:xs) 
  | (a == x) = True
  | otherwise = lookUp a xs

toFromListProp :: IO ()
toFromListProp =
  quickCheck
    ((\xs -> (HS.toList . HS.fromList $ xs) == (toList . fromList $ xs)) :: [Int] -> Bool)

instance (Ord a) => Eq (Set a) where
  s1 == s2 = (toList s1 == toList s2)

eqProp :: IO ()
eqProp =
  quickCheck ((\xs -> (fromList . HS.toList . HS.fromList $ xs) == fromList xs) :: [Char] -> Bool)

empty :: Set a
empty = Empty

isNull :: Set a -> Bool
isNull Empty = True
isNull s1 = False

singleton :: a -> Set a
singleton a = a :-: Empty

insert :: (Ord a) => a -> Set a -> Set a
insert x s = fromList (toList (x :-: s)) 

union :: (Ord a) => Set a -> Set a -> Set a
union Empty Empty = Empty
union (x :-: xs) Empty = (x :-: xs)
union Empty (y :-: ys) = (y :-: ys)
union (x :-: xs) (y :-: ys) = fromList (x : y : toList (union xs ys))

intersection :: (Ord a) => Set a -> Set a -> Set a
intersection s1 s2 = fromList [x | x <- (toList s1), y <- (toList s2), x == y]

difference :: (Ord a) => Set a -> Set a -> Set a
difference s1 s2 = fromList [x | x <- (toList s1), (elemof x (toList s2)) == False]

elemof :: (Eq a) => a -> [a] -> Bool
elemof a xs 
  | a `elem` xs = True
  | otherwise = False 

member :: (Ord a) => a -> Set a -> Bool
member x s1 = x `elem` (toList s1)

cardinality :: Set a -> Int
cardinality Empty = 0
cardinality (x :-: xs) = length (x : makeList xs)

makeList :: Set a -> [a]
makeList Empty = []
makeList (x :-: xs) = x : makeList xs

setmap :: (Ord b) => (a -> b) -> Set a -> Set b
setmap f Empty = Empty
setmap f (x :-: xs) = fromList (toList (f x :-: setmap f xs))
 
setfoldr :: (a -> b -> b) -> b -> Set a -> b
setfoldr f acc Empty = acc
setfoldr f acc (x :-: xs) = x `f` setfoldr f acc xs 

removeSet :: (Eq a) => a -> Set a -> Set a
removeSet x Empty = Empty
removeSet x (y :-: ys) 
  | (x == y) = removeSet x ys
  | otherwise = y :-: removeSet x ys

powerSet :: Set a -> Set (Set a)
powerSet Empty = Empty :-: Empty
powerSet (x :-: xs) = fromList' (map (fromList') ([x:ps | ps <- (toList' (setmap' (toList') (powerSet xs)))] ++ (toList' (setmap' (toList') (powerSet xs)))))

fromList' :: [a] -> Set a
fromList' [] = Empty
fromList' (x:xs) = x :-: fromList' xs

toList' :: Set a -> [a]
toList' Empty = [] 
toList' (x :-: xs) = (x : toList' xs)

setmap':: (a -> b) -> Set a -> Set b
setmap' f Empty = Empty
setmap' f (x :-: xs) = f x :-: setmap' f xs
