(ns rpn.core-test
  (:use midje.sweet)
  (:use [rpn.core]))

(facts "about calculations in rpn"
       (fact "it konws how to calculate a number"
             (calculate "1") => "1"
             (calculate "9") => "9")

       (fact "it konws how to calculate two numbers"
             (calculate "1 9") => "1 9"
             (calculate "9 1") => "9 1")

       (fact "it konws how to operate two numbers"
             (calculate "1 9 +") => "10"
             (calculate "9 1 -") => "8"
             (calculate "3 4 *") => "12"
             (calculate "20 4 /") => "5")

       (fact "it works for the examples"
             (calculate "4 2 + 3 -") => "3"
             (calculate "3 5 8 * 7 + *") => "141"
             (calculate "7 2 - 3 4") => "5 3 4")

       (fact "it works when operations and numbers are combined"
             (calculate "9 1 - 9 4 2 + 3 - 3 5 8 * 7 + * 1") => "8 9 3 141 1"))