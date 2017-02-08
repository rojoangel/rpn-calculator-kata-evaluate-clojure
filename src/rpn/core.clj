(ns rpn.core
  (:require [clojure.string :as str]))

(defn- store-number [tree n]
  (reverse (cons n (reverse tree))))

(defn- store-operation [tree op]
  (let [fn (resolve op)]
    (reverse
      (cons
        (cons fn (take-last 2 tree))
        (reverse
          (drop-last 2 tree))))))

(defn- store [tree symbol]
  (if (number? symbol)
    (store-number tree symbol)
    (store-operation tree symbol)))

(defn- walk [tree]
  (if (seq? (first tree))                                   ;; then, the first item is an operation
    (cons (eval (first tree)) (rest tree))
    tree))

(defn- format [tree]
  (str/join " " tree))

(defn calculate [operation]
  (let [tokens (str/split (str/trim operation) #"\s+")
        symbols (map read-string tokens)]
    (format (walk (reduce store nil symbols)))))
