import CourseworkRev

a1 = (insert 21 (insert 20 (insert 2 (singleton 3))))
a2 = (insert 11 (insert 12345 (insert 1 (insert 5 (insert 2 (singleton 7))))))
a3 = (insert 7 (insert 11 (insert 1 (insert 11 (insert 12345 (insert 1 (insert 5 (insert 2 (singleton 7)))))))))

test1 = TestCase (assertEqual "empty test 1" False (member 3 empty))
test2 = TestCase (assertEqual "singleton test 1" True (member 3 (singleton 3)))
test3 = TestCase (assertEqual "singleton test 2" [7] (toList (singleton 7)))
test4 = TestCase (assertEqual "singleton test 3" False (member 3 (singleton 7)))
test5 = TestCase (assertEqual "member test 1" True (member 3 a1))
test6 = TestCase (assertEqual "member test 2" False (member 7 a1))
test7 = TestCase (assertEqual "member test 3" True (member 7 a3))
test8 = TestCase (assertEqual "toList test 1" [2,3,20,21] (toList a1))
test9 = TestCase (assertEqual "toList test 2" [1,2,5,7,11,12345] (toList a2))
test10 = TestCase (assertEqual "toList test 3" [1,2,5,7,11,12345] (toList a3))
test11 = TestCase (assertEqual "union test 1" [1,2,3,5,7,11,20,21,12345] (toList (union a1 a3)))
test12 = TestCase (assertEqual "union test 2" [1,2,5,7,11,12345] (toList (union a2 a2)))
test13 = TestCase (assertEqual "union test 3" [1,2,5,7,11,321,12345] (toList (union a2 (singleton 321))))
test14 = TestCase (assertEqual "intersection test 1" [1,2,5,7,11,12345] (toList (intersection a2 a3)))
test15 = TestCase (assertEqual "intersection test 2" [2] (toList (intersection a1 a2)))
test16 = TestCase (assertEqual "intersection test 3" [2,3,20,21] (toList (intersection a1 a1)))
test17 = TestCase (assertEqual "difference test 1" [] (toList (difference a2 a3)))
test18 = TestCase (assertEqual "difference test 2" [3,20,21] (toList (difference a1 a2)))
test19 = TestCase (assertEqual "difference test 3" [] (toList (difference a1 a1)))
test20 = TestCase (assertEqual "fromList test 1" True (member 3 (fromList [1,2,3,4,5])))
test21 = TestCase (assertEqual "fromList test 2" [1,2,3,4,7] (toList (union (fromList [1,2,3]) (fromList [3,4,7]))))
test22 = TestCase (assertEqual "fromList test 3" [7] (toList (intersection (fromList [1,2,7]) (fromList [7,8,9]))))
test23 = TestCase (assertEqual "isNull test 1" True (isNull (empty)))
test24 = TestCase (assertEqual "isNull test 2" True (isNull (difference a2 a3)))
test25 = TestCase (assertEqual "isNull test 3" False (isNull (intersection (fromList [1,2,7]) (fromList [7,8,9]))))
test26 = TestCase (assertEqual "insert test 1" True (member 20 (insert 20 (fromList [1,2,7]))))
test27 = TestCase (assertEqual "insert test 2" [7,8,9,42] (toList (insert 42 (fromList [7,8,9]))))
test28 = TestCase (assertEqual "insert test 3" False (isNull (insert 42 empty)))
test29 = TestCase (assertEqual "cardinality test 1" 0 (cardinality empty))
test30 = TestCase (assertEqual "cardinality test 2" 6 (cardinality a3))
test31 = TestCase (assertEqual "cardinality test 3" 7 (cardinality (fromList [1,2,3,17,18,19,42])))
test32 = TestCase (assertEqual "setmap test 1" [1] (toList (setmap (\_ -> 1) a1)))
test33 = TestCase (assertEqual "setmap test 2" [3,4,21,22] (toList (setmap (+1) a1)))
test34 = TestCase (assertEqual "setmap test 3" [True] (toList (setmap (\x -> member x a1) a1)))
test35 = TestCase (assertEqual "setfoldr test 1" 12371 (setfoldr (+) 0 a3))
test36 = TestCase (assertEqual "setfoldr test 2" 460 (setfoldr (\x y -> y + 10*x) 0 a1))
test37 = TestCase (assertEqual "setfoldr test 3" True (setfoldr (\x y -> y && member x a1) True a1))
test38 = TestCase (assertEqual "equality operator test 1" True (a2 == a3))
test39 = TestCase (assertEqual "equality operator test 2" False (a1 == a3))
test40 = TestCase (assertEqual "equality operator test 3" True (a2 == (fromList [7, 5, 12345, 2, 1, 11])))
test41 = TestCase (assertEqual "removeSet test 1" False (member 3 (removeSet 3 a1)))
test42 = TestCase (assertEqual "removeSet test 2" True (isNull (foldr (\x y -> removeSet x y) a1 [2,3,20,21])))
test43 = TestCase (assertEqual "removeSet test 2" [1,2,5,12345] (toList ((foldr (\x y -> removeSet x y) a2 [11,7]))))


tests = TestList [TestLabel "test1" test1,
                  TestLabel "test2" test2,
                  TestLabel "test3" test3,
                  TestLabel "test4" test4,
                  TestLabel "test5" test5,
                  TestLabel "test6" test6,
                  TestLabel "test7" test7,
                  TestLabel "test8" test8,
                  TestLabel "test9" test9,
                  TestLabel "test10" test10,
                  TestLabel "test10" test11,
                  TestLabel "test12" test12,
                  TestLabel "test13" test13,
                  TestLabel "test14" test14,
                  TestLabel "test15" test15,
                  TestLabel "test16" test16,
                  TestLabel "test17" test17,
                  TestLabel "test18" test18,
                  TestLabel "test19" test19,
                  TestLabel "test20" test20,
                  TestLabel "test21" test21,
                  TestLabel "test22" test22,
                  TestLabel "test23" test23,
                  TestLabel "test24" test24,
                  TestLabel "test25" test25,
                  TestLabel "test26" test26,
                  TestLabel "test27" test27,
                  TestLabel "test28" test28,
                  TestLabel "test29" test29,
                  TestLabel "test30" test30,
                  TestLabel "test31" test31,
                  TestLabel "test32" test32,
                  TestLabel "test33" test33,
                  TestLabel "test34" test34,
                  TestLabel "test35" test35,
                  TestLabel "test36" test36,
                  TestLabel "test37" test37,
                  TestLabel "test38" test38,
                  TestLabel "test39" test39,
                  TestLabel "test40" test40,
                  TestLabel "test41" test41,
                  TestLabel "test42" test42,
                  TestLabel "test43" test43]

run = runTestTT tests