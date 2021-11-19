{-# LANGUAGE TemplateHaskell #-}
import CourseworkRev
import Test.QuickCheck
import Data.List as List

-- Convert back and forth from Set should be the same as sorting the list using std lib
prop_sorted :: [Int] -> Bool
prop_sorted xs = (List.sort $ List.nub xs) == (toList (fromList xs))

-- Most of these will be false
prop_ident_false :: [Int] -> [Int] -> Bool
prop_ident_false xs ys = ((List.sort $ List.nub xs) == (List.sort $ List.nub ys)) == ((fromList xs) == (fromList ys))

prop_ident_true :: [Int] -> Bool
prop_ident_true xs = (fromList $ List.sort xs) == (fromList xs)

prop_singleton :: Int -> Bool
prop_singleton x = (toList $ singleton x) == [x]

prop_insert :: [Int] -> Int -> Bool
prop_insert base x = fromList (x:base) == CourseworkRev.insert x (fromList base)

prop_union :: [Int] -> [Int] -> Bool
prop_union a b = (fromList $ a ++ b) == (CourseworkRev.union (fromList a) (fromList b))

-- A n B = {x | x ∈ A and x ∈ B}
test_intersection :: [Int] -> [Int] -> Bool
test_intersection a b =
  toList (CourseworkRev.intersection (fromList a) (fromList b))
  == (List.sort $ List.nub [x | x <- a, x `elem` b])

prop_intersection_a :: [Int] -> [Int] -> Bool
prop_intersection_a = test_intersection

prop_intersection_b :: [Int] -> [Int] -> Bool
prop_intersection_b a b = test_intersection (a ++ b) b

-- A = (A - B) u (A n B)
prop_difference_a :: [Int] -> [Int] -> Bool
prop_difference_a a b =
  CourseworkRev.union
  (CourseworkRev.difference (fromList a) (fromList b))
  (CourseworkRev.intersection (fromList a) (fromList b)) == fromList a

-- A - B = {x | x ∈ A and x ∉ B}
prop_difference_b :: [Int] -> [Int] -> Bool
prop_difference_b a b =
  toList (CourseworkRev.difference (fromList a) (fromList b)) ==
  (List.sort $ List.nub [x | x <- a, not (x `elem` b)])

-- A n ø = ø
prop_empty_intersect :: [Int] -> Bool
prop_empty_intersect xs = (CourseworkRev.intersection (fromList xs) (empty)) == empty

-- A n (B u C) = (A n B) u (A n C)
prop_union_intersection :: [Int] -> [Int] -> [Int] -> Bool
prop_union_intersection a b c = (CourseworkRev.intersection (fromList a) (CourseworkRev.union (fromList b) (fromList c))) == 
  (CourseworkRev.union (CourseworkRev.intersection (fromList a) (fromList b)) (CourseworkRev.intersection (fromList a) (fromList c)))

-- (A - B) u (B - A) = (A u B) - (A n B)
prop_symmetric_difference :: [Int] -> [Int] -> Bool
prop_symmetric_difference a b = (CourseworkRev.union (CourseworkRev.difference (fromList a) (fromList b)) (CourseworkRev.difference (fromList b) (fromList a))) == 
  (CourseworkRev.difference (CourseworkRev.union (fromList a) (fromList b)) (CourseworkRev.intersection (fromList a) (fromList b)))

-- | A u B | = | A | + | B | - | A n B |
prop_cardinality_c :: [Int] -> [Int] -> Bool
prop_cardinality_c a b = CourseworkRev.cardinality (CourseworkRev.union (fromList a) (fromList b)) ==
  (CourseworkRev.cardinality (fromList a)) + (CourseworkRev.cardinality (fromList b)) - CourseworkRev.cardinality (CourseworkRev.intersection (fromList a) (fromList b))


return []
runTests = quickCheckAll

-- prop_powerset :: [Int] -> Bool
-- prop_powerset xs = length (toList $ Coursework.powerSet (fromList xs)) == 2^(length $ List.nub xs)
