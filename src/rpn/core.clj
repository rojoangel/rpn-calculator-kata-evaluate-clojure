(ns rpn.core
  (:require [clojure.string :as str]))

(defn- add-number [tree n]
  (reverse (cons n (reverse tree))))

(defn- add-operation [tree op]
  (let [fn (resolve op)]
    (reverse
      (cons
        (cons fn (take-last 2 tree))
        (reverse
          (drop-last 2 tree))))))

(defn- add-symbol [tree symbol]
  (if (number? symbol)
    (add-number tree symbol)
    (add-operation tree symbol)))

(defn- walk [tree]
  (map eval tree))

(defn- format [tree]
  (str/join " " tree))

(defn calculate [operation]
  (let [tokens (str/split (str/trim operation) #"\s+")
        symbols (map read-string tokens)]
    (format (walk (reduce add-symbol nil symbols)))))
