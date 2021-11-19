{- DO NOT CHANGE MODULE NAME, if you do, the file will not load properly -}
module CourseworkRev where

import qualified Data.Set as HS (fromList, toList)
import Test.QuickCheck

{-
  Your task is to design a datatype that represents the mathematical concept of
  a (finite) set of elements (of the same type). We have provided you with an
  interface (do not change this!) but you will need to design the datatype and
  also support the required functions over sets. Any functions you write should
  maintain the following invariant: no duplication of set elements.

  There are lots of different ways to implement a set. The easiest is to use a
  list. Alternatively, one could use an algebraic data type, wrap a binary
  search tree, or even use a self-balancing binary search tree. Extra marks will
  be awarded for efficient implementations (a self-balancing tree will be more
  efficient than a linked list for example).

  You are **NOT** allowed to import anything from the standard library or other
  libraries. Your edit of this file should be completely self-contained.

  **DO NOT** change the type signatures of the functions below: if you do, we
  will not be able to test them and you will get 0% for that part. While sets
  are unordered collections, we have included the Ord constraint on some
  signatures: this is to make testing easier.

  You may write as many auxiliary functions as you need. Everything must be in
  this file.

  See the note **ON MARKING** at the end of the file.
-}

{-
   PART 1.
   You need to define a Set datatype.
-}

-- you **MUST** change this to your own data type. The declaration of Set a =
-- Int is just to allow you to load the file into ghci without an error, it
-- cannot be used to represent a set.
data Set a = Empty | a :-: (Set a) deriving (Show, Read)  

{-
   PART 2.
   If you do nothing else, you must get the toList, fromList and equality working. If they
   do not work properly, it is impossible to test your other functions, and you
   will fail the coursework!
-}

-- toList {2,1,4,3} => [1,2,3,4]
-- the output must be sorted.
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

-- fromList: do not forget to remove duplicates!
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

-- Make sure you satisfy this property. If it fails, then all of the functions
-- on Part 3 will also fail their tests
toFromListProp :: IO ()
toFromListProp =
  quickCheck
    ((\xs -> (HS.toList . HS.fromList $ xs) == (toList . fromList $ xs)) :: [Int] -> Bool)

-- test if two sets have the same elements (pointwise equivalent).
instance (Ord a) => Eq (Set a) where
  s1 == s2 = (toList s1 == toList s2)

-- you should be able to satisfy this property quite easily
eqProp :: IO ()
eqProp =
  quickCheck ((\xs -> (fromList . HS.toList . HS.fromList $ xs) == fromList xs) :: [Char] -> Bool)

{-
   PART 3. Your Set should contain the following functions. DO NOT CHANGE THE
   TYPE SIGNATURES.
-}

-- the empty set
empty :: Set a
empty = Empty

-- is it the empty set?
isNull :: Set a -> Bool
isNull Empty = True
isNull s1 = False

-- build a one element Set
singleton :: a -> Set a
singleton a = a :-: Empty

-- insert an element *x* of type *a* into Set *s*. Make sure there are no
-- duplicates!
insert :: (Ord a) => a -> Set a -> Set a
insert x s = fromList (toList (x :-: s)) 

-- join two Sets together be careful not to introduce duplicates.
union :: (Ord a) => Set a -> Set a -> Set a
union Empty Empty = Empty
union (x :-: xs) Empty = (x :-: xs)
union Empty (y :-: ys) = (y :-: ys)
union (x :-: xs) (y :-: ys) = fromList (x : y : toList (union xs ys))

-- return, as a Set, the common elements between two Sets
intersection :: (Ord a) => Set a -> Set a -> Set a
intersection s1 s2 = fromList [x | x <- (toList s1), y <- (toList s2), x == y]

-- all the elements in *s1* not in *s2*
-- {1,2,3,4} `difference` {3,4} => {1,2}
-- {} `difference` {0} => {}
difference :: (Ord a) => Set a -> Set a -> Set a
difference s1 s2 = fromList [x | x <- (toList s1), (elemof x (toList s2)) == False]

elemof :: (Eq a) => a -> [a] -> Bool
elemof a xs 
  | a `elem` xs = True
  | otherwise = False 

-- is element *x* in the Set s1?
member :: (Ord a) => a -> Set a -> Bool
member x s1 = x `elem` (toList s1)

-- how many elements are there in the Set?
cardinality :: Set a -> Int
cardinality Empty = 0
cardinality (x :-: xs) = length (x : makeList xs)

makeList :: Set a -> [a]
makeList Empty = []
makeList (x :-: xs) = x : makeList xs

-- apply a function to every element in the Set
setmap :: (Ord b) => (a -> b) -> Set a -> Set b
setmap f Empty = Empty
setmap f (x :-: xs) = fromList (toList (f x :-: setmap f xs))
 
-- right fold a Set using a function *f*
setfoldr :: (a -> b -> b) -> b -> Set a -> b
setfoldr f acc Empty = acc
setfoldr f acc (x :-: xs) = x `f` setfoldr f acc xs 

-- remove an element *x* from the set
-- return the set unaltered if *x* is not present
removeSet :: (Eq a) => a -> Set a -> Set a
removeSet x Empty = Empty
removeSet x (y :-: ys) 
  | (x == y) = removeSet x ys
  | otherwise = y :-: removeSet x ys

-- powerset of a set
-- powerset {1,2} => { {}, {1}, {2}, {1,2} }
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

{-
   ON MARKING:

   Be careful! This coursework will be marked using QuickCheck, against
   Haskell's own Data.Set implementation. This testing will be conducted
   automatically via a marking script that tests for equivalence between your
   output and Data.Set's output. There is no room for discussion, a failing test
   means that your function does not work properly: you do not know better than
   QuickCheck and Data.Set! Even one failing test means 0 marks for that
   function. Changing the interface by renaming functions, deleting functions,
   or changing the type of a function will cause the script to fail to load in
   the test harness. This requires manual adjustment by a TA: each manual
   adjustment will lose 10% from your score. If you do not want to/cannot
   implement a function, leave it as it is in the file (with undefined).

   Marks will be lost for too much similarity to the Data.Set implementation.

   Pass: creating the Set type and implementing toList and fromList is enough
   for a passing mark of 40%, as long as both toList and fromList satisfy the
   toFromListProp function.

   The maximum mark for those who use Haskell lists to represent a Set is 70%.
   To achieve a higher grade than is, one must write a more efficient
   implementation. 100% is reserved for those brave few who write their own
   self-balancing binary tree.
-}
